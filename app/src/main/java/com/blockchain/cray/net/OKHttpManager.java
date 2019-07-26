package com.blockchain.cray.net;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blockchain.cray.R;
import com.blockchain.cray.CrayApplication;
import com.blockchain.cray.ui.activity.LoginActivity;
import com.blockchain.cray.utils.ActivityCollector;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.cray.utils.JsonUtils;
import com.blockchain.cray.utils.RSAUtils;
import com.blockchain.cray.utils.SharedPreferencesUtil;
import com.blockchain.cray.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OKHttpManager {

    private static OKHttpManager sOKHttpManager = null;
    private final SharedPreferencesUtil sp;
    private static final int CONNECT_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 100;
    private static final int WRITE_TIMEOUT = 60;
    private final OkHttpClient mOkHttpClient;
    private  HttpLoggingInterceptor logInterceptor;
    private Context context;
    private String accessToken;
    private String AUTH_HEADER_KEY = "token";

    public static OKHttpManager getInstance() {
        if (sOKHttpManager == null) {
            synchronized (OKHttpManager.class){
                if (sOKHttpManager == null) {
                    sOKHttpManager = new OKHttpManager();
                }
            }
        }
        return sOKHttpManager;
    }

    public void setContext(Context context){
        this.context = context;
    }

    private OKHttpManager(){
        logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        sp = SharedPreferencesUtil.getInstance(CrayApplication.getApplication());
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .addNetworkInterceptor(logInterceptor)
                .addInterceptor(new TokenInterceptor())
                .build();
    }

    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.i("message =========>> \n", message);
        }
    }

    private synchronized String getNewAccessToken(){

//            FormBody.Builder builder =  new FormBody.Builder();
//            builder.add("client_id","skydevice-android");
//            builder.add("client_secret","skydevice-android-secret");
//            builder.add("grant_type","refresh_token");
//            builder.add("refresh_token", (String) sp.get(Constans.KEY_REFRESHTOKEN,""));
//
//            Request request = new Request.Builder()
//                    .url(Constans.BASEURL+Constans.URL_PARAM_REFRESHTOKEN)
//                    .post(builder.build())
//                    .addHeader("content-type","application/x-www-form-urlencoded;charset=utf-8")
//                    .build();
//
//            Call call = mOkHttpClient.newCall(request);
//            try {
//                Response response = call.execute();
//                if (response.isSuccessful()) {
//                    String body;
//                        body = response.body().string();
//                        if (body != null) {
//                            DebugLog.i("getAccessToken msg: "+body);
//                            JSONObject jb = new JSONObject(body);
//                            accessToken = jb.optString("access_token");
//                            String refresh_token = jb.optString("refresh_token");
//                            sp.put(Constans.KEY_ACCESSTOKEN, accessToken);
//                            sp.put(Constans.KEY_REFRESHTOKEN,refresh_token);
//                        }
//                }else {
//                    DebugLog.d("getAccessToken 刷新token失败，"+response.code()+" ,请重新登录!");
//                }
//
//            } catch (JSONException | IOException e) {
//                e.printStackTrace();
//            }
//
//        return accessToken;
        return "";
    }

    public String getNewAccessToken(String refrashToken){

        String resultRSA = getRSA();
        Map<String, Object> result = JsonUtils.toMap(resultRSA);
        Map<String, Object> rsaMap = (Map<String, Object>) result.get("attach");
        String modulus = (String) rsaMap.get("modulus");
        String publicExponent = (String) rsaMap.get("publicExponent");
        String id = (String) rsaMap.get("id");

        String data = null;
        try {
            data = encrRSA(refrashToken,modulus,publicExponent);
            Map<String, Object> meRSA = getRSAPublic();
            RSAPublicKey publicKey = (RSAPublicKey) meRSA.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) meRSA.get("private");

            String newAccessTokenResult = sendPost("user/get_token?id="+id+"&modulus="+publicKey.getModulus()+"&publicExponent="+publicKey.getPublicExponent(),data);
            return decryptRSA(newAccessTokenResult,""+privateKey.getModulus(),""+privateKey.getPrivateExponent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "AccessToken is null";
    }

    public  String sendPost(String url, String param) {
        return sendPost(url,param,"");
    }

    public  String sendPost(String url, String param,String token) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(Constans.BASEURL + url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("token", token);

            //  mMediaType = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 读取URL的响应
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
            int rc = 0;

            while ((rc = conn.getInputStream().read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果
            result = new String(in_b);

            //如果请求有涉及到RSA，但此次请求结果不需要RSA解密
            if ("false".equals(conn.getHeaderField("RequireRSADecrypt"))) {
                result = "NoRequireRSADecrypt"+result;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * RSA解密
     * @param data
     */
    public String decryptRSA(String data,String modulus, String exponent) throws Exception{
        RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, exponent);
        return RSAUtils.decryptByPrivateKey(data, priKey);
    }

    /**
     * 获取公钥
     * @return
     */
    public String getRSA() {

        String urlParam ="get_rsa";
        Request request = new Request.Builder()
                .url(Constans.BASEURL+urlParam)
                .get()
                .build();

        Call call = mOkHttpClient.newCall(request);
        try {
            Response response = call.execute();
            DebugLog.d("getRSA response code: "+response.code());
            String jsonResultRSA =response.body().string();
            return  jsonResultRSA == null?"RSA is not exist":jsonResultRSA;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "RSA is not exist";
    }

    /**
     * RSA加密
     * @param minwen
     * @param modulus
     * @param exponent
     * @return
     */
    public String encrRSA(String minwen,String modulus, String exponent) throws Exception {
        RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus,exponent);
        return RSAUtils.encryptByPublicKey(minwen,pubKey);
    }

    /**
     * 产生公钥和私钥
     * map.get("public", publicKey)获取公钥
     * map.get("private", privateKey)获取私钥
     * @return
     */
    public  Map<String, Object> getRSAPublic() throws Exception{
        return RSAUtils.getKeys();
    }

    public class TokenInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            int responseCode = response.code();
            DebugLog.d("TokenInterceptor response.code=" +responseCode);

            if (isTokenExpired(response)) {
                DebugLog.d("Token已过期，自动刷新Token,然后重新请求数据");
                //同步请求方式，获取最新的Token
                String newTokenResult = getNewAccessToken((String) sp.get(Constans.KEY_REFRESHTOKEN,""));
                if (null == newTokenResult) {
                    gotoLoginPage();
                    return response;
                }

                DebugLog.i("getNewAccessToken Result: "+newTokenResult);
                try {
                    JSONObject jb = new JSONObject(newTokenResult);
                    JSONObject attach = jb.optJSONObject("attach");
                    if (null == attach) {
                        gotoLoginPage();
                        return response;
                    }
                    DebugLog.i("getNewAccessToken attach: "+attach);
                    int code = attach.optInt("code");
                    if (code == Constans.REQUEST_SUCCESSFUL) {
                        String accessToken = attach.optString("accessToken");
                        String refreshToken = attach.optString("refreshToken");
                        sp.put(Constans.KEY_ACCESSTOKEN,accessToken);
                        sp.put(Constans.KEY_REFRESHTOKEN,refreshToken);
                    }else{
                        gotoLoginPage();
                       }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                //TODO 刷新Token接口
                //使用新的Token，创建新的请求
                Request newRequest = chain.request()
                        .newBuilder()
                        .header(AUTH_HEADER_KEY,  (String)sp.get(Constans.KEY_ACCESSTOKEN,""))
                        .build();
                //重新请求
                return chain.proceed(newRequest);
            }
            return response;
        }

        private boolean isTokenExpired(Response response) {
            if (response.code() == 401) {
                return true;
            }
            return false;
        }
    }

    private void gotoLoginPage(){
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                ActivityCollector.removeAllActivity();
                sp.put(Constans.KEY_ISLOGIN,false);
                context.startActivity(new Intent(context, LoginActivity.class));
                UIUtils.showToast(UIUtils.getString(R.string.please_login_again));
            }
        });
    }
}

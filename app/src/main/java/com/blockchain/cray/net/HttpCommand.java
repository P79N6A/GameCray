package com.blockchain.cray.net;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.blockchain.cray.CrayApplication;
import com.blockchain.cray.utils.Constans;
import com.blockchain.cray.utils.DebugLog;
import com.blockchain.cray.utils.JsonUtils;
import com.blockchain.cray.utils.RSAUtils;
import com.blockchain.cray.utils.SharedPreferencesUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpCommand {

    private static HttpCommand sIntance;
    private final OKHttpManager mOKHttpManager;
    private final OkHttpClient mOkHttpClient;
    private final MediaType mMediaType;
    private final SharedPreferencesUtil sp;
    private  String bearerHeaderValue;
    private String AUTH_HEADER_KEY = "token";
    private Context context;

    public static HttpCommand getInstance() {
        if (sIntance == null) {
            synchronized (HttpCommand.class){
                if (sIntance == null) {
                    sIntance = new HttpCommand();
                }
            }
        }
        return sIntance;
    }

    private HttpCommand(){

        sp = SharedPreferencesUtil.getInstance(CrayApplication.getApplication());
        mMediaType = MediaType.parse("application/json;charset=utf-8");//https://www.cnblogs.com/xiaozong/p/5732332.html
        mOKHttpManager = OKHttpManager.getInstance();
        mOkHttpClient = mOKHttpManager.getOkHttpClient();
    }

    public void setContext(Context context){
        this.context = context;
        mOKHttpManager.setContext(context);
    }

    /**
     * 获取验证码
     * @param phoneNumber
     * @param type
     * @param httpCmdCallback
     */
    public void getVerifyCode(String phoneNumber,int type,final HttpCmdCallback httpCmdCallback){
      //173 0263 2804
        JSONObject jb = new JSONObject();
        try {
            jb.put("mobile",phoneNumber);
            jb.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestParams = jb.toString();
        RequestBody requestBody = RequestBody.create(mMediaType, requestParams);
        Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_VERIFY_CODE)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                DebugLog.e("request failure ==>>"+e.getMessage());
                httpCmdCallback.onFail(e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                DebugLog.d("doRegister response code: "+response.code());
                httpCmdCallback.onSucceed(response);
            }
        });
    }

    /**
     * 修改登录密码
     * @param phoneNumber
     * @param psw
     * @param smsCode
     * @param httpCmdCallback
     */
    public void changePsw(String phoneNumber,String psw,String smsCode,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("account",phoneNumber);
            jb.put("password",psw);
            jb.put("smsCode",smsCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestParams = jb.toString();
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, requestParams);
        Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CHANGE_PSW)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
        });
    }

    /**
     * 修改支付密码
     * @param paymentPassword
     * @param smsCode
     * @param httpCmdCallback
     */
    public void changePaymentPsw(String paymentPassword,String smsCode,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("paymentPassword",paymentPassword);
            jb.put("smsCode",smsCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestParams = jb.toString();
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, requestParams);
        Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CHANGE_PAYMENT_PSW)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
        });
    }

    /***
     * 注册
     * @param account
     * @param password
     * @param superQrCode
     * @param paymentPassword
     * @param smsCode
     */
    public void register(final Activity activity,final String account, final String password,
                         final String superQrCode, final String paymentPassword, final String smsCode,final String userName,HttpCallback httpCallback){

//        //获取公钥
//        String urlParam ="get_rsa";
//        Request request = new Request.Builder()
//                .url(Constans.BASEURL+urlParam)
//                .get()
//                .build();
//
//        Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e){
//                DebugLog.d("request failure ==>>"+e.getMessage());
//            }
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response){
//                DebugLog.d("response code: "+response.code());
//                try {
//                    if (response.isSuccessful()){
//                        String jsonResultRSA =response.body().string();
//                        DebugLog.d("response str: "+jsonResultRSA);
//                        getEncryptionRSA( activity,jsonResultRSA, account, password, superQrCode, paymentPassword,smsCode);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        String resultRSA = mOKHttpManager.getRSA();
        getEncryptionRSA(activity,resultRSA, account, password, superQrCode, paymentPassword,smsCode,userName, httpCallback);
    }

    /**
     * 加密用户信息
     * @param activity
     * @param jsonResultRSA
     * @param account
     * @param password
     * @param superQrCode
     * @param paymentPassword
     * @param smsCode
     */
    public void getEncryptionRSA(Activity activity,String jsonResultRSA,String account,String password,
                                 String superQrCode,String paymentPassword,String smsCode, String userName,HttpCallback httpCallback){

        Map<String, Object> result = JsonUtils.toMap(jsonResultRSA);
        Map<String, Object> rsaMap = (Map<String, Object>) result.get("attach");
        String modulus = (String) rsaMap.get("modulus");//公钥
        String publicExponent = (String) rsaMap.get("publicExponent");//公钥
        String id = (String) rsaMap.get("id");

        try {
            JSONObject jb = new JSONObject();
            jb.put("account",account);
            jb.put("password",password);
            jb.put("superQrCode",superQrCode);
            jb.put("paymentPassword",paymentPassword);
            jb.put("smsCode",smsCode);
            jb.put("nickName",userName);

            //加密用户信息
//            String  encrData = encrRSA("{account:"+account+",password:"+password+",superQrCode:"+superQrCode+"," + "paymentPassword:"+paymentPassword+",smsCode:"+smsCode+"}",modulus,publicExponent);
            String  encrData = mOKHttpManager.encrRSA(jb.toString(),modulus,publicExponent);
            DebugLog.d("encrData: "+encrData);

            Map<String, Object> meRSA =  mOKHttpManager.getRSAPublic();
            RSAPublicKey publicKey = (RSAPublicKey) meRSA.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) meRSA.get("private");

            String resultLogin = mOKHttpManager.sendPost("user/register?id="+id+"&modulus="+publicKey.getModulus()+"&publicExponent="+publicKey.getPublicExponent(),encrData);
            DebugLog.i("resultLogin: "+resultLogin);
            String registerResult = mOKHttpManager.decryptRSA(resultLogin,""+privateKey.getModulus(),""+privateKey.getPrivateExponent());
            DebugLog.i("registerResult: "+registerResult);

            httpCallback.onResponse(registerResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void doRegister(String requestParams){

        RequestBody requestBody = RequestBody.create(mMediaType, requestParams);
        Request request = new Request.Builder().url(Constans.BASEURL+Constans.URL_PARAM_REGISTER).post(requestBody).build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                DebugLog.d("request failure ==>>"+e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                DebugLog.d("doRegister response code: "+response.code());
                if (response.isSuccessful()) {
                    try {
                        DebugLog.d("doRegister response body: "+response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 登录
     */
    public void login(String account,String password,HttpCallback httpCallback) throws Exception {

        String resultRSA = mOKHttpManager.getRSA();

        Map<String, Object> result = JsonUtils.toMap(resultRSA);
        Map<String, Object> rsaMap = (Map<String, Object>) result.get("attach");
        String modulus = (String) rsaMap.get("modulus");
        String publicExponent = (String) rsaMap.get("publicExponent");
        String id = (String) rsaMap.get("id");
        //加密用户登录信息
        String data = mOKHttpManager.encrRSA("{account:"+account+",password:"+password+"}",modulus,publicExponent);

        Map<String, Object> meRSA = mOKHttpManager.getRSAPublic();
        RSAPublicKey publicKey = (RSAPublicKey) meRSA.get("public");
        RSAPrivateKey privateKey = (RSAPrivateKey) meRSA.get("private");
        String resultLogin = mOKHttpManager.sendPost("user/login?id="+id+"&modulus="+publicKey.getModulus()+"&publicExponent="+publicKey.getPublicExponent(),data);
        DebugLog.d("login result: "+resultLogin);
        //解密
        String decrLoginUserInfo= decryptRSA(resultLogin, "" + privateKey.getModulus(), "" + privateKey.getPrivateExponent());
        DebugLog.d("login response: "+decrLoginUserInfo);
        httpCallback.onResponse(decrLoginUserInfo);
    }


    public void login(String phoneNumber,String psw,final HttpCmdCallback httpCmdCallback){

        String resultRSA = mOKHttpManager.getRSA();
        Map<String, Object> result = JsonUtils.toMap(resultRSA);
        Map<String, Object> rsaMap = (Map<String, Object>) result.get("attach");
        String modulus = (String) rsaMap.get("modulus");
        String publicExponent = (String) rsaMap.get("publicExponent");
        String id = (String) rsaMap.get("id");
        String data;
        try {
            //加密用户登录信息
            data = mOKHttpManager.encrRSA("{account:"+phoneNumber+",password:"+psw+"}",modulus,publicExponent);
            Map<String, Object> meRSA = mOKHttpManager.getRSAPublic();
            RSAPublicKey publicKey = (RSAPublicKey) meRSA.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) meRSA.get("private");
            String paramUrl = Constans.URL_PARAM_LOGIN+"?id="+id+"&modulus="+publicKey.getModulus()+"&publicExponent="+publicKey.getPublicExponent();
            RequestBody requestBody = RequestBody.create(mMediaType, data);
            final Request request = new Request.Builder().url(Constans.BASEURL+paramUrl).post(requestBody).build();

            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e){
                    DebugLog.d("request failure ==>>"+e.getMessage());
                    httpCmdCallback.onFail(e.getMessage());
                }
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response){
                    DebugLog.d("doRegister response code: "+response.code());
                    httpCmdCallback.onSucceed(response);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getdecryptData(String ecryptData) {

        Map<String, Object> meRSA = null;
        try {
            meRSA = HttpCommand.getInstance().getRSAPublic();
            DebugLog.i("meRSA: "+meRSA);
//            RSAPublicKey publicKey = (RSAPublicKey) meRSA.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) meRSA.get("private");
            DebugLog.i("privateKey: "+privateKey);
            String decryptJsonData = decryptRSA(ecryptData, "" + privateKey.getModulus(), "" + privateKey.getPrivateExponent());
            DebugLog.i("decryptJsonData: "+decryptJsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "decrypt Data error";
    }

    /**
     * 获取小龙虾数据
     * @param httpCmdCallback
     */
    public void loadCrayData(final HttpCmdCallback httpCmdCallback){

        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_ALL_CRAY)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .get()
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                DebugLog.d("request failure ==>>"+e.getMessage());
                httpCmdCallback.onFail(e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
        });
    }

    /**
     * 预约
     * @param id
     * @param httpCmdCallback
     */
    public void subscribeCray(int id,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("crayfishTypeId",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CRAYFISH_RESERVATION)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 抢购
     * @param id
     * @param httpCmdCallback
     */
    public void spikeCray(int id,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("crayfishSpeciesId",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CRAYFISH_SPIKE)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 获取领养列表：
     * @param status 领养中：1、 已领养：2、 取消/申诉：3
     * @param httpCmdCallback
     */
    public void getCrayfishAdoptionList(int status,String lastItemTime,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("status",status);
            jb.put("lastTime",lastItemTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CRAYFISH_ADOPTION_LIST)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 获取领养/转让详情
     * @param id
     * @param httpCmdCallback
     */
    public void getCrayfishOrderDetail(String id,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CRAYFISH_ADOPTION_DETAIL)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 获取转让列表
     * @param status 待转让：1，转让中：2，已完成：3，取消/申诉：4
     * @param httpCmdCallback
     */
    public void getCrayfishTransList(int status,String lastItemTime,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("status",status);
            jb.put("lastTime",lastItemTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CRAYFISH_TRANS_LIST)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 获取预约列表
     * @param page 分页
     * @param httpCmdCallback
     */
    public void getCrayfishReservationList(int page,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("page",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CRAYFISH_RESERVATION_LIST)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 获取我的团队列表
     * @param page 分页
     * @param httpCmdCallback
     */
    public void getCrayTeamList(int page,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("page",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CRAYFISH_TEAM_LIST)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 实名认证
     * @param name
     * @param idCard
     * @param httpCmdCallback
     */
    public void nameAuthentication(String name,String idCard,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("name",name);
            jb.put("idCard",idCard);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_NAME_AUTHENTICATION)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 卖家确认订单
     * @param id
     * @param httpCmdCallback
     */
    public void getCrayfishTransConfirm(String id,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CRAYFISH_TRANS_CONFIRM)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 卖家申诉
     * @param id 订单id
     * @param httpCmdCallback 接口回调
     */
    public void getCrayfishAppeal(String id,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CRAYFISH_APPEAL)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 买家支付
     * @param id 订单id
     * @param payCertUrl 支付凭证地址
     * @param httpCmdCallback 接口回调
     */
    public void getCrayfishTransPay(String id,String paymentPsw,String payCertUrl,final HttpCmdCallback httpCmdCallback){
        JSONObject jb = new JSONObject();
        try {
            jb.put("id",id);
            jb.put("payCertUrl",payCertUrl);
            jb.put("paymentPassword",paymentPsw);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_CRAYFISH_TRANS_PAY)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    /**
     * 获取用户信息
     * @param httpCmdCallback
     */
    public void loadUserInfo(final HttpCmdCallback httpCmdCallback){

        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_USER_INFO)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .get()
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                DebugLog.d("request failure ==>>"+e.getMessage());
                httpCmdCallback.onFail(e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
        });
    }

    /**
     * 更新用户信息
     * @param httpCmdCallback
     */
    public void updateUserInfo(String nickName,String weixinCodeUrl,String weixinAccount,String weixinName,
                               String zhifubaoAccount, String zhifubaoName,String zhifubaoCodeUrl,
                               String yinlianAccount,String yinlianName,String yinlianBankName,final HttpCmdCallback httpCmdCallback){

        JSONObject jb = new JSONObject();
        try {
            if (nickName != null) {
                jb.put("nickName",nickName);
            }
            if (weixinCodeUrl != null) {
                jb.put("weixinCodeUrl",weixinCodeUrl);
            }
            if (weixinAccount != null) {
                jb.put("weixinAccount",weixinAccount);
            }
            if (weixinName != null) {
                jb.put("weixinName",weixinName);
            }
            if (zhifubaoAccount != null) {
                jb.put("zhifubaoAccount",zhifubaoAccount);
            }
            if (zhifubaoName != null) {
                jb.put("zhifubaoName",zhifubaoName);
            }
            if (zhifubaoCodeUrl != null) {
                jb.put("zhifubaoCodeUrl",zhifubaoCodeUrl);
            }
            if (yinlianAccount != null) {
                jb.put("yinlianAccount",yinlianAccount);
            }
            if (yinlianName != null) {
                jb.put("yinlianName",yinlianName);
            }

            if (yinlianBankName != null) {
                jb.put("yinlianBankName",yinlianBankName);
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        DebugLog.i("accessToken: "+bearerHeaderValue);
        RequestBody requestBody = RequestBody.create(mMediaType, jb.toString());
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_USER_UPDATE)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                DebugLog.d("request failure ==>>"+e.getMessage());
                httpCmdCallback.onFail(e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
        });
    }

    /**
     * 退出登录
     * @param httpCmdCallback
     */
    public void logout(final HttpCmdCallback httpCmdCallback){

        bearerHeaderValue = (String) sp.get(Constans.KEY_ACCESSTOKEN,"");
        RequestBody requestBody = RequestBody.create(mMediaType, "");
        DebugLog.i("accessToken: "+bearerHeaderValue);
        final Request request = new Request.Builder()
                .url(Constans.BASEURL+Constans.URL_PARAM_LOGOUT)
                .addHeader(AUTH_HEADER_KEY,bearerHeaderValue)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                httpCmdCallback.onSucceed(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                httpCmdCallback.onFail(e.getMessage());
            }
        });
    }

    //    /**
//     * 获取token
//     * @param （des加密后的数据，加密的数据如{account:123,password:123,mac:d3-23-sdf-sdcsd}的字符串） superQrCode：二维码字符串，collectionCodeUrl：用户的收款码
//     * @return
//     */
//    public  String register(String account,String password,String superQrCode,String collectionCodeUrl) throws Exception {
//        //获取公钥字符串 json
//        String resultRSA = getRSA();
//        Map<String, Object> result = JsonUtils.toMap(resultRSA);
//
//        Map<String, Object> rsaMap = (Map<String, Object>) result.get("attach");
//        String modulus = (String) rsaMap.get("modulus");//公钥
//        String publicExponent = (String) rsaMap.get("publicExponent");//公钥
//        String id = (String) rsaMap.get("id");
//        //已加密的注册信息
//        String data = encrRSA("{account:"+account+",password:"+password+",superQrCode:"+superQrCode+",collectionCodeUrl:"+collectionCodeUrl+"}",modulus,publicExponent);
//        Map<String, Object> meRSA = getRSAPublic();
//        RSAPublicKey publicKey = (RSAPublicKey) meRSA.get("public");
//        RSAPrivateKey privateKey = (RSAPrivateKey) meRSA.get("private");
//        String resultLogin = sendPost("user/register?id="+id+"&modulus="+publicKey.getModulus()+"&publicExponent="+publicKey.getPublicExponent(),data);
//        return decryptRSA(resultLogin,""+privateKey.getModulus(),""+privateKey.getPrivateExponent());
//    }

    /**
     * RSA解密
     * @param data
     */
    public String decryptRSA(String data,String modulus, String exponent) throws Exception{
        RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, exponent);
        return RSAUtils.decryptByPrivateKey(data, priKey);
    }

    public String getNewAccessToken(){
        String refrashToken = (String) sp.get(Constans.KEY_REFRESHTOKEN,"");
        return mOKHttpManager.getNewAccessToken(refrashToken);
    }

    ///////////////////////////////////////////////// MVP style //////////////////////////////////////
    public void login(String urlParam, JSONObject params, Callback callback){

        JSONObject data = new JSONObject();
        try {
            data.put("data=",params.toString());
            String requestParams ="data="+ URLEncoder.encode(params.toString());
            RequestBody requestBody =RequestBody.create(mMediaType, requestParams);
            Request request = new Request.Builder().url(Constans.BASEURL+urlParam).post(requestBody).build();

            Call call = mOkHttpClient.newCall(request);
            call.enqueue(callback);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.blockchain.cray.utils;

public class Constans {

    public final static String BASEURL ="http://47.111.129.248/";
    public final static String URL_PARAM_VERIFY_CODE ="user/sms/send";//获取验证码
    public final static String URL_PARAM_REGISTER ="user/register";//注册
    public final static String URL_PARAM_LOGIN ="user/login";//登录
    public final static String URL_PARAM_CHANGE_PSW ="user/password/change";//修改登录密码
    public final static String URL_PARAM_CHANGE_PAYMENT_PSW ="user/info/update/payment/password";//修改支付密码
    public final static String URL_PARAM_ALL_CRAY ="crayfish/all/types/get";//获取小龙虾种类
    public final static String URL_PARAM_LOGOUT ="user/logout";//退出登录
    public final static String URL_PARAM_USER_INFO ="user/info/get";//获取用户信息
    public final static String URL_PARAM_USER_UPDATE ="user/info/update";//更新用户信息
    public final static String URL_PARAM_CRAYFISH_RESERVATION ="crayfish/reservation";//预约
    public final static String URL_PARAM_CRAYFISH_SPIKE ="crayfish/spike";//抢购
    public final static String URL_PARAM_CRAYFISH_ADOPTION_LIST ="crayfish/adoption/list";//获取领养列表
    public final static String URL_PARAM_CRAYFISH_ADOPTION_DETAIL ="crayfish/adoption/detail";//获取领养详情
    public final static String URL_PARAM_CRAYFISH_TRANS_LIST ="crayfish/trans/list";//获取转让列表
    public final static String URL_PARAM_CRAYFISH_RESERVATION_LIST ="crayfish/reservation/list";//获取预约列表
    public final static String URL_PARAM_CRAYFISH_TRANS_CONFIRM ="crayfish/trans/confirm";//卖家确认订单
    public final static String URL_PARAM_CRAYFISH_TRANS_PAY ="crayfish/trans/pay";//买家支付
    public final static String URL_PARAM_CRAYFISH_APPEAL ="crayfish/appeal";//卖家申诉
    public final static String URL_PARAM_CRAYFISH_TEAM_LIST ="user/team/list";//我的团队列表
    public final static String URL_PARAM_NAME_AUTHENTICATION ="user/info/update/idcard";//实名认证

    public final static String KEY_INVITE_CODE ="invite_code";
    public final static String KEY_ISLOGIN ="islogin";
    public final static String KEY_REFRESHTOKEN ="refreshToken";
    public final static String KEY_ACCESSTOKEN ="accessToken";
    public final static String KEY_QRCODE ="qrcode";
    public final static String KEY_USER_NAME ="user_name";
    public final static String KEY_MOBILE ="mobile";
    public final static String KEY_ACCOUNT_TYPE ="account_type";
    public final static String KEY_ADOPTION_ID ="key_adoptionBean";
    public final static String ACCOUNT_BANK_CARD ="bank_card";
    public final static String ACCOUNT_ALIPAY ="alipay";
    public final static String ACCOUNT_WEIXIN ="weixin";
    public final static String KEY_PSW_TYPE ="key_psw";
    public final static String PSW_TYPE_LOGIN ="changePsw";
    public final static String PSW_TYPE_FORGET ="forgetPsw";
    public final static String PSW_TYPE_PAYMENT ="changePayPsw";
    public final static String KEY_ACCOUNT_INFO ="account_info";

    public final static int TYPE_VERIFY_CODE_REGISTER = 1;
    public final static int TYPE_VERIFY_CODE_LOGIN_PSW = 2;
    public final static int TYPE_VERIFY_CODE_PAY_PSW = 3;
    public final static int REQUEST_SUCCESSFUL = 0;
    public final static int REGISTER_REPEAT = 10001;
    public final static int REGISTER_WRONG_VERIFY_CODE= 10006;
    public final static int LOGIN_PSW_ERROR= 10002;
    public final static int PAYMENT_ERROR= 20021;
    public final static int ACCOUNT_WEIXINPAY_REPEATED= 10010;
    public final static int ACCOUNT_ALIPAY_REPEATED= 10011;
    public final static int ACCOUNT_UNIONPAY_REPEATED= 10012;
    public final static int ACCESSTOKEN_EXPIRE= 10005;
    public final static int REFRESHTOKEN_EXPIRE= 10006;

    public static final String  MY_TEAM = "我的團隊";
    public static final String  INVITE_FRIEND = "邀請好友";
    public static final String  SYSTEM_MSG = "系統消息";
    public static final String  CERTIFICATION_NAME = "實名認證";
    public static final String  SECURITY_CENTER = "安全中心";
    public static final String  ACCOUNT_YOLLON = "收款賬戶";

    public final static int CRAY_STATUS_RESERVATION= 1;//可预约
    public final static int CRAY_STATUS_SNAP_UP= 2;//等待抢购
    public final static int CRAY_STATUS_IN_PANIC= 3;//抢购中
    public final static int CRAY_STATUS_IN_BREEDING= 4;//繁殖中
    public final static int CRAY_STATUS_BE_GROWING_UP= 5;//成长中

    public final static int CRAY_SUBSCRIBE_CODE_20001= 20001;
    public final static int CRAY_SUBSCRIBE_CODE_20002= 20002;
    public final static int CRAY_SUBSCRIBE_CODE_20003= 20003;
    public final static int CRAY_SUBSCRIBE_CODE_20004= 20004;

    public final static int CRAY_SPIKE_CODE_20001= 20001;
    public final static int CRAY_SPIKE_CODE_20011= 20011;
    public final static int CRAY_SPIKE_CODE_20012= 20012;
    public final static int CRAY_SPIKE_CODE_20013= 20013;

    public final static String CRAY_ADAPTION_STATUS_LEAVE_UNUSED = "0";//闲置/待转让
    public final static String CRAY_ADAPTION_STATUS_UN_PAYMENT = "1";//未付款/转让中/待领养
    public final static String CRAY_ADAPTION_STATUS_PAYMENTED = "2";//已付款/已完成 /领养中
    public final static String CRAY_ADAPTION_STATUS_APPEALING = "3";//申诉中
    public final static String CRAY_ADAPTION_STATUS_CANCEL = "4";//取消            /取消 申诉
    public final static String CRAY_ADAPTION_STATUS_EARNING = "99";//收益中 已完成
    public final static String CRAY_ADAPTION_STATUS_EARNING_FINISHIED = "100";//收益已完成 已完成

    public final static String USER_INFO_RANK_0 = "0";//普通用户
    public final static String USER_INFO_RANK_1 = "1";//推广大使
    public final static String USER_INFO_RANK_2 = "2";//服务商
    public final static String USER_INFO_RANK_3 = "3";//合伙人

    public final static int CRAY_STATUS_TRANSFERTO = 1;//待转让
    public final static int CRAY_STATUS_TRANSFERING = 2;//转让中
    public final static int CRAY_STATUS_TRANSFER_FINISH = 3;//已完成
    public final static int CRAY_STATUS_TRANSFER_CANCEL = 4;//取消/申诉

    public final static int CRAY_TANSFER_ORDER_STATUS_ERROR_20021= 20021;
    public final static int CRAY_TANSFER_ORDER_STATUS_ERROR_20022= 20022;

    public final static int REQUEST_CODE_ADD_ACCOUNT_WEIXIN= 2;
    public final static int REQUEST_CODE_ADD_ACCOUNT_ALIPAY= 1;
    public final static int REQUEST_CODE_ADD_ACCOUNT_BANK_CARD= 0;

}

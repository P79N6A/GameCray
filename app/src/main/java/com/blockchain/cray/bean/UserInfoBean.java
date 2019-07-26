package com.blockchain.cray.bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable {


    /**
     * attach : {"crayfishCoin":"0.00","idCard":"222333444555666777","integral":"580","mobile":"13923809473","name":"黄小泊","nickName":"小泊","proxyType":0,"qrCode":"6a891f82-f289-4604-b8ba-31fea705ad2b","rank":"0","spreadIncome":"0.00","teamIncome":"0.00","totalAssets":"61.88","totalIncome":"61.88","weixinAccount":"13923809473","weixinCodeUrl":"https:\\/\\/crash-test.oss-cn-beijing.aliyuncs.com\\/c8aac8fb-f2a8-4bd8-a9e3-96fe12f29bf1-13923809473-weixinPay","weixinName":"小泊","yinlianAccount":"","yinlianBankName":"","yinlianName":"","zhifubaoAccount":"123456","zhifubaoCodeUrl":"https:\\/\\/crash-test.oss-cn-beijing.aliyuncs.com\\/6b93aa46-a788-4854-a1a8-694378dd384a-13923809473-aliPay","zhifubaoName":"测试"}
     * code : 0
     * success : true
     */

    private AttachBean attach;
    private int code;
    private boolean success;

    public AttachBean getAttach() {
        return attach;
    }

    public void setAttach(AttachBean attach) {
        this.attach = attach;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class AttachBean implements Serializable{
        /**
         * crayfishCoin : 0.00
         * idCard : 222333444555666777
         * integral : 580
         * mobile : 13923809473
         * name : 黄小泊
         * nickName : 小泊
         * proxyType : 0
         * qrCode : 6a891f82-f289-4604-b8ba-31fea705ad2b
         * rank : 0
         * spreadIncome : 0.00
         * teamIncome : 0.00
         * totalAssets : 61.88
         * totalIncome : 61.88
         * weixinAccount : 13923809473
         * weixinCodeUrl : https:\/\/crash-test.oss-cn-beijing.aliyuncs.com\/c8aac8fb-f2a8-4bd8-a9e3-96fe12f29bf1-13923809473-weixinPay
         * weixinName : 小泊
         * yinlianAccount :
         * yinlianBankName :
         * yinlianName :
         * zhifubaoAccount : 123456
         * zhifubaoCodeUrl : https:\/\/crash-test.oss-cn-beijing.aliyuncs.com\/6b93aa46-a788-4854-a1a8-694378dd384a-13923809473-aliPay
         * zhifubaoName : 测试
         */

        private String crayfishCoin;
        private String idCard;
        private String integral;

        @Override
        public String toString() {
            return "AttachBean{" +
                    "crayfishCoin='" + crayfishCoin + '\'' +
                    ", idCard='" + idCard + '\'' +
                    ", integral='" + integral + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", name='" + name + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", proxyType=" + proxyType +
                    ", qrCode='" + qrCode + '\'' +
                    ", rank='" + rank + '\'' +
                    ", spreadIncome='" + spreadIncome + '\'' +
                    ", teamIncome='" + teamIncome + '\'' +
                    ", totalAssets='" + totalAssets + '\'' +
                    ", totalIncome='" + totalIncome + '\'' +
                    ", weixinAccount='" + weixinAccount + '\'' +
                    ", weixinCodeUrl='" + weixinCodeUrl + '\'' +
                    ", weixinName='" + weixinName + '\'' +
                    ", yinlianAccount='" + yinlianAccount + '\'' +
                    ", yinlianBankName='" + yinlianBankName + '\'' +
                    ", yinlianName='" + yinlianName + '\'' +
                    ", zhifubaoAccount='" + zhifubaoAccount + '\'' +
                    ", zhifubaoCodeUrl='" + zhifubaoCodeUrl + '\'' +
                    ", zhifubaoName='" + zhifubaoName + '\'' +
                    '}';
        }

        private String mobile;
        private String name;
        private String nickName;
        private int proxyType;
        private String qrCode;
        private String rank;
        private String spreadIncome;
        private String teamIncome;
        private String totalAssets;
        private String totalIncome;
        private String weixinAccount;
        private String weixinCodeUrl;
        private String weixinName;
        private String yinlianAccount;
        private String yinlianBankName;
        private String yinlianName;
        private String zhifubaoAccount;
        private String zhifubaoCodeUrl;
        private String zhifubaoName;

        public String getCrayfishCoin() {
            return crayfishCoin;
        }

        public void setCrayfishCoin(String crayfishCoin) {
            this.crayfishCoin = crayfishCoin;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getProxyType() {
            return proxyType;
        }

        public void setProxyType(int proxyType) {
            this.proxyType = proxyType;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getSpreadIncome() {
            return spreadIncome;
        }

        public void setSpreadIncome(String spreadIncome) {
            this.spreadIncome = spreadIncome;
        }

        public String getTeamIncome() {
            return teamIncome;
        }

        public void setTeamIncome(String teamIncome) {
            this.teamIncome = teamIncome;
        }

        public String getTotalAssets() {
            return totalAssets;
        }

        public void setTotalAssets(String totalAssets) {
            this.totalAssets = totalAssets;
        }

        public String getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(String totalIncome) {
            this.totalIncome = totalIncome;
        }

        public String getWeixinAccount() {
            return weixinAccount;
        }

        public void setWeixinAccount(String weixinAccount) {
            this.weixinAccount = weixinAccount;
        }

        public String getWeixinCodeUrl() {
            return weixinCodeUrl;
        }

        public void setWeixinCodeUrl(String weixinCodeUrl) {
            this.weixinCodeUrl = weixinCodeUrl;
        }

        public String getWeixinName() {
            return weixinName;
        }

        public void setWeixinName(String weixinName) {
            this.weixinName = weixinName;
        }

        public String getYinlianAccount() {
            return yinlianAccount;
        }

        public void setYinlianAccount(String yinlianAccount) {
            this.yinlianAccount = yinlianAccount;
        }

        public String getYinlianBankName() {
            return yinlianBankName;
        }

        public void setYinlianBankName(String yinlianBankName) {
            this.yinlianBankName = yinlianBankName;
        }

        public String getYinlianName() {
            return yinlianName;
        }

        public void setYinlianName(String yinlianName) {
            this.yinlianName = yinlianName;
        }

        public String getZhifubaoAccount() {
            return zhifubaoAccount;
        }

        public void setZhifubaoAccount(String zhifubaoAccount) {
            this.zhifubaoAccount = zhifubaoAccount;
        }

        public String getZhifubaoCodeUrl() {
            return zhifubaoCodeUrl;
        }

        public void setZhifubaoCodeUrl(String zhifubaoCodeUrl) {
            this.zhifubaoCodeUrl = zhifubaoCodeUrl;
        }

        public String getZhifubaoName() {
            return zhifubaoName;
        }

        public void setZhifubaoName(String zhifubaoName) {
            this.zhifubaoName = zhifubaoName;
        }
    }
}

package com.blockchain.cray.bean;

import java.util.List;

public class CrayBean {

    private int code;
    private boolean success;
    private List<AttachBean> attach;

    @Override
    public String toString() {
        return "CrayBean{" +
                "code=" + code +
                ", success=" + success +
                ", attach=" + attach +
                '}';
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

    public List<AttachBean> getAttach() {
        return attach;
    }

    public void setAttach(List<AttachBean> attach) {
        this.attach = attach;
    }

    public static class AttachBean {

        /**
         * adoptionDay : 7
         * endPrice : 300
         * endTime : 1200
         * id : 1
         * incomePoint : 21.0%
         * name : 小龙虾
         * quicklyCost : 4
         * reserveCost : 2
         * startPrice : 100
         * startTime : 1100
         * headPicUrl:
         * status:1 预约,2 待领养,3 领养中,4 繁殖中,5成长中
         */

        private int id;
        private String name; //名称
        private String headPicUrl; //龙虾头像
        private int startTime;//领养开始时间 1100
        private int endTime;//领养结束时间  1200
        private int startPrice;//最小价值 100

        @Override
        public String toString() {
            return "AttachBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", headPicUrl='" + headPicUrl + '\'' +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", startPrice=" + startPrice +
                    ", endPrice=" + endPrice +
                    ", reserveCost=" + reserveCost +
                    ", quicklyCost=" + quicklyCost +
                    ", adoptionDay=" + adoptionDay +
                    ", incomePoint='" + incomePoint + '\'' +
                    ", status=" + status +
                    '}';
        }

        private int endPrice;//最大价值 300
        private int reserveCost;//预约需花费的微分 2
        private int quicklyCost;//即抢领需花费的微分 4
        private int adoptionDay;//领养时间 7
        private String incomePoint;//增值 比分比 21.0%
        private int status;//增值 比分比 21.0%

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getHeadPicUrl() {
            return headPicUrl;
        }

        public void setHeadPicUrl(String headPicUrl) {
            this.headPicUrl = headPicUrl;
        }

        public int getAdoptionDay() {
            return adoptionDay;
        }

        public void setAdoptionDay(int adoptionDay) {
            this.adoptionDay = adoptionDay;
        }

        public int getEndPrice() {
            return endPrice;
        }

        public void setEndPrice(int endPrice) {
            this.endPrice = endPrice;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIncomePoint() {
            return incomePoint;
        }

        public void setIncomePoint(String incomePoint) {
            this.incomePoint = incomePoint;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuicklyCost() {
            return quicklyCost;
        }

        public void setQuicklyCost(int quicklyCost) {
            this.quicklyCost = quicklyCost;
        }

        public int getReserveCost() {
            return reserveCost;
        }

        public void setReserveCost(int reserveCost) {
            this.reserveCost = reserveCost;
        }

        public int getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(int startPrice) {
            this.startPrice = startPrice;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }
    }
}

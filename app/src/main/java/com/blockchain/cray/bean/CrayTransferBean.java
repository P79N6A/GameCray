package com.blockchain.cray.bean;

import java.util.List;

public class CrayTransferBean {


    /**
     * attach : {"list":[{"crayFishName":"樱花虾","id":"56711525579484014878724","income":"1958.30","incomeRule":"12天/21%","lastConfirmTime":"178323","sourceId":"4563456456","status":"2","transTime":"2019-07-20 22:03:24","updateTime":"2019-07-20 23:46:08","lastPayTime":"180110"},{"crayFishName":"樱花虾","id":"56711525579484014878724","income":"1958.30","incomeRule":"12天/21%","lastConfirmTime":"178323","sourceId":"4563456456","status":"2","transTime":"2019-07-20 22:03:24","updateTime":"2019-07-20 23:46:08"},{"crayFishName":"绿晶虾","id":"1111525579483259904005y","income":"3035.68","incomeRule":"12天/20%","lastPayTime":"180110","sourceId":"5456745674657","status":"1","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:50:14"},{"crayFishName":"绿晶虾","id":"1111525579483259904005y","income":"3035.68","incomeRule":"12天/20%","lastPayTime":"180110","sourceId":"5456745674657","status":"1","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:50:14"},{"crayFishName":"绿晶虾","id":"1111525579483259904005y","income":"9616.92","incomeRule":"12天/20%","lastConfirmTime":"178323","sourceId":"5456745674657","status":"2","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:48:08"},{"crayFishName":"绿晶虾","id":"1111525579483259904005y","income":"9616.92","incomeRule":"12天/20%","lastConfirmTime":"178323","sourceId":"5456745674657","status":"2","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:48:08"},{"crayFishName":"樱花虾","id":"56711525579484014878725y","income":"2963.85","incomeRule":"12天/21%","lastPayTime":"178927","sourceId":"4563456456","status":"1","transTime":"2019-07-20 22:03:24","updateTime":"2019-07-20 22:46:11"},{"crayFishName":"樱花虾","id":"56711525579484014878725y","income":"2963.85","incomeRule":"12天/21%","lastPayTime":"178927","sourceId":"4563456456","status":"1","transTime":"2019-07-20 22:03:24","updateTime":"2019-07-20 22:46:11"},{"crayFishName":"绿晶虾","id":"1111525579483259904005","income":"3933.01","incomeRule":"12天/20%","lastPayTime":"180110","sourceId":"5456745674657","status":"1","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:46:09"},{"crayFishName":"绿晶虾","id":"1111525579483259904005","income":"3933.01","incomeRule":"12天/20%","lastPayTime":"180110","sourceId":"5456745674657","status":"1","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:46:09"}],"page":{"currentPage":1,"hasNextPage":true,"hasPreviousPage":false,"pageEndRow":10,"pageSize":10,"pageStartRow":0,"pagination":true,"totalPages":16,"totalRows":159}}
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

    public static class AttachBean {
        /**
         * list : [{"crayFishName":"樱花虾","id":"56711525579484014878724","income":"1958.30","incomeRule":"12天/21%","lastConfirmTime":"178323","sourceId":"4563456456","status":"2","transTime":"2019-07-20 22:03:24","updateTime":"2019-07-20 23:46:08"},{"crayFishName":"樱花虾","id":"56711525579484014878724","income":"1958.30","incomeRule":"12天/21%","lastConfirmTime":"178323","sourceId":"4563456456","status":"2","transTime":"2019-07-20 22:03:24","updateTime":"2019-07-20 23:46:08"},{"crayFishName":"绿晶虾","id":"1111525579483259904005y","income":"3035.68","incomeRule":"12天/20%","lastPayTime":"180110","sourceId":"5456745674657","status":"1","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:50:14"},{"crayFishName":"绿晶虾","id":"1111525579483259904005y","income":"3035.68","incomeRule":"12天/20%","lastPayTime":"180110","sourceId":"5456745674657","status":"1","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:50:14"},{"crayFishName":"绿晶虾","id":"1111525579483259904005y","income":"9616.92","incomeRule":"12天/20%","lastConfirmTime":"178323","sourceId":"5456745674657","status":"2","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:48:08"},{"crayFishName":"绿晶虾","id":"1111525579483259904005y","income":"9616.92","incomeRule":"12天/20%","lastConfirmTime":"178323","sourceId":"5456745674657","status":"2","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:48:08"},{"crayFishName":"樱花虾","id":"56711525579484014878725y","income":"2963.85","incomeRule":"12天/21%","lastPayTime":"178927","sourceId":"4563456456","status":"1","transTime":"2019-07-20 22:03:24","updateTime":"2019-07-20 22:46:11"},{"crayFishName":"樱花虾","id":"56711525579484014878725y","income":"2963.85","incomeRule":"12天/21%","lastPayTime":"178927","sourceId":"4563456456","status":"1","transTime":"2019-07-20 22:03:24","updateTime":"2019-07-20 22:46:11"},{"crayFishName":"绿晶虾","id":"1111525579483259904005","income":"3933.01","incomeRule":"12天/20%","lastPayTime":"180110","sourceId":"5456745674657","status":"1","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:46:09"},{"crayFishName":"绿晶虾","id":"1111525579483259904005","income":"3933.01","incomeRule":"12天/20%","lastPayTime":"180110","sourceId":"5456745674657","status":"1","transTime":"2019-07-20 21:43:41","updateTime":"2019-07-20 22:46:09"}]
         * page : {"currentPage":1,"hasNextPage":true,"hasPreviousPage":false,"pageEndRow":10,"pageSize":10,"pageStartRow":0,"pagination":true,"totalPages":16,"totalRows":159}
         */

        private PageBean page;
        private List<ListBean> list;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageBean {
            /**
             * currentPage : 1
             * hasNextPage : true
             * hasPreviousPage : false
             * pageEndRow : 10
             * pageSize : 10
             * pageStartRow : 0
             * pagination : true
             * totalPages : 16
             * totalRows : 159
             */

            private int currentPage;
            private boolean hasNextPage;
            private boolean hasPreviousPage;
            private int pageEndRow;
            private int pageSize;
            private int pageStartRow;
            private boolean pagination;
            private int totalPages;
            private int totalRows;

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public boolean isHasNextPage() {
                return hasNextPage;
            }

            public void setHasNextPage(boolean hasNextPage) {
                this.hasNextPage = hasNextPage;
            }

            public boolean isHasPreviousPage() {
                return hasPreviousPage;
            }

            public void setHasPreviousPage(boolean hasPreviousPage) {
                this.hasPreviousPage = hasPreviousPage;
            }

            public int getPageEndRow() {
                return pageEndRow;
            }

            public void setPageEndRow(int pageEndRow) {
                this.pageEndRow = pageEndRow;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPageStartRow() {
                return pageStartRow;
            }

            public void setPageStartRow(int pageStartRow) {
                this.pageStartRow = pageStartRow;
            }

            public boolean isPagination() {
                return pagination;
            }

            public void setPagination(boolean pagination) {
                this.pagination = pagination;
            }

            public int getTotalPages() {
                return totalPages;
            }

            public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
            }

            public int getTotalRows() {
                return totalRows;
            }

            public void setTotalRows(int totalRows) {
                this.totalRows = totalRows;
            }
        }

        public static class ListBean {
            /**
             * crayFishName : 樱花虾
             * id : 56711525579484014878724
             * income : 1958.30
             * incomeRule : 12天/21%
             * lastConfirmTime : 178323
             * sourceId : 4563456456
             * status : 2
             * transTime : 2019-07-20 22:03:24
             * updateTime : 2019-07-20 23:46:08
             * lastPayTime : 180110
             */

            private String crayFishName;
            private String id;
            private String income;
            private String incomeRule;
            private String lastConfirmTime;
            private String sourceId;
            private String status;
            private String transTime;
            private String updateTime;
            private String lastPayTime;

            public String getCrayFishName() {
                return crayFishName == null?"":crayFishName;
            }

            public void setCrayFishName(String crayFishName) {
                this.crayFishName = crayFishName;
            }

            public String getId() {
                return id == null?"":id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIncome() {
                return income == null?"":income;
            }

            public void setIncome(String income) {
                this.income = income;
            }

            public String getIncomeRule() {
                return incomeRule == null?"":incomeRule;
            }

            public void setIncomeRule(String incomeRule) {
                this.incomeRule = incomeRule;
            }

            public String getLastConfirmTime() {
                return lastConfirmTime  == null?"":lastConfirmTime;
            }

            public void setLastConfirmTime(String lastConfirmTime) {
                this.lastConfirmTime = lastConfirmTime;
            }

            public String getSourceId() {
                return sourceId == null?"":sourceId;
            }

            public void setSourceId(String sourceId) {
                this.sourceId = sourceId;
            }

            public String getStatus() {
                return status == null?"":status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTransTime() {
                return transTime == null?"":transTime;
            }

            public void setTransTime(String transTime) {
                this.transTime = transTime;
            }

            public String getUpdateTime() {
                return updateTime == null?"":updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getLastPayTime() {
                return lastPayTime == null?"":lastPayTime;
            }

            public void setLastPayTime(String lastPayTime) {
                this.lastPayTime = lastPayTime;
            }
        }
    }
}

package com.blockchain.cray.bean;

import java.io.Serializable;
import java.util.List;

public class CrayAdoptionBean implements Serializable{


    /**
     * attach : {"list":[
     * {"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},
     * {"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},
     * {"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},
     * {"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},
     * {"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},
     * {"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},
     * {"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},
     * {"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},
     * {"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},
     * {"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"}],
     * "page":{"currentPage":1,"hasNextPage":true,"hasPreviousPage":false,"pageEndRow":10,"pageSize":10,"pageStartRow":0,"pagination":true,"totalPages":52,"totalRows":512}
     * }
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
         * list : [{"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},{"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},{"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},{"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},{"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},{"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},{"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},{"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},{"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"},{"crayFishName":"樱花虾","id":"575674567546345","income":"","incomeRule":"12天/21%","status":"","transTime":"15 Jul 2019 12:51:32 GMT","updateTime":"15 Jul 2019 13:03:54 GMT"}]
         * page : {"currentPage":1,"hasNextPage":true,"hasPreviousPage":false,"pageEndRow":10,"pageSize":10,"pageStartRow":0,"pagination":true,"totalPages":52,"totalRows":512}
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
             * totalPages : 52
             * totalRows : 512
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

            @Override
            public String toString() {
                return "PageBean{" +
                        "currentPage=" + currentPage +
                        ", hasNextPage=" + hasNextPage +
                        ", hasPreviousPage=" + hasPreviousPage +
                        ", pageEndRow=" + pageEndRow +
                        ", pageSize=" + pageSize +
                        ", pageStartRow=" + pageStartRow +
                        ", pagination=" + pagination +
                        ", totalPages=" + totalPages +
                        ", totalRows=" + totalRows +
                        '}';
            }

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

        public static class ListBean implements Serializable {
            /**
             * crayFishName : 樱花虾
             * id : 575674567546345
             * income :
             * incomeRule : 12天/21%
             * status : 1 未付款；2 已付款；3申诉中；4取消；100 已完成
             * transTime : 15 Jul 2019 12:51:32 GMT
             * updateTime : 15 Jul 2019 13:03:54 GMT
             * lastConfirmTime: 15152
             *lastPayTime: 150933
             */

            private String crayFishName;
            private String id;
            private String income;
            private String incomeRule;
            private String status;
            private String transTime;
            private String updateTime;
            private String lastConfirmTime;
            private String lastPayTime;


            public String getLastPayTime() {
                return lastPayTime == null?"":lastPayTime;
            }

            public void setLastPayTime(String lastPayTime) {
                this.lastPayTime = lastPayTime;
            }

            public String getLastConfirmTime() {
                return lastConfirmTime == null?"":lastConfirmTime ;
            }

            public void setLastConfirmTime(String lastConfirmTime) {
                this.lastConfirmTime = lastConfirmTime;
            }

            public String getCrayFishName() {
                return crayFishName ==null?"":crayFishName;
            }

            public void setCrayFishName(String crayFishName) {
                this.crayFishName = crayFishName;
            }

            public String getId() {
                return id ==null?"":id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIncome() {
                return income ==null?"":income;
            }

            public void setIncome(String income) {
                this.income = income;
            }

            public String getIncomeRule() {
                return incomeRule ==null?"":incomeRule;
            }

            public void setIncomeRule(String incomeRule) {
                this.incomeRule = incomeRule;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTransTime() {
                return transTime ==null?"":transTime;
            }

            public void setTransTime(String transTime) {
                this.transTime = transTime;
            }

            public String getUpdateTime() {
                return updateTime ==null?"":updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}

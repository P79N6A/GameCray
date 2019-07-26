package com.blockchain.cray.bean;

import java.util.List;

public class CrayReservationBean {


    /**
     * attach : {"list":[{"cost":"null","crayfishName":"绿晶虾","reservationTime":"2019-07-23 21:18:00"}],"page":{"currentPage":1,"hasNextPage":false,"hasPreviousPage":false,"pageEndRow":1,"pageSize":10,"pageStartRow":0,"pagination":true,"totalPages":1,"totalRows":1}}
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
         * list : [{"cost":"null","crayfishName":"绿晶虾","reservationTime":"2019-07-23 21:18:00"}]
         * page : {"currentPage":1,"hasNextPage":false,"hasPreviousPage":false,"pageEndRow":1,"pageSize":10,"pageStartRow":0,"pagination":true,"totalPages":1,"totalRows":1}
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
             * hasNextPage : false
             * hasPreviousPage : false
             * pageEndRow : 1
             * pageSize : 10
             * pageStartRow : 0
             * pagination : true
             * totalPages : 1
             * totalRows : 1
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
             * cost : null
             * crayfishName : 绿晶虾
             * reservationTime : 2019-07-23 21:18:00
             */

            private String cost;
            private String crayfishName;
            private String reservationTime;

            public String getCost() {
                return cost == null?"":cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }

            public String getCrayfishName() {
                return crayfishName == null?"":crayfishName;
            }

            public void setCrayfishName(String crayfishName) {
                this.crayfishName = crayfishName;
            }

            public String getReservationTime() {
                return reservationTime == null?"":reservationTime;
            }

            public void setReservationTime(String reservationTime) {
                this.reservationTime = reservationTime;
            }
        }
    }
}

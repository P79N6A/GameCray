package com.blockchain.cray.bean;

import java.util.List;

public class CrayMyTeamBean {


    /**
     * attach : {"list":[{"mobile":"12333333353","nickName":"小泊2级13号人物","proxyType":2},{"mobile":"12333333352","nickName":"小泊2级12号人物","proxyType":2},{"mobile":"12333333351","nickName":"小泊2级11号人物","proxyType":2},{"mobile":"12333333350","nickName":"小泊2级10号人物","proxyType":2},{"mobile":"12333333349","nickName":"小泊2级9号人物","proxyType":2},{"mobile":"12333333348","nickName":"小泊2级8号人物","proxyType":2},{"mobile":"12333333347","nickName":"小泊2级7号人物","proxyType":2},{"mobile":"12333333346","nickName":"小泊2级6号人物","proxyType":2},{"mobile":"12333333345","nickName":"小泊2级5号人物","proxyType":2},{"mobile":"12333333344","nickName":"小泊2级4号人物","proxyType":2}],"page":{"currentPage":2,"hasNextPage":true,"hasPreviousPage":true,"pageEndRow":20,"pageSize":10,"pageStartRow":10,"pagination":true,"totalPages":3,"totalRows":30}}
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
         * list : [{"mobile":"12333333353","nickName":"小泊2级13号人物","proxyType":2},{"mobile":"12333333352","nickName":"小泊2级12号人物","proxyType":2},{"mobile":"12333333351","nickName":"小泊2级11号人物","proxyType":2},{"mobile":"12333333350","nickName":"小泊2级10号人物","proxyType":2},{"mobile":"12333333349","nickName":"小泊2级9号人物","proxyType":2},{"mobile":"12333333348","nickName":"小泊2级8号人物","proxyType":2},{"mobile":"12333333347","nickName":"小泊2级7号人物","proxyType":2},{"mobile":"12333333346","nickName":"小泊2级6号人物","proxyType":2},{"mobile":"12333333345","nickName":"小泊2级5号人物","proxyType":2},{"mobile":"12333333344","nickName":"小泊2级4号人物","proxyType":2}]
         * page : {"currentPage":2,"hasNextPage":true,"hasPreviousPage":true,"pageEndRow":20,"pageSize":10,"pageStartRow":10,"pagination":true,"totalPages":3,"totalRows":30}
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
             * currentPage : 2
             * hasNextPage : true
             * hasPreviousPage : true
             * pageEndRow : 20
             * pageSize : 10
             * pageStartRow : 10
             * pagination : true
             * totalPages : 3
             * totalRows : 30
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
             * mobile : 12333333353
             * nickName : 小泊2级13号人物
             * proxyType : 2
             */

            private String mobile;
            private String nickName;
            private int proxyType;

            public String getMobile() {
                return mobile == null?"":mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNickName() {
                return nickName == null?"":nickName;
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
        }
    }
}

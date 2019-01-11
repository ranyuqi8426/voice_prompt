package com.wsct.util.page;


public class SearchPageVO {
	private int total = 0;
    private int page = 1;
    private int limit = GlobalConst.pageSize;//页面容量

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

package com.wsct.util.page;

/**
 * 分页Bean，保存分页用的所有信息
 * 
 * @author kcy
 * 
 */
public class PageBean {
	private int pageIndex;
	private int pageSize;
	private int recordCount;
	private int pageCount;

	public PageBean(int pageIndex, int pageSize, int recordCount) {
		if (recordCount % pageSize == 0)
			pageCount = recordCount / pageSize;
		else
			pageCount = recordCount / pageSize + 1;
		if ((pageIndex > pageCount) && (pageCount != 0))
			pageIndex = pageCount;
		else
			this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

}

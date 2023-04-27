package com.geostar.geoonline.entityserverhtml.framework.common.page;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Page<T> {
  @Expose
  private int pageSize = 10;
  
  @Expose
  private int totalCount;
  
  @Expose
  private int pageIndex;
  
  @Expose
  private int pageNo;
  
  @Expose
  private int pageCount;
  
  @Expose
  private List<T> data;
  
  private int startRow;
  
  private int endRow;
  
  public int getEndRow() {
    return getStartRow() + this.pageSize;
  }
  
  public void setEndRow(int endRow) {
    this.endRow = endRow;
  }
  
  public int getStartRow() {
    return this.pageSize * this.pageIndex + 1;
  }
  
  public void setStartRow(int startRow) {
    this.startRow = startRow;
  }
  
  public List<T> getData() {
    return this.data;
  }
  
  public void setData(List<T> data) {
    this.data = data;
  }
  
  public int getPageSize() {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize) {
    this.pageSize = (pageSize <= 0) ? 10 : pageSize;
  }
  
  public int getTotalCount() {
    return this.totalCount;
  }
  
  public int getPageIndex() {
    return this.pageIndex;
  }
  
  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }
  
  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    this.pageCount = (totalCount % getPageSize() > 0) ? (totalCount / getPageSize() + 1) : (totalCount / getPageSize());
    this.pageIndex = (this.pageCount <= this.pageIndex && this.pageCount != 0) ? this.pageCount : this.pageIndex;
  }
  
  public void resetPageNo() {
    this.pageNo = this.pageIndex + 1;
    this.pageCount = (this.totalCount % this.pageSize == 0) ? (this.totalCount / this.pageSize) : (this.totalCount / this.pageSize + 1);
  }
  
  public int getPageCount() {
    return this.pageCount;
  }
  
  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }
  
  public int getPageNo() {
    return (this.pageNo <= 0) ? 1 : ((this.pageNo > getPageCount()) ? getPageCount() : this.pageNo);
  }
  
  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }
  
  public static boolean validatePage(Page<?> page) {
    boolean result = true;
    if (page.getPageSize() <= 0 || page.getPageIndex() < 0)
      result = false; 
    return result;
  }
}

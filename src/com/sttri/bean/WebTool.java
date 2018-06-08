package com.sttri.bean;

public class WebTool {
	/** 
	 * 计算分页查询数据的开始索引和结束索引
	 * @param pageCount 页码数量
	 * @param currenPage 当前页码
	 * @param totalPage 总页数
	 * @return PageIndex
	 * */
  public static PageIndex getPageIndex(long pageCount, int currenPage, long totalPage){
		long startPage = currenPage-(pageCount%2==0? pageCount/2-1 : pageCount/2);
		long endPage = currenPage+pageCount/2;
		if(startPage<1){
			startPage = 1;
			if(totalPage>=pageCount){
				endPage = pageCount;
			}else {
				endPage = totalPage;
			}
		}
		if(endPage>totalPage){
			endPage = totalPage;
			if((endPage-pageCount)>0) {
				startPage = endPage-pageCount+1;
			}else {
				startPage = 1;
			}
		}
		PageIndex pageIndex = new PageIndex(startPage, endPage);
		return pageIndex;	
  }
}

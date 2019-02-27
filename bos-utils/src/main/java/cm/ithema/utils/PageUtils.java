package cm.ithema.utils;

import java.util.List;

public class PageUtils<T> {

	private Integer currentPage; // ��ǰҳ
	private Integer pageSize; // ÿҳ�ܼ�¼��
	private Integer totalCount;// ��ҳ��
	private Integer total; // ������
	private List<T> rows;
	
	private Integer startcount; //��ʼ��ѯ����
	private Integer endtcount; //������ѯ����
	
	public PageUtils(Integer page, Integer pageSize, Integer allCount) {
	
		this.total = allCount;
		if(pageSize < 1 || pageSize == null) {
			this.pageSize = 1;
		}else {
			this.pageSize = pageSize;
		}
		
		if(allCount > 1 ) {
			this.totalCount = (allCount + this.pageSize -1) / this.pageSize;
		}else {
			this.totalCount = 1;
		}
		
		if(page < 1 || page == null) {
			this.currentPage = 1;
		}else if (page > this.totalCount){
			this.currentPage = this.totalCount;
		}else {
			this.currentPage = page;
		}
		this.startcount = (this.currentPage - 1) * this.pageSize;
		this.endtcount = this.pageSize;
		
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotal(Integer totalCount) {
		this.totalCount = (this.total + this.pageSize -1) / this.pageSize;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getEndtcount() {
		return endtcount;
	}

	public void setEndtcount(Integer endtcount) {
		this.endtcount = endtcount;
	}

	public Integer getTotal() {
		return total;
	}

	public Integer getStartcount() {
		return startcount;
	}

	@Override
	public String toString() {
		return "PageUtils [currentPage=" + currentPage + ", pageSize=" + pageSize + ", totalCount=" + totalCount
				+ ", total=" + total + ", rows=" + rows + ", startcount=" + startcount + ", endtcount=" + endtcount
				+ "]";
	}
	
	
}

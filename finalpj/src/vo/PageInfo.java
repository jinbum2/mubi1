package vo;

public class PageInfo {//페이징처리

	private int page;
	private int totCnt;

	public PageInfo() {
		super();
		this.page = 1;
		this.totCnt = 0;
	}

	public PageInfo(int page, int totCnt) {
		super();
		this.page = page;
		this.totCnt = totCnt;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

}
package vo;

public class MovieChart {
	
	String rnum;// 순번
	String rank;// 해당일자의 박스오피스 순위
	String movieNm;// 영화명(국문)
	String openDt;// 영화의 개봉일
	String salesChange;// 전일 대비 매출액 증감 비율
	String salesAcc;// 누적매출액
	String audiAcc;// 누적관객수 
	String image;// 이미지 URL 

	public MovieChart() {
		super();
		this.rnum = "";//
		this.rank = "";//
		this.movieNm = "";
		this.openDt = "";
		this.salesChange = "";
		this.salesAcc = "";
		this.audiAcc = "";//
		this.image = "";//
	}

	public MovieChart(String rnum, String rank,String movieNm, String openDt,String salesChange, String salesAcc,
			String audiAcc, String image) {
		super();
		this.rnum = rnum;
		this.rank = rank;
		this.movieNm = movieNm;
		this.openDt = openDt;
		this.salesChange = salesChange;
		this.salesAcc = salesAcc;
		this.audiAcc = audiAcc;
		this.image = image;
	}


	public String getRnum() {
		return rnum;
	}

	public void setRnum(String rnum) {
		this.rnum = rnum;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}


	public String getMovieNm() {
		return movieNm;
	}

	public void setMovieNm(String movieNm) {
		this.movieNm = movieNm;
	}

	public String getOpenDt() {
		return openDt;
	}

	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}

	

	public String getSalesChange() {
		return salesChange;
	}

	public void setSalesChange(String salesChange) {
		this.salesChange = salesChange;
	}

	public String getSalesAcc() {
		return salesAcc;
	}

	public void setSalesAcc(String salesAcc) {
		this.salesAcc = salesAcc;
	}

	
	public String getAudiAcc() {
		return audiAcc;
	}

	public void setAudiAcc(String audiAcc) {
		this.audiAcc = audiAcc;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}

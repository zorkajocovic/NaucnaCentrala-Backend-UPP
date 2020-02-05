package com.example.dto;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Article;
import com.example.model.Review;

public class ArticleDto {
	
	private long id;

	private String abstract_;

	private String keyWords;

	private String pdfurl;

	private String title;

	private MagazineDto magazine;

	private AppUserDto appuser;
	
	private List<ReviewDto> reviews;
	
	public ArticleDto() { }
	
	public ArticleDto(Article article) {
		
		this.setId(article.getId());
		this.setTitle(article.getTitle());
		this.setAbstract_(article.getAbstract_());
		this.setKeyWords(article.getKeyWords());
		this.setPdfurl(article.getPdfurl());
		this.setAppuser(new AppUserDto(article.getAppuser()));
		this.setMagazine(new MagazineDto(article.getMagazine()));
		
		this.reviews = new ArrayList<ReviewDto>();
		for(Review p : article.getReviews()){
			ReviewDto revDto = new ReviewDto(p);
			this.reviews.add(revDto);
		}	
	}
	
	public List<ReviewDto> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewDto> reviews) {
		this.reviews = reviews;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAbstract_() {
		return abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getPdfurl() {
		return pdfurl;
	}

	public void setPdfurl(String pdfurl) {
		this.pdfurl = pdfurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MagazineDto getMagazine() {
		return magazine;
	}

	public void setMagazine(MagazineDto magazine) {
		this.magazine = magazine;
	}

	public AppUserDto getAppuser() {
		return appuser;
	}

	public void setAppuser(AppUserDto appuser) {
		this.appuser = appuser;
	}


	
}

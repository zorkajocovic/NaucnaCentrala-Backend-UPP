package com.example.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ArticleDto;
import com.example.model.Article;
import com.example.repositories.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	ArticleRepository articleRepo;
	
	public List<Article> getAll(){
		return articleRepo.findAll();
	}
	
	public void addMagazine(Article s) {
		articleRepo.save(s);
	}
	
	public void updateMagazine(Article s) {
		articleRepo.save(s);
	}
	
	public Article getMagazine(long id) {
		return articleRepo.getOne(id);
	}
	
	public void deleteMagazine(Long id) {
		articleRepo.deleteById(id);
	}
	
	public void deleteAllMagazines() {
		articleRepo.deleteAll();
	}
	
	public Boolean existsMagazine(Long id) {
		return articleRepo.existsById(id);
	}
	
	public Article mapDTO(ArticleDto articleDto){
		
		Article article = new Article();
		
		article.setTitle(articleDto.getTitle());
		article.setAbstract_(articleDto.getAbstract_());
		article.setKeyWords(articleDto.getKeyWords());
		article.setPdfurl(articleDto.getPdfurl());
	
		return article;
	}
	
	public ArticleDto mapToDTO(Article magazine){
		return new ArticleDto(magazine);
	}
	
	public List<ArticleDto> mapAllToDTO(){
		
		List<Article> articles = getAll();
		List<ArticleDto> articleDTOs = new ArrayList<>();
		
		for(Article r : articles){
			articleDTOs.add(mapToDTO(r));
		}
		
		return articleDTOs;
	}
}

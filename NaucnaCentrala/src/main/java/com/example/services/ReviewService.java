package com.example.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ReviewDto;
import com.example.model.Review;
import com.example.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	
	public List<Review> getAll(){
		return reviewRepository.findAll();
	}
	
	public void addReview(Review s) {
		reviewRepository.save(s);
	}
	
	public void updateReview(Review s) {
		reviewRepository.save(s);
	}
	
	public Review getReview(long id) {
		return reviewRepository.getOne(id);
	}
	
	public void deleteReview(Long id) {
		reviewRepository.deleteById(id);
	}
	
	public void deleteAllReviews() {
		reviewRepository.deleteAll();
	}
	
	public Boolean existsReview(Long id) {
		return reviewRepository.existsById(id);
	}
	
	public Review mapDTO(ReviewDto reviewDto){
		
		Review review = new Review();
		review.setRecommendation(reviewDto.getRecommendation());
		review.setCommentsForAuthors(reviewDto.getCommentsForAuthors());
		review.setCommentsForEditors(reviewDto.getCommentsForEditors());
		
		return review;
	}
	
	public ReviewDto mapToDTO(Review review){
		return new ReviewDto(review);
	}
	
	public List<ReviewDto> mapAllToDTO(){
		
		List<Review> reviews = getAll();
		List<ReviewDto> reviewDTOs = new ArrayList<>();
		
		for(Review r : reviews){
			reviewDTOs.add(mapToDTO(r));
		}
		
		return reviewDTOs;
	}
}

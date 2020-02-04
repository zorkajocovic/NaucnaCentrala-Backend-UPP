package com.example.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.SciFieldDto;
import com.example.repositories.ScientificFieldRepository;

import com.example.model.ScientificField;

@Service
public class SciFieldService {
	
	@Autowired
	ScientificFieldRepository sciRepository;
	
	public List<ScientificField> getAll(){
		return sciRepository.findAll();
	}
	
	public void addScientificArea(ScientificField s) {
		sciRepository.save(s);
	}
	
	public void updateScientificArea(ScientificField s) {
		sciRepository.save(s);
	}
	
	public ScientificField getScientificArea(long id) {
		return sciRepository.getOne(id);
	}
	
	public void deleteScientificArea(Long id) {
		sciRepository.deleteById(id);
	}
	
	public void deleteAllScientificAreas() {
		sciRepository.deleteAll();
	}
	
	public Boolean existsScientificArea(Long id) {
		return sciRepository.existsById(id);
	}
	
	public ScientificField mapDTO(SciFieldDto scientificAreaDTO){
			
		ScientificField scientificArea = new ScientificField();
		scientificArea.setName(scientificAreaDTO.getName());
			
		return scientificArea;
	}
	
	public SciFieldDto mapToDTO(ScientificField scientificArea){
		return new SciFieldDto(scientificArea);
	}
	
	public List<SciFieldDto> mapAllToDTO(){
		
		List<ScientificField> scientificareas = getAll();
		List<SciFieldDto> scientificAreaDTOs = new ArrayList<>();
		
		for(ScientificField r : scientificareas){
			scientificAreaDTOs.add(mapToDTO(r));
		}
		
		return scientificAreaDTOs;
	}
}
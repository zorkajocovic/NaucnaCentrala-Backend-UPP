package com.example.dto;

import java.util.ArrayList;

import com.example.model.Appuser;
import com.example.model.Magazine;
import com.example.model.ScientificField;

public class MagazineDto {

	private Long id;

	private AppUserDto creator;

	private String description;

	private boolean isopenaccess;

	private ArrayList<SciFieldDto> sciFieldDto;
	
	private String title;

	public MagazineDto() { }
	
	public MagazineDto(Magazine magazine) {
		this.setId(magazine.getId());
		this.setTitle(magazine.getTitle());
		this.setDescription(magazine.getDescription());
		this.setIsopenaccess(magazine.getIsopenaccess());
		this.setCreator(new AppUserDto(magazine.getAppuser()));
		
		this.sciFieldDto = new ArrayList<SciFieldDto>();
		for(ScientificField p : magazine.getScientificFields()){
			SciFieldDto sciFieldDto = new SciFieldDto(p);
			this.sciFieldDto.add(sciFieldDto);
		}	
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isIsopenaccess() {
		return isopenaccess;
	}

	public void setIsopenaccess(boolean isopenaccess) {
		this.isopenaccess = isopenaccess;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<SciFieldDto> getSciFieldDto() {
		return sciFieldDto;
	}

	public void setSciFieldDto(ArrayList<SciFieldDto> sciFieldDto) {
		this.sciFieldDto = sciFieldDto;
	}

	public AppUserDto getCreator() {
		return creator;
	}

	public void setCreator(AppUserDto creator) {
		this.creator = creator;
	}
}

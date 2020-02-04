package com.example.dto;

import com.example.model.ScientificField;

public class SciFieldDto {

	private Long id;

	private String name;

	public SciFieldDto() {
	}

	public SciFieldDto(ScientificField sciField) {
		this.setName(sciField.getName());
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

package com.example.camunda.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.model.ScientificField;
import com.example.services.SciFieldService;

@Component
public class AddSciFieldDto implements Converter<List<FieldDto>, Set<ScientificField>> {

	@Autowired
	SciFieldService scientificAreaService;
	
	@Override
	public Set<ScientificField> convert(List<FieldDto> source) {
		
		if(source == null) return null;
		
		Set<ScientificField> sciFields = new HashSet<ScientificField>();
		
		for(FieldDto field : source){
			if(field.getFieldId().equals("sciFields")){
				sciFields.add(scientificAreaService.getScientificArea(Long.parseLong(field.getFieldValue())));
			}
		}
		return sciFields;
	}
}

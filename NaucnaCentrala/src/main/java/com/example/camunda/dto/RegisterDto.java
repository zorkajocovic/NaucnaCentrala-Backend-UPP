package com.example.camunda.dto;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.dto.AppUserDto;
import com.example.model.Appuser;

@Component
public class RegisterDto implements Converter<List<FieldDto>, AppUserDto> {

	@Override
	public AppUserDto convert(List<FieldDto> source) {
		
		if(source == null)
			return null;
		
		AppUserDto user = new AppUserDto();
		
		for(FieldDto field : source){
			if(field.getFieldId().equals("name")){
				user.setName(field.getFieldValue());
			}
			if(field.getFieldId().equals("surname")){
				user.setSurname(field.getFieldValue());
			}
			if(field.getFieldId().equals("email")){
				user.setEmail(field.getFieldValue());
			}
			if(field.getFieldId().equals("password")){
				user.setPassword(field.getFieldValue());
			}
			if(field.getFieldId().equals("city")){
				user.setCity(field.getFieldValue());
			}
			if(field.getFieldId().equals("country")){
				user.setCountry(field.getFieldValue());
			}
			if(field.getFieldId().equals("role")){
				user.setRole(field.getFieldValue());
			}
			if(field.getFieldId().equals("username")){
				user.setUsername(field.getFieldValue());
			}
			if(field.getFieldId().equals("noSciFields")) {
				user.setNoOfSciFields(Long.parseLong(field.getFieldValue()));
			}
			if(field.getFieldId().equals("reviewer")) {
				user.setReviewer(Boolean.parseBoolean(field.getFieldValue()));
			}
		}		
		return user;
	}
}

package com.example.camunda.dto;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.dto.LoginRequestDto;

@Component
public class LoginDto implements Converter<List<FieldDto>, LoginRequestDto> {

	@Override
	public LoginRequestDto convert(List<FieldDto> source) {
		
		if(source == null)
			return null;
		
		LoginRequestDto user = new LoginRequestDto();
		
		for(FieldDto field : source){
			if(field.getFieldId().equals("username")){
				user.setUsername(field.getFieldValue());
			}
			if(field.getFieldId().equals("password")){
				user.setPassword(field.getFieldValue());
			}
		}
		return user;
		
	}
}

package com.example.security;

import com.example.model.Appuser;

public class TokenDto {
	private String token;

    
    public TokenDto() {
    	super();
    };
    
    public TokenDto(String token) {
		super();
		this.token = token;
    }

	public TokenDto(Appuser user)
    {
        this.token = user.getPassword();
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

package com.example.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.model.Appuser;
import com.example.services.AppUserService;
import io.jsonwebtoken.ExpiredJwtException;

public class AuthenticationTokenFiler extends UsernamePasswordAuthenticationFilter  {
	
    @Autowired
    private JWTUtils jwtTokenUtil;

    @Autowired
    private AppUserService userService;
    
    @Value("Authorization")
    private String tokenHeader;

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String requestHeader = httpRequest.getHeader(this.tokenHeader);
		String authToken = null;
		  if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
	            authToken = requestHeader.substring(7);
		//String authToken = httpRequest.getHeader("Authorization");
		String username = jwtTokenUtil.getUsernameFromToken(authToken);
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{			
			Appuser user = userService.getbyUsername(username);
			MyUserDetailsService customUserDetails = new MyUserDetailsService(user);
			
			if (jwtTokenUtil.validateToken(authToken)) {
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						customUserDetails, null, customUserDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		}
		chain.doFilter(request, response);
	}
}

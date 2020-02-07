package com.example.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.example.dto.AppUserDto;
import com.example.repositories.AppUserRepository;
import com.example.security.JWTUtils;
import com.example.security.MyUserDetailsService;
import com.example.security.TokenDto;
import com.example.model.Appuser;

@Service
public class AppUserService {

	@Autowired
	AppUserRepository userRepository;
	
	@Autowired
	IdentityService identityService;

	@Autowired
	JWTUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public List<Appuser> getAll(){
		return userRepository.findAll();
	}
	
	public void addUser(Appuser u) {
		userRepository.save(u);
	}
	
	public void updateUser(Appuser u) {
		userRepository.save(u);
	}
	
	public Appuser getOne(Long id) {
		return userRepository.getOne(id);
	}
	
	public Appuser getbyEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public Appuser getbyUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	
	public void deleteAllUser() {
		userRepository.deleteAll();
	}
	
	public Boolean existsUser(long id) {
		return userRepository.existsById(id);
	}

	public TokenDto generateToken(String username) {
		
		Appuser user = getbyUsername(username);
    	MyUserDetailsService customUserdetails = new MyUserDetailsService(user);
        String userToken = tokenUtils.generateToken(customUserdetails);            

        return new TokenDto(userToken);
	}
	
    public Appuser getCurrentUser() {
    	String currentUsername = "";
    	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	  if (!(auth instanceof AnonymousAuthenticationToken)) {
    		  currentUsername = auth.getName();
      		  System.out.println("username ulogovan: " + currentUsername);
    	  }
    	  else
      		  System.out.println(auth.getName());

          try {
              Long id = Long.parseLong(auth.getName());
             
              Optional<Appuser> user = userRepository.findById(id);
              Appuser ret = user.orElseGet(null);
              return ret;
          } catch (Exception e) {
              return null;
          }
    }

	public Appuser checkUser(Appuser checkLoggedIn) {
		Appuser found = userRepository.findByEmail(checkLoggedIn.getEmail());
		if(found!=null){
			if(checkLoggedIn.getPassword().equals(found.getPassword()))
				return found;
			else return null;			
		}else{
			return null;
		}
	}
	
	public Appuser mapDTO(AppUserDto userDto){
		
		Appuser user = new Appuser();
		
		user.setCity(userDto.getCity());
		user.setCountry(userDto.getCountry());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getEmail());
		user.setSurname(userDto.getSurname());
		user.setPassword(userDto.getPassword());
		user.setUsername(userDto.getUsername());
		user.setRole(userDto.getRole());
		return user;
	}
	
	public AppUserDto mapToDTO(Appuser user){
		return new AppUserDto(user);
	}
	
	public List<AppUserDto> mapAllToDTO(){
		
		List<Appuser> appusers = getAll();
		List<AppUserDto> appUserDTOs = new ArrayList<>();
		
		for(Appuser r : appusers){
			appUserDTOs.add(mapToDTO(r));
		}
		
		return appUserDTOs;
	}
}
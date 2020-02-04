package com.example.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.webapp.impl.security.auth.AuthenticationFilter;
import org.camunda.bpm.webapp.impl.security.auth.Authentications;
import org.camunda.bpm.webapp.impl.security.auth.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.example.dto.AppUserDto;
import com.example.repositories.AppUserRepository;

import com.example.model.Appuser;

@Service
public class AppUserService {

	@Autowired
	AppUserRepository userRepository;
	
	@Autowired
	IdentityService identityService;
	
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

	public void setCurrentUser(Appuser user) {
		/*Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("User"));
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(user.getId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);*/
        List<String> groups = new ArrayList<String>();
        groups.add(user.getRole());
        identityService.setAuthentication(user.getUsername(), groups, null);
        identityService.setAuthenticatedUserId(user.getUsername());		//log in to camunda engine
    }

    public Appuser getCurrentUser() {
    	/*Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        try {
            Long id = Long.parseLong(((Principal) auth).getName());
            return userRepository.findById(id).orElseGet(null);
        } catch (Exception e) {
            return null;
        }
        */
       // Authentications authentications = Authentications.getFromSession());
    	Authentication autenticated = identityService.getCurrentAuthentication();
    	if(autenticated != null) 
    		return userRepository.findByUsername(autenticated.getUserId());
    	else
    		return null;
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
package com.example.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.dto.AppUserDto;
import com.example.model.Appuser;

public class MyUserDetailsService implements UserDetails{
	
	private Appuser user;
	
	public MyUserDetailsService(Appuser user){
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority(user.getRole()));
		
		return roles;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	

}





/*implements UserDetailsService {

	@Autowired
	private AppUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Appuser userFromDB = userRepository.findByUsername(username);

		if (userFromDB == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}

		UserDto user = new UserDto();
		user.setIsEnabled(true);
		user.setPassword(userFromDB.getPassword());
		user.setUsername(userFromDB.getUsername());
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(userFromDB.getRole()));
		user.setAuthorities(authorities);

		return user;
	}

}*/

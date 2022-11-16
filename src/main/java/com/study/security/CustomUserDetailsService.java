package com.study.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// 실제 사용자 정보를 가져오는 역할
@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String encodedPw = passwordEncoder.encode(username + "pw");
		
		User user = new User(username, encodedPw, List.of());
		
		return user;
	}
}

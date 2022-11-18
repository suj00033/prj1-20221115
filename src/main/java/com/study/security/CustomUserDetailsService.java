package com.study.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.study.domain.member.MemberDto;
import com.study.mapper.member.MemberMapper;

// 실제 사용자 정보를 가져오는 역할
@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 실제 db 데이터
	@Autowired
	private MemberMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//                 mapper가 실제 db데이터의 유저 네임을 가져옴
		MemberDto member = mapper.selectById(username);
		
		if (member == null) {
			return null;
		}
		
		// 한멤버가 여러 권한을 가질 수 있음
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		if (member.getAuth() != null) {
			for (String auth : member.getAuth()) {
				authorityList.add(new SimpleGrantedAuthority(auth));
			}
		}
		
		//                                                        List.of는 권한(authorization) > auauthorityList로 교체
		User user = new User(member.getId(), member.getPassword(), authorityList);
		
		return user;
	}

}

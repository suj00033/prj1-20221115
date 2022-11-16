package com.study.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.domain.member.MemberDto;
import com.study.mapper.member.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public int insert(MemberDto member) {
		
		// pw를 평서문 말고 암호화로 인코딩해서 보안안전성 높임
		String pw = member.getPassword();
		
		member.setPassword(passwordEncoder.encode(pw));
		
		return mapper.insert(member);
	}

	public List<MemberDto> list() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}

	public MemberDto getById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectById(id);
	}

	public int modify(MemberDto member) {
		int cnt = 0;
		
		try {
			String encodedPw = passwordEncoder.encode(member.getPassword());
			member.setPassword(encodedPw);
			
			return mapper.update(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt; 
	}
	
	public int remove(String id) {
		return mapper.deleteById(id);
	}

	public MemberDto getByEmail(String email) {
		return mapper.selectByEmail(email);
	}

	public MemberDto getByNickName(String nickName) {
		return mapper.selectByNickName(nickName);
	}

}

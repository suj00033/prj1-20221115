package com.study.controller.member;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.domain.member.MemberDto;
import com.study.service.member.MemberService;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 포워드 요청은 get이랑 동일하기에 아무것도 안써도됨
	@GetMapping("login")
	public void login() {
		
	}
	
	// 닉네임 중복 확인
	@GetMapping("existNickName/{nickName}")
	@ResponseBody
	public Map<String, Object> existNickName(@PathVariable String nickName) {
		Map<String, Object> map = new HashMap<>();

		MemberDto member = service.getByNickName(nickName);

		if (member == null) {
			map.put("status", "not exist");
			map.put("message", "사용가능한 닉네임입니다.");
		} else {
			map.put("status", "exist");
			map.put("message", "이미 존재하는 닉네임입니다.");
		}

		return map;
	}
	
	// 이메일 중복 확인
	@PostMapping("existEmail")
	@ResponseBody
	public Map<String, Object> existEmail(@RequestBody Map<String, String> req) {

		Map<String, Object> map = new HashMap<>();

		MemberDto member = service.getByEmail(req.get("email"));

		if (member == null) {
			map.put("status", "not exist");
			map.put("message", "사용가능한 이메일입니다.");
		} else {
			map.put("status", "exist");
			map.put("message", "이미 존재하는 이메일입니다.");
		}

		return map;
	}

	@GetMapping("existId/{id}")
	@ResponseBody
	public Map<String, Object> existId(@PathVariable String id) {
		Map<String, Object> map = new HashMap<>();

		MemberDto member = service.getById(id);

		if (member == null) {
			map.put("status", "not exist");
			map.put("message", "사용가능한 아이디입니다.");
		} else {
			map.put("status", "exist");
			map.put("message", "이미 존재하는 아이디입니다.");
		}

		return map;
	}
	
	@GetMapping("signup")
	public void signup() {
		
	}
	
	@PostMapping("signup")
	public String signup(MemberDto member, RedirectAttributes rttr) {
		System.out.println(member);
		
		int cnt = service.insert(member);
		
		// 가입 잘되면
		rttr.addFlashAttribute("message", "회원가입 되었습니다.");
		return "redirect:/board/list";
	}
	
	@GetMapping("list")
	// 관리자만 회원목록 볼수있도록 설정, admin이라는 권한이 있는가
	@PreAuthorize("hasAuthority('admin')")
	public void list(Model model) {
		model.addAttribute("memberList", service.list());
	}
	
	@GetMapping({ "info", "modify" })
	// 로그인한 계정으로 자기 정보만 볼수있는 동시에 관리자는 모두 볼수있게(수정은 x)
	@PreAuthorize("hasAuthority('admin') or (authentication.name == #id)")
	public void info(String id, Model model) {

		model.addAttribute("member", service.getById(id));
	}
	
	@PostMapping("modify")
	// 로그인한 계정으로 자기 정보만 볼수있도록
	@PreAuthorize("authentication.name == #member.id")
	public String modify(MemberDto member, String oldPassword, RedirectAttributes rttr) {
		// 회원 정보 수정 시 전 암호를 입력하여 수정하기
		MemberDto oldMember = service.getById(member.getId());
		
		rttr.addAttribute("id", member.getId());
		boolean passwordMatch = passwordEncoder.matches(oldPassword, oldMember.getPassword());
		if (passwordMatch) {
			// 기존 암호가 맞으면 회원 정보 수정
			int cnt = service.modify(member);

			if (cnt == 1) {
				rttr.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
				return "redirect:/member/info";
			} else {
				rttr.addFlashAttribute("message", "회원 정보가 수정되지 않았습니다.");
				return "redirect:/member/modify";
			}
		} else {
			rttr.addFlashAttribute("message", "암호가 일치하지 않습니다.");
			return "redirect:/member/modify";
		}

	}
	
	@PostMapping("remove")
	public String remove(String id, String oldPassword, RedirectAttributes rttr, HttpServletRequest request)
			throws Exception {
		MemberDto oldmember = service.getById(id);

		boolean passwordMatch = passwordEncoder.matches(oldPassword, oldmember.getPassword());

		if (passwordMatch) {
			service.remove(id);

			rttr.addFlashAttribute("message", "회원 탈퇴하였습니다.");
			request.logout();

			return "redirect:/board/list";

		} else {
			rttr.addAttribute("id", id);
			rttr.addFlashAttribute("message", "암호가 일치하지 않습니다.");
			return "redirect:/member/modify";
		}

	}
}












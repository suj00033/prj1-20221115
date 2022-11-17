package com.study.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.domain.board.ReplyDto;
import com.study.service.board.ReplyService;

@Controller
@RequestMapping("reply")
public class ReplyController {
	
	@Autowired
	private ReplyService service;
	
	// 댓글 수정하기
	@PutMapping("modify")
	@ResponseBody
	// 권한                                                      argument쓸때는 #
	@PreAuthorize("@replySecurity.checkWriter(authentication.name, #reply.id)")
	public Map<String, Object> modify(@RequestBody ReplyDto reply) {
		Map<String, Object> map = new HashMap<>();
		
		int cnt = service.modify(reply);
		
		if (cnt == 1) {
			map.put("message", "댓글이 수정되었습니다.");
		} else {
			map.put("message", "댓글이 수정되지 않았습니다.");
		}
		
		return map;
	}
	
	// 댓글 가져오기	
	@GetMapping("get/{id}")
	@ResponseBody
	public ReplyDto get(@PathVariable int id) {
		return service.getById(id);
	}
	
	// 댓글 삭제
	@DeleteMapping("remove/{id}")
	@ResponseBody
	@PreAuthorize("@replySecurity.checkWriter(authentication.name, #id)")
	public Map<String, Object> remove(@PathVariable int id) {
		Map<String, Object> map = new HashMap<>();
		
		int cnt = service.removeById(id);
		if (cnt == 1) {
			map.put("message" ,"댓글이 삭제되었습니다.");
		} else {
			map.put("message", "댓글이 삭제되지 않았습니다.");
		}
		
		return map;
	}
	
	@GetMapping("list/{boardId}")
	@ResponseBody
	public List<ReplyDto> list(@PathVariable int boardId) {
		return service.listReplyByBoardId(boardId);
	}

	@PostMapping("add")
	@ResponseBody
	// 로그인한 사람들만 댓글을 달수 있도록
	@PreAuthorize("isAuthenticated()")
	public Map<String, Object> add(@RequestBody ReplyDto reply, Authentication authentication) {
//		System.out.println(reply);                              로그인한 사람의 유저정보를 넣어주는 역할
		
		// 로그인이 되어있으면 로그인한 회원 id를 가져옴
		if (authentication != null) {
			reply.setWriter(authentication.getName());
		}
		
		Map<String, Object> map = new HashMap<>();
		
		int cnt = service.addReply(reply);
		if (cnt == 1) {
			map.put("message", "새 댓글이 등록되었습니다.");
		} else {
			map.put("message", "새 댓글이 등록되지 않았습니다.");
		}
		
		return map;
	}
	
}

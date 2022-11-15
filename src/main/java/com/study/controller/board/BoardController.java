package com.study.controller.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.domain.board.BoardDto;
import com.study.domain.board.PageInfo;
import com.study.service.board.BoardService;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private BoardService service;

	// 게시물 등록
	@GetMapping("register")
	public void register() {
		// 게시물 작성 view로 포워드
		// /WER-INF/views/board/register.jsp
	}
	
	// 등록한글 보여줌
	@PostMapping("register")
	public String register(
			BoardDto board,
			MultipartFile[] files,
			RedirectAttributes rttr) {
		// * 파일업로드
		// 1. web.xml 
		//    dispatcherServlet 설정에 multipart-config 추가
		// 2. form 에 enctype="multipart/form-data" 속성 추가 
		// 3. Controller의 메소드 argument type : MultipartFile 
		
		// request param 수집/가공
//		System.out.println(files.length);
//		for (MultipartFile file : files) {
//			System.out.println(file.getOriginalFilename());
//		}
		
		
		// business logic
		int cnt = service.register(board, files);
		
		if (cnt == 1) {
			rttr.addFlashAttribute("message", "새 게시물이 등록되었습니다.");
		} else {
			rttr.addFlashAttribute("message", "새 게시물이 등록되지 않았습니다.");
		}
		
		// /board/list로 redirect
		return "redirect:/board/list";
	}
		
	// 게시물 목록으로 redirect
	@GetMapping("list")            // 페이지가 없으면 1로
	public void list(
			@RequestParam(name="page", defaultValue = "1") int page,
			@RequestParam(name = "t", defaultValue = "all") String type,
			@RequestParam(name="q", defaultValue = "") String keyword, // 검색 String q(keyword)
			PageInfo pageInfo, // Model이 생략되어있음
			Model model) {
		// request param 수집/가공
		
		// 비지니스 로직(서비스)
		List<BoardDto> list = service.listBoard(page, type, keyword, pageInfo);
		
		// add attribute
		model.addAttribute("boardList", list);
		// forward
	}
	
	// 게시글 글 보기
	@GetMapping("get")
	public void get(
			//@RequestParam 생략가능 (request 수집가공)
			@RequestParam(name="id") int id,
						Model model) {
		// 비지니스 로직 (게시물 db에서 가져오기)
		BoardDto board = service.get(id);
//		System.out.println(board);
		// add attribute
		model.addAttribute("board", board);
		// forward / redirect
		
	}
	
	@GetMapping("modify")
	public void modify(int id, Model model) {
		BoardDto board = service.get(id);
		model.addAttribute("board", board);
		
	}
	
	@PostMapping("modify")
	public String modify(
			BoardDto board,
			@RequestParam("files") MultipartFile[] addfiles,
			@RequestParam(name = "removeFiles", required = false) List<String> removeFiles,
			RedirectAttributes rttr) {
		
		int cnt = service.update(board, addfiles, removeFiles);
		
		if (cnt == 1) {
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되었습니다.");
		} else {
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되지 않았습니다.");
		}
		
		return "redirect:/board/list";
	}
	
	// 게시글 글 지우기
	@PostMapping("remove")
	public String remove(int id, RedirectAttributes rttr) {
		int cnt = service.remove(id);
		
		if(cnt == 1) {
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");
		} else {
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되지 않았습니다.");
		}
		
		return "redirect:/board/list";
	}
	
	
}

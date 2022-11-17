package com.study.mapper.board;

import java.util.List;

import com.study.domain.board.BoardDto;

public interface BoardMapper {
	
	int insert(BoardDto board);

	List<BoardDto> list(int offset, int records, String type, String keyword);

	BoardDto select(int id, String username);

	int update(BoardDto board);

	int delete(int id);

	int countAll(String type, String keyword);

	int insertFile(int id, String fileName);
	
	// 파일 삭제
	int deleteFileByBoardId(int id);

	int deleteFileBoardIdAndFileName(int id, String fileName);
	
	// 좋아요
	int getLikeByBoardIdAndMemberId(String boardId, String memberId);

	int deleteLike(String boardId, String memberId);

	int insertLike(String boardId, String memberId);

	int countLikeByBoardId(String boardId);
	
	// selete(id)를 호출해서 쓰는 메소드를 해결하기 위해 만듦
	default BoardDto select(int id) {
		return select(id, null);
	}
	
}

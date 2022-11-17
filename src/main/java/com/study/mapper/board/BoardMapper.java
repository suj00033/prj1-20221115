package com.study.mapper.board;

import java.util.List;

import com.study.domain.board.BoardDto;

public interface BoardMapper {
	
	int insert(BoardDto board);

	List<BoardDto> list(int offset, int records, String type, String keyword);

	BoardDto select(int id);

	int update(BoardDto board);

	int delete(int id);

	int countAll(String type, String keyword);

	int insertFile(int id, String fileName);
	
	// 파일 삭제
	int deleteFileByBoardId(int id);

	int deleteFileBoardIdAndFileName(int id, String fileName);
	
	// 좋아요
	int getLikeByBoardIdAndMemberId(String boardId, String memberId);

	void deleteLike(String boardId, String memberId);

	void insertLike(String boardId, String memberId);

	int countLikeByBoardId(String boardId);
	
}

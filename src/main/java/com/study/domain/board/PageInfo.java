package com.study.domain.board;

import lombok.Data;

// 마지막 페이지 처리
@Data
public class PageInfo {
	 private boolean hasPreButton;
	 private boolean hasNextButton;
	 private int jumpPrePageNumber;
	 private int jumpNextPageNumber;
	 private int lastPageNumber;
	 private int leftPageNumber;
	 private int rightPageNumber;
	 private int currentPageNumber;
	 
	
}

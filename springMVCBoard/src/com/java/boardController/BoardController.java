package com.java.boardController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.java.boardService.BoardService;

public class BoardController extends MultiActionController{
	private BoardService boardService;
	
	public void BoardController() {}

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	public void test(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("OK");
	}
}

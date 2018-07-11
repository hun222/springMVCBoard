package com.java.boardController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.java.boardDTO.BoardDTO;
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
	
	public ModelAndView boardWrite(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("request",request);
		
		boardService.boardWrite(mv);
		
		return mv;
	}
	
	public ModelAndView boardWriteOk(HttpServletRequest request, HttpServletResponse response, BoardDTO boardDTO) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("request",request);
		mv.addObject("boardDTO",boardDTO);
		
		boardService.boardWriteOk(mv);
		
		return mv;
	}
}

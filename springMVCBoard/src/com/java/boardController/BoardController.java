package com.java.boardController;

import java.io.UnsupportedEncodingException;

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
	
	public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response, BoardDTO boardDTO) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("request",request);
		mv.addObject("boardDTO",boardDTO);
		
		boardService.boardList(mv);
		
		return mv;
	}
	
	public ModelAndView boardRead(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("request",request);
		
		boardService.boardRead(mv);
		
		return mv;
	}
	
	public ModelAndView boardDelete(HttpServletRequest request, HttpServletResponse response) {
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("boardNumber",boardNumber);
		mv.addObject("pageNumber",pageNumber);
		mv.setViewName("board/delete");
		
		return mv;
	}
	
	public ModelAndView boardDeleteOk(HttpServletRequest request, HttpServletResponse response) {
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		String password = request.getParameter("password");
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("request",request);
		mv.addObject("boardNumber",boardNumber);
		mv.addObject("pageNumber",pageNumber);
		mv.addObject("password",password);
		
		boardService.boardDeleteOk(mv);
		
		return mv;
	}
	
	public ModelAndView boardUpdate(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("request",request);
		
		
		boardService.boardUpdate(mv);
		
		return mv;
	}
}

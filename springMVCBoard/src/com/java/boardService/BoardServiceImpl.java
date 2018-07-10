package com.java.boardService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.java.boardDAO.BoardDAO;

public class BoardServiceImpl implements BoardService {
	private BoardDAO boardDAO;
	
	public BoardServiceImpl() {}

	public BoardServiceImpl(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}

	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}

	@Override
	public void boardWrite(ModelAndView mv) {
		int boardNumber = 0;	// root 0, 답글은 부모의 글번호를 가져온다.
		int groupNumber = 1;
		int sequenceNumber = 0;
		int sequenceLevel = 0;
		
		Map<String, Object> map = mv.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		if(request.getParameter("boardNumber")!=null) {
			System.out.println("boardNumber: "+boardNumber);
			boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
			groupNumber = Integer.parseInt(request.getParameter("groupNumber"));
			sequenceNumber = Integer.parseInt(request.getParameter("sequenceNumber"));
			sequenceLevel = Integer.parseInt(request.getParameter("sequenceLevel"));
		}
		
		mv.addObject("boardNumber", boardNumber);
		mv.addObject("groupNumber", groupNumber);
		mv.addObject("sequenceNumber", sequenceNumber);
		mv.addObject("sequenceLevel", sequenceLevel);
		
		mv.setViewName("/WEB-INF/view/board/write.jsp");
	}
	
}

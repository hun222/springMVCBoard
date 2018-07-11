package com.java.boardService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.java.aop.HomeAspect;
import com.java.boardDAO.BoardDAO;
import com.java.boardDTO.BoardDTO;

//Command와 같은 역할
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

	@Override
	public void boardWriteOk(ModelAndView mv) {
		Map<String, Object> map = mv.getModelMap();
		
		BoardDTO boardDTO = (BoardDTO)map.get("boardDTO");
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		HttpServletResponse response = (HttpServletResponse)map.get("response");
		
		HomeAspect.logger.info(HomeAspect.logMsg+boardDTO.toString());
		
		boardWriteNumber(boardDTO);
		
		boardDTO.setWriteDate(new Date());
		boardDTO.setReadCount(0);
		
		int chk = boardDAO.boardWrite(boardDTO);
		HomeAspect.logger.info(HomeAspect.logMsg+chk);
	}
	
	public void boardWriteNumber(BoardDTO boardDTO) {
		int boardNumber = boardDTO.getBoardNumber();	// 0 1 0 0
		int groupNumber = boardDTO.getGroupNumber();
		int sequenceNumber = boardDTO.getSequenceNumber();
		int sequenceLevel = boardDTO.getSequenceLevel();
		int max = 0;
		
		if(boardNumber==0) {
			//root 
			max = boardDAO.boardGroupNumberMax();
			HomeAspect.logger.info(HomeAspect.logMsg + max);
			boardDTO.setGroupNumber(max);
		}else {
			//reply
			Map<String, Integer> map = new HashMap();
			map.put("groupNumber", groupNumber);
			map.put("sequenceNumber", sequenceNumber);
			
			int chk = boardDAO.boardWriteNumber(map);
			HomeAspect.logger.info(HomeAspect.logMsg + chk);
			
			sequenceNumber = sequenceNumber+1;
			sequenceLevel = sequenceLevel+1;

			boardDTO.setSequenceNumber(sequenceNumber);
			boardDTO.setSequenceLevel(sequenceLevel);
		}
	}
}

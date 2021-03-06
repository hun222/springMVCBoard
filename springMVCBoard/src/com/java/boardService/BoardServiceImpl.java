package com.java.boardService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
		
		mv.setViewName("board/write");
	}

	@Override
	public void boardWriteOk(ModelAndView mv) {
		Map<String, Object> map = mv.getModelMap();
		
		BoardDTO boardDTO = (BoardDTO)map.get("boardDTO");
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		HomeAspect.logger.info(HomeAspect.logMsg+boardDTO.toString());
		
		boardWriteNumber(boardDTO);
		
		boardDTO.setWriteDate(new Date());
		boardDTO.setReadCount(0);
		
		int chk = boardDAO.boardWrite(boardDTO);
		HomeAspect.logger.info(HomeAspect.logMsg+chk);
		
		mv.addObject("chk",chk);
		mv.setViewName("board/writeOk");
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
			
			boardDTO.setGroupNumber(max+1);
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

	@Override
	public void boardList(ModelAndView mv) {
		Map<String, Object> map = mv.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		String pageNumber=request.getParameter("pageNumber");
		if(pageNumber==null) pageNumber="1";
		
		int boardSize = 10;
		int currentPage = Integer.parseInt(pageNumber);
		int startRow = (currentPage-1)*boardSize+1;	
		int endRow = currentPage*boardSize;
		
		int count = boardDAO.boardCount();
		HomeAspect.logger.info(HomeAspect.logMsg + count);
		
		List<BoardDTO> boardList = null;
		if(count > 0) {
			boardList = boardDAO.boardList(startRow, endRow);
			HomeAspect.logger.info(HomeAspect.logMsg + boardList.size());
		}
		mv.addObject("boardSize", boardSize);
		mv.addObject("currentPage", currentPage);
		mv.addObject("count", count);
		mv.addObject("boardList", boardList);
		
		mv.setViewName("board/list");
	}

	@Override
	public void boardRead(ModelAndView mv) {
		Map<String, Object> map = mv.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		BoardDTO boardDTO = boardDAO.boardRead(boardNumber);
		HomeAspect.logger.info(HomeAspect.logMsg+boardDTO.toString());
		
		mv.addObject("boardDTO",boardDTO);
		mv.addObject("pageNumber",pageNumber);
		mv.setViewName("board/read");
	}

	@Override
	public void boardDeleteOk(ModelAndView mv) {
		Map<String, Object> map = mv.getModelMap();
		int boardNumber = (int)map.get("boardNumber");
		int pageNumber = (int)map.get("pageNumber");
		String password = (String)map.get("password");
		HomeAspect.logger.info(HomeAspect.logMsg+boardNumber+","+pageNumber+","+password);
		
		int chk = boardDAO.boardDelete(boardNumber, password);
		HomeAspect.logger.info(HomeAspect.logMsg+chk);
		
		mv.addObject("chk",chk);
		mv.addObject("pageNumber",pageNumber);
		mv.setViewName("board/deleteOk");
		
	}

	@Override
	public void boardUpdate(ModelAndView mv) {
		Map<String, Object> map = mv.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		BoardDTO boardDTO = boardDAO.boardUpRead(boardNumber);
		HomeAspect.logger.info(HomeAspect.logMsg+boardDTO.toString());
		
		mv.addObject("boardDTO",boardDTO);
		mv.addObject("pageNumber",pageNumber);
		mv.setViewName("board/update");
	}

	@Override
	public void boardUpdateOk(ModelAndView mv) {
		Map<String, Object> map = mv.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		BoardDTO boardDTO = (BoardDTO)map.get("boardDTO");
		HomeAspect.logger.info(HomeAspect.logMsg+boardDTO.toString());

		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		int count = boardDAO.boardUpdate(boardDTO);
		HomeAspect.logger.info(HomeAspect.logMsg+count);
		
		mv.addObject("count",count);
		mv.addObject("pageNumber",pageNumber);
		mv.addObject("boardNumber",boardNumber);
		mv.setViewName("board/updateOk");
	}
}

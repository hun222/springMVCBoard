package com.java.boardDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.java.aop.HomeAspect;
import com.java.boardDTO.BoardDTO;

public class BoardDAOImpl implements BoardDAO {
	// MyBatis 주입
	private SqlSessionTemplate sqlSession;
	
	public BoardDAOImpl() {}

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	public BoardDAOImpl(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int boardGroupNumberMax() {
		return sqlSession.selectOne("boardGroupNumberMax");
	}

	@Override
	public int boardWriteNumber(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return sqlSession.update("boardWriteNumber", map);
	}

	@Override
	public int boardWrite(BoardDTO boardDTO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("boardInsert", boardDTO);
	}

	@Override
	public int boardCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("countRow");
	}

	@Override
	public List<BoardDTO> boardList(int startRow, int endRow) {
		List<BoardDTO> boardList = null;
		Map<String, Integer> map = new HashMap();
		
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		boardList = sqlSession.selectList("boardList", map);
		
		return boardList;
	}

	@Override
	public BoardDTO boardRead(int boardNumber) {
		BoardDTO boardDTO = null;
		// Transaction 작업 나중에 해야한다.
		int chk = sqlSession.update("boardReadCount",boardNumber);
		if(chk>0) {
			boardDTO=sqlSession.selectOne("boardRead",boardNumber);
		}
		
		return boardDTO;
	}

	@Override
	public int boardDelete(int boardNumber, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("boardNumber", boardNumber);
		map.put("password", password);
		
		HomeAspect.logger.info(HomeAspect.logMsg+boardNumber+","+password);
		
		int chk = sqlSession.delete("deleteBoard", map);
		return chk;
	}

	@Override
	public BoardDTO boardUpRead(int boardNumber) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("boardRead", boardNumber);
	}
	
}

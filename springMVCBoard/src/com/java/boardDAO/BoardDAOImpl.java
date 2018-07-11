package com.java.boardDAO;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

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
	
}

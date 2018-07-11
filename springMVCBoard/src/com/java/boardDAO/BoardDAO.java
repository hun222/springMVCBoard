package com.java.boardDAO;

import java.util.Map;

import com.java.boardDTO.BoardDTO;

public interface BoardDAO {

	int boardGroupNumberMax();

	int boardWriteNumber(Map<String, Integer> map);

	int boardWrite(BoardDTO boardDTO);

}

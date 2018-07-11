package com.java.boardDAO;

import java.util.List;
import java.util.Map;

import com.java.boardDTO.BoardDTO;

public interface BoardDAO {

	int boardGroupNumberMax();

	int boardWriteNumber(Map<String, Integer> map);

	int boardWrite(BoardDTO boardDTO);

	int boardCount();

	List<BoardDTO> boardList(int startRow, int endRow);

	BoardDTO boardRead(int boardNumber);

	int boardDelete(int boardNumber, String password);

	BoardDTO boardUpRead(int boardNumber);

	int boardUpdate(BoardDTO boardDTO);

}

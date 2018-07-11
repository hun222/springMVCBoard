package com.java.boardService;

import org.springframework.web.servlet.ModelAndView;

public interface BoardService {

	void boardWrite(ModelAndView mv);

	void boardWriteOk(ModelAndView mv);

	void boardList(ModelAndView mv);

	void boardRead(ModelAndView mv);

	void boardDeleteOk(ModelAndView mv);

	void boardUpdate(ModelAndView mv);

	void boardUpdateOk(ModelAndView mv);

}

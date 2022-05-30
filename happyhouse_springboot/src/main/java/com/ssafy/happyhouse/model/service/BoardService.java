package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.BoardDTO;

public interface BoardService {
	public List<BoardDTO> retrieveBoard();
	public BoardDTO detailBoard(int articleno);
	public boolean writeBoard(BoardDTO board);
	public boolean updateBoard(BoardDTO board);
	public boolean deleteBoard(int articleno);
}

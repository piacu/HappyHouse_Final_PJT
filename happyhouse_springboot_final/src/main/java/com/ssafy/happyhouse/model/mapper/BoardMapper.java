package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.BoardDTO;

@Mapper
public interface BoardMapper {
	public List<BoardDTO> selectBoard();
	public BoardDTO selectBoardByNo(int articleno);
	public int insertBoard(BoardDTO board);
	public int updateBoard(BoardDTO board);
	public int deleteBoard(int articleno);
}

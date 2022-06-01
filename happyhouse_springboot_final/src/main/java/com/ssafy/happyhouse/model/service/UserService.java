package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.UserDTO;

public interface UserService {
	// User
	public UserDTO loginUser(String id, String pw) throws SQLException;
	public void insertUser(UserDTO c) throws SQLException;
	public void deleteUser(String id) throws SQLException;
	public void updateUser(UserDTO c) throws SQLException;
	public UserDTO viewUserId(String id) throws SQLException;
	
	// UserInter
	public void insertUserInter(Object o);
	public List<HouseInfoDto> viewUserInterId(String id) ;
	public void deleteUserInter(String id, String aptCode);

}

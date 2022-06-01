package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.UserDTO;
import com.ssafy.happyhouse.model.UserInterDTO;

public interface UserService {
	// User
	public void insertUser(UserDTO c) throws SQLException;
	public UserDTO viewUserId(String id) throws SQLException;
	public List<UserDTO> viewUser() throws SQLException;
	public void deleteUser(String id) throws SQLException;
	public void updateUser(UserDTO c) throws SQLException;
	public UserDTO loginUser(String id, String pw) throws SQLException ;
	public String findPW(String id, String email) throws SQLException;
	
	// UserInter
	public void insertUserInter(Object o);
	public List<HouseInfoDto> viewUserInterId(String id) ;
	public void viewUserInter() ;
	public void deleteUserInter(String id, String aptCode);
	public void viewAptUserInter(String id);
}

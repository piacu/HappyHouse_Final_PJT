package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.UserDTO;
import com.ssafy.happyhouse.model.UserInterDTO;
import com.ssafy.happyhouse.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public void insertUser(UserDTO c) throws SQLException {
		userMapper.insertUser(c);
	}

	@Override
	public UserDTO viewUserId(String id) throws SQLException {
		return userMapper.viewUserId(id);
	}

	@Override
	public List<UserDTO> viewUser() throws SQLException {
		return userMapper.viewUser();
	}

	@Override
	public void deleteUser(String id) throws SQLException {
		userMapper.deleteUser(id);
	}

	@Override
	public void updateUser(UserDTO c) throws SQLException {
		userMapper.updateUser(c);
	}

	@Override
	public UserDTO loginUser(String id, String pw) throws SQLException {
		return userMapper.loginUser(id, pw);
	}

	@Override
	public String findPW(String id, String email) throws SQLException {
		return userMapper.findPW(id, email);
	}

	@Override
	public void insertUserInter(Object data) {
		userMapper.insertUserInter(data);
	}

	@Override
	public List<HouseInfoDto> viewUserInterId(String id) {
		return userMapper.viewUserInterId(id);
	}

	@Override
	public void viewUserInter() {
		userMapper.viewUserInter();
	}

	@Override
	public void deleteUserInter(String id, String aptCode) {
		userMapper.deleteUserInter(id, aptCode);
	}

	@Override
	public void viewAptUserInter(String id) {
		userMapper.viewAptUserInter(id);
	}

}

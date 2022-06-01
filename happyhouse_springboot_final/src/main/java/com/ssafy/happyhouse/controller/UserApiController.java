package com.ssafy.happyhouse.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SocketDTO;
import com.ssafy.happyhouse.model.UserDTO;
import com.ssafy.happyhouse.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@Api("User Controller V1")
public class UserApiController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	@Autowired
	private UserService userservice;
	
	//로그인
	@GetMapping(value="/user")
	@ApiOperation(value = "로그인, 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", notes = "아이디와 비밀번호로 로그인", response = List.class)
	private ResponseEntity<?> userLogin(@RequestParam("id") String id, @RequestParam("password") String pw){
		try {
			UserDTO user = userservice.loginUser(id, pw);
			if(user!=null) {
				System.out.println("로그인 성공");
				return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
			}else {
				System.out.println("로그인 실패");
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
			
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//회원가입
	@PostMapping(value = "/user")
	@ApiOperation(value = "회원가입, DB입력 성공하면 'success' 문자열을 반환한다.", notes = "유저 회원가입")
	/* RequestBody 일 때, 
	 {
	    "id": "testest",
	    "password": "testest",
	    "name": "testest",
	    "email": "eee@.ee.e",
	    "age": 24
	 }
	 */
	public ResponseEntity<?> userRegister(@RequestBody UserDTO u) {
		try {
			System.out.println(u.toString());
			userservice.insertUser(u);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}
	
	//회원 삭제
	@DeleteMapping(value="/user")
	@ApiOperation(value = "회원탈퇴, DB삭제 성공하면 'success' 문자열을 반환한다.", notes = "id를 통한 회원 탈퇴")
	public ResponseEntity<?> userDelete(@RequestParam("id") String id){
		try {
			userservice.deleteUser(id);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//회원 수정
	@PutMapping(value="/user")
	@ApiOperation(value = "회원수정, DB수정 성공하면 해당 User 정보를 반환한다.", notes = "회원 수정")
	public ResponseEntity<?> userModify(@RequestBody UserDTO u) {
		try {
			userservice.updateUser(u);
			UserDTO user = userservice.viewUserId(u.getId());
			return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
		
	}
	
	// 찜한 매물 추가하기
	@ApiOperation(value = "유저마다 관심 아파트 매물을 저장한다.", response = List.class)
	@PostMapping(value = "/userinter")
	public ResponseEntity<?> insertuserinter(@RequestBody Object data) {
		try {
			if(data!=null) {
				System.out.println(data.toString());
				userservice.insertUserInter(data);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "해당 유저가 관심있는 아파트 매물 리스트를 반환한다.", response = List.class)
	@GetMapping(value = "/userinterlist/{id}")
	public ResponseEntity<?> userinterlist(@PathVariable("id") String id) {
		try {
			System.out.println(id);
			UserDTO user = userservice.viewUserId(id);
			if(user!=null) {
				return new ResponseEntity<List<HouseInfoDto>>(userservice.viewUserInterId(user.getId()), HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "해당 유저가 아파트코드에 해당하는 아파트 관심 정보를 삭제한다.", response = List.class)
	@DeleteMapping(value = "/userinter/{id}/{aptCode}")
	public ResponseEntity<?> deleteuserinter(@PathVariable("id") String id, @PathVariable("aptCode") String aptCode) {
		try {
			System.out.println(id +" " + aptCode);
			UserDTO user = userservice.viewUserId(id);
			if(user!=null) {
				userservice.deleteUserInter(id, aptCode);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<?> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/receive")
    // /send로 메시지를 반환합니다.
    @SendTo("/send")
    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.    
    public SocketDTO SocketHandler(SocketDTO socketDTO) {
    	// vo에서 getter로 userName을 가져옵니다.
        String userName = socketDTO.getUserName();
        // vo에서 setter로 content를 가져옵니다.
        String content = socketDTO.getContent();

        // 생성자로 반환값을 생성합니다.
        SocketDTO result = new SocketDTO(userName, content);
        // 반환
        return result;
    }
}

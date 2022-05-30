package com.ssafy.happyhouse.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import lombok.AllArgsConstructor;
import lombok.Data;

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
	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호로 로그인")
	private ResponseEntity<?> userLogin(@RequestParam("id") String id, @RequestParam("password") String pw, HttpServletRequest request ){
		Map<String, Object> map = new HashMap();
		try {
			UserDTO user = userservice.loginUser(id, pw);
			if(user!=null) {
				HttpSession session = request.getSession();
				session.setAttribute("logInfo", user);
				map.put("resMsg", "로그인 성공");
				System.out.println("로그인 성공");
				return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
			}else {
				map.put("resMsg", "로그인 실패");
				System.out.println("로그인 실패");
				return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
			}
			
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//로그아웃 어더케 해야될가~
	
	
	
	//회원가입
	@PostMapping(value = "/user")
	@ApiOperation(value = "회원가입", notes = "유저 회원가입")
	/* RequestBody 일 때, 
	 {
	    "id": "testest",
	    "password": "testest",
	    "name": "testest",
	    "email": "eee@.ee.e",
	    "age": 24
	 }
	 ModelAttribute로 받으면 http://localhost:80/api/user?id=? 이런 식으로!!
	 */
	public ResponseEntity<?> userRegister(@RequestBody UserDTO u) {
		try {
			System.out.println(u.toString());
			userservice.insertUser(u);
			Map<String, Object> map = new HashMap();
			map.put("resMsg", "회원등록 성공");
			map.put("users", userservice.viewUser());
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}
	
	//회원 삭제
	@DeleteMapping(value="/user")
	@ApiOperation(value = "회원탈퇴", notes = "id를 통한 회원 탈퇴")
	public ResponseEntity<?> userDelete(@RequestParam("id") String id, HttpServletRequest request){
		try {
			userservice.deleteUser(id);
			Map<String, Object> map = new HashMap();
			map.put("resMsg", "회원삭제 성공");
			map.put("users", userservice.viewUser());
			HttpSession session = request.getSession();
			session.invalidate();
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//회원 수정
	@PutMapping(value="/user")
	@ApiOperation(value = "회원수정", notes = "회원 수정")
	public ResponseEntity<?> userModify(@RequestBody UserDTO u, HttpServletRequest request) {
		Map<String, Object> map = new HashMap();
		try {
			userservice.updateUser(u);
			UserDTO user = userservice.viewUserId(u.getId());
			map.put("resMsg", "회원수정 성공");
			map.put("user", userservice.viewUserId(u.getId()));
			HttpSession session = request.getSession();
			session.setAttribute("logInfo", user);
			System.out.println(u.toString());
			//return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
			return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
		
	}
	
	// 찜한 매물 추가하기
//	@PostMapping(value = "/userinter")
//	public ResponseEntity<?> insertuserinter(@RequestParam("id") Object id, @RequestParam("aptCode") Object aptCode) {
//		try {
//			System.out.println(id +" " + aptCode);
////			UserDTO user = userservice.viewUserId(id);
////			if(user!=null) {
////				userservice.insertUserInter(id, aptCode);
////				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
////			}
//			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
	@PostMapping(value = "/userinter")
	public ResponseEntity<?> insertuserinter(@RequestBody Object data) {
		try {
//			System.out.println(id +" " + aptCode);
//			UserDTO user = userservice.viewUserId(id);
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
	
	//비밀번호 찾기
//	@GetMapping(value="/user")
//	@ApiOperation(value = "비밀번호 찾기", notes = "id, email을 통해 비밀번호 찾기")
//	public ResponseEntity<?> findpassword(@RequestParam("id") String id, @RequestParam("email") String email){
//		try {
//			Map<String, Object> map = new HashMap();
//			String pw = userservice.findPW(id, email);
//			if(pw!=null) {
//				map.put("pw", "비밀번호는 " + pw  + "입니다.");
//			}else {
//				map.put("pw", "등록된 정보가 없습니다.");
//			}
//			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
//		} catch (SQLException e) {
//			return exceptionHandling(e);
//		}
//	}
	
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

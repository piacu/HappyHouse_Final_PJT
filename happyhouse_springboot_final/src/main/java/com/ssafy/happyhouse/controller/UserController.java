package com.ssafy.happyhouse.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.happyhouse.model.service.UserService;
import com.ssafy.happyhouse.model.UserDTO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	//비밀번호 찾기 페이지로 이동
	@RequestMapping(value="/mvfindpw", method= {RequestMethod.GET, RequestMethod.POST})
	private String mvfindpw() {
		return "user/userFindPW";
	}
	
	//회원가입 페이지로 이동
	@RequestMapping(value="/mvregmember", method= {RequestMethod.GET, RequestMethod.POST})
	private String mvregmember() {
		return "user/userRegister";
	}
	
	//로그인 페이지로 이동
	@RequestMapping(value="/mvlogin", method= {RequestMethod.GET, RequestMethod.POST})
	private String mvlogin() {
		return "user/userLogin";
	}
	
	//회원 정보 수정,삭제 페이지로 이동
	@RequestMapping(value="/mvlogInfo", method= {RequestMethod.GET, RequestMethod.POST})
	private String mvlogInfo() {
		return "user/userChangeInfo";
	}
	
	//로그인
	@RequestMapping(value="/login", method= {RequestMethod.GET, RequestMethod.POST})
	private String login(@RequestParam("id") String id, @RequestParam("password") String pw, HttpServletRequest request) {
		try {
			UserDTO user = userservice.loginUser(id, pw);
			if(user!=null) {
				HttpSession session = request.getSession();
				session.setAttribute("logInfo", user);
				System.out.println("로그인 성공!");
				return "redirect:/";
			}else {
				request.setAttribute("msg", "아이디 또는 패스워드가 다릅니다. 다시 로그인해 주세요");
				System.out.println("너 여기 왓니 ?");
				return "user/userLogin";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	//로그아웃
	@RequestMapping(value="/logout", method= {RequestMethod.GET, RequestMethod.POST})
	private String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	//회원가입
	@RequestMapping(value="/reguser", method= {RequestMethod.GET, RequestMethod.POST})
	private ModelAndView registeruser(@ModelAttribute UserDTO u, HttpServletRequest request) throws SQLException {
		ModelAndView mav = new ModelAndView();
		
		try {
			userservice.insertUser(u);
			System.out.println("회원가입 성공!");
			mav.setViewName("redirect:mvlogin");
			return mav;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("회원가입 실패!");
			request.setAttribute("error", "회원가입에 실패하였습니다.");
			mav.setViewName("user/userRegister");
			return mav;
		}
	}
	
	//회원 삭제
	@RequestMapping(value="/deluser", method= {RequestMethod.GET, RequestMethod.POST})
	private String deleteUser(@RequestParam("id") String id, HttpServletRequest request) throws SQLException {
		userservice.deleteUser(id);
		HttpSession session = request.getSession();
		session.invalidate();
		return "index";
	}
	
	//회원 수정
	@RequestMapping(value="/updateuser", method= {RequestMethod.GET, RequestMethod.POST})
	private ModelAndView updateUser(@ModelAttribute UserDTO u, HttpServletRequest request) throws SQLException {
		ModelAndView mav = new ModelAndView();
		try {
			userservice.updateUser(u);
			UserDTO user = userservice.viewUserId(u.getId());
			System.out.println("수정완료!");
			HttpSession session = request.getSession();
			session.setAttribute("logInfo", user);
			// main으로 바꾸기!!!
			mav.setViewName("redirect:/");
			return mav;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("회원수정 실패!");
			request.setAttribute("error", "아이디는 수정할 수 없습니다.");
			//"/user/mvlogInfo"
			mav.setViewName("user/userChangeInfo");
			return mav;
		}
	}
	
	//비밀번호 찾기
	@RequestMapping(value="/findpw", method= {RequestMethod.GET, RequestMethod.POST})
	private String findpassword(@RequestParam("id") String id, @RequestParam("email") String email, HttpServletRequest request) {
		try {
			String pw = userservice.findPW(id, email);
			if(pw!=null) {
				request.setAttribute("pw", "비밀번호는 " + pw  + "입니다.");
			}else {
				request.setAttribute("pw", "등록된 정보가 없습니다.");
			}
			return "user/userFindPW";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("error", e);
			return "error/error";
		}
	}
	
}

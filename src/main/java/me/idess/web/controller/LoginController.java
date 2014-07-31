package me.idess.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.idess.web.model.SessionObject;
import me.idess.web.model.TokenObject;
import me.idess.web.model.dto.LoginFormDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger	logger	= LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public LoginFormDto login(Locale locale, @RequestBody LoginFormDto dto, HttpSession session) {
		
		logger.debug("Welcome {}! The client locale is {}.", session.getAttribute("username"),
				locale);
		
		dto.setSuccessLogin("Y");
		dto.setToken(TokenObject.makeToken(dto.getUsername()));
		
		// 로그인 완료 후 세션 관리
		session.setAttribute("Username", dto.getUsername());
		session.setAttribute("Token", dto.getToken());
		SessionObject.addSession(dto.getUsername(), session);
		TokenObject.setToken(dto.getUsername(), dto.getToken());
		
		logger.debug("call login in LoginController:" + dto);
		
		return dto;
	}
	
}

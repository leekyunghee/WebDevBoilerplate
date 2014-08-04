package me.idess.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import me.idess.web.filter.SessionObject;
import me.idess.web.filter.TokenObject;
import me.idess.web.model.dto.LoginFormDto;
import me.idess.web.model.validation.LoginFormDtoValidator;

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
@RequestMapping("/api")
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
		
		LoginFormDtoValidator validator = new LoginFormDtoValidator();
		// validator.validate(dto, new Errors());
		
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

package me.idess.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import me.idess.web.filter.SessionObject;
import me.idess.web.filter.TokenObject;
import me.idess.web.model.CommonBean;
import me.idess.web.model.dto.LoginFormDto;
import me.idess.web.model.validation.LoginFormDtoValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	
	@Autowired
	LoginFormDtoValidator		loginFormDtoValidator;
	@Autowired
	MessageSource				messageSource;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public LoginFormDto login(@RequestBody LoginFormDto dto, BindingResult result, Locale locale,
			HttpSession session) throws Exception {
		
		logger.debug("Welcome {}! The client locale is {}.", session.getAttribute("username"),
				locale);
		
		loginFormDtoValidator.validate(dto, result);
		
		logger.debug("result: " + result);
		
		if (result.hasErrors()) {
			dto.setSuccessLogin("N");
			String code = result.getAllErrors().get(0).getCode();
			dto.setReturnType(CommonBean.ReturnType.warning);
			dto.setErrorCode(code);
			dto.setErrorMessage(messageSource.getMessage(code, null, locale));
		} else {
			dto.setReturnType(CommonBean.ReturnType.success);
			dto.setSuccessLogin("Y");
			dto.setToken(TokenObject.makeToken(dto.getUsername()));
			
			// 로그인 완료 후 세션 관리
			session.setAttribute("Username", dto.getUsername());
			session.setAttribute("Token", dto.getToken());
			SessionObject.addSession(dto.getUsername(), session);
			TokenObject.setToken(dto.getUsername(), dto.getToken());
			
			logger.debug("call login in LoginController:" + dto);
		}
		return dto;
		
	}
	
}

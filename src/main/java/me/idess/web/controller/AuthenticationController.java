package me.idess.web.controller;

import javax.servlet.http.HttpSession;

import me.idess.web.filter.SessionObject;
import me.idess.web.filter.TokenObject;
import me.idess.web.mapper.AccountMapper;
import me.idess.web.model.CommonBean;
import me.idess.web.model.dto.LoginFormDto;
import me.idess.web.model.validation.LoginFormDtoValidator;
import me.idess.web.model.vo.AccountVO;
import me.idess.web.services.AuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
public class AuthenticationController {
	
	private static final Logger	logger	= LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	LoginFormDtoValidator		loginFormDtoValidator;
	@Autowired
	MessageSource				messageSource;
	@Autowired
	AuthenticationService		authenticationService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public LoginFormDto login(@RequestBody LoginFormDto dto, BindingResult result,
			HttpSession session) {
		
		logger.debug("Welcome {}! The client locale is {}.", session.getAttribute("Username"),
				LocaleContextHolder.getLocale());
		
		loginFormDtoValidator.validate(dto, result);
		
		logger.debug("result: " + result);
		
		if (result.hasErrors()) {
			dto.setSuccessLogin("N");
			String code = result.getAllErrors().get(0).getCode();
			dto.setReturnType(CommonBean.ReturnType.warning);
			dto.setErrorCode(code);
			dto.setErrorMessage(messageSource.getMessage(code, null,
					LocaleContextHolder.getLocale()));
		} else {
			authenticationService.login(dto, session);
			if (dto.getReturnType() == CommonBean.ReturnType.success) {
				dto.setErrorMessage("");
			} else {
				dto.setErrorMessage(messageSource.getMessage(dto.getErrorCode(), null,
						LocaleContextHolder.getLocale()));
			}
			
			logger.debug("call login in LoginController:" + dto);
		}
		return dto;
		
	}
	
}

package me.idess.web.controller;

import javax.servlet.http.HttpSession;

import me.idess.web.exception.BusinessException;
import me.idess.web.model.CommonBean;
import me.idess.web.model.CommonBean.ReturnType;
import me.idess.web.model.dto.LoginFormDto;
import me.idess.web.model.validation.LoginFormDtoValidator;
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
	public CommonBean login(@RequestBody LoginFormDto dto, BindingResult result, HttpSession session) {
		
		try {
			logger.debug("Welcome {}! The client locale is {}.", session.getAttribute("Username"),
					LocaleContextHolder.getLocale());
			
			loginFormDtoValidator.validate(dto, result);
			
			logger.debug("result: " + result);
			
			if (result.hasErrors()) {
				dto.setSuccessLogin("N");
				String code = result.getAllErrors().get(0).getCode();
				dto.setReturnType(ReturnType.warning);
				dto.setErrorCode(code);
				dto.setErrorMessage(messageSource.getMessage(code, null,
						LocaleContextHolder.getLocale()));
			} else {
				authenticationService.login(dto, session);
				if (dto.getReturnType() == ReturnType.success) {
					dto.setErrorMessage("");
				} else {
					dto.setErrorMessage(messageSource.getMessage(dto.getErrorCode(), null,
							LocaleContextHolder.getLocale()));
				}
				
				logger.debug("call login in LoginController:" + dto);
			}
			return dto;
		} catch (BusinessException e) {
			// Service등에서 알 수 있는 메시지 발생
			CommonBean returnErrorResult = e.returnErrorResult(messageSource);
			return returnErrorResult;
		} catch (Exception e) {
			// 알수 없는 에러 발생
			BusinessException exception = new BusinessException("errorCode", null,
					e.getLocalizedMessage(), e);
			return exception.returnErrorResult(messageSource);
		}
		
	}
	
}

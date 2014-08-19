package me.idess.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpSession;

import me.idess.web.controller.AuthenticationController;
import me.idess.web.model.dto.LoginFormDto;
import me.idess.web.model.vo.AccountVO;
import me.idess.web.services.system.AccountService;

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

@Controller
@RequestMapping("/api/account")
public class AccountController {
	
	private static final Logger	logger	= LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	MessageSource				messageSource;
	@Autowired
	AccountService				accountService;
	
	@RequestMapping(value = "/selectAccounts", method = RequestMethod.POST)
	@ResponseBody
	public List<AccountVO> selectAccounts(@RequestBody LoginFormDto dto, BindingResult result,
			HttpSession session) {
		logger.debug("AccountController selectAccounts method");
		List<AccountVO> accounts = accountService.selectAccounts();
		return accounts;
	}
}

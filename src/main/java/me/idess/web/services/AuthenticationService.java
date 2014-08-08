package me.idess.web.services;

import javax.servlet.http.HttpSession;

import me.idess.web.controller.AuthenticationController;
import me.idess.web.filter.SessionObject;
import me.idess.web.filter.TokenObject;
import me.idess.web.mapper.AccountMapper;
import me.idess.web.model.CommonBean;
import me.idess.web.model.dto.LoginFormDto;
import me.idess.web.model.vo.AccountVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	
	private static final Logger	logger	= LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	AccountMapper				accountMapper;
	
	public void login(LoginFormDto dto, HttpSession session) {
		AccountVO accountVO = accountMapper.selectAccountByUsername(dto.getUsername());
		if (accountVO != null && accountVO.getPassword().equals(dto.getPassword())) {
			dto.setSuccessLogin("Y");
			dto.setToken(TokenObject.makeToken(dto.getUsername()));
			
			dto.setReturnType(CommonBean.ReturnType.success);
			dto.setErrorCode("");
			
			// 로그인 완료 후 세션 관리
			session.setAttribute("Username", dto.getUsername());
			session.setAttribute("Token", dto.getToken());
			SessionObject.addSession(dto.getUsername(), session);
			TokenObject.setToken(dto.getUsername(), dto.getToken());
		} else {
			dto.setSuccessLogin("N");
			
			dto.setReturnType(CommonBean.ReturnType.warning);
			dto.setErrorCode("username.password.invalid");
		}
	}
	
}

package team.idess.web.controller.sysacc;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import team.idess.web.dto.web.sysacc.SignInFormDto;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SignInController {

	private static final Logger logger = LoggerFactory
			.getLogger(SignInController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	@ResponseBody
	public SignInFormDto signin(Locale locale, @RequestBody SignInFormDto dto) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		logger.info("call signin in SignInController:" + dto);
		
		dto.setSuccessSignIn("Y");
		
		return dto;
	}

}

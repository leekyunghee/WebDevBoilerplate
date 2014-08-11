package me.idess.web.exception;

import me.idess.web.model.CommonBean;
import me.idess.web.model.CommonBean.ReturnType;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class BusinessException extends Exception {
	private static final long	serialVersionUID	= 5546714808391561097L;
	
	protected String			defaultMessage		= null;
	protected String			errorCode			= null;
	protected Object[]			errorArgs			= null;
	
	public BusinessException() {
		this(null, null, "BaseException without message", null);
	}
	
	public BusinessException(String errorCode) {
		this(errorCode, null, null, null);
	}
	
	public BusinessException(String errorCode, Object[] errorArgs) {
		this(errorCode, errorArgs, null, null);
	}
	
	public BusinessException(String errorCode, Object[] errorArgs, String defaultMessage) {
		this(errorCode, errorArgs, defaultMessage, null);
	}
	
	public BusinessException(String errorCode, Object[] errorArgs, Throwable wrappedException) {
		this(errorCode, errorArgs, null, wrappedException);
	}
	
	public BusinessException(String errorCode, Object[] errorArgs, String defaultMessage,
			Throwable wrappedException) {
		super(wrappedException);
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
		this.defaultMessage = defaultMessage;
	}
	
	public CommonBean returnErrorResult(MessageSource messageSource) {
		CommonBean commonBean = new CommonBean();
		commonBean.setErrorCode(this.errorCode);
		commonBean.setReturnType(ReturnType.error);
		if (defaultMessage == null || defaultMessage.isEmpty()) {
			commonBean.setErrorMessage(messageSource.getMessage(errorCode, errorArgs,
					defaultMessage, LocaleContextHolder.getLocale()));
		} else {
			commonBean.setErrorMessage(messageSource.getMessage(errorCode, errorArgs,
					defaultMessage, LocaleContextHolder.getLocale()) + "(" + defaultMessage + ")");
		}
		
		return commonBean;
	}
	
}

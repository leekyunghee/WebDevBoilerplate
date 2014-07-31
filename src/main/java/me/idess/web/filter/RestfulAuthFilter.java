package me.idess.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.idess.web.model.TokenObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("restfulAuthFilter")
public class RestfulAuthFilter implements Filter {
	
	private static final Logger	logger	= LoggerFactory.getLogger(RestfulAuthFilter.class);
	
	private final String loginUri = "/login";
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			
			String uri = httpRequest.getRequestURI();
			
			HttpSession session = httpRequest.getSession();
			
			// 스크립트 파일, 스타일 파일, 이미지를 호출할 경우 필터 조건에서 제외
			if (uri.contains(".js") || uri.contains(".css") || uri.contains(".gif")
					|| uri.contains(".png") || uri.contains(".jpg")) {
				filterChain.doFilter(httpRequest, httpResponse);
				return;
			}
			
			// 로그인 페이지 및 세션 만료 정보 페이지로 갈경우 세션 제거
			if (uri.equals("/") || uri.equals("/index.html") || uri.equals("/sessionExpire.html")) {
				session.removeAttribute("Token");
				session.removeAttribute("Account");
				logger.debug("############### 세션 제거 #################");
			}
			
			logger.debug("session id: " + session.getId() + " session token: " + session.getAttribute("Token") + " session user: " + session.getAttribute("Username"));
			Object sessionToken = session.getAttribute("Token");
			if (sessionToken == null) {
				logger.debug("Unauthorized user");
				if (uri.equals(loginUri)) {
					logger.debug("Login process");
					filterChain.doFilter(httpRequest, httpResponse);
				}
			} else {
				logger.debug("Authorized user");
				// token 유효성 체크
				String tokenObjectToken = TokenObject.getToken((String) session.getAttribute("Username"));
				if (tokenObjectToken != null && tokenObjectToken.equals(sessionToken)) {
					logger.debug("Valid Token");
					filterChain.doFilter(httpRequest, httpResponse);
				} else {
					logger.debug("Invalid Token");
					session.removeAttribute("Token");
					session.removeAttribute("Username");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}

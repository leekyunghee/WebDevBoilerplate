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
			HttpSession session = httpRequest.getSession();
			String uri = httpRequest.getRequestURI();
			
			// Script files, style files, the files are removed from the filter.
			if (uri.contains(".js") || uri.contains(".css") || uri.contains(".gif")
					|| uri.contains(".png") || uri.contains(".jpg")) {
				filterChain.doFilter(httpRequest, httpResponse);
				return;
			}
			
			// Remove a session when you go to the login page and the session expired page.
			if (uri.equals("/") || uri.equals("/index.html") || uri.equals("/sessionExpire.html")) {
				logger.debug("############### 세션 제거 #################");
				session.removeAttribute("Token");
				session.removeAttribute("Username");
			}
			
			// login validation
			logger.debug("session id: " + session.getId() + " session token: " + session.getAttribute("Token") + " session user: " + session.getAttribute("Username"));
			Object sessionToken = session.getAttribute("Token");
			if (sessionToken == null) {
				logger.debug("Unauthorized user");
				if (uri.equals(loginUri)) {
					logger.debug("Login process");
					filterChain.doFilter(httpRequest, httpResponse);
				} else {
					logger.debug("Session is null or abnormal url access");
				}
			} else {
				logger.debug("Authorized user");
				// token validation  
				String tokenObjectToken = TokenObject.getToken((String) session.getAttribute("Username"));
				if (tokenObjectToken != null && tokenObjectToken.equals(sessionToken)) {
					logger.debug("Valid Token");
					filterChain.doFilter(httpRequest, httpResponse);
				} else {
					logger.debug("Invalid Token or duplicate login");
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

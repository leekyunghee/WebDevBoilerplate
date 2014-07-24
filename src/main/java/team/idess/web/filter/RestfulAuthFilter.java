package team.idess.web.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.security.sasl.AuthenticationException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("restfulAuthFilter")
public class RestfulAuthFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(RestfulAuthFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;

			HttpSession session = httpRequest.getSession();

			String uri = httpRequest.getRequestURI();
			String path = uri.replaceFirst(httpRequest.getContextPath(), "");

			String loginId = null;
			String token = null;

			// 스크립트 파일, 스타일 파일, 이미지를 호출할 경우 필터 조건에서 제외
			if (uri.contains(".js") || uri.contains(".css")
					|| uri.contains(".gif") || uri.contains(".png")
					|| uri.contains(".jpg")) {
				filterChain.doFilter(httpRequest, httpResponse);
				return;
			}

			// 로그인 페이지 및 세션 만료 정보 페이지로 갈경우 세션 제거
			if (uri.equals("/") || uri.equals("/index.html")
					|| uri.equals("/sessionExpire.html")) {
				session.removeAttribute("Token");
				session.removeAttribute("Account");
				session.removeAttribute("RoleNo");
				session.removeAttribute("InstitutionNo");
				logger.debug("############### 세션 제거 #################");
			}

			AuthenticationRequestWrapper requestWrapper = new AuthenticationRequestWrapper(httpRequest);
			String requestBody = requestWrapper.getBody();
			logger.debug(requestBody);

			filterChain.doFilter(requestWrapper, httpResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

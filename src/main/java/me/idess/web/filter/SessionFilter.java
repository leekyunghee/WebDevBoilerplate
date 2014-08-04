package me.idess.web.filter;

/**
 * 세션(token)을 관리하는 필터
 * @version 1.0
 * @author jrkang
 */
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

import me.idess.web.common.Constants;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionFilter implements Filter {
	
	private static final Logger	logger	= LoggerFactory.getLogger(SessionFilter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		logger.debug("destroy Call");
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		logger.debug("doFilter Call");
		
		// String serviceCode = "";
		boolean tokenStatus = false;
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String uri = request.getRequestURI();
		logger.debug("uri[" + uri + "]");
		
		HttpSession httpSession = request.getSession();
		
		try {
			RequestWrapper requestWrapper = new RequestWrapper(request);
			String requestBody = requestWrapper.getBody();
			
			// resources 파일 필터 조건 제외
			if (uri.contains(".js") || uri.contains(".css") || uri.contains(".gif")
					|| uri.contains(".png") || uri.contains(".jpg") || uri.contains(".ico")
					|| uri.contains(Constants.Template_File_Path)) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
			
			// 초기 페이지 및 로그인 페이지로 갈경우 세션 제거
			if (uri.equals("/") || uri.equals("/index.html")) {
				httpSession.removeAttribute("Token");
				httpSession.removeAttribute("Account");
				logger.debug("init/index page ; session removed");
			}
			
			if (requestBody != null) {
				logger.debug("requestBody is not null");
				JSONObject jsonObject = JSONObject.fromObject(requestBody);
				JSONObject dataInfoObject = (JSONObject) jsonObject.get(Constants.DataInfo);
				JSONObject headerObject = (JSONObject) dataInfoObject.get(Constants.Header);
				JSONObject bodyObject = (JSONObject) dataInfoObject.get(Constants.Body);
				
				// serviceCode = ObjectUtil.isNull(headerObject.get("ServiceCode"), "");
				
				// 세션이 존재하는지 확인
				if (httpSession.getAttribute("Token") != null) {
					logger.debug("세션 존재");
					
					// 현재 토큰이 유효하다면
					if (TokenObject.getToken(httpSession.getAttribute("Account").toString()) != null
							&& TokenObject.getToken(httpSession.getAttribute("Account").toString())
									.equals(httpSession.getAttribute("Token").toString())) {
						logger.debug("토큰 유효");
						headerObject.put("Token", httpSession.getAttribute("Token").toString());
						headerObject.put("Account", httpSession.getAttribute("Account").toString());
						tokenStatus = true;
					} else {
						// 토큰이 유효하지 않을 경우 세션 삭제
						logger.debug("토큰 유효하지 않음");
						httpSession.removeAttribute("Token");
						httpSession.removeAttribute("Account");
					}
					
				}
				// 세션이 존재하지 않으면
				else {
					// 로그인 서비스일 경우
					logger.debug("세션 존재하지 않음");
					// if (serviceCode.equals("loginServiceCode") || uri.equals("/login")) {
					if (uri.equals("/login")) {
						// headerObject.put("Account", ((JSONObject)((JSONObject)
						// bodyObject.get(Constants.Parameter)).get(Constants.LoginKey)).get(Constants.LoginId));
						headerObject.put("Account", ((JSONObject) bodyObject
								.get(Constants.Parameter)).get(Constants.LoginId));
					}
				}
				
				dataInfoObject.put(Constants.Header, headerObject);
				jsonObject.put(Constants.DataInfo, dataInfoObject);
				
				request.setAttribute("q", jsonObject.toString());
			}
			
			if (httpSession.getAttribute("Token") != null) {
				
				// if (serviceCode.equals("logoutServiceCode") || uri.equals("/logout")){
				if (uri.equals("/logout")) {
					// 토큰이 유효하지 않을 경우 세션 삭제
					httpSession.removeAttribute("Token");
					httpSession.removeAttribute("Account");
					response.sendRedirect("/");
					// RequestDispatcher rd = request.getRequestDispatcher("/");
					// rd.forward(request, response);
					logger.debug("logout service call; delete session");
					return;
				} else {
					// 정상적인 요청일 경우
					filterChain.doFilter(request, response);
					logger.debug("Session is not null and normal url access");
					return;
				}
			} else {
				// if (serviceCode.equals("loginServiceCode") || uri.equals("/login") ||
				// uri.equals("/") || uri.equals("/index.html")) {
				if (uri.equals("/login") || uri.equals("/") || uri.equals("/index.html")) {
					filterChain.doFilter(request, response);
				} else { // 비정상적인 요청일 경우
					response.sendRedirect("/");
					logger.debug("Session is null or abnormal url access");
					// filterChain.doFilter(servletRequest, servletResponse);
					return;
				}
			}
			
			// if ((serviceCode.equals("loginServiceCode") || uri.equals("/login"))&&
			// request.getAttribute("q") != null) {
			if (uri.equals("/login") && requestBody != null) {
				JSONObject jsonObject = JSONObject.fromObject(requestBody);
				
				// 로그인 성공일 경우 세션 생성
				if (jsonObject != null && "Y".equals(jsonObject.get("successLogin"))) {
					httpSession.setAttribute("Account", jsonObject.get("username"));
					httpSession.setAttribute("Token", jsonObject.get("token"));
					
					// token을 메모리에 설정
					TokenObject.setToken((String) jsonObject.get(Constants.LoginId),
							(String) jsonObject.get("token"));
					
					logger.debug("login success ; add session > Token["
							+ httpSession.getAttribute("Token") + "]");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		logger.debug("init Call");
	}
	
}

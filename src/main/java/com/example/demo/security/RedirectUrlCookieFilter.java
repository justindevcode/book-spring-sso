package com.example.demo.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class RedirectUrlCookieFilter extends OncePerRequestFilter {
	public static final String REDIRECT_URL_PARAM = "redirect_url";
	private static final int MAX_AGE = 180;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		if (request.getRequestURI().startsWith("/auth/authorize")) {
			try {
				log.info("request url {} ",request.getRequestURI());
				String redirectUrl = request.getParameter(REDIRECT_URL_PARAM); //리퀘스트 파라미터에서 리다이렉트 URL을 가져온다.

				Cookie cookie = new Cookie(REDIRECT_URL_PARAM, redirectUrl);
				cookie.setPath("/");
				cookie.setHttpOnly(true);
				cookie.setMaxAge(MAX_AGE);
				response.addCookie(cookie);
			} catch (Exception ex) {
				logger.error("Could not set user authentication in security context", ex);
				log.info("Unauthorized request");
			}
		}
		filterChain.doFilter(request, response);
	}

}

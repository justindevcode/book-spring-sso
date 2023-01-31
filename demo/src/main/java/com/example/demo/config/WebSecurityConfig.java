package com.example.demo.config;


import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.OAuthSuccessHandler;
import com.example.demo.security.OAuthUserServiceImpl;
import com.example.demo.security.RedirectUrlCookieFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationCodeGrantFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.filter.CorsFilter; //이거 import해야함
import lombok.extern.slf4j.Slf4j;
//import org.apache.catalina.filters.CorsFilter; 이거말고
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private OAuthUserServiceImpl oAuthUserService;

	@Autowired
	private OAuthSuccessHandler oAuthSuccessHandler;

	@Autowired
	private RedirectUrlCookieFilter redirectUrlFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http 시큐리티 빌더
		http.cors() // WebMvcConfig에서 이미 설정했으므로 기본 cors설정.
			.and()
			.csrf() // csrf는 현재 사용하지 않으므로 disable
				.disable()
			.httpBasic()//token을 사용하므로 basic 인증 disable
				.disable()
			.sessionManagement()// sesstion 기반이 아님을 선언
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests() // /와 /auth/** 경로는 인증 안해도됨.
				.antMatchers("/","/auth/**","/error","/oauth2/**").permitAll()//oauth2 엔드포인트 추가
			.anyRequest() // /와 /auth/**이외의 모든 경로는 인증해야됨.
				.authenticated()
					.and()
						.oauth2Login() //oauth2Login 설정
			.redirectionEndpoint()
				.baseUri("/oauth2/callback/*") //callback uri설정
			.and()
			.authorizationEndpoint()
			.baseUri("/auth/authorize")//OAuth 2.0 흐름 시작을 위한 엔드포인트 추가
			.and()
				.userInfoEndpoint()
					.userService(oAuthUserService)//OAuthUserServiceImpl를 유저 서비스로 등록
			.and()
				.successHandler(oAuthSuccessHandler)//success handler등록
			.and()
				.exceptionHandling()
					.authenticationEntryPoint(new Http403ForbiddenEntryPoint()); //http403Forbidden EntryPoint추가

		//filter 등록.
		//매 요정마다
		//CorsFilter 실행한 후에
		//jwtAuthenticationFilter 실행한다.
		http.addFilterAfter(
			jwtAuthenticationFilter,
			CorsFilter.class
		);
		http.addFilterBefore(//Before
			redirectUrlFilter,
			OAuth2AuthorizationRequestRedirectFilter.class // 리디렉트 되기전에 필터를 실행해야함
			);

	}

}

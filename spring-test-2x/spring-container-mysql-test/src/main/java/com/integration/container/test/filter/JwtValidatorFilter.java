package com.integration.container.test.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.integration.container.test.config.SecurityConstants;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtValidatorFilter extends OncePerRequestFilter {

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws IOException, ServletException {
		System.out.println("JwtValidatorFilter|doFilterInternal");
		//get authorization custom header from the given request
		String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
		if (null != jwt) {
			try {
				//get our key string and use inside hmacShaKeyFor method to generate the SecretKey
				SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
				
				//get claim object which allow us to get user name and authorities
				Claims claims = Jwts.parserBuilder().setSigningKey(key)
													.build()
													.parseClaimsJws(jwt)
													.getBody();
				
				//get back user name and authorities from claim part
				String username = String.valueOf(claims.get("username"));
				String authorities = (String) claims.get("authorities");
				
				//validate user name, if something goes wrong will raise BadCredentialsException 
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
				
				//add Authentication object to SecurityContext
				SecurityContextHolder.getContext().setAuthentication(auth);
			}catch (Exception e) {
				throw new BadCredentialsException("Invalid Token received!");
			}
			
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * apply the filter against all requests except login
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getServletPath().equals("/login");
	}
}

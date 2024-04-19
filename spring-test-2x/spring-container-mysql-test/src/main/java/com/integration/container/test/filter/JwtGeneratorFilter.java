package com.integration.container.test.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;


import com.integration.container.test.config.SecurityConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtGeneratorFilter extends OncePerRequestFilter {

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		System.out.println("JwtGeneratorFilter|doFilterInternal");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			//get our key string and use hmacShaKeyFor method to generate the SecretKey in signWith method
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			
			//generate the JWT using builder method and pass user info, expiration date and key
			String jwt = Jwts.builder().setIssuer("JWT demo")
										.setSubject("JWT Token")
										.claim("username", authentication.getName())
									    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
									    .setIssuedAt(new Date())
									    .setExpiration(new Date((new Date()).getTime() + 300000000))
									    .signWith(key)
									    .compact();
			System.out.println("jwt:" + jwt);
			//add JWT to Authorization header using response object
			response.setHeader(SecurityConstants.JWT_HEADER, jwt);
		}

		filterChain.doFilter(request, response);
	}
	
	/**
	 * allow this filter only for login request
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/login");
	}
	

	/**
	 * to return string of authorities separated with comma
	 */
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
        	authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
	}
}

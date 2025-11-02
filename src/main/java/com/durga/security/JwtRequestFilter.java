package com.durga.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.durga.service.LoginService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private LoginService service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authToken=readAuthTokenFormHeader(response);
		
		if(authToken!=null) {
			String username=jwtUtils.extractUsername(authToken);
			UserDetails userDetails=service.loadUserByUsername(username);
			if(username!=null&&SecurityContextHolder.getContext().getAuthentication()==null&&jwtUtils.isTokenValidate(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);

	}

	private String readAuthTokenFormHeader(HttpServletResponse response) {
		final String authHeader=response.getHeader("Authorization");
		String authToken=null;
		if(authHeader!=null&&authHeader.length()>0) {
			String[] tokens=authHeader.split(" ");
			if(tokens.length==2) {
				authToken=tokens[1];
			}
		}
		
		return authToken;
	}

}

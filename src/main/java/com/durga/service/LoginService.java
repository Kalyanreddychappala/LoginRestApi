package com.durga.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.durga.dto.LoginDto;

public interface LoginService extends UserDetailsService {
	
	public LoginDto loadByUserEmail(String email);
}

package com.durga.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.durga.dto.LoginDto;
import com.durga.security.JwtUtils;
import com.durga.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService service;
	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private JwtUtils jwtUtils;
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginDto dto){
		
		if(dto==null||dto.getEmail()==null||dto.getEmail().length()<6||dto.getPassword()==null||dto.getPassword().length()<5) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid Details");			
		}
		
		Authentication auth=manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
		
		if(auth.isAuthenticated()) {
			UserDetails userDetails=service.loadUserByUsername(dto.getEmail());
			String token=jwtUtils.generateToken(userDetails);
			Map<String, String> response=new HashMap<>();
			response.put("access_token", token);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Fail");
	}
}

package com.durga.util;

import org.springframework.beans.BeanUtils;

import com.durga.dto.LoginDto;
import com.durga.entity.LoginEntity;

public class LoginUtil {
	
	public static LoginEntity convertDtoToEntity(LoginDto dto) {
		LoginEntity entity=new LoginEntity();
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		return entity;
	}
	
	
	public static LoginDto convertEntityToDto(LoginEntity entity) {
		LoginDto dto=new LoginDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

}

package com.durga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.durga.entity.LoginEntity;
@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, String> {

}

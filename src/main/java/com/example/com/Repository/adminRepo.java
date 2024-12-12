package com.example.com.Repository;

import com.example.com.Entity.admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface adminRepo extends JpaRepository<admin,Integer> {

    admin findByEmailAndPasswordAndStatus(String email, String password, int status);
}

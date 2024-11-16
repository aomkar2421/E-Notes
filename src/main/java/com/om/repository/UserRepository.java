package com.om.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.om.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public boolean existsByEmail(String email);
	
	public User findByEmail(String email);

	public User findByVerificationCode(String verificationCode);
	
	@Query("update User u set u.failedAttempt=?1 where email=?2 ")
	@Modifying
	public void updateFailedAttempt(int attempt, String email);


}

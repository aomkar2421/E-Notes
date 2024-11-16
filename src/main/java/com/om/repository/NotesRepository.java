package com.om.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.om.entity.Notes;
import com.om.entity.User;

public interface NotesRepository extends JpaRepository<Notes, Integer>{
	
	public Page<Notes> findByUser(User user, Pageable pageable);
	
}

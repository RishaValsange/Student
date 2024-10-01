package com.example.demo2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.example.demo2.Entity.Student;
import com.example.demo2.repository.StudentRepository;

@Service
public class UserService {
	@Autowired
	StudentRepository dao;

	
}

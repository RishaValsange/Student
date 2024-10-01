package com.example.demo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo2.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

}

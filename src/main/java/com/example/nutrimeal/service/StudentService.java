package com.example.nutrimeal.service;

import java.util.Optional;

import javax.management.remote.SubjectDelegationPermission;

import org.apache.catalina.valves.StuckThreadDetectionValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.Student;
import com.example.nutrimeal.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired 
	private StudentRepository studentRepository;
	
	public Optional<Student> get(Integer id) {
		return studentRepository.findById(id);
	}

	public Student save(Student student) {
		return studentRepository.save(student);
	}
}

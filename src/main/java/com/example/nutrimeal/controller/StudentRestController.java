package com.example.nutrimeal.controller;

import java.awt.PageAttributes.MediaType;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.nutrimeal.model.Student;
import com.example.nutrimeal.service.StudentService;

@RestController
@RequestMapping(value = "/student")
public class StudentRestController {
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getThisStudent(@PathVariable("id") Integer id){
		Optional<Student> student = studentService.get(id);
		if (student.isPresent()) {
			return new ResponseEntity<>(student.get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(student.get(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> create(){
		Student student = new Student();
		student.setNom("Popol");
		Student persistedStudent = studentService.save(student);
		return new ResponseEntity<>(persistedStudent, HttpStatus.CREATED);
	}
}

package com.example.nutrimeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.nutrimeal.model.Student;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {

}

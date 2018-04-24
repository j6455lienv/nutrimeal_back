package com.example.nutrimeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nutrimeal.model.Instruction;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Long>{

}

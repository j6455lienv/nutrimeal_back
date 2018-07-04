package com.example.nutrimeal.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.nutrimeal.model.Instruction;

@Repository
public interface InstructionRepository extends PagingAndSortingRepository<Instruction, Long>{

}

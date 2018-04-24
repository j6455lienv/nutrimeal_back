package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Instruction;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Long>{

}

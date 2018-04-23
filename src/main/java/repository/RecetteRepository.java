package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Recette;

@Repository
public interface RecetteRepository extends JpaRepository<Recette, Long>{

}

package fr.diginamic.hello.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {
	Optional<Departement> findByCode(String code);
	boolean existsByCode(String code);
}

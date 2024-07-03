package fr.diginamic.hello.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import fr.diginamic.hello.classes.Departement;
import fr.diginamic.hello.classes.Ville;

public interface DepartementRepository extends CrudRepository<Departement, Integer> {
	Optional<Departement> findByNom(String nom);
}

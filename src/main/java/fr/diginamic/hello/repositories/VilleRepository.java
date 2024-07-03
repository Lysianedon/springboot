package fr.diginamic.hello.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import fr.diginamic.hello.classes.Departement;
import fr.diginamic.hello.classes.Ville;

import java.util.List;
import java.util.Optional;

public interface VilleRepository extends JpaRepository<Ville, Integer> {
	Optional<Ville> findByNom(String nom);
	List<Ville> findByDepartementOrderByNbHabitantsDesc(Departement departement, Pageable pageable);
	List<Ville> findByNbHabitantsBetweenAndDepartement(int minPopulation, int maxPopulation, Departement departement);
}

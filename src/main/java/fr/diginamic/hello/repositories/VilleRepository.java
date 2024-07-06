package fr.diginamic.hello.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;

import java.util.List;
import java.util.Optional;

public interface VilleRepository extends JpaRepository<Ville, Integer> {
	Optional<Ville> findByNom(String nom);

	List<Ville> findByDepartementOrderByNbHabitantsDesc(Departement departement, Pageable pageable);

	List<Ville> findByNbHabitantsBetweenAndDepartement(int minPopulation, int maxPopulation, Departement departement);

	Boolean existsByNom(String nom);

	Boolean existsByNomAndDepartement(String nom, Departement departement);

	// Recherche de toutes les villes dont le nom commence par une chaine de
	// caractères
	List<Ville> findByNomStartingWith(String prefix);

	// Recherche de toutes les villes dont la population est supérieure à min
	List<Ville> findByNbHabitantsGreaterThan(int minPopulation);

	// Recherche de toutes les villes dont la population est supérieure à min et
	// inférieure à max
	List<Ville> findByNbHabitantsBetween(int minPopulation, int maxPopulation);

	// Recherche de toutes les villes d’un département dont la population est
	// supérieure à min
	List<Ville> findByDepartementAndNbHabitantsGreaterThan(Departement departement, int minPopulation);

	// Recherche de toutes les villes d’un département dont la population est
	// supérieure à min et inférieure à max
	List<Ville> findByDepartementAndNbHabitantsBetween(Departement departement, int minPopulation, int maxPopulation);

	// Recherche des n villes les plus peuplées d’un département donné
	List<Ville> findTopNVillesByDepartementOrderByNbHabitantsDesc(Departement departement, Pageable pageable);

}

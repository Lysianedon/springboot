package fr.diginamic.hello.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Ville} entities, providing methods to perform
 * operations on the database.
 */
public interface VilleRepository extends JpaRepository<Ville, Integer> {

	/**
	 * Finds a city by its name.
	 *
	 * @param nom The name of the city.
	 * @return An Optional containing the city if found, or an empty Optional if not
	 *         found.
	 */
	Optional<Ville> findByNom(String nom);

	/**
	 * Retrieves a list of cities in a department, sorted by decreasing number of
	 * inhabitants.
	 *
	 * @param departement The department entity.
	 * @param pageable    Pagination information.
	 * @return A paginated list of cities.
	 */
	List<Ville> findByDepartementOrderByNbHabitantsDesc(Departement departement, Pageable pageable);

	/**
	 * Finds cities within a specific population range and within a specific
	 * department.
	 *
	 * @param minPopulation The minimum population limit.
	 * @param maxPopulation The maximum population limit.
	 * @param departement   The department entity.
	 * @return A list of cities matching the criteria.
	 */
	List<Ville> findByNbHabitantsBetweenAndDepartement(int minPopulation, int maxPopulation, Departement departement);

	/**
	 * Checks if a city with a specific name exists.
	 *
	 * @param nom The name of the city.
	 * @return True if a city with the given name exists, false otherwise.
	 */
	Boolean existsByNom(String nom);

	/**
	 * Checks if a city with a specific name and department exists.
	 *
	 * @param nom         The name of the city.
	 * @param departement The department entity.
	 * @return True if such a city exists, false otherwise.
	 */
	Boolean existsByNomAndDepartement(String nom, Departement departement);

	/**
	 * Finds all cities where the name starts with the specified prefix.
	 *
	 * @param prefix The prefix to match city names.
	 * @return A list of cities with names starting with the given prefix.
	 */
	List<Ville> findByNomStartingWith(String prefix);

	/**
	 * Finds all cities where the population is greater than the specified minimum.
	 *
	 * @param minPopulation The minimum population threshold.
	 * @return A list of cities with a population greater than the specified
	 *         minimum.
	 */
	List<Ville> findByNbHabitantsGreaterThan(int minPopulation);

	/**
	 * Finds all cities where the population is between the specified minimum and
	 * maximum.
	 *
	 * @param minPopulation The minimum population threshold.
	 * @param maxPopulation The maximum population threshold.
	 * @return A list of cities whose populations fall within the specified range.
	 */
	List<Ville> findByNbHabitantsBetween(long minPopulation, long maxPopulation);

	/**
	 * Finds all cities in a specified department where the population is greater
	 * than the specified minimum.
	 *
	 * @param departement   The department entity.
	 * @param minPopulation The minimum population threshold.
	 * @return A list of cities meeting the population criteria within the specified
	 *         department.
	 */
	List<Ville> findByDepartementAndNbHabitantsGreaterThan(Departement departement, int minPopulation);

	/**
	 * Finds all cities in a specified department where the population is between
	 * the specified minimum and maximum.
	 *
	 * @param departement   The department entity.
	 * @param minPopulation The minimum population threshold.
	 * @param maxPopulation The maximum population threshold.
	 * @return A list of cities whose populations fall within the specified range
	 *         within the given department.
	 */
	List<Ville> findByDepartementAndNbHabitantsBetween(Departement departement, int minPopulation, int maxPopulation);

	/**
	 * Finds the top 'n' most populated cities in a specified department.
	 *
	 * @param departement The department entity.
	 * @param pageable    Pagination information to specify the number of cities to
	 *                    return.
	 * @return A paginated list of the most populated cities in the specified
	 *         department.
	 */
	List<Ville> findTopNVillesByDepartementOrderByNbHabitantsDesc(Departement departement, Pageable pageable);

	Page<Ville> findByOrderByNbHabitantsDesc(Pageable pageable);

	List<Ville> findByDepartement(Departement departement);

}

package fr.diginamic.hello.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.hello.entities.Departement;

/**
 * Repository interface for {@link Departement} entities, providing methods to
 * perform operations related to department data in the database.
 */
public interface DepartementRepository extends JpaRepository<Departement, Integer> {

	/**
	 * Retrieves a department by its code.
	 *
	 * @param code The code of the department to find.
	 * @return An Optional containing the found department if it exists, or an empty
	 *         Optional if no department is found with the provided code.
	 */
	Optional<Departement> findByCode(String code);

	/**
	 * Checks if a department exists in the database by its code.
	 *
	 * @param code The code of the department to check.
	 * @return True if a department with the given code exists, otherwise false.
	 */
	boolean existsByCode(String code);
}

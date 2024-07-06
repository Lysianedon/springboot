package fr.diginamic.hello.repositories;

import fr.diginamic.hello.entities.Region;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Region} entities, providing methods to
 * perform operations on the database.
 */
public interface RegionRepository extends JpaRepository<Region, Long> {

	/**
	 * Checks if a region with the specified code exists in the database.
	 *
	 * @param code The code of the region.
	 * @return True if a region with the given code exists, otherwise false.
	 */
	boolean existsByCode(String code);

	/**
	 * Checks if a region with the specified name exists in the database.
	 *
	 * @param nomRegion The name of the region.
	 * @return True if a region with the given name exists, otherwise false.
	 */
	boolean existsByNom(String nomRegion);

	/**
	 * Finds a region by its name.
	 *
	 * @param nomRegion The name of the region to find.
	 * @return An Optional containing the region if found, or an empty Optional if
	 *         no region is found.
	 */
	Optional<Region> findByNom(String nomRegion);

	/**
	 * Finds a region by its code.
	 *
	 * @param codeRegion The code of the region to find.
	 * @return An Optional containing the region if found, or an empty Optional if
	 *         no region is found.
	 */
	Optional<Region> findByCode(String codeRegion);
}

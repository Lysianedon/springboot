package fr.diginamic.hello.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.entities.Region;
import fr.diginamic.hello.repositories.RegionRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Service class for managing regions. This class handles the business logic
 * associated with creation, retrieval, update, and deletion of region entities.
 */
@Service
public class RegionService {

	@Autowired
	private RegionRepository regionRepository;

	/**
	 * Creates a new region. Ensures that the region does not already exist based on
	 * its code.
	 *
	 * @param region The region to create.
	 * @return The saved region.
	 * @throws IllegalArgumentException if a region with the same code already
	 *                                  exists.
	 */
	@Transactional
	public Region createRegion(Region region) {
		boolean exists = regionRepository.existsByCode(region.getCode());
		if (exists) {
			throw new IllegalArgumentException("La région avec le code " + region.getCode() + " existe déjà.");
		}
		return regionRepository.save(region);
	}

	/**
	 * Checks if a region exists by its code.
	 *
	 * @param code The code to check.
	 * @return true if the region exists, false otherwise.
	 */
	@Transactional(readOnly = true)
	public boolean existsByCode(String code) {
		return regionRepository.existsByCode(code);
	}

	/**
	 * Retrieves all regions.
	 *
	 * @return A list of all regions.
	 */
	@Transactional(readOnly = true)
	public List<Region> getAllRegions() {
		return regionRepository.findAll();
	}

	/**
	 * Retrieves a region by its ID.
	 *
	 * @param id The ID of the region.
	 * @return An Optional containing the region if found, or an empty Optional if
	 *         not found.
	 */
	@Transactional(readOnly = true)
	public Optional<Region> getRegionById(long id) {
		return regionRepository.findById(id);
	}

	/**
	 * Updates a region identified by its ID with new details.
	 *
	 * @param id            The ID of the region to update.
	 * @param regionDetails The details to update the region with.
	 * @return The updated region.
	 * @throws IllegalArgumentException if the region with the specified ID does not
	 *                                  exist.
	 */
	@Transactional
	public Region updateRegion(long id, Region regionDetails) {
		Optional<Region> existingRegion = regionRepository.findById(id);
		if (!existingRegion.isPresent()) {
			throw new IllegalArgumentException("La région avec l'ID " + id + " n'existe pas.");
		}
		Region regionToUpdate = existingRegion.get();
		regionToUpdate.setCode(regionDetails.getCode());
		regionToUpdate.setNom(regionDetails.getNom());
		return regionRepository.save(regionToUpdate);
	}

	/**
	 * Deletes a region by its ID.
	 *
	 * @param id The ID of the region to delete.
	 * @throws IllegalArgumentException if the region with the specified ID does not
	 *                                  exist.
	 */
	@Transactional
	public void deleteRegionById(long id) {
		boolean exists = regionRepository.existsById(id);
		if (!exists) {
			throw new IllegalArgumentException("La région avec l'ID " + id + " n'existe pas.");
		}
		regionRepository.deleteById(id);
	}

	/**
	 * Deletes all regions.
	 */
	@Transactional
	public void deleteAllRegions() {
		regionRepository.deleteAll();
	}

	/**
	 * Retrieves a region by its name.
	 *
	 * @param nomRegion The name of the region.
	 * @return The region.
	 * @throws IllegalArgumentException if the region with the specified name does
	 *                                  not exist.
	 */
	@Transactional(readOnly = true)
	public Region getByNom(String nomRegion) {
		Optional<Region> region = regionRepository.findByNom(nomRegion);
		if (!region.isPresent()) {
			throw new IllegalArgumentException("La région avec le nom " + nomRegion + " n'existe pas.");
		}
		return region.get();
	}

	/**
	 * Finds a region by its code.
	 *
	 * @param codeRegion The code of the region.
	 * @return The region.
	 * @throws EntityNotFoundException if the region with the specified code does
	 *                                 not exist.
	 */

	@Transactional(readOnly = true)
	public Region findByCode(String codeRegion) {
		return regionRepository.findByCode(codeRegion).orElseThrow(
				() -> new EntityNotFoundException("La région avec le code " + codeRegion + " n'existe pas."));
	}

	public Region findById(long id) {
		return regionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("La région avec le code " + id + " n'existe pas."));
	}
}

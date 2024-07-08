package fr.diginamic.hello.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Region;
import fr.diginamic.hello.exceptions.DepartementNotFoundException;
import fr.diginamic.hello.repositories.DepartementRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Service class for managing departments. This class handles the business logic
 * associated with creation, retrieval, update, and deletion of department
 * entities.
 */
@Service
public class DepartementService {

	@Autowired
	private DepartementRepository departementRepository;
	
	@Autowired
	private RegionService regionService;

	/**
	 * Retrieves all departments.
	 *
	 * @return A list of all departments.
	 */
	@Transactional(readOnly = true)
	public List<Departement> extractDepartements() {
		return (List<Departement>) departementRepository.findAll();
	}

	/**
	 * Retrieves a department by its ID.
	 *
	 * @param id The ID of the department.
	 * @return The department if found, otherwise throws an exception.
	 * @throws EntityNotFoundException if no department with the given ID is found.
	 */
	@Transactional(readOnly = true)
	public Departement extractDepartement(int id) {
		return departementRepository.findById(id)
				.orElseThrow(() -> new DepartementNotFoundException("Departement non trouvé avec ID : " + id));
	}

	/**
	 * Retrieves a department by its code, or throws an exception if not found.
	 *
	 * @param code The code of the department to find.
	 * @return The department.
	 * @throws IllegalArgumentException if no department with the given code is
	 *                                  found.
	 */
	@Transactional(readOnly = true)
	public Departement getDepartementByCode(String code) {
		return departementRepository.findByCode(code).orElseThrow(
				() -> new DepartementNotFoundException("Le département avec le code " + code + " n'existe pas."));
	}

	/**
	 * Creates a new department ensuring it does not already exist.
	 *
	 * @param departement The department to create.
	 * @return The saved department.
	 * @throws IllegalArgumentException if a department with the same code already
	 *                                  exists.
	 */
	@Transactional
	public Departement createDepartement(Departement departement) {
		boolean exists = departementRepository.existsByCode(departement.getCode());
		if (exists) {
			throw new IllegalArgumentException(
					"Le département avec le code " + departement.getCode() + " existe déjà.");
		}

		Region region = departement.getRegion();
		if (region != null) {
			Region existingRegion = regionService.findById(region.getId());
			departement.setRegion(existingRegion);
		}
		return departementRepository.save(departement);
	}

	/**
	 * Updates an existing department by ID.
	 *
	 * @param id   The ID of the department to update.
	 * @param dptm The details to update the department with.
	 * @return The updated department.
	 * @throws EntityNotFoundException if no department with the given ID is found.
	 */
	@Transactional
	public Departement updateDepartement(int id, Departement dptm) {
		return departementRepository.findById(id).map(d -> {
			d.setCode(dptm.getCode());
			d.setRegion(dptm.getRegion());
			return departementRepository.save(d);
		}).orElseThrow(() -> new EntityNotFoundException("Departement non trouvé avec ID : " + id));

	}

	/**
	 * Deletes a department by its ID.
	 *
	 * @param id The ID of the department to delete.
	 * @throws EntityNotFoundException if no department with the given ID is found.
	 */
	@Transactional
	public void deleteDepartement(int id) {
		if (!departementRepository.existsById(id)) {
			throw new EntityNotFoundException("Departement non trouvé avec ID : " + id);
		}
		departementRepository.deleteById(id);
	}

	/**
	 * Checks if a department exists by its code.
	 *
	 * @param codeDepartement The code of the department.
	 * @return true if the department exists, false otherwise.
	 */

	@Transactional(readOnly = true)
	public boolean existsByCode(String codeDepartement) {
		return departementRepository.existsByCode(codeDepartement);
	}

}

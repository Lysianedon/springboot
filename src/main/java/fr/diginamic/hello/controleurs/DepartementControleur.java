package fr.diginamic.hello.controleurs;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.services.DepartementService;

/**
 * REST controller for managing departments. This controller handles the HTTP
 * requests for creating, retrieving, updating, and deleting departments.
 */
@RestController
@RequestMapping("/departements")
public class DepartementControleur {

	@Autowired
	DepartementService depService;

	/**
	 * Retrieves all departments.
	 *
	 * @return A ResponseEntity containing a list of all departments.
	 */
	@GetMapping
	public ResponseEntity<?> getDepartements() {
		List<Departement> departements = depService.extractDepartements();
		return ResponseEntity.ok(departements);
	}

	/**
	 * Retrieves a single department by its ID.
	 *
	 * @param id The ID of the department to retrieve.
	 * @return A ResponseEntity containing the requested department if found, or a
	 *         NOT_FOUND status if not found.
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getDepartement(@PathVariable int id) {
		try {
			Departement departement = depService.extractDepartement(id);
			return ResponseEntity.ok(departement);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departement non trouvé");
		}
	}

	/**
	 * Creates a new department.
	 *
	 * @param body   The department details from the request body.
	 * @param result BindingResult that captures validation errors.
	 * @return A ResponseEntity with the created department and HTTP status CREATED,
	 *         or throws an AccessException if validation fails.
	 * @throws AccessException if validation errors occur.
	 */
	@PostMapping
	public ResponseEntity<?> createDepartement(@RequestBody Departement body, BindingResult result)
			throws AccessException {
		if (result.hasErrors()) {
			throw new AccessException(result.getAllErrors().get(0).getDefaultMessage());
		}

		Departement newDepartement = depService.createDepartement(body);
		return ResponseEntity.status(HttpStatus.CREATED).body(newDepartement);

	}

	/**
	 * Updates an existing department by its ID.
	 *
	 * @param body The updated department details from the request body.
	 * @param id   The ID of the department to update.
	 * @return A ResponseEntity with the updated department if found, or a NOT_FOUND
	 *         status if not found.
	 */
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateDepartement(@RequestBody Departement body, @PathVariable int id) {

		try {
			Departement updatedDepartement = depService.updateDepartement(id, body);
			return ResponseEntity.ok(updatedDepartement);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departement non trouvé");
		}
	}

	/**
	 * Deletes a department by its ID.
	 *
	 * @param id The ID of the department to delete.
	 * @return A ResponseEntity with HTTP status NO_CONTENT if deleted successfully,
	 *         or NOT_FOUND if the department is not found.
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteDepartement(@PathVariable int id) {

		try {
			depService.deleteDepartement(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departement non trouvé");
		}
	}

}

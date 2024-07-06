package fr.diginamic.hello.controleurs;

import java.util.ArrayList;
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
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.services.DepartementService;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

	@Autowired
	DepartementService depService;

	@GetMapping
	public ResponseEntity<?> getDepartements() {
		List<Departement> departements = depService.extractDepartements();
		return ResponseEntity.ok(departements);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getDepartement(@PathVariable int id) {
		try {
			Departement departement = depService.extractDepartement(id);
			return ResponseEntity.ok(departement);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departement non trouvé");
		}
	}

	@PostMapping
	public ResponseEntity<?> addDepartement(@RequestBody Departement body, BindingResult result)
			throws AccessException {
		if (result.hasErrors()) {
			throw new AccessException(result.getAllErrors().get(0).getDefaultMessage());
		}

		Departement newDepartement = depService.createDepartement(body);
		return ResponseEntity.status(HttpStatus.CREATED).body(newDepartement);

	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateDepartement(@RequestBody Departement body, @PathVariable int id) {

		try {
			Departement updatedDepartement = depService.updateDepartement(id, body);
			return ResponseEntity.ok(updatedDepartement);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departement non trouvé");
		}
	}

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

package fr.diginamic.hello.controleurs;

import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

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

import com.itextpdf.text.DocumentException;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.services.DepartementService;
import fr.diginamic.hello.services.PdfGenerationService;
import fr.diginamic.hello.services.VilleService;

/**
 * REST controller for managing departments. This controller handles the HTTP
 * requests for creating, retrieving, updating, and deleting departments.
 */

@Tag(name = "DepartementController", description = "REST controller for managing departments. This controller handles the HTTP requests for creating, retrieving, updating, and deleting departments.")
@RestController
@RequestMapping("/departements")
public class DepartementControleur {

	@Autowired
	DepartementService depService;

	@Autowired
	VilleService villeService;

	@Autowired
	PdfGenerationService pdfGenerationService;

	/**
	 * Retrieves all departments.
	 *
	 * @return A ResponseEntity containing a list of all departments.
	 */

	@Operation(summary = "Retrieves all departments")
	@ApiResponse(responseCode = "200", description = "Successfully retrieved all departments", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Departement.class)))
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

	@Operation(summary = "Retrieves a single department by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Department found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Departement.class))),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getDepartement(@PathVariable int id) {
		Departement departement = depService.extractDepartement(id);
		return ResponseEntity.ok(departement);
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

	@Operation(summary = "Creates a new department")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Department created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Departement.class))),
			@ApiResponse(responseCode = "400", description = "Invalid department data provided") })
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

	@Operation(summary = "Updates an existing department by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Department updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Departement.class))),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateDepartement(@RequestBody Departement body, @PathVariable int id) {

		Departement updatedDepartement = depService.updateDepartement(id, body);
		return ResponseEntity.ok(updatedDepartement);
	}

	/**
	 * Deletes a department by its ID.
	 *
	 * @param id The ID of the department to delete.
	 * @return A ResponseEntity with HTTP status NO_CONTENT if deleted successfully,
	 *         or NOT_FOUND if the department is not found.
	 */

	@Operation(summary = "Deletes a department by its ID")
	@ApiResponse(responseCode = "204", description = "Department deleted successfully")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDepartement(@PathVariable int id) {

		depService.deleteDepartement(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	/**
	 * Exports the cities from a given department into a PDF file.
	 * 
	 * @param codeDepartement the department's code to include in the PDF.
	 * @param response        HttpServletResponse for setting up the file download.
	 * @throws IOException       If an input or output exception occurred
	 * @throws DocumentException If there is an error during document creation
	 */

	@Operation(summary = "Exports the cities from a given department into a PDF file", description = "Downloads a PDF file containing the cities of a given department based on the specified department's code.")
	@ApiResponse(responseCode = "200", description = "PDF file successfully downloaded")
	@GetMapping("/{codeDepartement}/villes/pdf-export")
	public void exportDepartementVillesToPDF(@PathVariable String codeDepartement, HttpServletResponse response)
			throws DocumentException, IOException {
		response.setHeader("Content-Disposition", "attachment; filename=\"top_villes.pdf\"");
		Departement departement = depService.getDepartementByCode(codeDepartement);
		List<Ville> cities = villeService.findByDepartement(departement);

		String titlePDF = "Liste des villes du département " + codeDepartement;
		String[] headers = { "NOM VILLE", "POPULATION" };

		pdfGenerationService.generatePDFReport(titlePDF, headers, cities, response.getOutputStream());
		response.flushBuffer();
	}

}

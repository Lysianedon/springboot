package fr.diginamic.hello.controleurs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.services.DepartementService;
import fr.diginamic.hello.services.VilleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Controller for managing cities. Provides endpoints for CRUD operations and
 * more specific city queries.
 */
@RestController
@RequestMapping("/villes")
@Tag(name = "VilleController", description = "Controller for managing cities. Provides endpoints for CRUD operations and more specific city queries.")
public class VilleControleur {

	@Autowired
	VilleService villeService;

	@Autowired
	private DepartementService depService;

	List<Ville> villes = new ArrayList<Ville>();

	/**
	 * Retrieves a paginated list of cities.
	 *
	 * @param page The page number to retrieve (default 0).
	 * @param size The number of records per page (default 100).
	 * @return A ResponseEntity containing a page of cities.
	 */

	@Operation(summary = "Get a paginated list of cities")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))) })
	@GetMapping
	public ResponseEntity<Page<Ville>> getVilles(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "100") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Ville> pageResult = villeService.extractVilles(pageable);
		return ResponseEntity.ok(pageResult);
	}

	/**
	 * Retrieves a city by its ID.
	 *
	 * @param id The ID of the city to retrieve.
	 * @return A ResponseEntity containing the city if found, or a NOT_FOUND status
	 *         if not found.
	 */

	@Operation(summary = "Get a city by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "City found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ville.class))),
			@ApiResponse(responseCode = "404", description = "City not found") })
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getVille(@PathVariable int id) {
		Ville ville = villeService.extractVille(id);
		return ResponseEntity.ok(ville);

	}

	/**
	 * Creates a new city.
	 *
	 * @param body   The city to create.
	 * @param result Contains any validation errors.
	 * @return A ResponseEntity with the created city or validation errors if any.
	 */

	@Operation(summary = "Create a new city")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "City created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ville.class))),
			@ApiResponse(responseCode = "400", description = "Validation error") })
	@PostMapping
	public ResponseEntity<?> insertVille(@Valid @RequestBody Ville body, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(
					result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining("\n")));
		}

		Ville newVille = villeService.createVille(body);
		return ResponseEntity.status(HttpStatus.CREATED).body(newVille);
	}

	/**
	 * Updates an existing city by its ID.
	 *
	 * @param body The updated city details.
	 * @param id   The ID of the city to update.
	 * @return A ResponseEntity containing the updated city or a NOT_FOUND status if
	 *         the city is not found.
	 */

	@Operation(summary = "Update an existing city")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "City updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ville.class))),
			@ApiResponse(responseCode = "404", description = "City not found") })
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateVille(@Valid @RequestBody Ville body, @PathVariable int id, BindingResult result) {

		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(
					result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining("\n")));
		}

		Ville updatedVille = villeService.modifierVille(id, body);
		return ResponseEntity.ok(updatedVille);
	}

	/**
	 * Deletes a city by its ID.
	 *
	 * @param id The ID of the city to delete.
	 * @return A ResponseEntity with NO_CONTENT status if the city was successfully
	 *         deleted or NOT_FOUND if the city is not found.
	 */

	@Operation(summary = "Delete a city by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "City deleted successfully"),
			@ApiResponse(responseCode = "404", description = "City not found") })
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteVille(@PathVariable int id) {
		villeService.supprimerVille(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	/**
	 * Retrieves the top n most populated cities in a specified department.
	 *
	 * @param departementId The ID of the department.
	 * @param maxResults    The maximum number of cities to retrieve.
	 * @return A ResponseEntity containing a list of cities if found, or NOT_FOUND
	 *         if the department is not found.
	 */

	@Operation(summary = "Retrieve the top N most populated cities in a specified department")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved top cities", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ville.class))),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	@GetMapping("/departement/{departementId}/top-villes")
	public ResponseEntity<?> getTopNVillesByDepartement(@PathVariable int departementId,
			@RequestParam(name = "maxResults", defaultValue = "5") int maxResults) {
		List<Ville> villes = villeService.findTopNVillesByDepartement(departementId, maxResults);
		return ResponseEntity.ok(villes);
	}

	/**
	 * Retrieves cities within a specific population range in a specified
	 * department.
	 *
	 * @param minPopulation The minimum population of the cities.
	 * @param maxPopulation The maximum population of the cities.
	 * @param departementId The ID of the department.
	 * @return A ResponseEntity containing a list of cities if any match the
	 *         criteria, or NOT_FOUND if none found.
	 */

	@Operation(summary = "Retrieve cities within a specific population range in a specified department")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cities found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	@GetMapping("/departement/{departementId}/population")
	public ResponseEntity<?> getVillesByPopulationAndDepartement(
			@RequestParam(name = "minPopulation") int minPopulation,
			@RequestParam(name = "maxPopulation") int maxPopulation, @PathVariable int departementId) {

		Departement departement = depService.extractDepartement(departementId);
		List<Ville> villes = villeService.findVillesByPopulationAndDepartement(minPopulation, maxPopulation,
				departement);
		return ResponseEntity.ok(villes);
	}

	/**
	 * Get a list of cities where the name starts with a specified prefix.
	 * 
	 * @param prefix The prefix to match city names against.
	 * @return A list of Ville objects or an empty list if none found.
	 */

	@Operation(summary = "Get a list of cities where the name starts with a specified prefix")
	@ApiResponse(responseCode = "200", description = "Cities retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
	@GetMapping("/search")
	public ResponseEntity<List<Ville>> getVillesByNomStartingWith(@RequestParam String prefix) {
		List<Ville> villes = villeService.findByNomStartingWith(prefix);
		return ResponseEntity.ok(villes);
	}

	/**
	 * Retrieves all cities with a population greater than a specified minimum.
	 *
	 * @param minPopulation the minimum population threshold
	 * @return a filtered list of cities
	 */

	@Operation(summary = "Retrieve all cities with a population greater than a specified minimum")
	@ApiResponse(responseCode = "200", description = "Cities retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
	@GetMapping("/search/by-min-population")
	public ResponseEntity<List<Ville>> getVillesByMinPopulation(@RequestParam int minPopulation) {
		List<Ville> villes = villeService.findByNbHabitantsGreaterThan(minPopulation);
		return ResponseEntity.ok(villes);
	}

	/**
	 * Retrieves all cities with a population between specified minimum and maximum
	 * limits.
	 *
	 * @param minPopulation the minimum population threshold
	 * @param maxPopulation the maximum population threshold
	 * @return a filtered list of cities
	 */

	@Operation(summary = "Retrieve all cities with a population between specified minimum and maximum limits")
	@ApiResponse(responseCode = "200", description = "Cities retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
	@GetMapping("/search/by-population-range")
	public ResponseEntity<?> getVillesByPopulationRange(@RequestParam long minPopulation,
			@RequestParam long maxPopulation) {
		return ResponseEntity.ok(villeService.findByNbHabitantsBetween(minPopulation, maxPopulation));
	}

	/**
	 * Retrieves all cities in a given department where the population is greater
	 * than a specified minimum.
	 *
	 * @param departementCode the code of the department
	 * @param minPopulation   the minimum population threshold
	 * @return a filtered list of cities, or a 404 response if the department is not
	 *         found
	 */

	@Operation(summary = "Retrieve all cities in a given department where the population is greater than a specified minimum")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cities retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	@GetMapping("/search/by-departement-and-min-population")
	public ResponseEntity<List<Ville>> getVillesByDepartementAndMinPopulation(@RequestParam String departementCode,
			@RequestParam int minPopulation) {
		Departement departement = depService.getDepartementByCode(departementCode);
		List<Ville> villes = villeService.findByDepartementAndNbHabitantsGreaterThan(departement, minPopulation);
		return ResponseEntity.ok(villes);
	}

	/**
	 * Retrieves all cities in a given department where the population is between
	 * specified minimum and maximum limits.
	 *
	 * @param departementCode the code of the department
	 * @param minPopulation   the minimum population threshold
	 * @param maxPopulation   the maximum population threshold
	 * @return a filtered list of cities, or a 404 response if the department is not
	 *         found
	 */

	@Operation(summary = "Retrieve all cities in a given department where the population is between specified minimum and maximum limits")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cities retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	@GetMapping("/search/by-departement-and-population-range")
	public ResponseEntity<List<Ville>> getVillesByDepartementAndPopulationRange(@RequestParam String departementCode,
			@RequestParam int minPopulation, @RequestParam int maxPopulation) {
		Departement departement = depService.getDepartementByCode(departementCode);

		List<Ville> villes = villeService.findByDepartementAndNbHabitantsBetween(departement, minPopulation,
				maxPopulation);
		return ResponseEntity.ok(villes);
	}

	/**
	 * Retrieves the top n most populated cities in a given department.
	 *
	 * @param departementCode the code of the department
	 * @param n               the number of cities to return
	 * @return a list of the most populated cities
	 */

	@Operation(summary = "Retrieves the top n most populated cities in a given department")
	@ApiResponse(responseCode = "200", description = "Successfully retrieved the top populated cities", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ville.class)))
	@GetMapping("/search/top-n-by-departement")
	public ResponseEntity<List<Ville>> getTopNVillesByDepartement(@RequestParam String departementCode,
			@RequestParam int n) {
		Departement departement = depService.getDepartementByCode(departementCode);

		Pageable pageable = PageRequest.of(0, n, Sort.by(Sort.Direction.DESC, "nbHabitants"));
		List<Ville> villes = villeService.findTopNVillesByDepartementOrderByNbHabitantsDesc(departement, pageable);
		return ResponseEntity.ok(villes);
	}
}

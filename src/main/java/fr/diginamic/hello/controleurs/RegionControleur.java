package fr.diginamic.hello.controleurs;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import fr.diginamic.hello.entities.Region;
import fr.diginamic.hello.services.RegionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing regions. This controller handles the HTTP
 * requests for creating, retrieving, updating, and deleting regions.
 */

@Tag(name = "RegionController", description = "REST controller for managing regions. This controller handles the HTTP requests for creating, retrieving, updating, and deleting regions.")
@RestController
@RequestMapping("/regions")
public class RegionControleur {

	@Autowired
	private RegionService regionService;

	/**
	 * Retrieves all regions.
	 *
	 * @return A ResponseEntity containing a list of all regions or NO_CONTENT if
	 *         there are no regions.
	 */

	@Operation(summary = "Retrieve all regions")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Regions found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
			@ApiResponse(responseCode = "204", description = "No regions found") })
	@GetMapping
	public ResponseEntity<List<Region>> getAllRegions() {
		List<Region> regions = regionService.getAllRegions();
		if (regions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(regions, HttpStatus.OK);
	}

	/**
	 * Retrieves a single region by its ID.
	 *
	 * @param id The ID of the region to retrieve.
	 * @return A ResponseEntity containing the requested region if found, or
	 *         NOT_FOUND if no such region exists.
	 */

	@Operation(summary = "Retrieve a single region by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Region found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
			@ApiResponse(responseCode = "404", description = "Region not found") })
	@GetMapping("/{id}")
	public ResponseEntity<Region> getRegionById(@PathVariable("id") long id) {
		Optional<Region> regionData = regionService.getRegionById(id);

		if (regionData.isPresent()) {
			return new ResponseEntity<>(regionData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Creates a new region.
	 *
	 * @param region The region details from the request body.
	 * @param result BindingResult that captures validation errors.
	 * @return A ResponseEntity with the created region and HTTP status CREATED if
	 *         successful, or CONFLICT if there's an IllegalArgumentException.
	 */

	@Operation(summary = "Create a new region")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Region created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
			@ApiResponse(responseCode = "409", description = "Conflict: region could not be created") })
	@PostMapping
	public ResponseEntity<?> createRegion(@Validated @RequestBody Region region, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(
					result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining("\n")));
		}
		try {
			Region savedRegion = regionService.createRegion(region);
			return new ResponseEntity<>(savedRegion, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
	}

	/**
	 * Updates an existing region by its ID.
	 *
	 * @param id     The ID of the region to update.
	 * @param region The updated region details from the request body.
	 * @param result BindingResult that captures validation errors.
	 * @return A ResponseEntity with the updated region if successful, or
	 *         BAD_REQUEST if validation fails.
	 */

	@Operation(summary = "Update an existing region by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Region updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
			@ApiResponse(responseCode = "400", description = "Bad request: validation failed") })
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRegion(@PathVariable("id") long id, @Validated @RequestBody Region region,
			BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(
					result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining("\n")));
		}
		try {
			Region updatedRegion = regionService.updateRegion(id, region);
			return new ResponseEntity<>(updatedRegion, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Deletes a region by its ID.
	 *
	 * @param id The ID of the region to delete.
	 * @return A ResponseEntity with HTTP status NO_CONTENT if deleted successfully,
	 *         or NOT_FOUND if the region does not exist.
	 */

	@Operation(summary = "Delete a region by ID")
	@ApiResponse(responseCode = "204", description = "Region deleted successfully")
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteRegion(@PathVariable("id") long id) {
		try {
			regionService.deleteRegionById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Deletes all regions.
	 *
	 * @return A ResponseEntity with HTTP status NO_CONTENT if all regions are
	 *         deleted successfully, or INTERNAL_SERVER_ERROR if an error occurs.
	 */

	@Operation(summary = "Delete all regions")
	@ApiResponse(responseCode = "204", description = "All regions deleted successfully")
	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllRegions() {
		try {
			regionService.deleteAllRegions();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

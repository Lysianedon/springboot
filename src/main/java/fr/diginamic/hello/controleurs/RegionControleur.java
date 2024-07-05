package fr.diginamic.hello.controleurs;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/regions")
public class RegionControleur {

	@Autowired
	private RegionService regionService;

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

	@GetMapping
	public ResponseEntity<List<Region>> getAllRegions() {
		List<Region> regions = regionService.getAllRegions();
		if (regions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(regions, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Region> getRegionById(@PathVariable("id") long id) {
		Optional<Region> regionData = regionService.getRegionById(id);

		if (regionData.isPresent()) {
			return new ResponseEntity<>(regionData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

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

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteRegion(@PathVariable("id") long id) {
		try {
			regionService.deleteRegionById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

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

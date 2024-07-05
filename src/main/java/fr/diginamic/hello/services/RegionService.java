package fr.diginamic.hello.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.entities.Region;
import fr.diginamic.hello.repositories.RegionRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RegionService {

	@Autowired
	private RegionRepository regionRepository;

	@Transactional
	public Region createRegion(Region region) {
		boolean exists = regionRepository.existsByCode(region.getCode());
		if (exists) {
			throw new IllegalArgumentException("La région avec le code " + region.getCode() + " existe déjà.");
		}
		return regionRepository.save(region);
	}

	@Transactional(readOnly = true)
	public boolean existsByCode(String code) {
		return regionRepository.existsByCode(code);
	}

	@Transactional(readOnly = true)
	public List<Region> getAllRegions() {
		return regionRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Region> getRegionById(long id) {
		return regionRepository.findById(id);
	}

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

	@Transactional
	public void deleteRegionById(long id) {
		boolean exists = regionRepository.existsById(id);
		if (!exists) {
			throw new IllegalArgumentException("La région avec l'ID " + id + " n'existe pas.");
		}
		regionRepository.deleteById(id);
	}

	@Transactional
	public void deleteAllRegions() {
		regionRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	public Region getByNom(String nomRegion) {
		Optional<Region> region = regionRepository.findByNom(nomRegion);
		if (!region.isPresent()) {
			throw new IllegalArgumentException("La région avec le nom " + nomRegion + " n'existe pas.");
		}
		return region.get();
	}

	@Transactional(readOnly = true)
	public Region findByCode(String codeRegion) {
		return regionRepository.findByCode(codeRegion).orElseThrow(
				() -> new IllegalArgumentException("La région avec le code " + codeRegion + " n'existe pas."));
	}
}

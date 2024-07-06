package fr.diginamic.hello.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Region;
import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.repositories.RegionRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DepartementService {

	@Autowired
	private DepartementRepository departementRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Transactional(readOnly = true)
	public List<Departement> extractDepartements() {
		return (List<Departement>) departementRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Departement extractDepartement(int id) {
		return departementRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Departement non trouvé avec ID : " + id));
	}

	@Transactional(readOnly = true)
	public Departement extractDepartement(String code) {
		return departementRepository.findByCode(code)
				.orElseThrow(() -> new EntityNotFoundException("Departement non trouvé avec code : " + code));
	}

	@Transactional
	public Departement createDepartement(Departement departement) {
		boolean exists = departementRepository.existsByCode(departement.getCode());
		if (exists) {
			throw new IllegalArgumentException(
					"Le département avec le code " + departement.getCode() + " existe déjà.");
		}

		Region region = departement.getRegion();
		if (region != null) {
			Optional<Region> existingRegion = regionRepository.findById(region.getId());
			if (existingRegion.isPresent()) {
				departement.setRegion(existingRegion.get());
			} else {
				throw new IllegalArgumentException("La région spécifiée n'existe pas.");
			}
		}
		return departementRepository.save(departement);
	}

	@Transactional
	public Departement updateDepartement(int id, Departement dptm) {
		return departementRepository.findById(id).map(d -> {
			d.setCode(dptm.getCode());
			d.setRegion(dptm.getRegion());
			return departementRepository.save(d);
		}).orElseThrow(() -> new EntityNotFoundException("Departement non trouvé avec ID : " + id));

	}

	@Transactional
	public void deleteDepartement(int id) {
		if (!departementRepository.existsById(id)) {
			throw new EntityNotFoundException("Departement non trouvé avec ID : " + id);
		}
		departementRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Departement getDepartementByCode(String code) {
		return departementRepository.findByCode(code).orElseThrow(
				() -> new IllegalArgumentException("Le département avec le code " + code + " n'existe pas."));
	}

	public boolean existsByCode(String codeDepartement) {
		return departementRepository.existsByCode(codeDepartement);
	}

}

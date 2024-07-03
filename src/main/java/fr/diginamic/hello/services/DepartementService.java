package fr.diginamic.hello.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.classes.Departement;
import fr.diginamic.hello.repositories.DepartementRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DepartementService {

	@Autowired
	private DepartementRepository departRepo;

	@Transactional
	public List<Departement> extractDepartements() {
		return (List<Departement>) departRepo.findAll();
	}

	@Transactional
	public Departement extractDepartement(int id) {
		return departRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Departement non trouvé avec ID : " + id));
	}

	@Transactional
	public Departement extractDepartement(String nom) {
		return departRepo.findByNom(nom)
				.orElseThrow(() -> new EntityNotFoundException("Departement non trouvé avec nom : " + nom));
	}

	@Transactional
	public Departement insertDepartement(Departement departement) {
		return departRepo.save(departement);
	}

	@Transactional
	public Departement modifierDepartement(int id, Departement dptm) {
		return departRepo.findById(id).map(d -> {
			d.setNom(dptm.getNom());
			d.setVilles(dptm.getVilles());
			return departRepo.save(d);
		}).orElseThrow(() -> new EntityNotFoundException("Departement non trouvé avec ID : " + id));

	}

	public void supprimerDepartement(int id) {
		if (!departRepo.existsById(id)) {
			throw new EntityNotFoundException("Departement non trouvé avec ID : " + id);
		}
		departRepo.deleteById(id);
	}
	

}

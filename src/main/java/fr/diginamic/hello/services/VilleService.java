package fr.diginamic.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.exceptions.EntityException;
import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.repositories.VilleRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

	@Autowired
	private VilleRepository villeRepository;

	@Autowired
	private DepartementRepository departementRepository;

//	@Autowired
//	private EntityException EntityException;

	@Transactional(readOnly = true)
	public Page<Ville> extractVilles(Pageable pageable) {
	    return villeRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Ville extractVille(int idVille) {
		return villeRepository.findById(idVille)
				.orElseThrow(() -> new EntityNotFoundException("Ville non trouvée avec ID : " + idVille));
	}

	@Transactional(readOnly = true)
	public Ville extractVille(String nom) {
		return villeRepository.findByNom(nom)
				.orElseThrow(() -> new EntityNotFoundException("Ville non trouvée avec nom : " + nom));
	}

	@Transactional
	public Ville createVille(Ville ville) {
		boolean villeExists = villeRepository.existsByNomAndDepartement(ville.getNom(), ville.getDepartement());
		boolean departementExists = ville.getDepartement() != null
				&& departementRepository.existsById(ville.getDepartement().getId());

		if (villeExists) {
			throw new EntityException("La ville existe déjà.");
		}

		if (!departementExists) {
			throw new IllegalArgumentException("Département non renseigné ou introuvable.");
		}

		Departement departement = departementRepository.findById(ville.getDepartement().getId()).get();
		ville.setDepartement(departement);

		return villeRepository.save(ville);
	}

	@Transactional
	public Ville modifierVille(int idVille, Ville villeModifiee) {
		return villeRepository.findById(idVille).map(ville -> {
			ville.setNom(villeModifiee.getNom());
			ville.setNbHabitants(villeModifiee.getNbHabitants());
			return villeRepository.save(ville);
		}).orElseThrow(() -> new EntityNotFoundException("Ville non trouvée avec ID : " + idVille));
	}

	@Transactional
	public void supprimerVille(int idVille) {
		if (!villeRepository.existsById(idVille)) {
			throw new EntityNotFoundException("Ville non trouvée avec ID : " + idVille);
		}
		villeRepository.deleteById(idVille);
	}

	@Transactional(readOnly = true)
	public List<Ville> findTopNVillesByDepartement(int departementId, int maxResults) {
		Optional<Departement> departementOpt = departementRepository.findById(departementId);
		if (departementOpt.isPresent()) {
			Departement departement = departementOpt.get();
			PageRequest pageRequest = PageRequest.of(0, maxResults);
			return villeRepository.findByDepartementOrderByNbHabitantsDesc(departement, pageRequest);
		}
		return null;
	}

	@Transactional(readOnly = true)
	public List<Ville> findVillesByPopulationAndDepartement(int minPopulation, int maxPopulation,
			Departement departement) {
		return villeRepository.findByNbHabitantsBetweenAndDepartement(minPopulation, maxPopulation, departement);
	}

	@Transactional(readOnly = true)
	public boolean existsByNom(String nom) {
		return villeRepository.existsByNom(nom);
	}

	// Recherche de toutes les villes dont le nom commence par une chaine de
	// caractères
	@Transactional(readOnly = true)
	public List<Ville> findByNomStartingWith(String prefix) {
		return villeRepository.findByNomStartingWith(prefix);
	}

	// Recherche de toutes les villes dont la population est supérieure à min
	@Transactional(readOnly = true)
	public List<Ville> findByNbHabitantsGreaterThan(int minPopulation){
		return villeRepository.findByNbHabitantsGreaterThan(minPopulation);
	}

	// Recherche de toutes les villes dont la population est supérieure à min et
	// inférieure à max
	@Transactional(readOnly = true)
	public List<Ville> findByNbHabitantsBetween(int minPopulation, int maxPopulation){
		return villeRepository.findByNbHabitantsBetween(minPopulation, maxPopulation);
	}

	// Recherche de toutes les villes d’un département dont la population est
	// supérieure à min
	@Transactional(readOnly = true)
	public List<Ville> findByDepartementAndNbHabitantsGreaterThan(Departement departement, int minPopulation){
		return villeRepository.findByDepartementAndNbHabitantsGreaterThan(departement, minPopulation);
	}

	// Recherche de toutes les villes d’un département dont la population est
	// supérieure à min et inférieure à max
	@Transactional(readOnly = true)
	public List<Ville> findByDepartementAndNbHabitantsBetween(Departement departement, int minPopulation, int maxPopulation){
		return villeRepository.findByDepartementAndNbHabitantsBetween(departement, minPopulation, maxPopulation);
	}

	// Recherche des n villes les plus peuplées d’un département donné
	@Transactional(readOnly = true)
	public List<Ville> findTopNVillesByDepartementOrderByNbHabitantsDesc(Departement departement, Pageable pageable){
		return villeRepository.findTopNVillesByDepartementOrderByNbHabitantsDesc(departement, pageable);
	}

}

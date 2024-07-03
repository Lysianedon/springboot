package fr.diginamic.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.classes.Departement;
import fr.diginamic.hello.classes.Ville;
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

	public List<Ville> extractVilles() {
		return (List<Ville>) villeRepository.findAll();
	}

	public Ville extractVille(int idVille) {
		return villeRepository.findById(idVille)
				.orElseThrow(() -> new EntityNotFoundException("Ville non trouvée avec ID : " + idVille));
	}

	public Ville extractVille(String nom) {
		return villeRepository.findByNom(nom)
				.orElseThrow(() -> new EntityNotFoundException("Ville non trouvée avec nom : " + nom));
	}

	public Ville insertVille(Ville ville) {
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
	
	   public List<Ville> findTopNVillesByDepartement(int departementId, int maxResults) {
	        Optional<Departement> departementOpt = departementRepository.findById(departementId);
	        if (departementOpt.isPresent()) {
	            Departement departement = departementOpt.get();
	            PageRequest pageRequest = PageRequest.of(0, maxResults);
	            return villeRepository.findByDepartementOrderByNbHabitantsDesc(departement, pageRequest);
	        }
	        return null;
	    }
	   
	   public List<Ville> findVillesByPopulationAndDepartement(int minPopulation, int maxPopulation, Departement departement) {
	        return villeRepository.findByNbHabitantsBetweenAndDepartement(minPopulation, maxPopulation, departement);
	    }
}

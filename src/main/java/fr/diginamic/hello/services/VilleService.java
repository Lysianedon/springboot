package fr.diginamic.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.exceptions.DepartementNotFoundException;
import fr.diginamic.hello.exceptions.VilleNotFoundException;
import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.repositories.VilleRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

/**
 * Service class for managing city entities.
 */
@Service
public class VilleService {

	@Autowired
	private VilleRepository villeRepository;

	@Autowired
	private DepartementRepository departementRepository;

	@Autowired
	private DepartementService departementService;

	/**
	 * Retrieves a paginated list of all cities.
	 *
	 * @param pageable Pagination information.
	 * @return A page of cities.
	 */
	@Transactional(readOnly = true)
	public Page<Ville> extractVilles(Pageable pageable) {
		return villeRepository.findAll(pageable);
	}

	/**
	 * Retrieves a city by its ID.
	 *
	 * @param idVille The ID of the city.
	 * @return The requested city if found.
	 * @throws EntityNotFoundException if the city is not found.
	 */
	@Transactional(readOnly = true)
	public Ville extractVille(int idVille) {
		return villeRepository.findById(idVille)
				.orElseThrow(() -> new EntityNotFoundException("Ville non trouvée avec ID : " + idVille));
	}

	/**
	 * Retrieves a city by its name.
	 *
	 * @param nom The name of the city.
	 * @return The requested city if found.
	 * @throws EntityNotFoundException if the city is not found.
	 */
	@Transactional(readOnly = true)
	public Ville extractVille(String nom) {
		return villeRepository.findByNom(nom)
				.orElseThrow(() -> new EntityNotFoundException("Ville non trouvée avec nom : " + nom));
	}

	/**
	 * Creates a new city.
	 *
	 * @param ville The city to be created.
	 * @return The created city.
	 * @throws EntityException          if the city already exists.
	 * @throws IllegalArgumentException if the associated department does not exist.
	 */
	@Transactional
	public Ville createVille(Ville ville) {
		boolean villeExists = villeRepository.existsByNomAndDepartement(ville.getNom(), ville.getDepartement());
		boolean departementExists = ville.getDepartement() != null
				&& departementRepository.existsById(ville.getDepartement().getId());

		if (villeExists) {
			throw new IllegalArgumentException("La ville existe déjà.");
		}

		if (!departementExists) {
			throw new DepartementNotFoundException("Département non renseigné ou introuvable.");
		}

		Departement departement = departementRepository.findById(ville.getDepartement().getId()).get();
		ville.setDepartement(departement);

		return villeRepository.save(ville);
	}

	/**
	 * Modifies an existing city.
	 *
	 * @param idVille       The ID of the city to modify.
	 * @param villeModifiee The city data to update.
	 * @return The updated city.
	 * @throws EntityNotFoundException if the city is not found.
	 */
	@Transactional
	public Ville modifierVille(int idVille, Ville villeModifiee) {
		return villeRepository.findById(idVille).map(ville -> {
			ville.setNom(villeModifiee.getNom());
			ville.setNbHabitants(villeModifiee.getNbHabitants());
			return villeRepository.save(ville);
		}).orElseThrow(() -> new EntityNotFoundException("Ville non trouvée avec ID : " + idVille));
	}

	/**
	 * Deletes a city by its ID.
	 *
	 * @param idVille The ID of the city to delete.
	 * @throws EntityNotFoundException if the city is not found.
	 */
	@Transactional
	public void supprimerVille(int idVille) {
		if (!villeRepository.existsById(idVille)) {
			throw new EntityNotFoundException("Ville non trouvée avec ID : " + idVille);
		}
		villeRepository.deleteById(idVille);
	}

	/**
	 * Retrieves a list of the top n most populated cities within a specific
	 * department.
	 *
	 * @param departementId The ID of the department.
	 * @param maxResults    The maximum number of results to return.
	 * @return A list of cities, or null if the department is not found.
	 */
	@Transactional(readOnly = true)
	public List<Ville> findTopNVillesByDepartement(int departementId, int maxResults) {
		Departement departement = departementService.extractDepartement(departementId);
		PageRequest pageRequest = PageRequest.of(0, maxResults);
		return villeRepository.findByDepartementOrderByNbHabitantsDesc(departement, pageRequest);
	}

	/**
	 * Retrieves a list of cities within a specific department where the population
	 * falls within a specified range.
	 *
	 * @param minPopulation The minimum population threshold.
	 * @param maxPopulation The maximum population threshold.
	 * @param departement   The department in which to search for cities.
	 * @return A list of cities meeting the population criteria.
	 */
	@Transactional(readOnly = true)
	public List<Ville> findVillesByPopulationAndDepartement(int minPopulation, int maxPopulation,
			Departement departement) {
		return villeRepository.findByNbHabitantsBetweenAndDepartement(minPopulation, maxPopulation, departement);
	}

	/**
	 * Checks if a city with a given name exists.
	 *
	 * @param nom The name of the city to check.
	 * @return true if the city exists, false otherwise.
	 */
	@Transactional(readOnly = true)
	public boolean existsByNom(String nom) {
		return villeRepository.existsByNom(nom);
	}

	/**
	 * Retrieves a list of cities whose names start with a specified prefix.
	 *
	 * @param prefix The prefix to search by.
	 * @return A list of cities that start with the given prefix.
	 */
	@Transactional(readOnly = true)
	public List<Ville> findByNomStartingWith(String prefix) {
		List<Ville> result = villeRepository.findByNomStartingWith(prefix);
		if (result.isEmpty()) {
			throw new VilleNotFoundException("Aucune ville dont le nom commence par " + prefix + " n’a été trouvée.");
		}
		return result;
	}

	/**
	 * Retrieves a list of cities where the population is greater than a specified
	 * minimum.
	 *
	 * @param minPopulation The minimum population threshold.
	 * @return A list of cities with a population greater than the specified
	 *         minimum.
	 */
	@Transactional(readOnly = true)
	public List<Ville> findByNbHabitantsGreaterThan(int minPopulation) {
		List<Ville> result = villeRepository.findByNbHabitantsGreaterThan(minPopulation);
		if (result.isEmpty()) {
			throw new VilleNotFoundException("Aucune ville n’a une population supérieure à " + minPopulation);
		}
		return result;
	}

	/**
	 * Retrieves a list of cities where the population falls within a specified
	 * range.
	 *
	 * @param minPopulation The minimum population threshold.
	 * @param maxPopulation The maximum population threshold.
	 * @return A list of cities where the population is between the specified
	 *         minimum and maximum.
	 */
	@Transactional(readOnly = true)
	public List<Ville> findByNbHabitantsBetween(long minPopulation, long maxPopulation) {

		List<Ville> result = villeRepository.findByNbHabitantsBetween(minPopulation, maxPopulation);

		if (result.isEmpty()) {
			throw new VilleNotFoundException(
					"Aucune ville n’a une population comprise entre " + minPopulation + " et " + maxPopulation);
		}
		return result;
	}

	/**
	 * Retrieves a list of cities in a given department where the population is
	 * greater than a specified minimum.
	 *
	 * @param departement   The department in which to search for cities.
	 * @param minPopulation The minimum population threshold.
	 * @return A list of cities in the specified department with a population
	 *         greater than the specified minimum.
	 */
	@Transactional(readOnly = true)
	public List<Ville> findByDepartementAndNbHabitantsGreaterThan(Departement departement, int minPopulation) {

		List<Ville> result = villeRepository.findByDepartementAndNbHabitantsGreaterThan(departement, minPopulation);

		if (result.isEmpty()) {
			throw new VilleNotFoundException("Aucune ville n’a une population supérieure à " + minPopulation
					+ "dans le département " + departement.getCode());
		}
		return result;
	}

	/**
	 * Retrieves a list of cities in a given department where the population falls
	 * within a specified range.
	 *
	 * @param departement   The department in which to search for cities.
	 * @param minPopulation The minimum population threshold.
	 * @param maxPopulation The maximum population threshold.
	 * @return A list of cities in the specified department where the population is
	 *         between the specified minimum and maximum.
	 */
	@Transactional(readOnly = true)
	public List<Ville> findByDepartementAndNbHabitantsBetween(Departement departement, int minPopulation,
			int maxPopulation) {
		List<Ville> result = villeRepository.findByDepartementAndNbHabitantsBetween(departement, minPopulation,
				maxPopulation);
		if (result.isEmpty()) {
			throw new VilleNotFoundException("Aucune ville n’a une population comprise entre " + minPopulation + " et "
					+ maxPopulation + " dans le département " + departement.getCode());
		}
		return result;
	}

	/**
	 * Retrieves a list of the top n most populated cities within a specific
	 * department, ordered by population descending.
	 *
	 * @param departement The department to search within.
	 * @param pageable    Pagination and sorting information.
	 * @return A paginated list of cities ordered by descending population.
	 */
	@Transactional(readOnly = true)
	public List<Ville> findTopNVillesByDepartementOrderByNbHabitantsDesc(Departement departement, Pageable pageable) {
		return villeRepository.findTopNVillesByDepartementOrderByNbHabitantsDesc(departement, pageable);
	}

}

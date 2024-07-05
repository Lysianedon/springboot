package fr.diginamic.hello;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Region;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.services.DepartementService;
import fr.diginamic.hello.services.RegionService;
import fr.diginamic.hello.services.VilleService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Main class for processing a CSV file to load data into a database. This class
 * reads data about cities, departments, and regions from a CSV file, and
 * creates corresponding entities in the database.
 *
 * The application runs as a non-web application within Spring Boot's context,
 * primarily intended to be used as a batch processing job that doesn't start a
 * web server.
 */

@SpringBootApplication
public class TraitementFichiersApplication implements CommandLineRunner {
	@Autowired
	private VilleService villeService;

	@Autowired
	private DepartementService departementService;

	@Autowired
	private RegionService regionService;

	private final String filePath = "/Users/lysianedon/Documents/DEV/recensement.csv";

	// Index des colonnes
	private final int indexCodeRegion = 0;
	private final int indexNomRegion = 1;
	private final int indexCodeDepartement = 2;
	private final int indexCodeArrondissement = 3;
	private final int indexCodeCanton = 4;
	private final int indexCodeCommune = 5;
	private final int indexNomCommune = 6;
	private final int indexPopulationMunicipale = 7;
	private final int indexPopulationCompteeAPart = 8;
	private final int indexPopulationTotale = 9;

	private Map<String, Region> regionCache = new HashMap<>();
	private Map<String, Departement> departementCache = new HashMap<>();

	/**
	 * The main method uses Spring Boot's SpringApplication.run to launch the
	 * application.
	 * 
	 * @param args Command line arguments passed to the application.
	 */

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(TraitementFichiersApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

	/**
	 * Runs the CSV processing task which reads from a specified CSV file and
	 * processes each line.
	 * 
	 * @param args Command line arguments, not used here.
	 * @throws Exception if there is an issue in processing the file or interacting
	 *                   with the database.
	 */

	@Override
	public void run(String... args) throws Exception {
		List<String> lines = loadLinesFromCsv(filePath);

		// Skip header line
		for (int i = 1; i < lines.size(); i++) {
			String[] fields = splitLine(lines.get(i));
			createEntitiesFromFields(fields);
		}
	}

	/**
	 * Creates entities based on the fields extracted from a single line of the CSV
	 * file. This includes creating or fetching cached instances of regions and
	 * departments, and creating new city instances.
	 * 
	 * @param fields String array representing columns of a single CSV row.
	 * @return Ville The created Ville object, or null if an inconsistency is found
	 *         or an error occurs.
	 */
	private Ville createEntitiesFromFields(String[] fields) {
		String codeRegion = fields[indexCodeRegion];
		String nomRegion = fields[indexNomRegion];
		String codeDepartement = fields[indexCodeDepartement];
		String codeArrondissement = fields[indexCodeArrondissement];
		String codeCanton = fields[indexCodeCanton];
		String codeCommune = fields[indexCodeCommune];
		String nomCommune = fields[indexNomCommune];
		long populationMunicipale = parseLong(fields[indexPopulationMunicipale]);
		long populationCompteeAPart = parseLong(fields[indexPopulationCompteeAPart]);
		long populationTotale = parseLong(fields[indexPopulationTotale]);

		try {

			Region region = regionCache.get(codeRegion);
			if (region == null) {
				boolean regionExists = regionService.existsByCode(codeRegion);
				if (!regionExists) {
					region = new Region(codeRegion, nomRegion);
					regionService.createRegion(region);
				} else {
					region = regionService.findByCode(codeRegion);
				}
				regionCache.put(codeRegion, region);
			}

			if (!region.getNom().equals(nomRegion)) {
				System.err.println("Inconsistency found: Region code " + codeRegion + " has different names: "
						+ region.getNom() + " and " + nomRegion);
				return null;
			}

			Departement departement = departementCache.get(codeDepartement);
			if (departement == null) {
				boolean departementExists = departementService.existsByCode(codeDepartement);
				if (!departementExists) {
					departement = new Departement(codeDepartement, region);
					departementService.createDepartement(departement);
				} else {
					departement = departementService.getDepartementByCode(codeDepartement);

				}
			}

			Ville ville = new Ville(nomCommune, populationTotale, codeArrondissement, codeCanton, codeCommune,
					populationMunicipale, populationCompteeAPart, populationTotale, departement);

			try {
				boolean villeExists = villeService.existsByNom(ville.getNom());
				if (!villeExists) {
					villeService.createVille(ville);
				}
			} catch (Exception e) {
				System.err.println("Error saving Ville: " + ville.getNom() + ": " + e.getMessage());
				e.printStackTrace();
			}
			return ville;

		} catch (Exception e) {
			System.err.println("Error processing line " + Arrays.toString(fields) + ": " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Loads all lines from the specified CSV file path.
	 * 
	 * @param filePath Path to the CSV file.
	 * @return List<String> containing all lines read from the file.
	 * @throws IOException if there's an issue reading the file.
	 */
	private List<String> loadLinesFromCsv(String filePath) throws IOException {
		Path csvPath = Paths.get(filePath);
		List<String> lines = Files.readAllLines(csvPath, StandardCharsets.UTF_8);
		return lines;
	}

	/**
	 * Splits a single line of text into its component fields based on the CSV
	 * delimiter.
	 * 
	 * @param line The string line to split.
	 * @return An array of string fields extracted from the line.
	 */
	private String[] splitLine(String line) {
		return line.split(";");
	}

	/**
	 * Parses a string to extract a long value, removing any whitespace characters.
	 * 
	 * @param value The string to parse.
	 * @return long The parsed long value.
	 * @throws NumberFormatException if the string does not contain a parsable long.
	 */
	private long parseLong(String value) {
		try {
			return Long.parseLong(value.replaceAll("\\s", ""));
		} catch (NumberFormatException e) {
			System.err.println("Error parsing long value: " + value);
			throw e;
		}
	}
}

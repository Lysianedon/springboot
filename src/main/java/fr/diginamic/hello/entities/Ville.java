package fr.diginamic.hello.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Représente une ville avec un nom, un nombre d'habitants, un departement et un
 * identifiant unique.
 */
@Entity
public class Ville {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "NOM")
	@NotNull(message = "Le nom ne peut pas être nul")
	@Size(min = 2, max = 100)
	private String nom;

	@Column(name = "NB_HABITANTS")
	@Min(value = 1)
	@NotNull
	private long nbHabitants;

	@Column(name = "CODE_ARRONDISSEMENT")
	private String codeArrondissement;

	@Column(name = "CODE_CANTON")
	private String codeCanton;

	@Column(name = "CODE_COMMUNE")
	private String codeCommune;

	@Column(name = "POPULATION_MUNICIPALE")
	private long populationMunicipale;

	@Column(name = "POPULATION_COMPTEE_A_PART")
	private long populationCompteeAPart;

	@Column(name = "POPULATION_TOTALE")
	private long populationTotale;

	@ManyToOne
	@JoinColumn(name = "DEPARTEMENT_ID")
	private Departement departement;

	/**
	 * Constructeur par défaut requis par JPA.
	 */
	public Ville() {
	}

	/**
	 * Constructeur pour créer une ville avec tous les champs.
	 *
	 * @param nom                    le nom de la ville
	 * @param nbHabitants            le nombre d'habitants de la ville
	 * @param codeArrondissement     le code de l'arrondissement
	 * @param codeCanton             le code du canton
	 * @param codeCommune            le code de la commune
	 * @param populationMunicipale   la population municipale
	 * @param populationCompteeAPart la population comptée à part
	 * @param populationTotale       la population totale
	 * @param codeDepartement        le code du département
	 * @param departement            le département
	 */
	public Ville(String nom, long nbHabitants, String codeArrondissement, String codeCanton, String codeCommune,
			long populationMunicipale, long populationCompteeAPart, long populationTotale, Departement departement) {
		this.nom = nom;
		this.nbHabitants = nbHabitants;
		this.codeArrondissement = codeArrondissement;
		this.codeCanton = codeCanton;
		this.codeCommune = codeCommune;
		this.populationMunicipale = populationMunicipale;
		this.populationCompteeAPart = populationCompteeAPart;
		this.populationTotale = populationTotale;
		this.departement = departement;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public long getNbHabitants() {
		return nbHabitants;
	}

	public void setNbHabitants(long nbHabitants) {
		this.nbHabitants = nbHabitants;
	}

	public String getCodeArrondissement() {
		return codeArrondissement;
	}

	public void setCodeArrondissement(String codeArrondissement) {
		this.codeArrondissement = codeArrondissement;
	}

	public String getCodeCanton() {
		return codeCanton;
	}

	public void setCodeCanton(String codeCanton) {
		this.codeCanton = codeCanton;
	}

	public String getCodeCommune() {
		return codeCommune;
	}

	public void setCodeCommune(String codeCommune) {
		this.codeCommune = codeCommune;
	}

	public long getPopulationMunicipale() {
		return populationMunicipale;
	}

	public void setPopulationMunicipale(long populationMunicipale) {
		this.populationMunicipale = populationMunicipale;
	}

	public long getPopulationCompteeAPart() {
		return populationCompteeAPart;
	}

	public void setPopulationCompteeAPart(long populationCompteeAPart) {
		this.populationCompteeAPart = populationCompteeAPart;
	}

	public long getPopulationTotale() {
		return populationTotale;
	}

	public void setPopulationTotale(long populationTotale) {
		this.populationTotale = populationTotale;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Ville [id=" + id + ", nom=" + nom + ", nbHabitants=" + nbHabitants + ", codeArrondissement="
				+ codeArrondissement + ", codeCanton=" + codeCanton + ", codeCommune=" + codeCommune
				+ ", populationMunicipale=" + populationMunicipale + ", populationCompteeAPart="
				+ populationCompteeAPart + ", populationTotale=" + populationTotale + "departement=" + departement
				+ "]";
	}

}

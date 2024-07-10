package fr.diginamic.hello.dto;

public class VilleDto {

	private String nomVille;
	private long nombreHabitants;
	private String codeDepartement;
	private String nomDepartement;

	public VilleDto() {
	}

	public VilleDto(String nomVille, long nombreHabitants, String codeDepartement) {
		this.nomVille = nomVille;
		this.nombreHabitants = nombreHabitants;
		this.codeDepartement = codeDepartement;
	}

	public String getNomVille() {
		return nomVille;
	}

	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}

	public long getNombreHabitants() {
		return nombreHabitants;
	}

	public void setNombreHabitants(long nombreHabitants) {
		this.nombreHabitants = nombreHabitants;
	}

	public String getCodeDepartement() {
		return codeDepartement;
	}

	public void setCodeDepartement(String codeDepartement) {
		this.codeDepartement = codeDepartement;
	}

	public String getNomDepartement() {
		return nomDepartement;
	}

	public void setNomDepartement(String nomDepartement) {
		this.nomDepartement = nomDepartement;
	}
	
	@Override
	public String toString() {
		return "VilleDto{" + "nomVille='" + nomVille + '\'' + ", nombreHabitants=" + nombreHabitants
				+ ", codeDepartement='" + codeDepartement + '\'' + '}';
	}
}

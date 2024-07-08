package fr.diginamic.hello.dto;

public class VilleDto {

	private String codeVille;
	private long nombreHabitants;
	private String codeDepartement;

	public VilleDto() {
	}

	public VilleDto(String codeVille, long nombreHabitants, String codeDepartement) {
		this.codeVille = codeVille;
		this.nombreHabitants = nombreHabitants;
		this.codeDepartement = codeDepartement;
	}

	public String getCodeVille() {
		return codeVille;
	}

	public void setCodeVille(String codeVille) {
		this.codeVille = codeVille;
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

	@Override
	public String toString() {
		return "VilleDto{" + "codeVille='" + codeVille + '\'' + ", nombreHabitants=" + nombreHabitants
				+ ", codeDepartement='" + codeDepartement + '\'' + '}';
	}
}

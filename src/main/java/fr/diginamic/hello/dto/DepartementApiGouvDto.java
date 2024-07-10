package fr.diginamic.hello.dto;

public class DepartementApiGouvDto {

	private String nom;
	private String code;
	private String codeRegion;

	public DepartementApiGouvDto() {

	}

	public DepartementApiGouvDto(String nom, String code, String codeRegion) {
		this.nom = nom;
		this.code = code;
		this.codeRegion = codeRegion;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeRegion() {
		return codeRegion;
	}

	public void setCodeRegion(String codeRegion) {
		this.codeRegion = codeRegion;
	}

	@Override
	public String toString() {
		return "DepartementApiGouvDto [nom=" + nom + ", code=" + code + ", codeRegion=" + codeRegion + "]";
	}

}

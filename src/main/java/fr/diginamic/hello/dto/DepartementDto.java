package fr.diginamic.hello.dto;

public class DepartementDto {

	private String code;
	private long nombreHabitants;

	public DepartementDto() {

	}

	public DepartementDto(String code, long nombreHabitants) {
		this.code = code;
		this.nombreHabitants = nombreHabitants;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getNombreHabitants() {
		return nombreHabitants;
	}

	public void setNombreHabitants(long nombreHabitants) {
		this.nombreHabitants = nombreHabitants;
	}

	@Override
	public String toString() {
		return "DepartementDto [code=" + code + ", nombreHabitants=" + nombreHabitants + "]";
	}

}

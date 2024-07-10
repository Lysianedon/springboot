package fr.diginamic.hello.mappers;

import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;

public class VilleMapper {

	public static VilleDto toDto(Ville ville) {
		if (ville == null) {
			return null;
		}
		VilleDto dto = new VilleDto();

		dto.setNombreHabitants(ville.getPopulationTotale());
		if (ville.getDepartement() != null) {
			dto.setCodeDepartement(ville.getDepartement().getCode());
		}
		return dto;
	}

	public VilleDto toDto(String nomVille, long nombreHabitants, String codeDepartement, String nomDepartement) {

		if (nomVille == null || nomVille.isEmpty() || nomDepartement == null || nomDepartement.isEmpty()) {
			 throw new IllegalArgumentException("Nom de ville ou nom de département est manquant.");
		}
		
		VilleDto dto = new VilleDto();

		dto.setNomVille(nomVille);
		dto.setNombreHabitants(nombreHabitants);
		dto.setCodeDepartement(codeDepartement);
		dto.setNomDepartement(nomDepartement);

		return dto;
	}

	public static Ville toBean(VilleDto dto) {
		if (dto == null) {
			return null;
		}
		Ville ville = new Ville();

		ville.setPopulationTotale(dto.getNombreHabitants());
		Departement departement = new Departement();
		departement.setCode(dto.getCodeDepartement());
		ville.setDepartement(departement);

		return ville;
	}
}

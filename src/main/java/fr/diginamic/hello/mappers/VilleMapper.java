package fr.diginamic.hello.mappers;

import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;

public class ClientMapper {

	public static VilleDto toDto(Ville ville) {
		if (ville == null) {
			return null;
		}
		VilleDto dto = new VilleDto();
		dto.setCodeVille(ville.getCodeCommune());
		dto.setNombreHabitants(ville.getPopulationTotale());
		if (ville.getDepartement() != null) {
			dto.setCodeDepartement(ville.getDepartement().getCode());
		}
		return dto;
	}

	public static Ville toBean(VilleDto dto) {
		if (dto == null) {
			return null;
		}
		Ville ville = new Ville();
		ville.setCodeCommune(dto.getCodeVille());
		ville.setPopulationTotale(dto.getNombreHabitants());
		Departement departement = new Departement();
		departement.setCode(dto.getCodeDepartement());
		ville.setDepartement(departement);

		return ville;
	}
}

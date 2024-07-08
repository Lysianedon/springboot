package fr.diginamic.hello.mappers;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.dto.DepartementDto;

public class DepartementMapper {
	
	public static DepartementDto toDto(Departement departement) {
		
		if(departement == null) {
			return null;
		}
		
		DepartementDto dto = new DepartementDto();
		dto.setCode(departement.getCode());
			
		return dto;
	}
	
	public static Departement toBean(DepartementDto dto) {
		
		Departement departement = new Departement();
		
		departement.setCode(dto.getCode());
		
		return departement;
	}

}

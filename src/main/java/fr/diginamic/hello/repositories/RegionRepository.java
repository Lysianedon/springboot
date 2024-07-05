package fr.diginamic.hello.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.hello.entities.Region;
import fr.diginamic.hello.entities.Ville;

public interface RegionRepository extends JpaRepository<Region, Long> {

	boolean existsByCode(String code);
	boolean existsByNom(String nomRegion);
	Optional<Region> findByNom(String nomRegion);
	Optional<Region> findByCode(String codeRegion);
}

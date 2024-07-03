package fr.diginamic.hello.controleurs;

import java.util.ArrayList;
import java.util.List;

import fr.diginamic.hello.classes.Ville;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/villes")
public class VilleControleur {

	List<Ville> villes = new ArrayList<Ville>();

	public VilleControleur() {
		villes.add(new Ville("Paris", 50));
		villes.add(new Ville("Sydney", 20));
		villes.add(new Ville("Londres", 590));
	}

	@GetMapping
	public List<Ville> getVilles() {
		return villes;
	}

	@PutMapping
	public ResponseEntity<String> addVille(@RequestBody Ville body) {
		
		boolean villeExists = villes.stream().anyMatch(ville -> ville.getNom().equalsIgnoreCase(body.getNom()));


		if (villeExists) {
			return ResponseEntity.badRequest().body("La ville existe déjà");
		}
		villes.add(body);
		return ResponseEntity.ok("Ville insérée avec succès");

	}

}

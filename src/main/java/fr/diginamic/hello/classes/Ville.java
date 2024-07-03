package fr.diginamic.hello.classes;

public class Ville {

	private String nom;
	private int nbHabitants;
	private int id;

	public Ville(String nom, int nbHabitants, int id) {
		this.nom = nom;
		this.nbHabitants = nbHabitants;
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbHabitants() {
		return nbHabitants;
	}

	public void setNbHabitants(int nbHabitants) {
		this.nbHabitants = nbHabitants;
	}

	public int getId() {
		return id;
	}

}

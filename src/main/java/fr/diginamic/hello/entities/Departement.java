package fr.diginamic.hello.classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NOM")
    @NotNull
    @Size(min = 1, max = 100)
    private String nom;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Ville> villes = new HashSet<>();;

    public Departement() {
    }

    public Departement(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void addVille(Ville ville) {
        villes.add(ville);
        ville.setDepartement(this);
    }
    
    public void removeVille(Ville ville) {
        villes.remove(ville);
        ville.setDepartement(null);
    }

    public Set<Ville> getVilles() {
        return villes;
    }

    public void setVilles(Set<Ville> villes) {
        this.villes = villes;
    }
}

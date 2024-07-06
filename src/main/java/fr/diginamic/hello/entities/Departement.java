package fr.diginamic.hello.entities;

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

	@Column(name = "CODE")
	@NotNull
	@Size(min = 1, max = 10)
	private String code;

	@ManyToOne
	@JoinColumn(name = "REGION_ID")
	@JsonIgnore
	private Region region;

	public Departement() {
	}

	public Departement(String code, Region region) {
		this.code = code;
		this.region = region;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Departement [id=" + id + ", code=" + code + ", region=" + region + "]";
	}

}

package de.hn.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Abrechnung {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	String gruppe;

	@ManyToOne
	Medicament medicament;
	
	public Abrechnung() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getGruppe() {
		return gruppe;
	}

	public void setGruppe(String gruppe) {
		this.gruppe = gruppe;
	}

	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}
	
	
}

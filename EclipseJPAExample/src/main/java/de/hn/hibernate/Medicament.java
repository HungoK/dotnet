package de.hn.hibernate;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Medicament implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private NameMedicament nameMedicament;

	@OneToOne
	Abrechnung gruppe;
	
	public Medicament() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Medicament(String name) {
		setName(name);
	}

	public String getName() {
		if (nameMedicament != null)
			return nameMedicament.getName();
		return "LEER";
	}

	public void setName(String name) {
		if (nameMedicament == null)
			nameMedicament = new NameMedicament();
		nameMedicament.setName(name);
	}

	public Long getId() {
		return id;
	}
	
	public Abrechnung getGruppe() {
		return gruppe;
	}

	public void setGruppe(Abrechnung gruppe) {
		this.gruppe = gruppe;
	}

	
	public NameMedicament getNameMedicament() {
		return nameMedicament;
	}

	public void setNameMedicament(NameMedicament nameMedicament) {
		this.nameMedicament = nameMedicament;
	}

	@Override
	public String toString() {
		return "Medicament [id=" + id + ", nameMedicament=" + nameMedicament
				+ ", gruppe=" + gruppe + "]";
	}
	
	
}

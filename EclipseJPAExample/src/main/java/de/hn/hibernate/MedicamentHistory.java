package de.hn.hibernate;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

@Entity
public class MedicamentHistory {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@Temporal(TemporalType.DATE)
	private Date von;
	@Temporal(TemporalType.DATE)
	private Date bis;
	
	@OneToOne(cascade = CascadeType.ALL)
	Medicament medicament;
	
	@OneToOne(cascade = CascadeType.ALL)
	MedicamentHistory link;

	String name;
	
	public MedicamentHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicamentHistory(Medicament medicament, Date von, Date bis) {
		this.setMedicament(medicament);
		this.setBis(bis);
		this.setVon(von);
	}
	
	public Date getVon() {
		return von;
	}

	public void setVon(Date von) {
		this.von = von;
	}

	public Date getBis() {
		return bis;
	}

	public void setBis(Date bis) {
		this.bis = bis;
	}

	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

	
	public void setLink(MedicamentHistory folger) {
		this.link = folger;
	}
	
	public MedicamentHistory getLink() {
		return link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "MedicamentHistory [von=" + von + ", bis=" + bis + ", name="
				+ name + "]";
	}
	
	
}

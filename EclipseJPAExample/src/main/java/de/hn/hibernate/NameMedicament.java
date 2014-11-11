package de.hn.hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class NameMedicament {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	String name;
	
	@OneToOne(cascade=CascadeType.ALL)
	Leiter leiter;
	
	public NameMedicament() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public Leiter getLeiter() {
		return leiter;
	}
	public void setLeiter(Leiter leiter) {
		this.leiter = leiter;
	}
	@Override
	public String toString() {
		return "NameMedicament [name=" + name + "]";
	}
	
	
	
}

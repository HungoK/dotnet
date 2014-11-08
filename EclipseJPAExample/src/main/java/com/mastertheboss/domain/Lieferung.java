package com.mastertheboss.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Lieferung implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	
	@OneToMany(mappedBy="lieferung")
	private List<Computer> computers = new ArrayList<Computer>();

	
	public Lieferung(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Lieferung() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}
	
	public void addComputer(Computer c){
		computers.add(c);
		c.setLieferung(this);
	}
	
}

package com.mastertheboss.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Computer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	//@NaturalId
	private String computername;
	//@NaturalId
	private String ipaddress;
	private String belong;
	@ManyToOne
	Employee emp;
	
	@ManyToOne 
	Lieferung lieferung;
	
	public Computer() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Computer(Long id, String computername) {
		super();
		this.id = id;
		this.computername = computername;
	}


	public Computer(String computername) {
		super();
		this.computername = computername;
	}


	
	public String getIpaddress() {
		return ipaddress;
	}


	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getComputername() {
		return computername;
	}


	public void setComputername(String computername) {
		this.computername = computername;
	}


	public Employee getEmp() {
		return emp;
	}


	public void setEmp(Employee emp) {
		this.emp = emp;
	}


	public Lieferung getLieferung() {
		return lieferung;
	}


	public void setLieferung(Lieferung lieferung) {
		this.lieferung = lieferung;
	}
	
	
	
	
}

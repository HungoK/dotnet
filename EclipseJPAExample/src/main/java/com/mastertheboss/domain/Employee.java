package com.mastertheboss.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



//import org.hibernate.envers.Audited;

@Entity
//@Audited
public class Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String name;
	
	@OneToMany(mappedBy="emp")
	List<Computer> computers = new ArrayList<Computer>();
	
	@ManyToOne
	private Department department;

//	@Column
//	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
//	private DateTime fromDate;
	        
	@Temporal(TemporalType.DATE)
	private Date date;
	
	
	@Column(updatable = false)
	private Date geburtsdatum;
	
//	@OneToMany(mappedBy="employee")
//	private Collection<ProjectAssignment> assignments = new ArrayList<ProjectAssignment>();
//	
	public Employee() {}
	
	public Employee(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Employee(String name, Department department) {
		this.name = name;
		this.department = department;
	}
	

	public Employee(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	
	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	public void addComputer(Computer c){
		getComputers().add(c);
		c.setEmp(this);
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

//	public Collection<ProjectAssignment> getAssignments() {
//		return assignments;
//	}
//
//	public void setAssignments(Collection<ProjectAssignment> assignments) {
//		this.assignments = assignments;
//	}

	@Override
	public String toString() {
		String stringFormat = String.format("Employee [id=%d,name=%s,department]",  id, name,department!=null? department.getName():"");
		return stringFormat;
	}

//	public void addAssigmnent(ProjectAssignment projectassignment){
//		if( assignments == null ) {
//			assignments = new ArrayList<ProjectAssignment>();
//		}
//		assignments.add(projectassignment);
//	}
	
}

package com.mastertheboss.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;


//import org.hibernate.envers.Audited;

@Entity
//@Audited
public class Department {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="updated")
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Temporal(TemporalType.DATE)
	private Date updated;
	private String name;
	
	@OneToMany(mappedBy="department",cascade=CascadeType.PERSIST)
	private List<Employee> employees = new ArrayList<Employee>();
	
	
	
	public Department() {
		super();
	}
	
	
	public Department(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	public Department(String name) {
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
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	public void addEmployee(Employee emp){
		employees.add(emp);
		emp.setDepartment(this);
	}
}

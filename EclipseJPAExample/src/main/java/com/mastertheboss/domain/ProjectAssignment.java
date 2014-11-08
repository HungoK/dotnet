package com.mastertheboss.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
//@IdClass(ProjectAssignmentId.class)
public class ProjectAssignment implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Id
//	@Column(name="EMP_ID",insertable=false, updatable=false)
//	private long empId;
//	@Id
//	@Column(name="PROJ_ID", insertable=false, updatable=false)
//	private long projectId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@ManyToOne
	@JoinColumn(name="EMP_ID")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="PROJ_ID")
	private Project project;

	public ProjectAssignment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectAssignment(Employee employee, Project project) {
		super();
		addProjectAssignment(project,employee);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee aemployee) {
		this.employee = aemployee;
		
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project aproject) {
		this.project = aproject;
		
	}
	

	public void addProjectAssignment(Project aproject, Employee aemp){
		setProject(aproject);
		setEmployee(aemp);
		project.getAssignments().add(this);
	
		
	}
}

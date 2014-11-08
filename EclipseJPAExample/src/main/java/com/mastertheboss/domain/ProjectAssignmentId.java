package com.mastertheboss.domain;

import java.io.Serializable;

public class ProjectAssignmentId implements Serializable {
	private long empId;
	private long projectId;
	
	public ProjectAssignmentId( long emp, long project) {
		this.empId = emp;
		this.projectId = project;
	}

	
	public ProjectAssignmentId() {
		super();
		// TODO Auto-generated constructor stub
	}


	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
	
}

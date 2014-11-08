package com.mastertheboss.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity

public class Project implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private Set<ProjectAssignment> assignments = new HashSet<ProjectAssignment>();

	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.EAGER)
	private List<Task> tasks = new ArrayList<>();

	public boolean addTask(Task task) {
		if (!tasks.contains(task)) {
			Project oldProject = task.getProject();
			if (oldProject != null) {
				removeTask(task);
			}
			task.setProject(this);
			tasks.add(task);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeTask(Task task) {
		if (tasks.contains(task)) {
			tasks.remove(task);
			task.setProject(null);
			return true;
		} else {
			return false;
		}
	}

	public Set<ProjectAssignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(Set<ProjectAssignment> assignments) {
		this.assignments = assignments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Project() {
		super();
		// TODO Auto-generated constructor stub

	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", assignments=" + assignments + ", name="
				+ name + ", tasks=" + tasks + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}

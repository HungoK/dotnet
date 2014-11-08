@NamedQueries({
		@NamedQuery(name = "Project.findAllProjects", query = "select p from Project p order by p.name"),
		@NamedQuery(name = "Project.findProjectById", query = "select p from Project p where p.id = :id"),
		@NamedQuery(name = "Project.findTaskById", query = "select t from Task t where t.id = :id"),
		@NamedQuery(name = "Project.findTasksByProjectId", query = "select t from Project p, Task t "
				+ "where p.id = :id and t.project = p"),
		@NamedQuery(name = "Project.findTasksByProjectName", query = "select t from Project p, Task t "
				+ "where p.name = :name and t.project = p"),
		@NamedQuery(name = "Project.findTasksByProject", query = "select t from Project p, Task t "
						+ "where t.project = p"),

		@NamedQuery(name = "Project.findAllTasksByProjectName", query = "select t from Task t "
								+ "where t.project.name = :name "),
		@NamedQuery(name = "Employee.findAll", query = "select e from Employee " ),
		@NamedQuery(name = "Computer.findAll", query = "select c from Computer " )
})
package com.mastertheboss.domain;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


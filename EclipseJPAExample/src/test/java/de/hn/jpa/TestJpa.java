package de.hn.jpa;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import junit.framework.Assert;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Subqueries;
import org.junit.Ignore;
import org.junit.Test;

import com.mastertheboss.domain.Computer;
import com.mastertheboss.domain.Employee;
import com.mastertheboss.domain.Project;
import com.mastertheboss.domain.ProjectAssignment;
import com.mastertheboss.domain.Task;

public class TestJpa extends AbstractTestEntityManager {

	private Temporal adjustInto;
	private Instant instant;
	private LocalDate plusMonths_later;

	@Test
	@Ignore
	public void task_insert() {
		em.getTransaction().begin();
		Task task = new Task();
		task.setName("name");
		em.persist(task);
		em.getTransaction().commit();

	}

	@Test
	@Ignore
	public void project_task() {
		em.getTransaction().begin();
		Project project = new Project();
		project.setName("name1");
		Task specification = new Task();
		specification.setName("specifikation1");
		project.addTask(specification);
		Task develop = new Task();
		develop.setName("develop1");
		project.addTask(develop);
		em.persist(project);

		Project project2 = createTestData("project2","task1","task2");
		em.persist(project2);
		em.getTransaction().commit();

		em.getTransaction().begin();
		TypedQuery<Task> query = em.createNamedQuery(
				"Project.findTasksByProject", Task.class);
		// query.setParameter("name", "name");
		List<Task> resultList = query.getResultList();
		Assert.assertEquals(4, resultList.size());

		TypedQuery<Project> allProjectQuery = em.createNamedQuery(
				"Project.findAllProjects", Project.class);
		Assert.assertEquals(2, allProjectQuery.getResultList().size());

		Query allTaskByProjectNameQuery = em
				.createNamedQuery("Project.findAllTasksByProjectName");
		allTaskByProjectNameQuery.setParameter("name", "name2");
		Assert.assertEquals(2, allTaskByProjectNameQuery.getResultList().size());
		em.getTransaction().commit();
	}

	private void init() {
		em.getTransaction().begin();
		Project project = new Project();
		project.setName("name1");
		Task specification = new Task();
		specification.setName("specifikation1");
		project.addTask(specification);
		Task develop = new Task();
		develop.setName("develop1");
		project.addTask(develop);
		em.persist(project);

		Project project2 = createTestData("name2","specifikation2","develop2");
		em.persist(project2);
		em.getTransaction().commit();

	}

	private Project createTestData(String projectName, String taskname, String taskname2) {
		Project project2 = new Project();
		project2.setName(projectName);
		Task specification2 = new Task();
		specification2.setName(taskname);
		specification2.setTargetDate(new Date());
		project2.addTask(specification2);
		Task develop2 = new Task();
		develop2.setName(taskname2);
		// project2.addTask(develop);
		project2.addTask(develop2);
		return project2;
	}

	@Test
	@Ignore
	public void testCriteriaSimpleApi() {
		init();
		Session session = em.unwrap(org.hibernate.Session.class);
		Criteria exists = session.createCriteria(Task.class, "task");
		exists.add(Restrictions.eq("name", "specifikation2"));
		List<Task> list = exists.list();
		// createCriteria.setProjection(arg0)
		// createCriteria
		System.out.println("===========" + list.size());
		Criteria taskCriteria = session.createCriteria(Project.class,
				"myproject");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + " " + list.get(i));
			taskCriteria.add(Restrictions.idEq(list.get(i).getProject().getId()));
		}
		
		System.out.println(" ==> " + taskCriteria.list());

	}

	@Test
	public void testHibernateCriteriApi() {
		init();

		// em.getTransaction().begin();
		Session session = em.unwrap(org.hibernate.Session.class);
		DetachedCriteria subquery = DetachedCriteria.forClass(Task.class, "task");
		subquery.add(Restrictions.eq("name", "develop1"));
		subquery.createCriteria("project", "project");
		subquery.add(Restrictions.eqProperty("project.id", "myproject.id"));

		Criteria taskCriteria = session.createCriteria(Project.class,
				"myproject");
		taskCriteria.add(Subqueries.exists(
				subquery.setProjection(Projections.id())
				));
		//taskCriteria.setProjection(Projections.distinct(Projections.property("id")));
		List<Project> list = taskCriteria.list();
		Set<Project> set = new HashSet<Project>(list);
		// createCriteria.setProjection(arg0)
		// createCriteria
		System.out.println("===========" + list.size());
		Iterator<Project> iterator = set.iterator();
		while( iterator.hasNext()) {
			Project next = iterator.next();
			System.out.println(next.getName() + " " + next.getId());
		}
		
		// em.getTransaction().commit();

	}

	@Test
    @Ignore
	public void persist_onecomputer_returntrue() {
		Computer computer1 = new Computer("1.Comp");
		computer1.setIpaddress("127.0.0.1");
		this.persist(computer1);
	}

	@Test
	//@Ignore
	public void persist_projectassignment_true() {
		em.getTransaction().begin();
		Project project = new Project();
		project.setName("nameproject");
		Project project2 = new Project();
		project2.setName("nameproject2");
		Employee emp = new Employee();
		emp.setName("name1");
		Employee emp2 = new Employee();
		emp.setName("name2");
		ProjectAssignment projectAssignment = new ProjectAssignment(emp,
				project);
		Date startDate = Calendar.getInstance().getTime();
		projectAssignment.setStartDate(startDate);

		ProjectAssignment projectAssignment2 = new ProjectAssignment(emp2,
				project);
		Calendar cal = Calendar.getInstance();
		Date date2 = cal.getTime();
		projectAssignment2.setStartDate(date2);
		em.persist(emp);
		em.persist(project);
		em.persist(emp2);
		em.persist(project2);
		Employee emp3 = new Employee();
		emp.setName("name3");
		em.persist(emp3);
		LocalDate timePoint = LocalDate.now();
		plusMonths_later = timePoint.plusMonths(6L);
		// LocalDateTime timePoint = LocalDateTime.now(); // The current date
		// and
		// time
		LocalDate localTime = LocalDate.of(2011, Month.DECEMBER, 15); // from
																		// values
		ProjectAssignment projectAssignment3 = new ProjectAssignment(emp3,
				project);

		Date resTime = Date.from(localTime.atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant());
		projectAssignment3.setStartDate(resTime);
		ProjectAssignment projectAssignment4 = new ProjectAssignment(emp3,
				project2);
		Date date4 = Date.from(plusMonths_later.atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant());
		projectAssignment4.setStartDate(date4);
		// em.persist(projectAssignment);
		em.getTransaction().commit();

		getDaten();
	}

	void getDaten() {
		em.getTransaction().begin();
		Session session = em.unwrap(org.hibernate.Session.class);
		Criteria selectCriteria = session.createCriteria(Project.class);

		List<Project> list = selectCriteria.list();
		for (int i = 0; i < list.size(); ++i) {
			System.out.println(list.get(i));
		}

		em.getTransaction().commit();
	}
}

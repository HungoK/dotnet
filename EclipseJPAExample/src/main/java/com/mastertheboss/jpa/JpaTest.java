package com.mastertheboss.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.mastertheboss.domain.Computer;
import com.mastertheboss.domain.Department;
import com.mastertheboss.domain.Employee;
import com.mastertheboss.domain.Lieferung;

public class JpaTest {

	private EntityManager manager;
	private Employee employee;
	private Employee employee2;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			test.createEmployees();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();

		test.listEmployees();

		System.out.println(".. done");
	}




	private void commitandbegin() {
		manager.getTransaction().commit();
		manager.getTransaction().begin();
	}
	private void initComputer() {
		
	}
	private void initDaten() {
		
		Computer computer1 = new Computer("1.Comp");
		computer1.setIpaddress("127.0.0.1");
		Computer computer2 = new Computer("2.Comp");
		computer2.setIpaddress("127.0.0.2");
		Computer computer3 = new Computer("3.Comp");
		computer3.setIpaddress("127.0.0.3");
		Computer computer4 = new Computer("4.Comp");
		computer4.setIpaddress("127.0.0.4");
		manager.persist(computer1);
		manager.persist(computer2);
		manager.persist(computer3);
		manager.persist(computer4);
	//	commitandbegin();
		Employee emp1 = new Employee(1L,"name1");
		Employee emp2 = new Employee(2L,"name2");
		Employee emp3 = new Employee(3L,"name3");
		
		Date dateTime = new Date();
		emp1.addComputer(computer1);
		emp1.setDate(dateTime);
		emp1.addComputer(computer3);
		emp2.addComputer(computer2);
		emp2.setDate(dateTime);
		manager.merge(emp1);
		manager.merge(emp2);
		manager.merge(emp3);
	//	commitandbegin();
		Department dep = new Department(1L,"Dep1");
		dep.addEmployee(emp1);
		dep.addEmployee(emp2);
		manager.merge(dep);
		
		Department dep2 = new Department(2L,"Dep2");
		dep2.addEmployee(emp3);
		manager.merge(dep2);
	//	commitandbegin();
		
		Lieferung lieferung1 = new Lieferung(1L,"liefer1");
		Lieferung lieferung2 = new Lieferung(2L,"liefer2");
		lieferung1.addComputer(computer1);
		lieferung1.addComputer(computer2);
		lieferung2.addComputer(computer3);
		lieferung2.addComputer(computer4);
		manager.merge(lieferung1);
		manager.merge(lieferung2);
		listEmployees();
		
		
	}
	private void createEmployees() {
		initDaten();
		int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
		System.out.println("Nummber of Employees: " + numOfEmployees);
	}

	/**
	 * 
	 */
	private void listEmployees() {
		List<Employee> resultList = manager.createNamedQuery("Employee.findAll", Employee.class).getResultList();
		System.out.println("num of employess:" + resultList.size());
		for (Employee next : resultList) {
			System.out.println("next employee: " + next);
		}
	}


}

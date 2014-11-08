package com.mastertheboss.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;



import com.mastertheboss.domain.Computer;
import com.mastertheboss.domain.Department;
import com.mastertheboss.domain.Employee;
import com.mastertheboss.domain.Lieferung;

public class Jpa2Test {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
	EntityManager em = factory.createEntityManager();
	HibernateEntityManager hem = em.unwrap(HibernateEntityManager.class);
	Session session ; //= hem.getSession();
	public void init() {
		session = hem.getSession();
	}
	
	public Jpa2Test() {
		init();
		initDaten();
	}
	
	public void close() {
		em.close();
		factory.close();
	}
	
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	private void commitandbegin() {
		em.getTransaction().commit();
		em.getTransaction().begin();
	}
	private void initDaten() {
		em.getTransaction().begin();
		Computer computer1 = new Computer(1L,"1.Comp");
		computer1.setIpaddress("127.0.0.1");
		Computer computer2 = new Computer(2L,"2.Comp");
		computer2.setIpaddress("127.0.0.2");
		Computer computer3 = new Computer(3L,"3.Comp");
		computer3.setIpaddress("127.0.0.3");
		Computer computer4 = new Computer(4L,"4.Comp");
		computer4.setIpaddress("127.0.0.4");
		em.merge(computer1);
		em.merge(computer2);
		em.merge(computer3);
		em.merge(computer4);
		commitandbegin();
		Employee emp1 = new Employee(1L,"name1");
		Employee emp2 = new Employee(2L,"name2");
		Employee emp3 = new Employee(3L,"name3");
		
		Date dateTime = new Date();
		emp1.addComputer(computer1);
		emp1.setDate(dateTime);
		emp1.addComputer(computer3);
		emp2.addComputer(computer2);
		emp2.setDate(dateTime);
		em.merge(emp1);
		em.merge(emp2);
		em.merge(emp3);
		commitandbegin();
		Department dep = new Department(1L,"Dep1");
		dep.addEmployee(emp1);
		dep.addEmployee(emp2);
		em.merge(dep);
		
		Department dep2 = new Department(2L,"Dep2");
		dep2.addEmployee(emp3);
		em.merge(dep2);
		commitandbegin();
		
		Lieferung lieferung1 = new Lieferung(1L,"liefer1");
		Lieferung lieferung2 = new Lieferung(2L,"liefer2");
		lieferung1.addComputer(computer1);
		lieferung1.addComputer(computer2);
		lieferung2.addComputer(computer3);
		lieferung2.addComputer(computer4);
		em.merge(lieferung1);
		em.merge(lieferung2);
		em.getTransaction().commit();
		
		
	}
	public static void main(String[] args) {
		Jpa2Test jpa = new Jpa2Test();
		System.out.println("Criteria .......");
		Criteria criteria = jpa.getSession().createCriteria(Employee.class);
	// work	criteria.createAlias("computers","comp").add(Expression.eq("comp.computername", "4.Comp"));
		criteria.createAlias("computers","comp").createAlias("comp.lieferung", "lieferung");
//		criteria.createAlias("computers.lieferung","lieferung");
		criteria.add(Restrictions.eq("lieferung.name","liefer2"));
	//	Property name = Property.forName("name");
	//	criteria.add(name.eq("name2"));
		List list = criteria.list();
		for( int i = 0; i < list.size(); i++){
			
			System.out.println(list.get(i));
		}
		System.out.println(list.size());
		System.out.println("ende ....");
	//	session.close();
		jpa.close();
		System.out.println("close and Ende");
	}
}

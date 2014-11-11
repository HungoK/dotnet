package de.hn.jpa;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.junit.Ignore;
import org.junit.Test;

import com.mastertheboss.domain.Project;
import com.mastertheboss.domain.Task;

import de.hn.hibernate.Abrechnung;
import de.hn.hibernate.Leiter;
import de.hn.hibernate.Medicament;
import de.hn.hibernate.MedicamentHistory;
import de.hn.hibernate.NameMedicament;

public class TestMedicament extends AbstractTestEntityManager {


	private Instant instant;
	private LocalDate plusMonths_later;
	

	Date getDate(LocalDate localDate){
		Date date = Date.from(localDate.atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant());
		return date;
		
	}
	@Test
	@Ignore
	public void testMedicamentHistoryNoSubtask() {
	//	initMedicamentHistory();
		Session session = em.unwrap(org.hibernate.Session.class);
		Criteria history = session.createCriteria(MedicamentHistory.class, "history");
		
		Date today = getDate(LocalDate.of(2013, Month.AUGUST, 1));
		history.add(Restrictions.lt("von",today));
		history.add(Restrictions.gt("bis",today));
//		
		
		List<MedicamentHistory> list = history.list();
		for( MedicamentHistory i : list){
			System.out.println(" ======== "  + i );
		}
		
	}
	@Test
	@Ignore
	public void testMedicamentHistoryJPA() {
		
		initMedicamentHistory();
		System.out.println("testMedicamentHistoryJPA()");
		em.getTransaction().begin();
		String queryname = "Select distinct m from Medicament m , MedicamentHistory h where h.medicament=m and m.nameMedicament.leiter.name = :name  and h.von < :heute and h.bis > :heute ";
		Query medicamentQuery= em.createQuery(queryname);
		Date heute = getDate(LocalDate.of(2014, Month.JANUARY, 1));
		medicamentQuery.setParameter("heute", heute);
		medicamentQuery.setParameter("name", "Bean2");
	//	medicamentQuery.setParameter("bis", heute);
		List<Medicament> resultList = medicamentQuery.getResultList();
		for( Medicament i : resultList) {
			System.out.println(" lll======== "  + i );
		}
		em.getTransaction().commit();
	}
	@Test
	public void testCount() {
		initMedicamentHistory();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT count(m) FROM Medicament m");
		Long singleResult = (Long)q.getSingleResult();
		em.getTransaction().commit();
		System.out.println("=========");
		Session session = em.unwrap(org.hibernate.Session.class);
		Criteria countCriteria = session.createCriteria(Medicament.class);
		countCriteria.setProjection(Projections.rowCount());
		List list = countCriteria.list();
		System.out.println("Criteria= " + list.get(0) + " Namequery " + singleResult);
	}
	@Test
	@Ignore
	public void testMedicamentHistory() {
		initMedicamentHistory();
		Session session = em.unwrap(org.hibernate.Session.class);
		DetachedCriteria history = DetachedCriteria.forClass(MedicamentHistory.class, "history");
		Date heute = getDate(LocalDate.of(2014, Month.JANUARY, 1));
		history.add(Restrictions.lt("von",heute));
		history.add(Restrictions.gt("bis",heute));
		history.createCriteria("medicament", "medicamentAl");
		history.add(Restrictions.eqProperty("medicamentAl.id", "medicamentx.id"));
		Criteria taskCriteria = session.createCriteria(Medicament.class,
				"medicamentx");
		Criteria nameMedicament = taskCriteria.createCriteria("nameMedicament","nameMedicament");
		Criteria leiter = nameMedicament.createCriteria("leiter","leiter");
		leiter.add(Restrictions.eq("name", "Bean2"));
	//	taskCriteria.add(arg0)
		taskCriteria.add(Subqueries.exists(
				history.setProjection(Projections.id())
				));
		List<Medicament> list = taskCriteria.list();
		for( Medicament i : list){
			System.out.println(" xxxx======== "  + i );
		}
		
		List<Leiter> leiters = leiter.list();
		System.out.println("====Leiters " + leiters.size() );
		for( int i = 0; i < leiters.size(); i++){
			System.out.println(i + "===>"+leiters.get(i) + "===");
		}
//		for( Leiter l : leiters) {
//			System.out.println(" leiter ==> " + l);
//		}
//		System.out.println("====Leiters out");
	}
	public void initMedicamentHistory() {
		em.getTransaction().begin();
		LocalDate timePoint = LocalDate.now();
		plusMonths_later = timePoint.plusMonths(6L);
		
		LocalDate timeDezember = LocalDate.of(2011, Month.DECEMBER, 1); 
		Leiter leiter1 = new Leiter();
		leiter1.setName("Bean");
		Leiter leiter2= new Leiter();
		leiter2.setName("Bean2");
		NameMedicament nameMedicament1 = new NameMedicament();
		nameMedicament1.setName("Aspirin A");
		nameMedicament1.setLeiter(leiter1);
		NameMedicament nameMedicament2 = new NameMedicament();
		nameMedicament2.setName("Aspirin B");
		nameMedicament2.setLeiter(leiter2);
		Date von = getDate(LocalDate.of(2012, Month.DECEMBER, 1));
		Date bis =  getDate( LocalDate.of(2013, Month.JULY, 31));
		Medicament medicament = new Medicament("name1");
		MedicamentHistory a1history = new MedicamentHistory(medicament, von, bis);
		a1history.setName("a1history");
		Medicament medicament2 = new Medicament("name2");
		Date von2 = bis;
		Date bis2 = getDate( LocalDate.of(2013, Month.DECEMBER, 31));
		medicament2.getNameMedicament().setLeiter(leiter1);
		
		MedicamentHistory a2history = new MedicamentHistory(medicament2, von2, bis2);
		a2history.setName("a2history");
		a1history.setLink(a2history);
		Abrechnung abrechnung = new Abrechnung();
		abrechnung.setGruppe("abrechnunggruppe1");
		Abrechnung abrechnung2 = new Abrechnung();
		abrechnung2.setGruppe("abrechnunggruppe2");
		abrechnung.setMedicament(medicament);
		abrechnung2.setMedicament(medicament2);
		
		Medicament medicament3 = new Medicament("name3");
		medicament3.getNameMedicament().setLeiter(leiter2);
		MedicamentHistory a3history = new MedicamentHistory(medicament3,bis2,
					getDate( LocalDate.of(2014, Month.MARCH, 1)) );
		a3history.setName("a3history");
		a2history.setLink(a3history);
		
		em.persist(medicament);
		em.persist(a1history);
		em.persist(medicament2);
		em.persist(a2history);
		em.persist(abrechnung);
		em.persist(abrechnung2);
		em.persist(medicament3);
		em.persist(a3history);
		em.persist(leiter1);
		em.persist(leiter2);
		em.persist(nameMedicament1);
		em.persist(nameMedicament2);
		em.getTransaction().commit();
	}

}

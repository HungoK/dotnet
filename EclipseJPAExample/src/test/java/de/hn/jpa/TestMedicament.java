package de.hn.jpa;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;

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
import de.hn.hibernate.Medicament;
import de.hn.hibernate.MedicamentHistory;

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
	//@Ignore
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
		taskCriteria.add(Subqueries.exists(
				history.setProjection(Projections.id())
				));
		List<Medicament> list = taskCriteria.list();
		for( Medicament i : list){
			System.out.println(" xxxx======== "  + i );
		}
		
	}
	public void initMedicamentHistory() {
		em.getTransaction().begin();
		LocalDate timePoint = LocalDate.now();
		plusMonths_later = timePoint.plusMonths(6L);
		
		LocalDate timeDezember = LocalDate.of(2011, Month.DECEMBER, 1); 
		
		Date von = getDate(LocalDate.of(2012, Month.DECEMBER, 1));
		Date bis =  getDate( LocalDate.of(2013, Month.JULY, 31));
		Medicament medicament = new Medicament("name1");
		MedicamentHistory a1history = new MedicamentHistory(medicament, von, bis);
		a1history.setName("a1history");
		Medicament medicament2 = new Medicament("name2");
		Date von2 = bis;
		Date bis2 = getDate( LocalDate.of(2013, Month.DECEMBER, 31));
		
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
		em.getTransaction().commit();
	}

}

package de.hn.jpa.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class QueryTester {
	public static void main(String ... args) throws Exception {
		String unitName = args[0];
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(unitName);
		EntityManager em = emf.createEntityManager();
		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
		while( true ){
			System.out.println("JPQL > ");
			String query = reader.readLine();
			if( query.equals("quit")){
				break;
			}
			if( query.length() == 0){
				continue;
			}
			
			List result = em.createQuery(query).getResultList();
			if( result.size() > 0){
				int count = 0;
				for( Object o : result ){
					System.out.println(++ count + " ");
					printResult(o);
				}
			}
			else {
				System.out.println(" 0 result returned");
			}
		}
	}

	private static void printResult(Object result) {
		if( result == null ){
			System.out.println("NULL");
		} else if( result instanceof Object[]) {
			Object[] row = (Object[]) result;
			System.out.println("[");
			for( int i = 0; i < row.length; i++) {
				printResult(row[i]);
			}
			System.out.print("]");
		} else if ( result instanceof Long ||
					result instanceof Double ||
					result instanceof String ) {
			System.out.print(result.getClass().getName() + ": " + result);
		} else {
			System.out.print(ReflectionToStringBuilder.toString(result, ToStringStyle.SHORT_PREFIX_STYLE));
		}
		System.out.println();
		
	}
}

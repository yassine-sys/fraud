package com.fraude.services;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.Wrapper;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import com.fraude.interfaces.FunctionsInterfaces;

@Stateless
public class FunctionsService implements FunctionsInterfaces {

	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public void createReplaceFunctions(String function_name,String tablename, List<String> champs1, List<String> champs2 ,String condition) {
		// TODO Auto-generated method stub
	
			Session hibernateSession = null;
			try {
				hibernateSession = ((Wrapper) em).unwrap(Session.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  hibernateSession.doWork(new org.hibernate.jdbc.Work() {

			        @Override
			        public void execute(Connection conn) throws SQLException {
			       
			        	// do whatever you need to do with the connection
			        	Statement stmt = conn.createStatement();
			    		stmt.execute("CREATE OR REPLACE FUNCTION "+function_name+"() RETURNS refcursor AS '"
			    		        + " DECLARE "
			    		        + "    mycurs refcursor; "
			    		        + " BEGIN "
			    		        + "    OPEN mycurs FOR SELECT 1 UNION SELECT 2; "
			    		        + "    RETURN mycurs; "
			    		        + " END;' language plpgsql");
			    		stmt.close();

			    		// We must be inside a transaction for cursors to work.
			    		conn.setAutoCommit(false);

			    		// Procedure call.
			    		CallableStatement proc;
			    		
			    			proc = conn.prepareCall("{ ? = call refcursorfunc() }");
			    	
			    		proc.registerOutParameter(1, Types.OTHER);
			    		proc.execute();
			    		ResultSet results = (ResultSet) proc.getObject(1);
			    		while (results.next()) {
			    		    // do something with the results...
			    		}
			    		results.close();
			    		proc.close();
			        }
			    });
			
	
		
	}

}

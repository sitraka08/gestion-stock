package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class Connex{

	private static String url = "jdbc:mysql://localhost/gestion_produit";
	private static String user = "root";
	private static String password ="";
	private static  Connection conn = null;
	
	
	public Connex() {
		
	}
	public static Connection seconnecter() {
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver ok");
		    conn = DriverManager.getConnection(url,user,password); 
		    System.out.println("connexion etablie");
		    return conn;
		} catch (SQLException | ClassNotFoundException e) {
			
			e.printStackTrace();
			return null;
		}
		
			
		
	}
	
	 
	
}

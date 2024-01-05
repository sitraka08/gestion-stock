package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import application.Connex;

public class UsersDAO {

	public boolean get_users(Users user) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		
		Connection connect = (Connection) Connex.seconnecter();
		
		try {
			PreparedStatement stat_users = connect.prepareStatement(sql);
			stat_users.setString(1, user.getUsername());
			stat_users.setString(2, user.getPassword());
			ResultSet users_via_bdd =  stat_users.executeQuery();
			
			while(users_via_bdd.next()) {
				if(users_via_bdd.getString("username").equals(user.getUsername()) && users_via_bdd.getString("password").equals(user.getPassword())) {
					return true;
				}else
					return false;
			}
			
			
			System.out.println("connecter succsess");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
}

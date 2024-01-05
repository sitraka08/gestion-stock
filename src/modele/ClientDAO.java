package modele;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import application.Connex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientDAO {
	public static java.sql.Connection connect;
	
	private String sql2 = "select * from Client order by CODE_CLI ASC";
	private int code = 1;
	private String clientDAO;
	private  ObservableList<Client> liste_de_client = FXCollections.observableArrayList();
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	
	
	public String getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(String clientDAO) {
		this.clientDAO = clientDAO;
	}

	public void save_client(Client client) {
		String sql = "INSERT INTO client(NOM_CLI) VALUES (?)";
		
		try {
			connect = Connex.seconnecter();
			java.sql.PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, client.getNom_cli());
			statement.execute();
			System.out.println("ajout client reussir");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ObservableList<Client> getdata_client_enobservableliste() {
		
		connect = Connex.seconnecter();
		int kaody = 0;  
		try {
			PreparedStatement stat =  (PreparedStatement) connect.prepareStatement(sql2);
			ResultSet liste_cli_viabdd = stat.executeQuery();

			while(liste_cli_viabdd.next()) {
				//micreer objet client isakin boucle, de n compilateur no manome n anaran objet; 
				//resultat type objet no anaty add() ary affectena ao anaty observable liste
				Client client = new Client("Cli-" + liste_cli_viabdd.getInt("CODE_CLI"),liste_cli_viabdd.getString("NOM_CLI"));
				liste_de_client.add(client);
		
				kaody = liste_cli_viabdd.getInt("CODE_CLI");
			
			}
			setCode(kaody+1);
			return liste_de_client;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		
	}
	
	public  ResultSet getdata_client_en_resultset() {
		
		try {
			connect = Connex.seconnecter();
			PreparedStatement stat =  (PreparedStatement) connect.prepareStatement(sql2);
			ResultSet liste_cli_viabdd = stat.executeQuery();
			return liste_cli_viabdd;
		
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	
	public void supprimer_client(Client client) {
		String sql_suppr =" DELETE FROM Client WHERE CODE_CLI = ? ";
		connect = Connex.seconnecter();
		try {
			
			
			String code = codecli_str_toint(client.getCode_clistr());
		    PreparedStatement stat_suppr = (PreparedStatement) connect.prepareStatement(sql_suppr);
		    stat_suppr.setString(1,code);
		    stat_suppr.execute();
		    System.out.println("client supprimer");
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update_client(Client client){
		String sql_update = "UPDATE Client SET NOM_CLI = ? WHERE CODE_CLI = ?";
		connect = Connex.seconnecter();
		try {
			PreparedStatement stat_update = (PreparedStatement) connect.prepareStatement(sql_update);
			stat_update.setString(1, client.getNom_cli());
			stat_update.setString(2, codecli_str_toint(client.getCode_clistr()));
			stat_update.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String codecli_str_toint(String code_enstr) {
		String teste = code_enstr;
		String code;
		code = teste.charAt(4)+"";
		for(int i = 5; i< teste.length(); i++) {
			code = code +""+teste.charAt(i);
		}
		return code;
	}
	
	public static void delete_all_client() {
		
		try {
			connect = Connex.seconnecter();
			PreparedStatement stat_delete_all = (PreparedStatement) connect.prepareStatement("TRUNCATE Client");
			stat_delete_all.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Client getLastClient() {
		connect = Connex.seconnecter();
		Client client = new Client();
		String sql = "SELECT client.CODE_CLI, client.NOM_CLI FROM commande, client WHERE commande.CODE_CLI = client.CODE_CLI";
		
		try {
			PreparedStatement con = (PreparedStatement) connect.prepareStatement(sql);
			ResultSet res = con.executeQuery();
			while(res.next()) {
			
				client.setCode_cli(res.getInt("CODE_CLI"));
				client.setNom_cli(res.getString("NOM_CLI"));
				
			}
			
		return client;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	
		
	}
}

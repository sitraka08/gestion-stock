package modele;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import application.Connex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandeDAO {
	private ObservableList<Commande> list = FXCollections.observableArrayList();
	Connection connect;
	String save_commande ="INSERT INTO Commande(DATE, CODE_CLI,REF_PROD,QTE_COM)"
			+ "VALUES(?,?,?,?)";
	
	
	public void save_commande(Commande commande ) {
		
		connect = Connex.seconnecter();
		try {
			PreparedStatement stat = connect.prepareStatement(save_commande);
			stat.setString(1, commande.getDate_com().toString());
			stat.setInt(2, commande.getCode_cli());
			stat.setInt(3, commande.getRef_prod());
			stat.setInt(4, commande.getQte_com());
			stat.execute();
			System.out.println("commnde ajouter au tbale commande");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public ObservableList<Commande> getCommandeObservablelist() {
		
		
		connect = Connex.seconnecter();
		
		String sql = "SELECT * FROM (client, produit, commande) WHERE commande.CODE_CLI = client.CODE_CLI"
				+ " AND produit.REF_PROD = commande.REF_PROD";
		
		try {
			PreparedStatement stat = connect.prepareStatement(sql);
			ResultSet res = stat.executeQuery();
			
			while(res.next()) {
			
				Commande com = new Commande("cm-"+
						res.getInt("ID_COM"),"ref-"+
						res.getInt("REF_PROD"),
						res.getString("DESIGN_PROD"),"cli-"+
						res.getInt("CODE_CLI"),
						res.getString("NOM_CLI"),
						res.getFloat("PRIX_UNITAIRE"),
						res.getDate("DATE").toLocalDate(), 
						res.getInt("QTE_COM"));
						
				list.add(com);
				
				
			}
		
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public int ilyaCommande() {
		connect = Connex.seconnecter();
		int vide = 0;
		try {
			PreparedStatement stat = connect.prepareStatement("SELECT * FROM commande");
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				vide = res.getInt("CODE_CLI");
			}
		return vide;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return vide;
	
	}
	
	public ObservableList<Commande> getCommndeParCodeCLi(Commande commande) {
		
		ObservableList<Commande> comListe = FXCollections.observableArrayList();
		String sql = "SELECT * FROM client,produit,commande WHERE commande.CODE_CLI = ?"
				+ " AND commande.CODE_CLI = client.CODE_CLI AND produit.REF_PROD = commande.REF_PROD";
		
		PreparedStatement stat;
		try {
			stat = (PreparedStatement) Connex.seconnecter().prepareStatement(sql);
			stat.setInt(1, Integer.parseInt(commande.getCode_clistr().replaceAll("cli-", "")));
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				
				Commande com = new Commande("Com-"+
						res.getInt("ID_COM"),"ref-"+
						res.getInt("REF_PROD"),
						res.getString("DESIGN_PROD"),"cli-"+
						res.getInt("CODE_CLI"),
						res.getString("NOM_CLI"),
						res.getFloat("PRIX_UNITAIRE"),
						res.getDate("DATE").toLocalDate(), 
						res.getInt("QTE_COM"));
						
				comListe.add(com);		
			}
			
			return comListe;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return comListe;
	}
	
	public Commande getCommandeCodecom(Commande com) {

		String sql = "SELECT * FROM commande, client, produit WHERE commande.CODE_CLI = client.CODE_CLI "
				+ "AND commande.REF_PROD = produit.REF_PROD AND ID_COM = ?";
		try {
			PreparedStatement stat = Connex.seconnecter().prepareStatement(sql);
			stat.setInt(1, Integer.parseInt(com.getIdcom().replaceAll("cm-", "")));
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				Commande commande = new Commande("cm-"+
						res.getInt("ID_COM"),"ref-"+
						res.getInt("REF_PROD"),
						res.getString("DESIGN_PROD"),"cli-"+
						res.getInt("CODE_CLI"),
						res.getString("NOM_CLI"),
						res.getFloat("PRIX_UNITAIRE"),
						res.getDate("DATE").toLocalDate(), 
						res.getInt("QTE_COM"));
				return commande;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateCommande(Commande com) {
		String sql0 ="UPDATE produit SET STOCK_PROD = STOCK_PROD +? WHERE REF_PROD = ?";
		try {
			PreparedStatement stat0 = Connex.seconnecter().prepareStatement(sql0);
			stat0.setInt(1, com.getQte_com());
			stat0.setInt(2, Integer.parseInt(com.getRef_prodstr().replaceAll("[^0-9]", "")));
			
			stat0.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
//		String sql1 = "UPDATE produit SET STOCK_PROD = "
		String sql = "UPDATE client, commande,produit SET client.NOM_CLI = ?, commande.QTE_COM = ?, "
				+ "commande.DATE = ?, commande.REF_PROD = ?, produit.STOCK_PROD = ? WHERE ID_COM = ? "
				+ "AND client.CODE_CLI = commande.CODE_CLI AND produit.REF_PROD = commande.REF_PROD ";
		
		try {
			System.out.println(com.getRef_prodstr().replaceAll("[^0-9]", "")+"proood");
			
			PreparedStatement stat = Connex.seconnecter().prepareStatement(sql);
			stat.setString(1, com.getNom_cli());
			stat.setInt(2, com.getQte_com());
			stat.setDate(3, Date.valueOf(com.getDate()));
			stat.setInt(4, Integer.parseInt(com.getRef_prodstr().replaceAll("[^0-9]", "")));
			stat.setInt(5, com.getQte_comm());
			stat.setInt(6, Integer. parseInt(com.getIdcom().replaceAll("[^0-9]", "")));
//			stat.setInt(7, Integer.parseInt(com.getRef_prodstr().replaceAll("[^0-9]", "")));
			stat.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteCommande(Commande com) {
		String sql = "UPDATE produit,commande SET produit.STOCK_PROD = produit.STOCK_PROD +? WHERE produit.REF_PROD = commande.REF_PROD AND commande.ID_COM = ?";
		String sql2 = "DELETE FROM commande WHERE ID_COM = ?";
		try {
			PreparedStatement stat =  Connex.seconnecter().prepareStatement(sql);
			stat.setInt(1, com.getQte_comm());
			stat.setInt(2, Integer.parseInt(com.getIdcom().replaceAll("[^0-9]", "")));
			stat.execute();
			
			PreparedStatement stat2 =  Connex.seconnecter().prepareStatement(sql2);
			stat2.setInt(1, Integer.parseInt(com.getIdcom().replaceAll("[^0-9]", "")));
			stat2.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

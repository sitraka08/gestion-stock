package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import application.Connex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProduitDAO {
	
	private int ref_auto = 1;
	private Connection connect;
	
	private  ObservableList<Produit> liste_de_produit = FXCollections.observableArrayList();
	
	
	
	public int getRef_auto() {
		return ref_auto;
	}

	public void setRef_auto(int ref_auto) {
		this.ref_auto = ref_auto;
	}

	public void save_product(Produit produit) {
		String sql = "INSERT INTO PRODUIT(DESIGN_PROD, STOCK_PROD,PRIX_UNITAIRE) VALUES (?,?,?)";
		try {
			connect = (Connection) Connex.seconnecter();
			java.sql.PreparedStatement statement = connect.prepareStatement(sql);
		
			statement.setString(1, produit.getDesign_prod());
			statement.setInt(2, produit.getStock_prod());
			statement.setFloat(3, produit.getPrix_unitaire());
			statement.execute();
			
			System.out.println("produit ajouter");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ObservableList<Produit> getdata_produit_en_observablelist() {
			String sql = "SELECT*FROM PRODUIT ORDER BY REF_PROD ASC";
			
			try {
				connect = (Connection) Connex.seconnecter();
				PreparedStatement stat = (PreparedStatement) connect.prepareStatement(sql);
				ResultSet rsltset_produit_viabdd = stat.executeQuery();
		
		 //isakin mi appel fct afficherproduit de misy objet ray creer auto ny nom, de stocke ao anaty observable liste n valeur de retour
		// resultat.getString() miretourne string; isakin boucle de manao ajout anle entite objet ao anaty observable liste
				while(rsltset_produit_viabdd.next()) {
					Produit produit = new Produit("ref-" + rsltset_produit_viabdd.getInt("REF_PROD"), rsltset_produit_viabdd.getString("DESIGN_PROD"), rsltset_produit_viabdd.getInt("STOCK_PROD"), rsltset_produit_viabdd.getFloat("PRIX_UNITAIRE"));
					liste_de_produit.add(produit);
					setRef_auto(rsltset_produit_viabdd.getInt("REF_PROD") + 1);
				}
				return liste_de_produit;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	
	public List<String> getdata_produit_en_arraylist() {
		String sql = "SELECT*FROM PRODUIT ORDER BY REF_PROD ASC";
		List<String> liste = new ArrayList<String>();
		 
		try {
			connect = (Connection) Connex.seconnecter();
			PreparedStatement stat = (PreparedStatement) connect.prepareStatement(sql);
			ResultSet rsltset_produit_viabdd = stat.executeQuery();
	
	 //isakin mi appel fct afficherproduit de misy objet ray creer auto ny nom, de stocke ao anaty observable liste n valeur de retour
	// resultat.getString() miretourne string; isakin boucle de manao ajout anle entite objet ao anaty observable liste
			while(rsltset_produit_viabdd.next()) {
				Produit produit = new Produit("ref-" + rsltset_produit_viabdd.getInt("REF_PROD"), rsltset_produit_viabdd.getString("DESIGN_PROD"), rsltset_produit_viabdd.getInt("STOCK_PROD"), rsltset_produit_viabdd.getFloat("PRIX_UNITAIRE"));
				liste.add(produit.getRef_prodstr()+" "+produit.getDesign_prod());
				setRef_auto(rsltset_produit_viabdd.getInt("REF_PROD") + 1);
			}
			return liste;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
		

	public ResultSet getdata_produit_en_reultset() {
		String sql = "SELECT*FROM PRODUIT ORDER BY REF_PROD";
		try {
			connect = (Connection) Connex.seconnecter();
			PreparedStatement stat = (PreparedStatement) connect.prepareStatement(sql);
			ResultSet resultat = stat.executeQuery();
	
			return resultat;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void delete_produit(Produit produit) {
		String delete_prod = "DELETE FROM Produit WHERE REF_PROD = ?";
		connect = (Connection) Connex.seconnecter();
		String refstrtoint = refstring_to_int(produit.getRef_prodstr());
		try {
			PreparedStatement delete_prod_stat = (PreparedStatement) connect.prepareStatement(delete_prod);
			delete_prod_stat.setString(1, refstrtoint);
			
			delete_prod_stat.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update_produit(Produit produit) {
	
		String sql_up_prod = "UPDATE Produit SET DESIGN_PROD = ?, STOCK_PROD = ?, PRIX_UNITAIRE = ? WHERE REF_PROD = ?";
		String sql_up_stock = "UPDATE Produit SET STOCK_PROD = (STOCK_PROD + ?) WHERE DESIGN_PROD = ?";
		if(produit.getSignal().equals("update")) {
			connect = (Connection) Connex.seconnecter();
			try {
				PreparedStatement stat_up_prod_st = (PreparedStatement) connect.prepareStatement(sql_up_stock);
				stat_up_prod_st.setInt(1, produit.getStock_prod());
				stat_up_prod_st.setString(2, produit.getDesign_prod());
				
				stat_up_prod_st.execute();
				System.out.println("product update succefull sans confirmer");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}else {
			connect = (Connection) Connex.seconnecter();
			try {
				
					PreparedStatement stat_up_prod = (PreparedStatement) connect.prepareStatement(sql_up_prod);
					stat_up_prod.setString(1, produit.getDesign_prod());
					stat_up_prod.setInt(2, produit.getStock_prod());
					stat_up_prod.setFloat(3, produit.getPrix_unitaire());
					stat_up_prod.setString(4, refstring_to_int(produit.getRef_prodstr()));
					stat_up_prod.execute();
				
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	public void update_qte_prod(Produit prod) {
		connect = (Connection) Connex.seconnecter();
		PreparedStatement stat_up_stock;
		try {
			stat_up_stock = (PreparedStatement) connect.prepareStatement("UPDATE Produit SET STOCK_PROD = (STOCK_PROD - ? ) WHERE REF_PROD = ?");
			stat_up_stock.setInt(1, prod.getStock_prod());
			stat_up_stock.setInt(2, prod.getRef_prod());
			stat_up_stock.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private String refstring_to_int(String refprod) {
		String ref;
		ref = refprod.charAt(4) +"";
		for(int i = 5; i<refprod.length();  i++) {
			ref = ref + refprod.charAt(i) ;
		}
		
		return ref;
	}
	
	public int getMax_prod(Produit prod1) {
		
		connect = (Connection) Connex.seconnecter();
		PreparedStatement max;
		String sql = "SELECT * FROM produit WHERE REF_PROD = ?";
		try {
		
			max = (PreparedStatement) connect.prepareStatement(sql);
			max.setInt(1, prod1.getRef_prod());
			ResultSet res = max.executeQuery();
			
			while(res.next()) {
				 prod1.setStock_prod(res.getInt("STOCK_PROD"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prod1.getStock_prod();
		
	}
	
	
		
}

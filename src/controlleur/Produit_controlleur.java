package controlleur;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import animatefx.animation.ZoomInLeft;
import animatefx.animation.ZoomInRight;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import modele.Produit;
import modele.ProduitDAO;

public class Produit_controlleur implements Initializable {
  
	
	private double xOffset = 0;
	private double yOffset = 0;
  
	private String listeCombo;

	public String getListeCombo() {
		return listeCombo;
	}

	public void setListeCombo(String listeCombo) {
		this.listeCombo = listeCombo;
	}

	@FXML
    private AnchorPane anchorpane_produit;
    private Parent fxml;

    @FXML
    private Button btn_confirm_update_prod;


    @FXML
    private Circle btn_minimise;

    @FXML
    private Circle btn_close;

    @FXML
    private BorderPane bdpane_produit;

    @FXML
    private TableView<Produit> table_produit;

    @FXML
    private TableColumn<Produit, String> col_reference;

    @FXML
    private TableColumn<Produit, String> col_designation;

    @FXML
    private TableColumn<Produit, Float> col_prix_unitaire;

    @FXML
    private TableColumn<Produit,Integer> col_stocks;

    @FXML
    private Button btn_delete_produit;

    @FXML
    private Button btn_acheter_produit_inprod;

    @FXML
    private Pane pane_modifier_produit;

    @FXML
    private Button btn_ajouter_produit_bdd21;

    @FXML
    private Button btn_annuler_modif_prod;

    @FXML
    private JFXTextField reference_modif;

    @FXML
    private JFXTextField designation_modif;

    @FXML
    private JFXTextField prix_unitaire_modif;

    @FXML
    private JFXTextField stocks_modif;

    @FXML
    private Label label_alert1;

    @FXML
    private Pane pane_ajouter_produit;

    @FXML
    private Button btn_ajouter_produit_bdd2;

    @FXML
    private JFXTextField reference;

    @FXML
    private JFXTextField designation;

    @FXML
    private JFXTextField prix_unitaire;

    @FXML
    private JFXTextField stocks;

    @FXML
    private Label label_alert;

    @FXML
    private Label alert_onenter;
    
    @FXML
    private TextField edit_search_prod;
    
    public  ObservableList<Produit> liste_produit = FXCollections.observableArrayList();

    @FXML
    void ajouter_produit_bdd(MouseEvent event) {
    	function_ajouter_produit();
    	
    }
    
    @FXML
    void on_enter_pressed_inprod(KeyEvent event) {
    	function_ajouter_produit();
    }


    @FXML
    void annuler_modif_prod(ActionEvent event) {
    	new ZoomInRight(pane_ajouter_produit).play();
    	pane_ajouter_produit.toFront();
    }

    @FXML
    void clic_table_produit(MouseEvent event) {
    	if(event.getSource() == table_produit && event.getClickCount() == 2 && table_produit.getSelectionModel().getSelectedItem() != null) {    	
	    	ProduitDAO produitDAO = new ProduitDAO();
	    	Produit produit = new Produit();
	    	ResultSet resultat = produitDAO.getdata_produit_en_reultset();
	    	produit = table_produit.getSelectionModel().getSelectedItem();
	    	
			try {
				while(resultat.next()) {
						if(("ref-"+resultat.getInt("REF_PROD")).equals(produit.getRef_prodstr()) && !(produit.getRef_prodstr().isEmpty()) ) {
							reference_modif.setText("ref-"+ resultat.getString("REF_PROD"));
							prix_unitaire_modif.setText(resultat.getString("PRIX_UNITAIRE"));
							stocks_modif.setText(resultat.getString("STOCK_PROD"));
							designation_modif.setText(resultat.getString("DESIGN_PROD"));
							
							new ZoomInLeft(pane_modifier_produit).play();
							pane_modifier_produit.toFront();
						}
	
					}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    	}
    }

    @FXML
    void close_fenetre(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void minimize_fenetre(MouseEvent event) {
    	if(event.getSource() == btn_minimise /*&& event.getClickCount() == 2*/) {
    		Stage stage = (Stage) btn_minimise.getScene().getWindow();
    		stage.setIconified(true);
    		
    	}
    }

  
   

    @FXML
    void on_change_designation(KeyEvent event) {
    	control_regex_string(designation, 20);
    	label_alert.setText("");
    }
    
    void control_regex_number( JFXTextField editNumber, int subMax) {
    	editNumber.textProperty().addListener(new ChangeListener<String>() {
			public void changed(
				ObservableValue<? extends String> observable, 
				String oldValue, String newValue) {
		
				editNumber.setText(editNumber.getText().replaceAll("^[^1-9]|[^0-9]$",""));
				
				if(editNumber.getText().length() >= subMax ) {
					
					// substring([ nombre, nombre[)
					editNumber.setText(editNumber.getText().substring(0,subMax));
				}
			}
		    		
		} );
    }
    

    public void control_regex_string(JFXTextField edit, int max) {
    	String nonregex = "^[^a-zA-Z]|[^0-9]$}";
    	edit.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String valeur) {
			
				edit.setText(edit.getText().replaceAll(nonregex, ""));
				if(edit.getText().length() > max)
					edit.setText(edit.getText().substring(0,max));
			}
    		
    	});
    	
    }
    @FXML
    void on_change_designation_mouse(MouseEvent event) {

    }

    @FXML
    void on_delete_prod_key_pressed(KeyEvent event) {
    	if(event.getCode().equals(KeyCode.DELETE) && table_produit.getSelectionModel().getSelectedItem() != null) {
    		function_delete_produit();
    		ctrl_affichage_produit();
    		System.out.println("delete prod");
    	}
    }

    @FXML
    void on_delete_produit(MouseEvent event) {
    	if(event.getSource() == btn_delete_produit && table_produit.getSelectionModel().getSelectedItem() != null) {
    		function_delete_produit();
    		ctrl_affichage_produit();
    		System.out.println("delete prod");
    	}
    }

   
    @FXML
    void on_prix_unit_changed(KeyEvent event) {

    }

   

    @FXML
    void onchane_prix_unitaire_modif_mouse(MouseEvent event) {
//    	control_regex_number(prix_unitaire_modif, 11);
    	
    }

    @FXML
    void onchane_prix_unitaire_mouse(MouseEvent event) {
    	control_regex_number(prix_unitaire, 11);
    	label_alert.setText("");
    }

    @FXML
    void onchane_prix_unitaire(KeyEvent event) {
    	control_regex_number(prix_unitaire, 11);
    	label_alert.setText("");
    	
    	
    }
    
    
    @FXML
    void onchange_stocks() {
    	
    	control_regex_number(stocks, 4);
    	label_alert.setText("");
    	
    }
    
  
    @FXML
    void onchane_prix_unitaire_modif(KeyEvent event) {
    	control_regex_number(prix_unitaire_modif, 11);
  
    }

    @FXML
    void onchange_stocks_modif(KeyEvent event) {
    	control_regex_number(stocks_modif, 4);
    }

    @FXML
    void onchange_stocks_modif_mouse(MouseEvent event) {
    	control_regex_number(stocks_modif,4);
    }

    @FXML
    void onchange_stocks_mouse(MouseEvent event) {
    	
    }
    
    


    @FXML
    void open_fen_autre(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/vue/facture.fxml"));
			anchorpane_produit.getChildren().removeAll();
			anchorpane_produit.getChildren().setAll(fxml);
	
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void open_fen_client(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/vue/client.fxml"));
			anchorpane_produit.getChildren().removeAll();
			anchorpane_produit.getChildren().setAll(fxml);
	
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void open_fen_commande(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/vue/commande.fxml"));
			anchorpane_produit.getChildren().removeAll();
			anchorpane_produit.getChildren().setAll(fxml);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void open_fen_produit(ActionEvent event) {

    }
    
    @FXML
    void on_confirm_update_prod(MouseEvent event) {
    	String existe;
    	
    	if(designation_modif.getText().isEmpty() || prix_unitaire_modif.getText().isEmpty() || stocks_modif.getText().isEmpty()) {
    		
    		label_alert1.setText("Completez tous les champs");
			
		}else {
			label_alert1.setText("");
	    	if(event.getSource() == btn_confirm_update_prod) {
	    	 	//alert modif
	        	javafx.scene.control.ButtonType oui = new javafx.scene.control.ButtonType("Oui") ;
	        	javafx.scene.control.ButtonType non = new javafx.scene.control.ButtonType("Non") ;
	        	Alert alert = new Alert(AlertType.CONFIRMATION);
	    		alert.setHeaderText("ATTENTION !!!");
	    		alert.setContentText("Vous voulez le modifier ?");
	    		alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
	    		alert.getDialogPane().getStyleClass().add("dialog_alert");
	    		alert.getButtonTypes().setAll(oui,non);
	    		Optional<javafx.scene.control.ButtonType> resultat = alert.showAndWait();
	        	
	    		//---------------------------------------
	        	
	    		
	    		
	    		ProduitDAO produitDAO = new ProduitDAO();
	        	Produit produit = new Produit();
	        	
	        	produit.setRef_prodstr(reference_modif.getText());
	        	produit.setDesign_prod(designation_modif.getText().toString());
	        	produit.setPrix_unitaire(Float.parseFloat(prix_unitaire_modif.getText()));
	        	produit.setStock_prod(Integer.parseInt(stocks_modif.getText()));
	        	produit.setSignal("");
	        	existe = teste_si_produit_existe();
	        	
	        	if(resultat.isPresent() && resultat.get() == oui) {
	        	
	        		produitDAO.update_produit(produit);
	        	}
	        	       	
	        	designation_modif.setText("");
				prix_unitaire_modif.setText("");
				stocks_modif.setText("");
				new ZoomInRight(pane_ajouter_produit).play();
				pane_ajouter_produit.toFront();
	        	ctrl_affichage_produit();
	        	
	    	}
	    	
		}
    	
    }
    
    
    void controle_et_regex() {
    	String desigantion = "^[a-zA-Z]{25}$";
//    	String prix
    }
    
    void function_ajouter_produit() {
    	String existe;
    	if(designation.getText().isEmpty() || prix_unitaire.getText().isEmpty() || stocks.getText().isEmpty()) {
    		
			label_alert.setText("Completez tous les champs");
			
		}else {
			
			label_alert.setText("");
			Produit p = new Produit();
	    	ProduitDAO produitDAO = new ProduitDAO();
	    	existe = teste_si_produit_existe();
	    	
	    	if(existe.equals("non")) {
	    		p.setDesign_prod(designation.getText().toString());
				p.setPrix_unitaire(Float.parseFloat(prix_unitaire.getText()));
				p.setStock_prod(Integer.parseInt(stocks.getText()));
				produitDAO.save_product(p);
	    	
	    	}else {
	    		p.setSignal("update");
	    		p.setDesign_prod(existe);
	    		p.setStock_prod(Integer.parseInt(stocks.getText()));
	    		produitDAO.update_produit(p);
	    	}
	    	
			
			
			
			designation.setText("");
			prix_unitaire.setText("");
			prix_unitaire.setText("");
			stocks.setText("");
			label_alert.setText("");
			ctrl_affichage_produit();
			
			
		}

    }

    String teste_si_produit_existe() {
    	ProduitDAO produitDAO = new ProduitDAO();
    	ResultSet res;
    
    	res = produitDAO.getdata_produit_en_reultset();
    	System.out.println(designation.getText());
    	try {
			while(res.next()) {
		
				if(designation.getText().equals(res.getString("DESIGN_PROD")) && 
					Float.parseFloat(prix_unitaire.getText()) == res.getFloat("PRIX_UNITAIRE")) {
					return res.getString("DESIGN_PROD");
				}else {
					System.out.println("tsy mitovy");
					
				}
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "non";
		
    			

    }
    
    
    
   
    
	public void ctrl_affichage_produit() {
		
		table_produit.getItems().clear();
		ProduitDAO produitDAO = new ProduitDAO();
		liste_produit = produitDAO.getdata_produit_en_observablelist();
		
		col_reference.setCellValueFactory(new PropertyValueFactory<Produit, String>("ref_prodstr"));
		col_designation.setCellValueFactory(new PropertyValueFactory<Produit, String>("design_prod"));
		col_prix_unitaire.setCellValueFactory(new PropertyValueFactory<Produit, Float>("prix_unitaire"));
		col_stocks.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("stock_prod"));
	
		table_produit.setItems(liste_produit);
		reference.setText("ref-" + produitDAO.getRef_auto());
		edit_search_prod.setText("");
	}
	
	  void function_delete_produit() {
	    	javafx.scene.control.ButtonType oui = new javafx.scene.control.ButtonType("Oui") ;
	    	javafx.scene.control.ButtonType non = new javafx.scene.control.ButtonType("Non") ;
	    	
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
			
			alert.setHeaderText("ATTENTION !!!");
			alert.setContentText("Vous voulez le Supprimer ?");
			alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			alert.getDialogPane().getStyleClass().add("dialog_alert");
			alert.getButtonTypes().setAll(oui,non);
			
			 
	    	Produit produit = new Produit();
	    	ProduitDAO produitDAO = new ProduitDAO();
	    	produit.setRef_prodstr(table_produit.getSelectionModel().getSelectedItem().getRef_prodstr());
	    	Optional<javafx.scene.control.ButtonType> resultat = alert.showAndWait();
	    	
			if(resultat.isPresent() && resultat.get() == oui) { 
				
				produitDAO.delete_produit(produit);
	    		ctrl_affichage_produit();
			}
	 
	    	
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ctrl_affichage_produit();
		
		
	}

	
		


	
    @FXML
    void search_produit() {
    	
		ProduitDAO produit = new ProduitDAO();
		 ObservableList<Produit> list_product;
		 list_product = produit.getdata_produit_en_observablelist();
		 ObservableList<Produit> list_produit_filter = FXCollections.observableArrayList();
		
		 edit_search_prod.textProperty().addListener(new ChangeListener<String>() {
	
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				edit_search_prod.getText();
				for(Produit p: list_product) {
					if((p.getDesign_prod().toLowerCase()).indexOf(edit_search_prod.getText().toLowerCase()) >-1) {
						
						list_produit_filter.add(p);
//						table_produit.getItems().clear();
						table_produit.setItems(list_produit_filter);
						
					}else {
						table_produit.setItems(list_produit_filter);
					}
					
					
					
				}
				
			}
			 
		 });
		 
    }

    @FXML
    void exit_search_product(MouseEvent event) {
    	if(event.getSource() == edit_search_prod) {
//    		edit_search_prod.setText("");
    	}
    }
	
    @FXML
    void on_acheter_inprod(MouseEvent event) {
    	Parent root;
    	Stage stage;
    	Scene scene;
        	
    	if(event.getSource() == btn_acheter_produit_inprod && table_produit.getSelectionModel().getSelectedItem() != null) {

    		try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/commande.fxml"));
				root = loader.load();
				Commande_controlleur commande = loader.getController();
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
		    	stage.show();
		    	
		    	
		    	root.setOnMousePressed((MouseEvent e) -> {
    				xOffset = e.getSceneX();
    				yOffset = e.getSceneY();
    			});
    			
    			root.setOnMouseDragged((MouseEvent e) ->{
    				stage.setX(e.getScreenX() - xOffset);
    				stage.setY(e.getScreenY() - yOffset);
    				
    			});
    			
				
//				open_fen_commande(null);
				commande.setcombo_com(table_produit.getSelectionModel().getSelectedItem());
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	
    	}
    }
    
    

  

}

package controlleur;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import animatefx.animation.ZoomInLeft;
import animatefx.animation.ZoomInRight;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
import modele.Client;
import modele.ClientDAO;

public class Client_controlleur implements Initializable{
	private Parent fxml;
	
	
	ObservableList<Client> liste_de_client;

    @FXML
    private AnchorPane anchorpane_client;

    @FXML
    private Circle btn_minimise;

    @FXML
    private Circle btn_close;

    @FXML
    private BorderPane bdpane_client;

    @FXML
    private TableView<Client> table_client;

    @FXML
    private TableColumn<Client, String> cln_codecli;

    @FXML
    private TableColumn<Client, String> cln_nomcli;

    @FXML
    private Button btn_supprimer_client;

    @FXML
    private Button btn_delete_all_client;

    @FXML
    private TextField field_recherche_client;

    @FXML
    private Pane pane_modifier_client;

    @FXML
    private JFXButton btn_confirmer_modif_client;

    @FXML
    private JFXButton btn_annuler_modif_client;

    @FXML
    private JFXTextField code_client_modif;

    @FXML
    private JFXTextField nom_client_modif;

    @FXML
    void annuler_modif_client(ActionEvent event) {
    	if(event.getSource() == btn_annuler_modif_client) {
    		ctrl_affichage_client();
    	
    	}
    }

    @FXML
    void close_fenetre(MouseEvent event) {
  
    		System.exit(0);
    
    }

    @FXML
    void confirmer_modif_client(ActionEvent event) {
    	ClientDAO clientDAO = new ClientDAO();
    	String code = code_client_modif.getText();
    	Client client = new Client();
    	
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
    	
    	if(event.getSource() == btn_confirmer_modif_client) {
    		client.setCode_clistr(code);
    		client.setNom_cli(nom_client_modif.getText());
    	
    	
    		
    		if(resultat.isPresent() && resultat.get() == oui) {
	    		clientDAO.update_client(client);
	 
	    		nom_client_modif.setText("");
	    		code_client_modif.setText("");
	    		new ZoomInRight(pane_modifier_client).play();
	    		
	    		ctrl_affichage_client();
	    		
    		}
    	}
    }

    @FXML
    void minimize_fenetre(MouseEvent event) {
    	if(event.getSource() == btn_minimise /*&& event.getClickCount() == 2*/) {
    		Stage stage = (Stage) btn_minimise.getScene().getWindow();
    		stage.setIconified(true);
    		
    	}
    }

    @FXML
    void on_click_table_client(MouseEvent event) {
    	if(event.getSource() == table_client && event.getClickCount() == 2 && table_client.getSelectionModel().getSelectedItem() != null) {
    		btn_confirmer_modif_client.disableProperty().setValue(false);
    	  	ClientDAO clientDAO = new ClientDAO();
    	  	Client cl = new Client();
        	ResultSet resultat = clientDAO.getdata_client_en_resultset();
        	cl = table_client.getSelectionModel().getSelectedItem();
        	
        	 try {
    			while(resultat.next()){
    				 if(("Cli-"+ resultat.getInt("CODE_CLI")).equals(cl.getCode_clistr())) {
    					 new ZoomInLeft(pane_modifier_client).play();
    					 pane_modifier_client.toFront();
    					 
    					 code_client_modif.setText("Cli-" + resultat.getInt("CODE_CLI"));
    					 nom_client_modif.setText(resultat.getString("NOM_CLI"));
    					 
    				 }
    			 }
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }

    @FXML
    void on_delete_all_client(MouseEvent event) {
    	javafx.scene.control.ButtonType oui = new javafx.scene.control.ButtonType("Oui") ;
    	javafx.scene.control.ButtonType non = new javafx.scene.control.ButtonType("Non") ;
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		
		alert.setHeaderText("ATTENTION !!!");
		alert.setContentText("Vous voulez le supprimer tous les clients ?");
		alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		alert.getDialogPane().getStyleClass().add("dialog_alert");
		alert.getButtonTypes().setAll(oui,non);
		Optional<javafx.scene.control.ButtonType> resultat = alert.showAndWait();
		
    	if(event.getSource() == btn_delete_all_client) {
    		if(resultat.isPresent() && resultat.get() == oui) {
    			ClientDAO.delete_all_client();
    			ctrl_affichage_client();
    		}
    	}
    }

    @FXML
    void on_delete_client(MouseEvent event) {
    	
    	if(event.getSource() == btn_supprimer_client && table_client.getSelectionModel().getSelectedItem() != null) {
    		function_delete_client();
    	}
    
    }
    
    void function_delete_client(){
    	javafx.scene.control.ButtonType oui = new javafx.scene.control.ButtonType("Oui") ;
    	javafx.scene.control.ButtonType non = new javafx.scene.control.ButtonType("Non") ;
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		
		alert.setHeaderText("ATTENTION !!!");
		alert.setContentText("Vous voulez le Supprimer ?");
		alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		alert.getDialogPane().getStyleClass().add("dialog_alert");
		alert.getButtonTypes().setAll(oui,non);
		
    	Client client = new Client();
    	client.setCode_clistr(table_client.getSelectionModel().getSelectedItem().getCode_clistr());
    	ClientDAO clientDAO = new ClientDAO();
    	Optional<javafx.scene.control.ButtonType> resultat = alert.showAndWait();
    	
		if(resultat.isPresent() && resultat.get() == oui) { 
			
    		clientDAO.supprimer_client(client);
    		ctrl_affichage_client();   	
		}
    }

    @FXML
    void on_delete_pressed_inclient(KeyEvent event) {
    	if(event.getCode().equals(KeyCode.DELETE)) {
    		function_delete_client();
    		System.out.println("delete client");
    	}
    }
    @FXML
    void on_enter_pressed_inclient(KeyEvent event) {
    	if(event.getCode().equals(KeyCode.ENTER)) {
//    		function_ajouter_client();
    	}
    }
    

    @FXML
    void open_fen_autre(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/vue/facture.fxml"));
			anchorpane_client.getChildren().removeAll();
			anchorpane_client.getChildren().setAll(fxml);
	
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void open_fen_client(ActionEvent event) {
    	
    }

    @FXML
    void open_fen_commande(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/vue/commande.fxml"));
			anchorpane_client.getChildren().removeAll();
			anchorpane_client.getChildren().setAll(fxml);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void open_fen_produit(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/vue/produit.fxml"));
			anchorpane_client.getChildren().removeAll();
			anchorpane_client.getChildren().setAll(fxml);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ctrl_affichage_client();
	}
	
	public  void ctrl_affichage_client() {
		
		table_client.getItems().clear();
		ClientDAO clientDAO = new ClientDAO();
		liste_de_client = clientDAO.getdata_client_enobservableliste();

		
		cln_codecli.setCellValueFactory(new PropertyValueFactory<Client, String>("code_clistr"));
		cln_nomcli.setCellValueFactory(new PropertyValueFactory<Client, String>("nom_cli"));
		table_client.setItems(liste_de_client);
		btn_confirmer_modif_client.disableProperty().setValue(true);
//		code_client_modif.setText("Cli-" + clientDAO.getCode());
		
	
	}
	
	
    @FXML
    void cli_change_nomcli(KeyEvent event) {
    	Produit_controlleur prodcontr = new Produit_controlleur();
    	prodcontr.control_regex_string(nom_client_modif, 20);
    }

    
	
	
}

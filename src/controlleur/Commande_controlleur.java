package controlleur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.management.openmbean.CompositeDataView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import modele.Client;
import modele.ClientDAO;
import modele.Commande;
import modele.CommandeDAO;
import modele.Produit;
import modele.ProduitDAO;

public class Commande_controlleur implements Initializable {
	
	private double xOffset = 0;
	private double yOffset = 0;
	int qteModif;
	String refProdTable;
    @FXML
    private JFXTextField idComModif;
    @FXML
    private Label label_alertModif;
    @FXML
    private JFXTextField codeCLiModifCom;

    @FXML
    private Pane paneModifierCommande;

    @FXML
    private Button btnAnnulerModifCom;

    @FXML
    private JFXTextField cmModifQteCom;
    @FXML
    private JFXDatePicker cmModifDateCom;

    @FXML
    private JFXButton btnConfirmerModifCommande;

    @FXML
    private JFXComboBox<String> cmModifProdCom;

    @FXML
    private JFXTextField cmModifClientCom;

	  @FXML
	    private Button btnFacture;

    @FXML
    private Label total;
    @FXML
    private JFXDatePicker date1;

    @FXML
    private JFXDatePicker date2;
    @FXML
    private TextField rechercheCommande;
	
    @FXML
    private JFXCheckBox check_client;
    @FXML
    private TableView<Commande> table_commande;
    @FXML
    private Label label_alert;
    @FXML
    private JFXTextField cm_nom_client;

	List<String> list_prod_combo = new ArrayList<String>();
    @FXML
    private JFXButton btn_valider_commande;

    @FXML
    private JFXButton btn_open_command;


    @FXML
    private AnchorPane anchorpane_commande;
    private Parent fxml;
	
    @FXML
    private Circle btn_minimise;

    @FXML
    private Circle btn_close;

    @FXML
    private BorderPane bdpane_commande;

    @FXML
    private TableColumn<Commande, String> cln_produit_com;

    @FXML
    private TableColumn<Commande, String> cln_refprod_com;

    @FXML
    private TableColumn<Commande, Float> cln_pu_com;

    @FXML
    private TableColumn<Commande, String> cln_client_com;

    @FXML
    private TableColumn<Commande, String> cln_code_com;

    @FXML
    private TableColumn<Commande, Integer> cln_qte_com;

    @FXML
    private TableColumn<Commande, String> cln_date_com;

    @FXML
    private Button btn_ajouter_produit1;

    @FXML
    private Pane pane_commander;

    @FXML
    private Button btn_annuler_modif_prod1;

    @FXML
    private JFXTextField cm_stock_commander;

    @FXML
    private Label label_alert11;

    @FXML
    private JFXDatePicker cm_date_de_commande;

    @FXML
    private JFXComboBox<String> cm_produit_commander;

    @FXML
    private JFXComboBox<String> cm_ref_commande;

    @FXML
    private JFXTextField nom_client;

    @FXML
    private JFXTextField code_client;
   

    @FXML
    void annuler_modif_prod(ActionEvent event) {
    	ctrlAffichageCommande();
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
    void on_change_combo_ref(MouseEvent event) {

    }


    @FXML
    void on_change_qte_commander_mouse(MouseEvent event) {

    }


    @FXML
    void open_fen_autre(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/vue/facture.fxml"));
			anchorpane_commande.getChildren().removeAll();
			anchorpane_commande.getChildren().setAll(fxml);
	
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void open_fen_client(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/vue/client.fxml"));
			anchorpane_commande.getChildren().removeAll();
			anchorpane_commande.getChildren().setAll(fxml);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void open_fen_commande(ActionEvent event) {

		cm_produit_commander.mouseTransparentProperty().setValue(false);
    	cm_produit_commander.editableProperty().setValue(false);
    	ctrlAffichageCommande();
    }

    @FXML
    void open_fen_produit(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/vue/produit.fxml"));
			anchorpane_commande.getChildren().removeAll();
			anchorpane_commande.getChildren().setAll(fxml);
		
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void open_pane_ajout_produit(ActionEvent event) {
    	cm_produit_commander.mouseTransparentProperty().setValue(false);
    	cm_produit_commander.editableProperty().setValue(false);
    }

//    public void send_ref_nameprod_to_commande(Produit p) {
//		valueCombo = p.getDesign_prod() + p.getRef_prodstr();
//		
//	}
//    
    public void setcombo_com(Produit prod) {
    	ProduitDAO produitDAO = new ProduitDAO();
    	List<String> list = produitDAO.getdata_produit_en_arraylist();
		cm_produit_commander.setItems(FXCollections.observableArrayList(list));
		
		cm_produit_commander.setValue(prod.getRef_prodstr() +" "+ prod.getDesign_prod());
			
	
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	
    	cm_produit_commander.editableProperty().setValue(false);
		
		ProduitDAO produitDAO = new ProduitDAO();
		List<String> list = produitDAO.getdata_produit_en_arraylist();
		cm_produit_commander.setItems(FXCollections.observableArrayList(list));
		
		ClientDAO clientDAO = new ClientDAO();
		clientDAO.getdata_client_enobservableliste();
		clientDAO.getCode();
		code_client.setText("cli-"+clientDAO.getCode());
		code_client.editableProperty().setValue(false);
		cm_date_de_commande.setValue(LocalDate.now());
//		code_client.disableProperty().setValue(true);
		
		ctrlAffichageCommande();
	
    	
	}
	
	

    @FXML
    void on_valider_commande(ActionEvent event) {
    	int refint;
    	String refprod;
    	String codecli;
    	int codeint;
   
    	if(event.getSource() == btn_valider_commande) {
    		
    		if(cm_date_de_commande.getValue() == null || cm_nom_client.getText().equals("") 
    				|| cm_stock_commander.getText().equals("") || cm_produit_commander.getValue() == null ) {
    			label_alert.setText("Complétez tous les champs");
    		}else {
    			
    			label_alert.setText("");
    			
    			//ajout dans table client
        		Client client = new Client();
            	client.setNom_cli(cm_nom_client.getText());
            	ClientDAO clientDAO = new ClientDAO();
            	
            	
            	// ajout dans tables commandes
            	Commande commande = new Commande();
            	CommandeDAO commandeDAO = new CommandeDAO();
            	//convert ref prod en int
            	refprod = cm_produit_commander.getValue().
            			toString().substring(4,(cm_produit_commander.getValue().toString().length()))
            			.replaceAll("([\\s])[a-z0-9A-Z]*","");
            	refint = Integer.parseInt(refprod);
            	
            	codecli = code_client.getText().toString().substring(4, code_client.getText().length());
            	codeint = Integer.parseInt(codecli);
            
            
            	commande.setCode_cli(codeint);
            	commande.setRef_prod(refint);
            	commande.setDate_com(cm_date_de_commande.getValue());
            	commande.setQte_com(Integer.parseInt(cm_stock_commander.getText()));
            	
            	
            	
            	Produit produit = new Produit();
            	ProduitDAO produitDAO = new ProduitDAO();
            	produit.setRef_prod(refint);
       
            	
            	//verifier l reste de produit
            	if(Integer.parseInt(cm_stock_commander.getText()) > produitDAO.getMax_prod(produit) && produitDAO.getMax_prod(produit) != 0) {  
            		cm_stock_commander.setText(produitDAO.getMax_prod(produit)+"");
            		cm_stock_commander.setFocusColor(Color.RED);
            		cm_stock_commander.requestFocus();
            		cm_stock_commander.setPromptText("Quantité maximum:");
            	}
            	else if(produitDAO.getMax_prod(produit) == 0){
            		cm_stock_commander.setFocusColor(Color.RED);
            		cm_stock_commander.requestFocus();
            		cm_stock_commander.setPromptText("Stock insuffisant");
            		cm_stock_commander.setText("");
            		
            	}
            	else {
            		if(check_client.isSelected()) {
            			produit.setStock_prod(Integer.parseInt(cm_stock_commander.getText()));
                    	produit.setRef_prod(refint);
                    	produitDAO.update_qte_prod(produit); 
                    	commandeDAO.save_commande(commande);
                    	ctrlAffichageCommande();
            		}else {
            			produit.setStock_prod(Integer.parseInt(cm_stock_commander.getText()));
                    	produit.setRef_prod(refint);
                    	produitDAO.update_qte_prod(produit);
                    	clientDAO.save_client(client); 
                    	commandeDAO.save_commande(commande);
                    	ctrlAffichageCommande();
            		}
                	
               
            	}
            	
    		}

   
    	}
    	
    	
    }
    
    public void verifieStock() {
    	
    }
 

    @FXML
    void on_change_qte_commander(KeyEvent event) {
    	
    	Produit prod = new Produit();
    	ProduitDAO prodDAO = new ProduitDAO();
    	
    	Produit_controlleur prodControlleur = new Produit_controlleur();
    	prodControlleur.control_regex_number(cm_stock_commander, 4);
    }
    
    @FXML
    void on_change_client_com(KeyEvent event) {
    	Produit_controlleur prodControlleur = new Produit_controlleur();
    	prodControlleur.control_regex_string(cm_nom_client, 20);
    }
    
    
    public void ctrlAffichageCommande() {
        	
//    	table_commande.getItems().clear();
    	CommandeDAO commandeDAO = new CommandeDAO();
    	ObservableList<Commande> list = commandeDAO.getCommandeObservablelist();
    	
    	cln_code_com.setCellValueFactory(new PropertyValueFactory<Commande, String>("idcom"));
    	cln_produit_com.setCellValueFactory(new PropertyValueFactory<Commande, String>("design_prod"));
    	cln_client_com.setCellValueFactory(new PropertyValueFactory<Commande, String>("nom_cli"));
    	cln_pu_com.setCellValueFactory(new PropertyValueFactory<Commande, Float>("prix_unitaire"));
    	cln_date_com.setCellValueFactory(new PropertyValueFactory<Commande, String>("date"));
    	cln_qte_com.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("qte_comm"));
    	
    	
    	
    	table_commande.setItems(list);
		cm_date_de_commande.setValue(LocalDate.now());
		cm_nom_client.setText("");
		cm_stock_commander.setText("");
		cm_stock_commander.setPromptText("Qte");
		date1.setValue(null);
		date2.setValue(null);
		totalCommande(list);
		onCheckClient();
		if(list.isEmpty()) {
			check_client.disableProperty().setValue(true);
			check_client.selectedProperty().setValue(false);
			cm_nom_client.setEditable(true);
		}else {
			check_client.disableProperty().setValue(false);
			check_client.selectedProperty().setValue(true);
			onCheckClient();
		}
     }
    
    @FXML
    void onCheckClient() {
    	
    	CommandeDAO comDAO = new CommandeDAO();

    	
    	
    	ClientDAO clientDAO = new ClientDAO();
    	
    	
    	if(check_client.isSelected()) {
    		code_client.setText("Cli-"+clientDAO.getLastClient().getCode_cli());
    		cm_nom_client.setText(clientDAO.getLastClient().getNom_cli());
    		cm_nom_client.setEditable(false);
    		
    	}else {
    		if(comDAO.ilyaCommande() == 0) {
    			code_client.setText("Cli-"+(clientDAO.getLastClient().getCode_cli()));
        		cm_nom_client.setText("");
        		
        		cm_nom_client.setEditable(true);
    		}
    		else {
    			code_client.setText("Cli-"+(clientDAO.getLastClient().getCode_cli()+1));
        		cm_nom_client.setText("");
        		cm_nom_client.setEditable(true);
    		}
    	}
    	
    }
    
    void searchCommande() {
    	CommandeDAO  comDAO = new CommandeDAO();
    	ObservableList<Commande> observCommande = comDAO.getCommandeObservablelist();
    	ObservableList<Commande> filterCommande = FXCollections.observableArrayList();
    	
    	rechercheCommande.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				for(Commande com : observCommande) {
					if(com.getDesign_prod().toLowerCase().indexOf(rechercheCommande.getText().toLowerCase())>-1 || 
							com.getIdcom().toLowerCase().indexOf(rechercheCommande.getText().toLowerCase())>-1 ||
							com.getNom_cli().toLowerCase().indexOf(rechercheCommande.getText().toLowerCase())>-1)
					{
						filterCommande.add(com);
						table_commande.setItems(filterCommande);
						totalCommande(filterCommande);
					}else {
						table_commande.setItems(filterCommande);
						totalCommande(filterCommande);
					}
				}
				
			}
		});
    	
    }
    
    @FXML
    void onSearchCommande() {
    	searchCommande();
    }
    
   

    @FXML
    void changeDate2(ActionEvent event) {
    	CommandeDAO comDAO = new CommandeDAO();
    	ObservableList<Commande> comliste = comDAO.getCommandeObservablelist();
    	ObservableList<Commande> comTrier = FXCollections.observableArrayList();
    	
    	if(date1.getValue() != null) {
    		for(Commande c : comliste) {
    			if((c.getDate().isAfter(date1.getValue()) || c.getDate().isEqual(date1.getValue())) 
    					&& (c.getDate().isBefore(date2.getValue())) || c.getDate().isEqual(date2.getValue())) {
    				comTrier.add(c);
    				table_commande.setItems(comTrier);
    				totalCommande(comTrier);
    			}else {
    				table_commande.setItems(comTrier);
    				totalCommande(comTrier);
    			}
    		}
    	}
    	
    	
    	
    	
    }
    @FXML
    void changeDate1(ActionEvent event) {
    	CommandeDAO comDAO = new CommandeDAO();
    	ObservableList<Commande> comliste = comDAO.getCommandeObservablelist();
    	ObservableList<Commande> comTrier = FXCollections.observableArrayList();
    	float tot = 0;
    	
    	if(date2.getValue() != null) {
    		for(Commande c : comliste) {
    			if((c.getDate().isAfter(date1.getValue()) || c.getDate().isEqual(date1.getValue())) 
    					&& (c.getDate().isBefore(date2.getValue())) || c.getDate().isEqual(date2.getValue())) {
    				comTrier.add(c);
    				table_commande.setItems(comTrier);
    				totalCommande(comTrier);
    			}else {
    				table_commande.setItems(comTrier);
    				totalCommande(comTrier);
    			}
    		}
    	}
    	
   
    }

    
	public Float totalCommande(ObservableList<Commande> list) {
		
		float tot = 0;
    	for(Commande com : list) {
    		tot += (com.getPrix_unitaire() * com.getQte_comm());
    	}
    	
    	total.setText(tot + "Ar");
		
		return (float) 0;
		
	}
	
	
  @FXML
    void facturerCommande(ActionEvent event) {
	Parent root;
  	Stage stage;
  	Scene scene;
      	
	  	Commande com = new Commande();
    	com = table_commande.getSelectionModel().getSelectedItem();
    	if(com != null) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/facture.fxml"));
			try {
				root = loader.load();
				FactureControlleur fact = loader.getController();
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
		    	stage.show();
		    	fact.setDataFacture(com);
		    	
		    	root.setOnMousePressed((MouseEvent e) -> {
    				xOffset = e.getSceneX();
    				yOffset = e.getSceneY();
    			});
    			
    			root.setOnMouseDragged((MouseEvent e) ->{
    				stage.setX(e.getScreenX() - xOffset);
    				stage.setY(e.getScreenY() - yOffset);
    				
    			});
    			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
  
  
  @FXML
  void onClickTableCommande(MouseEvent event) {
	  ProduitDAO produitDAO = new ProduitDAO();
	  List<String> list = produitDAO.getdata_produit_en_arraylist();
	  if(event.getSource() == table_commande && event.getClickCount() == 2
			  && table_commande.getSelectionModel().getSelectedItem() != null) {
		  Commande commande = new Commande();
		  CommandeDAO comDAO = new CommandeDAO();
		  commande = comDAO.getCommandeCodecom(table_commande.getSelectionModel().getSelectedItem());
		  
		  cmModifClientCom.setText(commande.getNom_cli());
		  cmModifDateCom.setValue(commande.getDate());
		  cmModifQteCom.setText(commande.getQte_comm()+"");
		  cmModifProdCom.setItems(FXCollections.observableArrayList(list));
		  cmModifProdCom.setValue(commande.getRef_prodstr() + " "+ commande.getDesign_prod());
		  codeCLiModifCom.setText(commande.getCode_clistr());
		  idComModif.setText(commande.getIdcom());
		 
		  
		  new ZoomInLeft(paneModifierCommande).play();
		  paneModifierCommande.toFront();
		  qteModif = commande.getQte_comm();
		  refProdTable = commande.getRef_prodstr();
		
		 
	  }

	  
  }
  
  @FXML
  void annulerModifCom(ActionEvent event) {
	  new ZoomInRight(pane_commander).play();
	  pane_commander.toFront();
  }

  @FXML
  void onConfirmModifCom(ActionEvent event) {

	  if(cmModifClientCom.getText().equals("") || cmModifQteCom.getText().equals("") 
			  || cmModifDateCom.getValue() == null || cmModifProdCom.getValue() == null) {
		  label_alertModif.setText("Complétez tous les champs");
	  }
	  else{
			
		Produit produit = new Produit();
      	ProduitDAO produitDAO = new ProduitDAO();
      	String getref = cmModifProdCom.getValue().replaceAll("[//s][a-z0-9A-Z]*", "").replaceAll("[^1-9]", "");
        produit.setRef_prod(Integer.parseInt(getref));
		  
		//verifier l reste de produit
//        System.out.println(qteModif);

        int qtemaxmod = qteModif +produitDAO.getMax_prod(produit);
        
        
      	if( cmModifProdCom.getValue().substring(0, cmModifProdCom.getValue().indexOf(" "))
      			.equals(refProdTable)) 
      	{
      		if(Integer.parseInt(cmModifQteCom.getText()) > qtemaxmod && qtemaxmod != 0) {  
          		cmModifQteCom.setText(qtemaxmod+"");
          		cmModifQteCom.setFocusColor(Color.RED);
          		cmModifQteCom.requestFocus();
          		cmModifQteCom.setPromptText("Quantité maximum:");
          	}
          	else if(qtemaxmod == 0){
          		cmModifQteCom.setFocusColor(Color.RED);
          		cmModifQteCom.requestFocus();
          		cmModifQteCom.setPromptText("Stock insuffisant");
          		cmModifQteCom.setText("");
          	}else 
          	{
          	  Commande commande = new Commande();
      		  commande.setIdcom(idComModif.getText());
      		  commande.setNom_cli(cmModifClientCom.getText());
      		  commande.setRef_prodstr(refProdTable);
      		  commande.setDate(cmModifDateCom.getValue());
      		  commande.setQte_comm(qtemaxmod - Integer.valueOf(cmModifQteCom.getText()));
      		  commande.setQte_com(Integer.valueOf(cmModifQteCom.getText()));
      		  
      		  
      		  CommandeDAO comDAO = new CommandeDAO();
      		  javafx.scene.control.ButtonType oui = new javafx.scene.control.ButtonType("Oui") ;
      	    	javafx.scene.control.ButtonType non = new javafx.scene.control.ButtonType("Non") ;
      	    	
      	    	Alert alert = new Alert(AlertType.CONFIRMATION);
      			
      			alert.setHeaderText("ATTENTION !!!");
      			alert.setContentText("Vous voulez le modifier ?");
      			alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
      			alert.getDialogPane().getStyleClass().add("dialog_alert");
      			alert.getButtonTypes().setAll(oui,non);
      			Optional<javafx.scene.control.ButtonType> resultat = alert.showAndWait();
      			
      			if(resultat.isPresent() && resultat.get() == oui) {
      				
      				comDAO.updateCommande(commande);
      				new ZoomInRight(pane_commander).play();
      				  pane_commander.toFront();
      				  ctrlAffichageCommande();            
            	
      			}
          	}
    		 
      	}else {

      		if(Integer.parseInt(cmModifQteCom.getText()) > produitDAO.getMax_prod(produit) && produitDAO.getMax_prod(produit) != 0) {  
          		cmModifQteCom.setText(produitDAO.getMax_prod(produit)+"");
          		cmModifQteCom.setFocusColor(Color.RED);
          		cmModifQteCom.requestFocus();
          		cmModifQteCom.setPromptText("Quantité maximum:");
          	}
          	else if(produitDAO.getMax_prod(produit) == 0){
          		cmModifQteCom.setFocusColor(Color.RED);
          		cmModifQteCom.requestFocus();
          		cmModifQteCom.setPromptText("Stock insuffisant");
          		cmModifQteCom.setText("");
          	}else 
          	{
          	  Commande commande = new Commande();
      		  commande.setIdcom(idComModif.getText());
      		  commande.setNom_cli(cmModifClientCom.getText());
      		  commande.setRef_prodstr(refProdTable );
      		  commande.setDate(cmModifDateCom.getValue());
      		  commande.setQte_com(Integer.valueOf(cmModifQteCom.getText()));
      		  
      		  System.out.println(commande +"io");
      		  CommandeDAO comDAO = new CommandeDAO();
      		  javafx.scene.control.ButtonType oui = new javafx.scene.control.ButtonType("Oui") ;
      	    	javafx.scene.control.ButtonType non = new javafx.scene.control.ButtonType("Non") ;
      	    	
      	    	Alert alert = new Alert(AlertType.CONFIRMATION);
      			
      			alert.setHeaderText("ATTENTION !!!");
      			alert.setContentText("Vous voulez le modifier ?");
      			alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
      			alert.getDialogPane().getStyleClass().add("dialog_alert");
      			alert.getButtonTypes().setAll(oui,non);
      			Optional<javafx.scene.control.ButtonType> resultat = alert.showAndWait();
      			
      			if(resultat.isPresent() && resultat.get() == oui) {
      				
      				comDAO.updateCommande(commande);
      				new ZoomInRight(pane_commander).play();
      				  pane_commander.toFront();
      				  ctrlAffichageCommande();            
            	
      			}
          	}
      	}
      	
      	
      	 
		  
		  
		  
			
	  }
  }

  @FXML
  void on_change_combo_produit(MouseEvent event) {
	 
  }
  
 
  @FXML
  void changeQteModifCom(KeyEvent event) {
	  Produit_controlleur prodContr = new Produit_controlleur();
	  prodContr.control_regex_number(cmModifQteCom, 4);
  }
  @FXML
  void changeClientModifCom(KeyEvent event) {
	  Produit_controlleur prodContr = new Produit_controlleur();
	  prodContr.control_regex_string(cmModifClientCom, 20);
  }
  

  @FXML
  void supprimmerCommande(ActionEvent event) {
  	if(table_commande.getSelectionModel().getSelectedItem() != null) {
  		
		javafx.scene.control.ButtonType oui = new javafx.scene.control.ButtonType("Oui") ;
    	javafx.scene.control.ButtonType non = new javafx.scene.control.ButtonType("Non") ;
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		
		alert.setHeaderText("ATTENTION !!!");
		alert.setContentText("Vous voulez le supprimer ?");
		alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		alert.getDialogPane().getStyleClass().add("dialog_alert");
		alert.getButtonTypes().setAll(oui,non);
		Optional<javafx.scene.control.ButtonType> resultat = alert.showAndWait();
		
		if(resultat.isPresent() && resultat.get() == oui) {
  		
	  		CommandeDAO comDAO = new CommandeDAO();
	  		comDAO.deleteCommande(table_commande.getSelectionModel().getSelectedItem());
	  		ctrlAffichageCommande();
		}
  	}
  }

}

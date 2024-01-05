package controlleur;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Formatter;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.JTable;
import javax.swing.text.Document;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.api.Indentable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import modele.Commande;
import modele.CommandeDAO;

public class FactureControlleur {
	


    @FXML
    private Label numeroFacture;

    @FXML
    private Label dateFacture;


    @FXML
    private Label totalFacture;

    @FXML
    private Label totalEnLettre;
   @FXML
    private TableView<Commande> tabFacture;

    @FXML
    private TableColumn<Commande, String> clnProduit;

    @FXML
    private TableColumn<Commande, Integer> clnQuantite;

    @FXML
    private TableColumn<Commande, Float> clnPrixUnitaire;
    @FXML
    private Label nomCliFact;
    @FXML
    private JFXButton btnImprimer;
	private Parent fxml;
    @FXML
    private Circle btn_minimise;
    @FXML
    private AnchorPane anchorpane_client;

    @FXML
    private Circle btn_close;
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
	    void open_fen_autre(ActionEvent event) {

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
	    
	   
	    
	    void setDataFacture(Commande commande) {
	    	CommandeDAO comDAO = new CommandeDAO();
	    	ObservableList<Commande> comliste = comDAO.getCommndeParCodeCLi(commande);
	    	float tot = 0;
	    	String dateformat = "dd-MM-yyyy";
	    	SimpleDateFormat simpledate = new SimpleDateFormat(dateformat);
	    	String date = simpledate.format(new Date());
	    	
	    	
	      	clnProduit.setCellValueFactory(new PropertyValueFactory<Commande, String>("design_prod"));
	      	clnQuantite.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("qte_comm"));
	      	clnPrixUnitaire.setCellValueFactory(new PropertyValueFactory<Commande, Float>("prix_unitaire"));
	      	tabFacture.setItems(comDAO.getCommndeParCodeCLi(commande));
	      	
	      	
	    	nomCliFact.setText(commande.getNom_cli());
	    	dateFacture.setText(date);
	    	numeroFacture.setText(commande.getCode_clistr().replaceAll("cli-", ""));
	    

	    	for(Commande commnd : comliste) {
	    		tot += commnd.getPrix_unitaire()*commnd.getQte_comm();
	    	}
	    	totalFacture.setText(tot +"Ar");
	    	totalEnLettre.setText(("Net à payer: ") + totalLettre(tot)+" Ariary");
	    	
	    }
	    
	    @FXML
	    void imprimerCommande(ActionEvent event) {
	    	com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
	    	try {
				PdfWriter.getInstance(doc, new FileOutputStream("facture N°"+numeroFacture.getText()+".pdf"));
				doc.open();
				doc.add(new Paragraph(
						"Facture N° " + numeroFacture.getText() +
						"\nDu "+ dateFacture.getText() +
						"\nPour Mr/Mme: " + nomCliFact.getText() + "\n\n\n"
						
						
						));
//				float[] colwidht = {150f, 150f,150f};
				
				PdfPTable table = new PdfPTable(3);
				PdfPCell produit =  new PdfPCell(new Paragraph("Produits"));
				PdfPCell Qte =  new PdfPCell(new Paragraph("Quantités"));
				PdfPCell pu =  new PdfPCell(new Paragraph("Prix unitaire"));
				
				table.addCell(produit);
				table.addCell(Qte);
				table.addCell(pu);
		
				
				for(Commande com : tabFacture.getItems()) {
					table.addCell(com.getDesign_prod());
					table.addCell(com.getQte_comm() +"");
					table.addCell(com.getPrix_unitaire()+"");
				}
				table.addCell("");
				table.addCell("Total");
				table.addCell(totalFacture.getText());
				
				doc.add(table);
				doc.add(new Paragraph(
						
						"\n\n "
						 + totalEnLettre.getText()
						
						));
				
				
		
				doc.close();
				Desktop.getDesktop().open(new File("facture N°"+numeroFacture.getText()+".pdf"));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	    
	    

	    
	    public String totalLettre(float total) {
	    	BigDecimal num = new BigDecimal(total);
	    	NumberFormat formatter = new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT);
	    	String tot = formatter.format(num).replaceAll("-", " ");
	    	
	    	return tot;
	    }
	    
	    
}

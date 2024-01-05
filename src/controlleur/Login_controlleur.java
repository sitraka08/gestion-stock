package controlleur;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modele.Users;
import modele.UsersDAO;

public class Login_controlleur implements Initializable{
	private double xOffset = 0;
	private double yOffset = 0;
	
	

    @FXML
    private AnchorPane anchorpane_login;

    @FXML
    private JFXTextField edit_username;

    @FXML
    private JFXPasswordField edit_password;

    @FXML
    private JFXButton btn_se_connecter;

    @FXML
    private Label label_login;

    @FXML
    private Circle btn_exit_login;

    @FXML
    private Circle btn_minimize_login;

    @FXML
    void exit_login() {
    	System.exit(0);
    }

    @FXML
    void minimize_login(MouseEvent event) {
    	if(event.getSource() == btn_minimize_login /*&& event.getClickCount() == 2*/) {
    		Stage stage = (Stage) btn_minimize_login.getScene().getWindow();
    		stage.setIconified(true);
    		
    	}
    }

    @SuppressWarnings("unused")
	@FXML
    void se_connecter(MouseEvent e) {
    	UsersDAO userDAO = new UsersDAO();
    	Users user = new Users();
    	    	
    	
    	Stage home = new Stage();
    	if(e.getSource() == btn_se_connecter || e.getSource().equals(KeyCode.DELETE)) {
    		
    		user.setUsername(edit_username.getText());
    		user.setPassword(edit_password.getText());
    		
    		userDAO.get_users(user);
    		
    		if(userDAO.get_users(user)) {
    			Stage stage = (Stage) btn_minimize_login.getScene().getWindow();
    			stage.hide();
    			label_login.setText(" ");
    			try {
        			Parent root = FXMLLoader.load(getClass().getResource("/vue/produit.fxml"));
        			Scene scene = new Scene(root);
        			home.setScene(scene);
        			home.initStyle(StageStyle.TRANSPARENT);
        			
        			root.setOnMousePressed((MouseEvent event) -> {
        				xOffset = event.getSceneX();
        				yOffset = event.getSceneY();
        			});
        			
        			root.setOnMouseDragged((MouseEvent event) ->{
        				home.setX(event.getScreenX() - xOffset);
        				home.setY(event.getScreenY() - yOffset);
        				
        			});
        			
        			
        			
        			home.show();
        			
        			
        			
        			
        		} catch(Exception ex) {
        			ex.printStackTrace();
        		}
    		}else {
    			label_login.setText("login incorrect");
    		}
    		
    	}		
    	
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
		
	}
    
    @SuppressWarnings("unused")
	@FXML
    void on_action_login(KeyEvent e) {
    	UsersDAO userDAO = new UsersDAO();
    	Users user = new Users();
    	    	
    	
    	Stage home = new Stage();
    
    		
    		user.setUsername(edit_username.getText());
    		user.setPassword(edit_password.getText());
    		
    		userDAO.get_users(user);
    		
    		if(true) {
    			Stage stage = (Stage) btn_minimize_login.getScene().getWindow();
    			stage.hide();
    			label_login.setText(" ");
    			try {
        			Parent root = FXMLLoader.load(getClass().getResource("/vue/produit.fxml"));
        			Scene scene = new Scene(root);
        			home.setScene(scene);
        			home.initStyle(StageStyle.TRANSPARENT);
        			
        			root.setOnMousePressed((MouseEvent event) -> {
        				xOffset = event.getSceneX();
        				yOffset = event.getSceneY();
        			});
        			
        			root.setOnMouseDragged((MouseEvent event) ->{
        				home.setX(event.getScreenX() - xOffset);
        				home.setY(event.getScreenY() - yOffset);
        				
        			});
        			home.show();
        	
        		} catch(Exception ex) {
        			ex.printStackTrace();
        		}
    		}else {
    			label_login.setText("login incorrect");
    		}
    		
    			
    	
    }
    
    
    
}

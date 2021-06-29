/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.util.LinkedList;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnLocaleMigliore"
    private Button btnLocaleMigliore; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="cmbLocale"
    private ComboBox<Business> cmbLocale; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	try {
    	Business b=cmbLocale.getValue();
    	String s=txtX.getText();
    	double i=Double.parseDouble(s);
    	if(i<0 ||i>1) {
    		 txtResult.setText("Isnerire valore compreso tra 0 e 1");
    		 return;
    	}
    	List<Business> lista=model.calcolaPercorso(i,b);
    	String ss="";
    	if(lista.size()==0) {
    		ss="Non ci sono percorsi";
    	}
    	
    	for(Business bb:lista) {
    		ss=ss+bb.getBusinessName()+"\n";
    	}
    	 txtResult.setText(ss);
    	
    	}catch(NullPointerException npe) {
    	 txtResult.setText("Selezionare valori corretti");
    	}catch(NumberFormatException nfe) {
    		 txtResult.setText("Inserire un valore numerico");
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	try {
    	int i=cmbAnno.getValue();
    	String citta=cmbCitta.getValue();
    	model.creaGrafo(citta, i);
    	txtResult.setText("#vertici "+model.getVertexsize()+"\n"+"#archi "+model.getArchiSize());
    	
    	
    	}catch(NullPointerException npe) {
    		
    		txtResult.setText("Selezionare valori corretti");
    	}
    	  cmbLocale.getItems().addAll(model.getVertici());
    	
    	
    
    }

    @FXML
    void doLocaleMigliore(ActionEvent event) {

    	if(model.getVertexsize()==0 ) {
    		txtResult.setText("Grafo non creato");
    		return;
    	}else {
    		txtResult.setText(model.getMigliore().getBusinessName());
    	}
    	
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnLocaleMigliore != null : "fx:id=\"btnLocaleMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLocale != null : "fx:id=\"cmbLocale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
        List<Business> lista=model.getCity();
        for(Business bb:lista) {
        	if(!cmbCitta.getItems().contains(bb.getCity()))
        	cmbCitta.getItems().add(bb.getCity());
        	
        }
        List<Integer> anno=new LinkedList<Integer>();
        for(int i=2005;i<2014;i++)
        	anno.add(i);
        cmbAnno.getItems().addAll(anno);
        
      
    }
}

package modele;

public class Produit {

	private int ref_prod;
	private String ref_prodstr;
	private String design_prod;
	private int stock_prod;
	private float prix_unitaire;
	private String signal;
	

	public Produit() {
		// TODO Auto-generated constructor stub
	}

	
	
	
	public Produit(String ref_prodstr, String design_prod, int stock_prod, float prix_unitaire) {
		super();
		this.ref_prodstr = ref_prodstr;
		this.design_prod = design_prod;
		this.stock_prod = stock_prod;
		this.prix_unitaire = prix_unitaire;
	}




	public Produit(int ref_prod, String design_prod, int stock_prod, float prix_unitaire) {
		super();
		this.ref_prod = ref_prod;
		this.design_prod = design_prod;
		this.stock_prod = stock_prod;
		this.prix_unitaire = prix_unitaire;
	}



	public String getSignal() {
		return signal;
	}




	public void setSignal(String signal) {
		this.signal = signal;
	}




	public String getRef_prodstr() {
		return ref_prodstr;
	}



	public void setRef_prodstr(String ref_prodstr) {
		this.ref_prodstr = ref_prodstr;
	}



	public void setStock_prod(int stock_prod) {
		this.stock_prod = stock_prod;
	}



	public void setPrix_unitaire(float prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}



	public int getRef_prod() {
		return ref_prod;
	}

	public void setRef_prod(int ref_prod) {
		this.ref_prod = ref_prod;
	}

	public String getDesign_prod() {
		return design_prod;
	}

	public void setDesign_prod(String design_prod) {
		this.design_prod = design_prod;
	}

	public int getStock_prod() {
		return stock_prod;
	}

	public void setStock_prod(Object object) {
		this.stock_prod = (int) object;
	}

	public float getPrix_unitaire() {
		return prix_unitaire;
	}

	public void setPrix_unitaire(Object object) {
		this.prix_unitaire = (float) object;
	}
	
	
	
	
}

package modele;

import java.time.LocalDate;

public class Commande {
	
	private int ref_prod;
	private int code_cli;
	private LocalDate date_com;
	private int qte_com;
	
	
	private String idcom;
	private String ref_prodstr;
	private String design_prod;
	private String code_clistr;
	private String nom_cli;
	private Float prix_unitaire;
	private LocalDate date;
	private int qte_comm;
	
	
	public Commande(String idcom, String ref_prodstr, String design_prod, String code_clistr, String nom_cli,
			Float prix_unitaire, LocalDate date, int qte_comm) {
		super();
		this.idcom = idcom;
		this.ref_prodstr = ref_prodstr;
		this.design_prod = design_prod;
		this.code_clistr = code_clistr;
		this.nom_cli = nom_cli;
		this.prix_unitaire = prix_unitaire;
		this.date = date;
		this.qte_comm = qte_comm;
	}


	@Override
	public String toString() {
		return "Commande [idcom=" + idcom + ", ref_prodstr=" + ref_prodstr + ", design_prod=" + design_prod
				+ ", code_clistr=" + code_clistr + ", nom_cli=" + nom_cli + ", prix_unitaire=" + prix_unitaire
				+ ", date=" + date + ", qte_comm=" + qte_comm + ", getIdcom()=" + getIdcom() + ", getRef_prodstr()="
				+ getRef_prodstr() + ", getDesign_prod()=" + getDesign_prod() + ", getCode_clistr()=" + getCode_clistr()
				+ ", getNom_cli()=" + getNom_cli() + ", getPrix_unitaire()=" + getPrix_unitaire() + ", getDate()="
				+ getDate() + ", getQte_comm()=" + getQte_comm() + ", getRef_prod()=" + getRef_prod()
				+ ", getCode_cli()=" + getCode_cli() + ", getDate_com()=" + getDate_com() + ", getQte_com()="
				+ getQte_com() + "]";
	}


	public String getIdcom() {
		return idcom;
	}


	public void setIdcom(String idcom) {
		this.idcom = idcom;
	}


	public String getRef_prodstr() {
		return ref_prodstr;
	}


	public void setRef_prodstr(String ref_prodstr) {
		this.ref_prodstr = ref_prodstr;
	}


	public String getDesign_prod() {
		return design_prod;
	}


	public void setDesign_prod(String design_prod) {
		this.design_prod = design_prod;
	}


	public String getCode_clistr() {
		return code_clistr;
	}


	public void setCode_clistr(String code_clistr) {
		this.code_clistr = code_clistr;
	}


	public String getNom_cli() {
		return nom_cli;
	}


	public void setNom_cli(String nom_cli) {
		this.nom_cli = nom_cli;
	}


	public Float getPrix_unitaire() {
		return prix_unitaire;
	}


	public void setPrix_unitaire(Float prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public int getQte_comm() {
		return qte_comm;
	}


	public void setQte_comm(int qte_comm) {
		this.qte_comm = qte_comm;
	}


	public Commande() {
		
	}
	
	
	public Commande(int ref_prod, int code_cli, LocalDate date_com, int qte_com) {
		super();
		this.ref_prod = ref_prod;
		this.code_cli = code_cli;
		this.date_com = date_com;
		this.qte_com = qte_com;
	}


	public int getRef_prod() {
		return ref_prod;
	}
	public void setRef_prod(int ref_prod) {
		this.ref_prod = ref_prod;
	}
	public int getCode_cli() {
		return code_cli;
	}
	public void setCode_cli(int code_cli) {
		this.code_cli = code_cli;
	}
	public LocalDate getDate_com() {
		return date_com;
	}
	public void setDate_com(LocalDate localDate) {
		this.date_com = localDate;
	}
	public int getQte_com() {
		return qte_com;
	}
	public void setQte_com(int qte_com) {
		this.qte_com = qte_com;
	}
	
	
}

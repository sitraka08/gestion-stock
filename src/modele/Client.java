package modele;

public class Client {
	
	
	private int code_cli = 1;
	private String nom_cli;
	private String code_clistr;
	
		
	
	public String getCode_clistr() {
		return code_clistr;
	}

	public void setCode_clistr(String code_clistr) {
		this.code_clistr = code_clistr;
	}

	public String getNom_cli() {
		return nom_cli;
	}
	
	public Client() {
		
	}
	
	

	public Client(int code_cli,String nom_cli) {
		super();
		this.nom_cli = nom_cli;
		this.code_cli = code_cli;
		
	}

	public Client(String code_clistr,String nom_cli) {
		super();
		this.nom_cli = nom_cli;
		this.code_clistr = code_clistr;
		
	}
	

	public void setNom_cli(String nom_cli) {
		this.nom_cli = nom_cli;
	}
	public int getCode_cli() {
		return code_cli;
	}
	public void setCode_cli(int code_cli) {
		this.code_cli = code_cli;
	}
	
	@Override
	public String toString() {
		return "Client code_cli=" + code_clistr + ", nom_cli=" + nom_cli;
	}

	
}

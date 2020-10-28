package es.caib.ProjecteBaseEJB.entity;

public class Illa {
	
	private Integer id_illa;
	private String nom;
		
	// Constructor
	public Illa() { }
	public Illa(Integer id, String nom)
	{
		this.id_illa = id;
		this.nom = nom;
	}
	
	// Mètodes get - set
	public Integer getId() { return this.id_illa;	}
	public void setId(Integer id) {	this.id_illa = id;	}
	
	public String getNom() { return this.nom; }
	public void setNom(String value) { this.nom = value; }
	
}

package es.caib.ProjecteBaseEJB.entity;

public class Municipi {
	private Integer id_municipi;
	private String nom;
		
	// Constructor
	public Municipi() { }
	public Municipi(Integer id, String nom)
	{
		this.id_municipi = id;
		this.nom = nom;
	}
	
	// Mètodes get - set
	public Integer getId() { return this.id_municipi;	}
	public void setId(Integer id) {	this.id_municipi = id;	}
	
	public String getNom() { return this.nom; }
	public void setNom(String value) { this.nom = value; }
}

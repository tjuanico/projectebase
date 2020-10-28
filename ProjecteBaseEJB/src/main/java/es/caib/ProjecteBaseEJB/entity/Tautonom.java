package es.caib.ProjecteBaseEJB.entity;

public class Tautonom {
	private Integer id_tautonom;
	private String nom;
		
	// Constructor
	public Tautonom() { }
	public Tautonom(Integer id, String nom)
	{
		this.id_tautonom = id;
		this.nom = nom;
	}
	
	// Mètodes get - set
	public Integer getId() { return this.id_tautonom;	}
	public void setId(Integer id) {	this.id_tautonom = id;	}
	
	public String getNom() { return this.nom; }
	public void setNom(String value) { this.nom = value; }
}

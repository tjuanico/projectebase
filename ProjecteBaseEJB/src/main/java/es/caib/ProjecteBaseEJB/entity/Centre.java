package es.caib.ProjecteBaseEJB.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="qsi_centre")
public class Centre {
	
	@Id
	private Integer id_centre;
	
	@Column
	private String nom;
	
	@Column
	private String dir3;
	
	@Column
	private Date data_creacio;
	
	@Column
	private String usuari;
	
	@Column
	private String actiu;
	
	@Column
	private String visible_web;

	
	// Constructor
	public Centre() { }
	public Centre(Integer id_centre, String nom, String dir3)
	{
		this.id_centre = id_centre;
		this.nom = nom;
		this.dir3 = dir3;
	}
	
	// Mètodes get - set
	public Integer getId() { return this.id_centre;	}
	public void setId(Integer id) {	this.id_centre = id;	}
	
	public String getNom() { return this.nom; }
	public void setNom(String value) { this.nom = value; }
	
	
}

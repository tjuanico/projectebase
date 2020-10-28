package es.caib.ProjecteBaseEJB.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="qsi_torn")
public class Torn {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="qsi_sequencia")
	@SequenceGenerator(name="qsi_sequencia", sequenceName = "qsi_sequencia", allocationSize=1)
	private Integer id_torn;
	
	@Column
	private String nom;
	
	@Column
	private Date data_creacio;
	
	@Column
	private String llinatge1;
	
	@Column
	private String llinatge2;
	
	@Column
	private String identificacio;
	
	@Column
	private Integer illa;
	
	@Column
	private Integer municipi;
	
	@Column
	private Integer tautonom;
	
	@Column 
	private String cadena_hash;
	
	// Constructor
	public Torn() { }
	public Torn(String nom, String llinatge1, String llinatge2, String identificacio, Integer illa, Integer municipi, Integer tautonom, String cadena_hash)
	{
		this.nom = nom;
		this.llinatge1 = llinatge1;
		this.llinatge2 = llinatge2;
		this.identificacio = identificacio;
		this.illa = illa;
		this.municipi = municipi;
		this.tautonom = tautonom;
		this.cadena_hash = cadena_hash;
		
	}
	
	// Mètodes get - set
	public Integer getId() { return this.id_torn;	}
	public void setId(Integer id) {	this.id_torn = id;	}
	
	public String getNom() { return this.nom; }
	public void setNom(String value) { this.nom = value; }

	public Date getDatacreacio() { return this.data_creacio; }
	public void setDatacreacio(Date data) { this.data_creacio = data; } 
	
	public String getLlinatge1() { return this.llinatge1; }
	public void setLlinatge1(String value) { this.llinatge1 = value; }
	
	public String getLlinatge2() { return this.llinatge2; }
	public void setLlinatge2(String value) { this.llinatge2 = value; }
	
	public String getIdentificacio() { return this.identificacio; }
	public void setIdentificacio(String value) { this.identificacio = value; }
	
	public Integer getIlla() { return this.illa; }
	public void setIlla(Integer i) { this.illa = i; }
	
	public Integer getMunicipi() { return this.municipi; }
	public void setMunicipi(Integer m) { this.municipi = m; }
	
	public Integer getTautonom() { return this.tautonom; }
	public void setTautonom(Integer ta) { this.tautonom = ta; }
	
	public String getCadena_hash() { return this.cadena_hash; }
	public void setCadena_hash(String value) { this.cadena_hash = value; }
	
	
	
}

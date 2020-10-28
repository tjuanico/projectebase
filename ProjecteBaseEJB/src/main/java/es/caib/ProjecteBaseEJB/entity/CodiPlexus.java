package es.caib.ProjecteBaseEJB.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entitat de mapeig de la taula RDV_DOWNLOADED_ACCESS_CODE
 * @author [u97091] Toni Juanico Soler
 * data: 26/08/2020
 */
@Entity
@Table(name="RDV_DOWNLOADED_ACCESS_CODE")
public class CodiPlexus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rdv_sequencia")
	@SequenceGenerator(name="RDV_DOWNLOADED_ACCESS_CODE_ID", sequenceName = "rdv_sequencia", allocationSize=1)
	private Integer id;
	
	@Column
	private String access_code;
	
	@Column
	private Date created_at;
	
	@Column
	private Date activated_at;
	
	
	// Constructors
	public CodiPlexus() { 
		
	}
	
	public CodiPlexus(Integer id, String codi, Date data_creacio)
	{
		this.id = id;
		this.access_code = codi;
		this.created_at = data_creacio;
	}
	
	// Mètodes set i get
	public Integer getId() { return id;	}
	public void setId(Integer id) {	this.id = id;	}
	
	public String getCodi() { return this.access_code; }
	public void setCodi(String value) { this.access_code = value; }
	
	public Date getDatacreacio() { return this.created_at; }
	public void setDatacreacio(Date data) { this.created_at = data; }
	
	public Date getDataassignacio() { return this.activated_at; }
	public void setDataassignacio(Date data) { this.activated_at = data; }
	
}

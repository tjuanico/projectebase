package es.caib.ProjecteWeb.controller;


import java.util.Date;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import es.caib.ProjecteBaseEJB.entity.Illa;
import es.caib.ProjecteBaseEJB.entity.Municipi;
import es.caib.ProjecteBaseEJB.entity.Tautonom;
import es.caib.ProjecteBaseEJB.entity.Torn;
import es.caib.ProjecteBaseEJB.interfaces.TornServiceInterface;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;

/**
 * Controlador de la vista reservatorn
 * @author [u97091] Toni Juanico Soler
 * data: 05/06/2020
 * eines RAD - COVI19 - benchmark
 */

@ManagedBean
@ViewScoped
public class reservatornController {
	
	// EJB's
	@EJB
	TornServiceInterface TornServ;
	
	// Private properties
	private final static Logger LOGGER = Logger.getLogger(reservatornController.class);
	
	private String message = new String("");
	
	private ArrayList<Illa> llista_illes;
	private ArrayList<Municipi> llista_municipis;
	private ArrayList<Tautonom> llista_tautonom;
	
	private String tornId;
	private String identificacio;
	private String nom;
	private String llinatge1;
	private String llinatge2;
	private Integer illa = 0;
	private Integer municipi = 0;
	private Integer tautonom = 0;
	
	private String hostid;
	private String aes;
	private String hash; // hash rebut per paràmetre
	private String captcha;
	
	// Getters & Setters
	
	public void setTornid(String tornId) { this.tornId = tornId; }
	public String getTornid() { return this.tornId; }
	
	public void setIdentificacio(String i) { this.identificacio = i; }
	public String getIdentificacio() { return this.identificacio; }
	
	public String getNom() { return this.nom; }
	public void setNom(String n) { this.nom = n; }
	
	public String getLlinatge1() { return this.llinatge1; }
	public void setLlinatge1(String l) { this.llinatge1 = l; }
	
	public String getLlinatge2() { return this.llinatge2; }
	public void setLlinatge2(String l) { this.llinatge2 = l; }
	
	public Integer getIlla() { return this.illa; }
	public void setIlla(Integer i) { this.illa = i; }
	
	public Integer getMunicipi() { return this.municipi; }
	public void setMunicipi(Integer m) { this.municipi = m; }
	
	public Integer getTautonom() { return this.tautonom; }
	public void setTautonom(Integer ta) { this.tautonom = ta; }
	
	public String getHostid() { return this.hostid; }
	public String getHash() { return this.hash; }
	public String getAes() { return this.aes; }
	public String getCaptcha() { return this.captcha; }
	
	
	// Methods
	@PostConstruct
	public void init() {
				
		LOGGER.info("Proxy a reservatornController ");
		
		try {
			InetAddress addr = InetAddress.getLocalHost();
			this.hostid = addr.getHostName();
			this.aes = calculaAES();
			
		} catch (UnknownHostException e) {
			LOGGER.info("Error: " + e.getStackTrace());
		}
		
		String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		String param2 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hash");
						
		if (param!=null) {
			LOGGER.info("reservatornController amb id= "+param + " i hash=" + param2);
			this.tornId = param;
			this.hash = param2;
			this.getTornInfo(this.tornId);
		}
	
	}
	
	public ArrayList<Municipi> getLlista_Municipis() {
		 
		    ArrayList<Municipi> llista_Municipis = new ArrayList<Municipi>();
		 
			LOGGER.info("Obtenim llista de municipis");
			try {
							
				Municipi m1 = new Municipi(1, "Alaró");
				Municipi m2 = new Municipi(2, "Palma");
				Municipi m3 = new Municipi(3, "Consell");
				Municipi m4 = new Municipi(4, "Ciutadella");
				
				llista_Municipis.add(m1);
				llista_Municipis.add(m2);
				llista_Municipis.add(m3);
				llista_Municipis.add(m4);
				
			}catch (Exception e) {
				LOGGER.info("Error_+ " + e.toString());
				this.message = this.message + " -- " + e.toString();
			}
			
		return llista_Municipis; 
	}
	 
	public ArrayList<Illa> getLlista_Illes() {
		 
	    ArrayList<Illa> llista_Illes = new ArrayList<Illa>();
	 
		LOGGER.info("Obtenim llista illes");
		try {
						
			Illa i1 = new Illa(1, "Mallorca");
			Illa i2 = new Illa(2, "Menorca");
			Illa i3 = new Illa(3, "Eivissa");
			Illa i4 = new Illa(4, "Formentera");
					
			llista_Illes.add(i1);
			llista_Illes.add(i2);
			llista_Illes.add(i3);
			llista_Illes.add(i4);
			
		}catch (Exception e) {
			LOGGER.info("Error_+ " + e.toString());
			this.message = this.message + " -- " + e.toString();
		}
		
	return llista_Illes; 
	}
	
	public ArrayList<Tautonom> getLlista_Tautonom() {
		 
	    ArrayList<Tautonom> llista_tautonom = new ArrayList<Tautonom>();
	 
		LOGGER.info("Obtenim llista autonoms");
		try {
						
			Tautonom ta1 = new Tautonom(1, "Llarga durada");
			Tautonom ta2 = new Tautonom(2, "Curta durada");
								
			llista_tautonom.add(ta1);
			llista_tautonom.add(ta2);
		
		}catch (Exception e) {
			LOGGER.info("Error_+ " + e.toString());
			this.message = this.message + " -- " + e.toString();
		}
		
	return llista_tautonom; 
	}
	
	public void addtorn()
	{
		
		LOGGER.info("addTorn " + this.captcha);
		
		try
		{
			LOGGER.info("EJB lookup "+ TornServ);	
				
			HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			LOGGER.info("dins addTorn " + origRequest);	
			
				
			// Construim el torn
			Torn t = new Torn();
			Date dataCreacio = new Date();
			t.setIdentificacio(this.identificacio);
			t.setDatacreacio(dataCreacio);
			t.setNom(this.nom);
			t.setLlinatge1(this.llinatge1);
			t.setLlinatge2(this.llinatge2);
			t.setMunicipi(this.municipi);
			t.setIlla(this.illa);
			t.setTautonom(this.tautonom);
			t.setCadena_hash(this.getHash());
			
			String cadena = new String("covid19pass$2" + this.nom + "|" + this.llinatge1 + "|" + this.llinatge2 + "|" + this.identificacio + "|" + dataCreacio.toString());
			t.setCadena_hash(this.getHash(cadena));
			
			TornServ.addTorn(t); // Cridem l'EJB
					 
			if (TornServ.getResultat()==true)
			{
				LOGGER.info("Reserva de torn realitzada correctament: " + t.getId());
				//FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva de torn realitzada correctament", "Reserva de torn realitzada correctament " + t.getId()));
				FacesContext.getCurrentInstance().getExternalContext().redirect(origRequest.getContextPath()  + "/reservatornOk.xhtml?id=" + t.getId() + "&hash=" + t.getCadena_hash());	
			}
			
			else
			{				
				LOGGER.info("error obtingut: "+ TornServ.getError());
				FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error establint torn",  TornServ.getError()));
			}
			
		} 
		catch (Exception ex) {	
			LOGGER.info("Error: " + ex.toString());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error desant la reserva de torn", ex.toString()));
		}
	}
	
	public void getTornInfo(String id)
	{
		//
		LOGGER.info("getTornInfo " + id);
		
		try
		{
			LOGGER.info("EJB lookup "+ TornServ);	
				
			Torn t = TornServ.getTorn(Integer.parseInt(id));
			this.setIdentificacio(t.getIdentificacio());
			this.setNom(t.getNom());
			this.setLlinatge1(t.getLlinatge1());
			this.setLlinatge2(t.getLlinatge2());
			this.setIlla(t.getIlla());
			this.setMunicipi(t.getMunicipi());
			this.setTautonom(t.getTautonom());
			
		}
		catch (Exception ex) {
			LOGGER.info("Error: " + ex.toString());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error obtenint la reserva de torn", ex.toString()));
		}
	}
	
	public String calculaAES() {
		KeyGenerator keyGen;
		String key = new String("");
		try {
			keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256);
			key = Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return key;
	}
		
	private String getHash(String input) {
		String resultat = new String("");
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(encodedhash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resultat;
		}
	}
	
	private static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
	
}

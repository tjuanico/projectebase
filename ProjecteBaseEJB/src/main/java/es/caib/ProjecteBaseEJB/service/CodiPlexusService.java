package es.caib.ProjecteBaseEJB.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import es.caib.ProjecteBaseEJB.entity.CodiPlexus;
import es.caib.ProjecteBaseEJB.interfaces.CodiPlexusServiceInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.Proxy;
import java.net.InetSocketAddress;
import java.net.ProxySelector;

/**
 * Servei (EJB) per a l'entitat Codi
 * @author [u97091] Antoni Juanico soler
 */

@Stateless
@RolesAllowed({"RDV_ADMIN", "RDV_USUARI"}) 
public class CodiPlexusService implements CodiPlexusServiceInterface {

		
	private final static Logger LOGGER = Logger.getLogger(CodiPlexusService.class);
	@PersistenceContext(unitName="radarcovidDS")
	EntityManager em;
	
	private String strError = new String("");
	private String strMessage = new String("");
	private boolean resultat;
	
	@PostConstruct
	public void init() {
		LOGGER.info("Proxy a entityManager: "+this.em);
	}
	
	public boolean getResultat() { return this.resultat; }
	public String getError() { return this.strError; }
	public String getMessage() { return this.strMessage; }
	
	
	// Obte el numero de codis disponibles
	@Override
	public int getNumDisponibles() {
		LOGGER.info("in getNumDisponibles, estat entity manager: " + em.toString());
		
		String strQuery = "select count(*) from rdv_downloaded_access_code where activated_at is null";
		        
        try {
        	
        	Query myQuery = em.createNativeQuery(strQuery);
        	BigDecimal num = (BigDecimal) myQuery.getSingleResult();
        	
        	LOGGER.info("Num disponibles: " + num);
		
        	return num.intValue(); 
        		
        }
		catch (Exception ex)
		{
			LOGGER.error(ex);
			this.resultat = false;
			this.strError = ex.toString(); 
			return 0;
		}
	}
		
	// Obte el numero de codis servits o proporcionats
	@Override
	public int getNumServits()
	{
		LOGGER.info("in getNumServits, estat entity manager: " + em.toString());
			
	   String strQuery = "select count(*) from rdv_downloaded_access_code where activated_at is not null";
	        
	        try {
	        	
	        	Query myQuery = em.createNativeQuery(strQuery);
	        	BigDecimal num = (BigDecimal) myQuery.getSingleResult();
	        	
	        	LOGGER.info("Num servits: " + num);
			
	        	return num.intValue(); 
	        		
	        }
			catch (Exception ex)
			{
				LOGGER.error(ex);
				this.resultat = false;
				this.strError = ex.toString(); 
				return 0;
			}
	}
	
	// Obte la data del darrer codi servit
	@Override
	public Date getDataDarrerServit() {
		
		LOGGER.info("in getDataDarrerServit, estat entity manager: " + em.toString());
		
		String strQuery = "select max(activated_at) from rdv_downloaded_access_code where activated_at is not null";
		        
        try {
        	
        	Query myQuery = em.createNativeQuery(strQuery);
        	Date data = (Date) myQuery.getSingleResult();
        	
        	LOGGER.info("data darrer servit: " + data.toString());
		
        	return data; 
        		
        }
		catch (Exception ex)
		{
			LOGGER.error(ex);
			this.resultat = false;
			this.strError = ex.toString(); 
			return null;
		}
	}
	
	// Obte la data del darrer lot baixat
	@Override
	public Date getDataDarrerLotBaixat() {
		LOGGER.info("in getDataDarrerLotBaixat, estat entity manager: " + em.toString());
		
		String strQuery = "select max(created_at) from rdv_downloaded_access_code";
		        
        try {
        	
        	Query myQuery = em.createNativeQuery(strQuery);
        	Date data = (Date) myQuery.getSingleResult();
        	
        	LOGGER.info("data darrer servit: " + data.toString());
		
        	return data; 
        		
        }
		catch (Exception ex)
		{
			LOGGER.error(ex);
			this.resultat = false;
			this.strError = ex.toString(); 
			return null;
		}
	}
	
	// Obte el seguent codi i el marca com a proporcionat
	@Override
	public String getNextCodi() {
		LOGGER.info("in getNextCodi, estat entity manager: " + em.toString());
		
        String strQuery = "select id,access_code from rdv_downloaded_access_code where activated_at is null and rownum=1 for update";
        
        try {
        	
        	Query myQuery = em.createNativeQuery(strQuery);

        	List<Object[]> results = myQuery.getResultList();
		 
        	BigDecimal id_unico = new BigDecimal(0);
        	String codigo = new String("");
        	for(Object[] ret : results) {
        	     id_unico = (BigDecimal) ret[0];
		         codigo = (String) ret[1];
        	}
        	
        	LOGGER.info("Obtingut codi: " + codigo + ", obtingut val: " + id_unico);
		
        	if (id_unico != null) 
        	{
			
        		strQuery = "update rdv_downloaded_access_code set activated_at=sysdate where id=" + id_unico;
        		LOGGER.info("query update: " + strQuery);
        		myQuery = em.createNativeQuery(strQuery);
        		myQuery.executeUpdate();
        		this.resultat = true;
        		return codigo;
        	}
        	else
        	{
        		LOGGER.info("No existeix codi");
        		this.resultat = false;
        		this.strError= "No existeix codi disponible";
        		return "";
        	}
        }
		catch (Exception ex)
		{
			LOGGER.error(ex);
			this.resultat = false;
			this.strError = ex.toString(); 
			return "";
		}
	}
	
	
	 private  String encodeValue(String value) {
	        try {
	            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
	        } catch (UnsupportedEncodingException ex) {
	            throw new RuntimeException(ex.getCause());
	        }
	    }
	 
	@Override
	public void enviarSms(String telefon, String codigo)
	{
		
		String user = new String("jorge.sanchezferreiro@plexus.es");
		String password = new String("Tecnocrata-666");
		String resultat = new String("");
		
		try {
		  String encode_message = new String("");
		  encode_message = this.encodeValue("RADARCOV GOIB, el seu codi app: " + codigo);
          URL url = new URL("http://bitsms.fundaciobit.org/bitsms/MensajeEnviar?remitente=APPCOVID&numero=" + telefon + "&texto="+encode_message+"&idCampanya=APPCOVID");
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("GET");
          
          String auth = user + ":" + password;
      	  byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
      	  
      	  String authHeaderValue = "Basic " + new String(encodedAuth);
      	  conn.setRequestProperty("Authorization", authHeaderValue);
      	  
	      conn.setRequestProperty("Connection", "close");
	      
	      if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
		  }
	                    
		  BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				resultat = output;				
			}

			conn.disconnect();
			this.resultat = true;

		  } catch (MalformedURLException e) {
			this.resultat = false;
			this.strError = e.getMessage();

		  } catch (IOException e) {
			this.resultat = false;
			this.strError = e.getMessage();
		  }
		
	}
	
	@Override
	public void bulkCodi() {
		// Llançar Tx contra Ministeri
			try {
			  System.setProperty("javax.net.ssl.trustStore","/app/caib/radarcov/prueba.jks");
			  System.setProperty("javax.net.ssl.trustStorePassword","changeit");
			  //TokenService ts = new TokenService();
			  String tokenJWT 	= new String("");
			  //ts.generaToken();
			  //if (ts.resultat)
			  if (true) // xapu Toni Juanico
			  {
				  //LOGGER.info("in bulkCodi, tokenJWT ok" + ts.strToken);
				  //tokenJWT 	= ts.strToken;
				  
				  Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 3121));
		          URL url = new URL("https://radarcovidpre.covid19.gob.es/verification/generate?n=5");
		          //URL url = new URL("https://ddu362ufohvxt.cloudfront.net/verification/generate?n=5");
		          HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
		          
		          conn.setRequestMethod("GET");
		          conn.setRequestProperty("N", "5");
		          conn.setRequestProperty("Content-Type", "application/json");
		          conn.setRequestProperty("Accept", "application/json");
		          conn.setRequestProperty("x-sedia-authentication",tokenJWT);
			      conn.setRequestProperty("Connection", "close");
			      
			      if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : "
								+ conn.getResponseCode());
				  }
			                    
				  BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

					String output;
					while ((output = br.readLine()) != null) {
						this.strMessage = output;				
					}

					conn.disconnect();
					this.resultat = true;
					// si es ok, anam fent insert a la base de dades
			  }
			else
			{
				LOGGER.info("in bulkCodi, tokenJWT error:  ");		
			}
		} catch (MalformedURLException e) {
			this.resultat = false;
			this.strError = e.getMessage();

		} catch (IOException e) {
	        this.resultat = false;
		    this.strError = e.getMessage();
		}
	}				  
}

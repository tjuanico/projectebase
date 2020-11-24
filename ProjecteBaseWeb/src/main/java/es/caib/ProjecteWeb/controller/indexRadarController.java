package es.caib.ProjecteWeb.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import es.caib.ProjecteBaseEJB.interfaces.CodiPlexusServiceInterface;


/**
 * Controlador de la vista Codis
 * @author [u97091] Toni Juanico Soler
 * data: 26/08/2020
 */

@ManagedBean
@ViewScoped
public class indexRadarController {

	// EJB's
	@EJB
	CodiPlexusServiceInterface CodiServ;
		
	// Private properties
	private final static Logger LOGGER = Logger.getLogger(indexRadarController.class);
		
	private String codi;
		
	private String message = new String("");
	private String telefon = new String("");
		
	private String num_servits = new String("");
	private String num_disponibles = new String("");
	private String data_servit = new String("");
	private String data_disponible = new String("");
	
	// Getters & Setters
	public void setMessage(String m) { this.message = m; }
	public String getMessage() { return this.message; }
		
	public String getCodi() { return this.codi; }
	public void setCodi(String c) { this.codi = c;}
	
	public String getTelefon() { return this.telefon; }
	public void setTelefon(String c) { this.telefon = c;}
	
	public String getNumservits() { return this.num_servits; }
	public void setNumservits(String ns) { this.num_servits = ns;}
	
	public String getDataservit() { return this.data_servit; }
	public void setDataservit(String ds) { this.data_servit = ds;}
	
	public String getNumdisponibles() { return this.num_disponibles; }
	public void setNumdisponibles(String nd) { this.num_disponibles = nd;}
	
	public String getDatadisponible() { return this.data_disponible; }
	public void setDatadisponible(String dd) { this.data_disponible = dd;}
	
	// Methods
	@PostConstruct
	public void init() {
			HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String usuari = origRequest.getUserPrincipal().getName();
			if (origRequest.isUserInRole("RDV_ADMIN"))
			{
				
				LOGGER.info("user logged " + usuari + " amb role RDV_ADMIN ");
				obteEstadistiques();
			}
			else
			{
				LOGGER.info("user logged " + usuari);
			}
	}
	
	public void obteEstadistiques()
	{
		Integer ns = 0;
		Integer nd = 0;
		Date ds = new Date();
		Date dd = new Date();
		
		String pattern = "dd/MM/yyyy HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		try
		{
			// Num. servits
			ns = CodiServ.getNumServits();
			this.num_servits = ns.toString();
			
			// Num. disponibles
			nd = CodiServ.getNumDisponibles();
			this.num_disponibles = nd.toString();
			
			// Data darrer servit
			ds = CodiServ.getDataDarrerServit();
			this.data_servit = simpleDateFormat.format(ds);
			
			// Data darrer lot baixat
			dd = CodiServ.getDataDarrerLotBaixat();
			this.data_disponible = simpleDateFormat.format(dd);
			
		}
		catch (Exception ex)
		{
			this.num_servits = new String("");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error obtenint info. estadística", ex.toString()));
		}
		
	}
	public void nouPacient()
	{
		LOGGER.info("nouPacient");
		this.codi = "";
		this.telefon = "";
	}
	public void enviarSms()
	{
		try
		{
			if (this.telefon != "" && this.codi != "")
			{
				CodiServ.enviarSms(this.telefon, this.codi);
				
				if (!CodiServ.getResultat())
				{
					LOGGER.info("error enviant sms: " + CodiServ.getError());
					this.message = CodiServ.getError();
					
				}	
				else
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SMS Enviat correctament", "SMS Enviat correctament"));				
				}
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ha d'omplir el telefon","Telefon necessari"));
			}
			
		}
		catch (Exception ex)
		{
			LOGGER.info("Error: " + ex.toString());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error enviant sms", ex.toString()));
		}
		
	}
	
	public void keepSessionAlive()
	{
		LOGGER.info("keepSessionAlive().");
	}
	
	public void logout()
	{
		LOGGER.info("logout");
		
		try {
			HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			origRequest.getSession().invalidate();
			origRequest.logout();
			FacesContext.getCurrentInstance().getExternalContext().redirect(origRequest.getContextPath()  + "/radarcovback/index.xhtml");
			//String logout_page = new String("http://loginpre.caib.es/auth/realms/goib-default/protocol/openid-connect/logout?redirect_uri=/radarcovback/index.xhtml");
			//FacesContext.getCurrentInstance().getExternalContext().redirect(logout_page);
		} catch (Exception ex) {
			LOGGER.info("Error tancant sessió: " + ex.toString());
		}
		
	}
	
	public void obtenirCodi()
	{
					
		try
		{
				
			String codigo = CodiServ.getNextCodi(); // Cridem l'EJB
			
			if (!CodiServ.getResultat())
			{
				LOGGER.info("error obtingut: " + CodiServ.getError());
				this.message = CodiServ.getError();
			}
			else
			{
				
				if (!codigo.isEmpty())
					this.codi = codigo;
				else
				{
					this.codi = "";
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No hi ha codis disponibles. Per favor, esperi uns minuts.", "Per favor, esperi uns minuts"));
				}
			}
            
			
		} 
		catch (Exception ex) {
				
			LOGGER.info("Error: " + ex.toString());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error obtenint codis del Ministeri", ex.toString()));
		}
	}
}

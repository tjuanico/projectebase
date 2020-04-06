package es.caib.ProjecteWeb.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import es.caib.ProjecteBaseEJB.interfaces.ControladorInterface;
import es.caib.ProjecteBaseEJB.interfaces.CentreServiceInterface;

@ManagedBean
@ViewScoped
public class Controlador {

	@EJB
	private ControladorInterface myController;
	
	@EJB
	private CentreServiceInterface myCentreService;
	
	
	private final static Logger LOGGER = Logger.getLogger(Controlador.class);
	private String message = new String("Pep");
	
	@PostConstruct
	public void init() {
		LOGGER.info("Proxy a IndexController " + myController);
	}
	
	public void setMissatge(String m) { this.message = m; }
	public String getMissatge() 
	{ 
		try {
			Integer resultat = myController.suma(2, 3);
			message =  message + resultat.toString(); 	
		}
		catch (Exception ex) {
			return ex.toString();
		} 
		
		try {
			String resultat = myCentreService.getCentre();
			message = message + "Prova de connexio a bbdd --> " + resultat.toString(); 	
		}
		catch (Exception ex) {
			return ex.toString();
		}
		
		return message;
		
	}
	
}

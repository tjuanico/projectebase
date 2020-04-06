package es.caib.ProjecteBaseEJB.interfaces;

import javax.ejb.Local;

/**
 * Interfície del servei (EJB) 
 * @author [u97091] Toni Juanico Soler
 * data: 23/03/2020
 */

@Local
public interface CentreServiceInterface {
	
	public String getCentre();
	
}
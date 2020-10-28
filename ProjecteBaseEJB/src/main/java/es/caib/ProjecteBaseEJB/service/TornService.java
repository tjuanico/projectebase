package es.caib.ProjecteBaseEJB.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import es.caib.ProjecteBaseEJB.entity.Torn;
import es.caib.ProjecteBaseEJB.interfaces.TornServiceInterface;

import org.apache.log4j.Logger;

@Stateless
public class TornService implements TornServiceInterface  {
	
	private final static Logger LOGGER = Logger.getLogger(TornService.class);
	
	@PersistenceContext(unitName="ProjecteBaseDS")
	EntityManager em;
	
	private String strError = new String("");
	private boolean resultat;
	
	@Override
	public void addTorn(Torn t) {
		
        LOGGER.info("in addTorn, estat entity manager: " + em.toString());
		
		try
		{
			em.persist(t);
			LOGGER.info("Inserit torn");
			this.resultat = true;	
		}
		catch (Exception ex)
		{
			LOGGER.error(ex);
			this.strError = ex.toString();
			this.resultat = false;
		}
	}
	
	
	@Override
	public Torn getTorn(Integer idTorn) {
		try
		{
			LOGGER.info("in getTorn, estat entity manager: " + em.toString());
			Torn t = em.find(Torn.class, idTorn);
			this.resultat = true;
			return t;
		}
		catch (Exception ex)
		{
			LOGGER.error(ex);
			this.resultat = false;
			this.strError = ex.toString();
			return null;
		}
		
	}
	@Override
	public boolean getResultat() {return this.resultat;	}

	@Override
	public String getError() { return this.strError; }

}

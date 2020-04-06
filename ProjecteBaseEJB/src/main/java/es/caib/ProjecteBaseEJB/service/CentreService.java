package es.caib.ProjecteBaseEJB.service;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.caib.ProjecteBaseEJB.entity.Centre;
import es.caib.ProjecteBaseEJB.interfaces.CentreServiceInterface;

/**
 * Servei (EJB) per a l'entitat Illa
 * @author [u97091] Antoni Juanico soler
 * data 23/03/2020
 */

@Stateless
public class CentreService implements CentreServiceInterface {

	@PersistenceContext(unitName="ProjecteBaseDS")
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public String getCentre() {
		
		ArrayList<Centre> l = new ArrayList<Centre>();
				
		String queryString = new String("select c from Centre c");
		String resultat = new String("");
		try
		{
			l = (ArrayList<Centre>) em.createQuery(queryString).getResultList();
			if (!l.isEmpty())
			{
				resultat = "id: " + l.get(0).getId() + " nom: " + l.get(0).getNom();
			}
			else
				resultat = "Sense resultats a bbdd";
			
		}
		catch (Exception ex)
		{
			resultat = ex.toString();
		}
		return resultat;
	}

}

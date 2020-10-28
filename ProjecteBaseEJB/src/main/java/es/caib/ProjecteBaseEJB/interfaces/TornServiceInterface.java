package es.caib.ProjecteBaseEJB.interfaces;

import javax.ejb.Local;

import es.caib.ProjecteBaseEJB.entity.Torn;

@Local
public interface TornServiceInterface {
	public void addTorn(Torn t);
	public Torn getTorn(Integer idTorn);
	public boolean getResultat();
	public String getError();
}

package es.caib.ProjecteBaseEJB.interfaces;

import javax.ejb.Local;

@Local
public interface GetOKDInterface {
	public String getPods();
	public boolean getResultat();
	public String getError();
}

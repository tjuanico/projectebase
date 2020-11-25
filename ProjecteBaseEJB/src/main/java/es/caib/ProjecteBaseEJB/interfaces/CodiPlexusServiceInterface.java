package es.caib.ProjecteBaseEJB.interfaces;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Local;
import es.caib.ProjecteBaseEJB.entity.CodiPlexus;

/**
 * Interfície del servei (EJB) CodiService
 * @author [u97091] Toni Juanico Soler
 * data: 25/08/2020
 */

@Local
public interface CodiPlexusServiceInterface {
	public void bulkCodi();
	public String getNextCodi();
	public void enviarSms(String telefon, String codigo);
	public int getNumServits();
	public int getNumServitsByDate(String strData); // data: dd/MM/yyyy HH:mm
	public int getNumDisponibles();
	public Date getDataDarrerServit();
	public Date getDataDarrerLotBaixat();
	public boolean getResultat();
	public String getError();
	public String getMessage();
}

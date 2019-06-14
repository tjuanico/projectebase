package es.caib.ProjecteBaseEJB.interfaces;

import javax.ejb.Local;

@Local
public interface ControladorInterface {
	public int suma(int a, int b);
}

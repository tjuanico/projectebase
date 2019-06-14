package es.caib.ProjecteBaseEJB.service;

import javax.ejb.Local;
import javax.ejb.Stateless;

import es.caib.ProjecteBaseEJB.interfaces.ControladorInterface;

@Stateless(name="ControladorBeanService")
@Local
public class ControladorBeanService implements ControladorInterface {

	@Override
	public int suma(int a, int b) {
		return a + b;
	}

}

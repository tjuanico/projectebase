package es.caib.ProjecteBaseEJB.service;

import javax.ejb.Stateful;

import es.caib.ProjecteBaseEJB.interfaces.ControladorInterface;

@Stateful
public class ControladorBeanService implements ControladorInterface {

	@Override
	public int suma(int a, int b) {
		return a + b;
	}

}

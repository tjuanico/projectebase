package es.caib.ProjecteBaseEJB.service;

import javax.ejb.Stateful;

import org.apache.log4j.Logger;

import es.caib.ProjecteBaseEJB.interfaces.ControladorInterface;


@Stateful
public class ControladorBeanService implements ControladorInterface {

	private final static Logger LOGGER = Logger.getLogger(ControladorBeanService.class);
	@Override
	public int suma(int a, int b) {
		LOGGER.info("Suma!");
		return a + b;
	}

}

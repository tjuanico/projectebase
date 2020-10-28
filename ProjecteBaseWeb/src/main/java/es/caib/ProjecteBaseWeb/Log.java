package es.caib.ProjecteBaseWeb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import es.caib.ProjecteBaseEJB.interfaces.ControladorInterface;
import es.caib.ProjecteBaseEJB.interfaces.GetOKDInterface;
import es.caib.ProjecteWeb.controller.Controlador;

public class Log extends HttpServlet {

	/**
	 * Classe per tornar els logs d'una aplicació dins els cluster openshift
	 * Autor: Toni Juanico
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = Logger.getLogger(Controlador.class);
	
	@EJB
	private GetOKDInterface myOKDInterface;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		LOGGER.info("Entrada a doGet del Servlet Log amb parametre: "+ request.getParameter("app"));
			
	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	 
	    try {
	    	out.println("<!DOCTYPE html>");
	        out.println("<html><head>");
	        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
	        out.println("<title>Hello, World</title></head>");
	        out.println("<body>");
	        out.println("<h1>LLista de pods corrent a OKD</h1>");
	        out.println(this.myOKDInterface.getPods());
	        out.println("<h1>Llistat de logs de l'aplicació</h1>");  // says Hello
	      
	        out.println(this.obtenirDirectorisApp(request.getParameter("app")));
	        out.println("</body>");
	        out.println("</html>");
	    } catch (Exception ex)
	    {
	    	out.println(ex.toString());
	    }
	    finally {
	         out.close();  // Always close the output writer
	    }
	}
	
	private String obtenirDirectorisApp(String app)
	{
		 String root= new String("/app/caib/visor/" + app);
		 LOGGER.info("obtinguda ruta a consultar: "+ root);
		 String resultat = new String("");
		 
		 java.io.File file;
		 java.io.File dir = new java.io.File(root);

		 LOGGER.info("pass 1");
		 
		 String[] list = dir.list();

		 if (list.length > 0) {
			 LOGGER.info("pass 2");
			 for (int i = 0; i < list.length; i++) {
				 LOGGER.info("pass 3");
				 file = new java.io.File(root + list[i]);
				 LOGGER.info("pass 4");
				 if (file!= null && !file.isDirectory() ) {
					 LOGGER.info("pass 5");
					 resultat = resultat + "Fitxer: " + file.getName() + "   " + file.getTotalSpace() + "<br/>";
					 LOGGER.info("pass 6");
				 }
			 }
		 }
		 
		 return resultat;
	}
}

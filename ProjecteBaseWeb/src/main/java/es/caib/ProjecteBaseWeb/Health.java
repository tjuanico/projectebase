package es.caib.ProjecteBaseWeb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import es.caib.ProjecteWeb.controller.Controlador;

public class Health extends HttpServlet{

	/**
	 * Pàgina de health per Openshift - Kubernetes
	 */
	
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Controlador.class);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	 
	    try {
	    	out.println("<!DOCTYPE html>");
	        out.println("<html><head>");
	        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
	        out.println("<title>Hello, Health checker</title></head>");
	        out.println("<body>");
	        out.println("<h1>Hello, Health checker!</h1>");  // says Hello
	        // Echo client's request information
	        out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
	        out.println("<p>Protocol: " + request.getProtocol() + "</p>");
	        out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
	        out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
	        out.println("</body>");
	        out.println("</html>");
	        Date now = new Date();
	        
	        //LOGGER.info("Pàgina health " + now.toString());
	    } finally {
	         out.close();  // Always close the output writer
	    }
	}
	

}

package es.caib.ProjecteBaseWeb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import es.caib.ProjecteWeb.controller.Controlador;

public class Dead extends HttpServlet{

	/**
	 * Classe per comprovar les limitacions del java heap space
	 * Autor: Toni Juanico
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = Logger.getLogger(Controlador.class);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		LOGGER.info("Entrada a doGet del Servlet Dead amb parametre: "+ request.getParameter("x"));
		// Cridam a l'accio recursivo que desbordara la memoria
		accio2(Integer.parseInt(request.getParameter("x")));
		
	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	 
	    try {
	    	out.println("<!DOCTYPE html>");
	        out.println("<html><head>");
	        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
	        out.println("<title>Hello, World</title></head>");
	        out.println("<body>");
	        out.println("<h1>Hello, world!</h1>");  // says Hello
	        // Echo client's request information
	        out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
	        out.println("<p>Protocol: " + request.getProtocol() + "</p>");
	        out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
	        out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
	        
	        out.println("</body>");
	        out.println("</html>");
	    } finally {
	         out.close();  // Always close the output writer
	    }
	}
	
	private void accio(int x)
	{
		ArrayList<byte[]> data = new ArrayList<>();
		if (x > 0)
		{
			LOGGER.info("Reserva: "+ x);
			data.add(new byte[1048576]);  // 1MB
			accio(x-1);
		}
		else
		{
			LOGGER.info("Fi de la recursivitat: "+ x);
		}
	}
	
	private void accio2(int x)
	{
		ArrayList<byte[]> data = new ArrayList<>();
		for (int i=0; i < x; i++) {
			LOGGER.info("Reserva: " + i);
			data.add(new byte[1048576]); // 1 MB
		}
	}
}

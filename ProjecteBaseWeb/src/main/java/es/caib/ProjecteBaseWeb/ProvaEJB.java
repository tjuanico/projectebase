package es.caib.ProjecteBaseWeb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.caib.ProjecteBaseEJB.interfaces.ControladorInterface;


public class ProvaEJB extends HttpServlet {

	/**
	 Author: Toni Juanico* 
	 Comments: Projecte base amb la finalitat de provar EJB Injection*/
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ControladorInterface myController;
	
	@PostConstruct
	public void init() {
		System.out.print("Entrada a init: " + myController);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		System.out.print("Entrada a doGet: " + myController);
		int resultat = myController.suma(3, 5); // Cridem EJB
		
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
	        // Generate a random number upon each request
	        out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
	        out.println("<p>Resultat crida EJB: suma(3,5)" + resultat + "</p>");
	        out.println("</body>");
	        out.println("</html>");
	    } finally {
	         out.close();  // Always close the output writer
	    }
	}

}

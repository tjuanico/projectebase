package es.caib.ProjecteBaseWeb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import es.caib.ProjecteBaseEJB.interfaces.ControladorInterface;


public class ProvaEJB extends HttpServlet {

	/**
	 Author: Toni Juanico* 
	 Comments: Projecte base amb la finalitat de provar EJB Injection*/
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(ProvaEJB.class);
	
	@EJB
	private ControladorInterface myController;
	
	@PostConstruct
	public void init() {
		LOGGER.info("Entrada a init: " + myController);
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		LOGGER.info("Entrada a doGet: " + myController);
		int resultat = myController.suma(3, 5); // Cridem EJB
		
	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	 
	    Properties properties = new Properties();
	    			
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
	        // Get server info
	        InetAddress addr = InetAddress.getLocalHost();
			out.println("<p>Server hostname: " + addr.getHostName() + "</p>");
			
			// Llegim d'un arxiu de propierties
			//properties.load(ProvaEJB.class.getClassLoader().getResourceAsStream("/projectebase.properties"));
			//URL pro = Thread.currentThread().getContextClassLoader().getResource("projectebase.properties");
			//out.println("url: "+ pro.toString());
			//properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("projectebase.properties"));
						
			String fileName = System.getProperty("jboss.server.config.dir") + "/projectebase.properties";
			try(FileInputStream fis = new FileInputStream(fileName)) {
			  properties.load(fis);
			}
			out.println("Arxiu de properties (projectebase.properties) -> property: url1: " + properties.getProperty("url1"));
			
			// Escrivim un fitxer
			String filename = properties.getProperty("path1") + "/" + addr.getHostName() + "_file1.txt";
			String opt = this.createfile(filename);
			out.println("Escrivim fitxer: " + filename + " amb resultat: " + opt);
	        // Generate a random number upon each request
	        out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
	        out.println("<p>Resultat crida EJB: suma(3,5)" + resultat + "</p>");
	        out.println("</body>");
	        out.println("</html>");
	    }
	      catch (Exception ex)	        {
	    	  out.println("<!DOCTYPE html>");
	    	  out.println("<html><head></head><body>");
	    	  out.println(ex.toString());
	    	  out.println("</body></html>");
	    } finally {
	         out.close();  // Always close the output writer
	    }
	}
		
	public String createfile(String path_and_file_name) {
		String resultat= new String("");
		try {
			File myObj = new File(path_and_file_name);
			if (myObj.createNewFile()) {
				resultat ="File created: " + myObj.getName();
			}
			else {
		        resultat = "File already exists.";
		    }
		    } catch (IOException e) {
		      resultat = e.toString();
		    }
		return resultat;
	}

}

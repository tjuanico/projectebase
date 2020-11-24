package es.caib.ProjecteBaseApi;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.caib.ProjecteBaseEJB.interfaces.CodiPlexusServiceInterface;

/**
 * Pàgina JSON per otenir estadístiques de manera automatitzada
 */
public class Estadistiques extends HttpServlet {
	
	// EJB's
	@EJB
	CodiPlexusServiceInterface CodiServ;
		
	private static final long serialVersionUID = 1L;
		
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String pattern = "dd/MM/yyyy HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	    
	    Integer ns = 0;
	    Integer nd = 0;
	    Date ds = new Date();
	    Date dd = new Date();
	    Date now = new Date();
	    
	    ns = CodiServ.getNumServits();
		nd = CodiServ.getNumDisponibles();
		ds = CodiServ.getDataDarrerServit();
		dd = CodiServ.getDataDarrerLotBaixat();
		
	    PrintWriter out = response.getWriter();
	 
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    try {
	    	
	    	out.println("{");
	        out.println("   \"codisServits\" : "+ ns +", ");
	        out.println("   \"codisDisponibles\" : "+ nd +", ");
	        out.println("   \"lastCodeTimestamp\" : \""+ simpleDateFormat.format(ds)+"\", ");
	        out.println("   \"lastBulkDownloadTimestamp\" : \""+ simpleDateFormat.format(dd)+"\", ");
	        out.println("   \"nowTimestamp\" : \""+ simpleDateFormat.format(now) +"\"");
	        out.println("}");
	        out.flush();
	    } finally {
	         out.close();  // Always close the output writer
	    }
	}
}

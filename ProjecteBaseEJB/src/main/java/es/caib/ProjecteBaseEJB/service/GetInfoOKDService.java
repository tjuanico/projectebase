package es.caib.ProjecteBaseEJB.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ejb.Stateless;

import es.caib.ProjecteBaseEJB.interfaces.GetOKDInterface;

@Stateless
public class GetInfoOKDService implements GetOKDInterface{

	     
	@Override
	public String getPods() {
		String resultat = new String("");
		
		try {
			
		  System.setProperty("javax.net.ssl.trustStore","/app/caib/data/prueba.jks");
		  System.setProperty("javax.net.ssl.trustStorePassword","changeit");
		  
          URL url = new URL("https://spreokdlin1.caib.es:8443/api/v1/namespaces/test/services");
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("GET");
		  conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJ0ZXN0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFwaXRlc3QtdG9rZW4teGQ5dG4iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiYXBpdGVzdCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6ImNjYzQ2N2ZlLWM1Y2YtMTFlYS1iZWU4LTAwNTA1Njg5NDI5YiIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDp0ZXN0OmFwaXRlc3QifQ.d0WZSI05uPwLwEfbcxKtsbWmpXvnFO7Zyv3gu9t04kdpzuNpOOKRc3ABWSJdxzUcbc97TbJp0wvc7KWOp6hG3snJXt3zvXkdi1eeZg4Nejt2_Hhffvi86u3mr_yJ6pyW6OGY5gz6RRIcef_6ml6S2rkJkWspMFIlo3nMqBN2NvN3mZkkdQ_9-n2xRXJZn7RDzGkV_wnBRGK9JDSUqz1XVxgo7txGjCLG_MHecjMFoOSLuJgrT37QAArU4-bl-80Vx2ka4Z47hSnIKuDGVsf3t1QAnBopIsChqhwxqZapRl8-mzK_YUTpIxiVfjd4xSjJCNeXmwJianGdnu-JWDa3aQ");
	      conn.setRequestProperty("Accept", "application/json");
	      conn.setRequestProperty("Connection", "close");
	      
	      if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
		  }
	                    
		  BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				resultat = output;				
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			resultat = e.getMessage();

		  } catch (IOException e) {

			  resultat = e.getMessage();
		  }
		return resultat;

	}

	@Override
	public boolean getResultat() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getError() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

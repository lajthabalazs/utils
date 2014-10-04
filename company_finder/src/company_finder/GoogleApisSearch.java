package company_finder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class GoogleApisSearch implements Search {
	
	private int delay = 20000;
	
	public String search(String searchTerm) throws UnsupportedEncodingException {
		URL url;
	    InputStream is = null;
    	String encoded = URLEncoder.encode("\"" + searchTerm + "\"", "UTF-8");
    	System.out.println("Encoded " + encoded);
	    while (true) {
		    try {
		    	System.out.println("Trying " + searchTerm);
		        url = new URL("http://ajax.googleapis.com/ajax/services/search/web?start=0&rsz=large&v=1.0&q=" + encoded + "+site:hu");
		        System.out.println("URL " + url);
		        is = url.openStream();  // throws an IOException
				ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
				JsonNode response = mapper.readTree(is);
				System.out.println("Response " + response);
				JsonNode responseData = response.get("responseData");
				int responseStatus = response.get("responseStatus").asInt();
				String responseCount = "0";
				System.out.println("Response status " + responseStatus);
				if (responseStatus == 200) {
					if (responseData != null) {
						try {
							responseCount = responseData.get("cursor").get("resultCount").getTextValue();
						} catch (NullPointerException e) {
							System.out.println("No result count.");
						}
					}
			    	try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return "" + responseCount;
				} else {
					System.out.println("Suspected Terms of Service Abuse, new delay " + delay);
					delay += 1000;
				}
		    } catch (MalformedURLException mue) {
		         mue.printStackTrace();
		    } catch (IOException ioe) {
		         ioe.printStackTrace();
		    } finally {
		        try {
		            if (is != null) is.close();
		        } catch (IOException ioe) {
		        }
		    }
	    	try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	}
}

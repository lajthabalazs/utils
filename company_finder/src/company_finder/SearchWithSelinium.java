package company_finder;

import java.io.UnsupportedEncodingException;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class SearchWithSelinium  implements Search {
	
	private WebDriver driver;

	public SearchWithSelinium() {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
		System.setProperty("-Dwebdriver.firefox.profile", "default");
        driver = new FirefoxDriver();
        driver.get("http://www.google.com");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

   }

	@Override
	public String search(String searchTerm) throws UnsupportedEncodingException {
		System.out.println("Starting waiting period");
        // And now use this to visit Google
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        driver.get("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        element.sendKeys("\"" + searchTerm + "\" site:hu");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());
    	String resultStats;
    	resultStats = driver.findElement(By.id("resultStats")).getText();
        WebElement item = null;
        try {
        	item = driver.findElement(By.tagName("i"));
        } catch (org.openqa.selenium.NoSuchElementException e) {
        	System.err.println(e);
        }
        String misspelled = "OK";
        if (item != null) {
        	misspelled = item.getText();
        }
        System.out.println(resultStats);
        try {
	        String ret = resultStats.split(" ")[1] + "," + misspelled;
	        return ret;
        } catch (Exception e){
        	return resultStats;
        }
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		SearchWithSelinium s = new SearchWithSelinium();
		String result = s.search("Caterpilar");
		System.out.println("Result " + result);
	}
}

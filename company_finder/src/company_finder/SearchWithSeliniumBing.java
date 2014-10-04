package company_finder;

import java.io.UnsupportedEncodingException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SearchWithSeliniumBing  implements Search {
	
	private WebDriver driver;

	public SearchWithSeliniumBing() {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
		System.setProperty("-Dwebdriver.firefox.profile", "default");
        driver = new FirefoxDriver();
        driver.get("http://www.bing.com");
   }

	@Override
	public String search(String searchTerm) throws UnsupportedEncodingException {
		System.out.println("Starting waiting period");
        // And now use this to visit Google
        driver.get("http://www.bing.com");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        // Find the text input element by its name
        WebElement element;
        try {
        	element = driver.findElement(By.name("q"));
        } catch (NoSuchElementException e) {
        	return "ERROR,ERROR";
        }
        // Enter something to search for
        element.sendKeys("\"" + searchTerm + "\" site:hu");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Find result count
    	String resultStats = "0";
        String misspelled = "OK,";
        
        try {
	    	resultStats = driver.findElement(By.className("sb_count")).getText();
	    	resultStats = resultStats.split(" ")[0];
	    	resultStats = resultStats.replace(".", "");
	        System.out.println(resultStats);
	        System.out.println(resultStats.split(" ")[0]);
        } catch (NoSuchElementException e) {
        	System.out.println("No result");
        	misspelled = "NO RESULT,";
        }
        
        // Find alternatives
        try {
	        WebElement item = driver.findElement(By.className("b_pAlt"));
	        misspelled = "SPELLING," + item.findElement(By.tagName("strong")).getText();
        } catch (NoSuchElementException e) {
        	System.out.println("No spelling alternative");
        }
        
        try {
	        WebElement item = driver.findElement(By.id("sp_recourse"));
	        misspelled = "NO QUOTES," + item.findElement(By.tagName("a")).getText();
        } catch (NoSuchElementException e) {
        	System.out.println("No no quotes");
        }
        
        String ret = resultStats + "," + misspelled + "," + driver.getCurrentUrl();
        return ret;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		SearchWithSeliniumBing s = new SearchWithSeliniumBing();
		String result;
		result = s.search("Caterpilar");
		System.out.println("Result " + result);

		result = s.search("a f lewis");
		System.out.println("Result " + result);

		result = s.search("ABEILLEVALUEHOLDINGS, LLC");
		System.out.println("Result " + result);

		result = s.search("ABILITIESATMORNINGSIDE, INC.");
		System.out.println("Result " + result);

		result = s.search("ABS Capital Partners III, Limited Partnership");
		System.out.println("Result " + result);

		result = s.search("hjujedasa");
		System.out.println("Result " + result);

		result = s.search("yujutrfdvsa");
		System.out.println("Result " + result);

		result = s.search("KOYTGRRW");
		System.out.println("Result " + result);

		result = s.search("sditjrefjwedewdoksdfee");
		System.out.println("Result " + result);
	}
}

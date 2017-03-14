/**
 * 
 */
package pageObjects;


//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;

import utility.utilConstants;
import utility.Globals;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * @author sudipaul
 *
 */
public class BasePage extends Object {
	protected WebDriver m_WebDriver;
	protected Globals m_objGlobalVars;
	protected Logger Log;

    /*
	@FindBy(linkText="SIGN-OFF")
	private static WebElement link_SignOff;
	*/
	
    public BasePage(WebDriver driver, Globals objGlobalVars) {
        m_WebDriver = driver;
		m_objGlobalVars = objGlobalVars;
		Log = LogManager.getLogger(utilConstants.HTMLLogFile);
    	
    	Log.debug("BasePage :: Constructor executed");
    }
	 
    /*
	public boolean LogOut(){
		try {
			link_SignOff.click();

			Log.debug("BasePage.LogOut :: Click action is performed on Sign Off link");
		} catch (Exception e) {
			Log.error("BasePage.LogOut :: Sign Off Failed, with stack trace: \n" + e.getMessage());
			
			return false;
		}		
		
	    return true;
	}
	*/
	
	//put code here for other common page-section
}

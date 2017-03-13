package autoFramework;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import utility.utilConstants;
import utility.Globals;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class byName_Cust extends By {
	static Logger Log;		// = Logger.getLogger(byName_Cust.class);
	
	String m_strElementName = "";
	String m_strElementType = "";
	String m_strCallerClass = "";
	int m_iCallerClassFile_LineNumber = -1;
	
	public byName_Cust(Globals objGlobalVars, final String sUIControlName, String sUIControlType, 
						final String CallerClass, final int CallerFile_LineNumber) {
		Log = LogManager.getLogger(utilConstants.HTMLLogFile);
		
		m_strElementName = sUIControlName;
		m_strElementType = sUIControlType;
		m_strCallerClass = CallerClass;
		m_iCallerClassFile_LineNumber = CallerFile_LineNumber;
	}
	
    @Override
    public WebElement findElement(final SearchContext context)
    {
    	WebElement ele = null;
    	
    	try {
    		ele = context.findElement(By.name(m_strElementName));
		} catch (Exception e) {
			Log.error("Overridden findElement :: Failed for element having name: \n" + m_strElementName);
			Log.error("Overridden findElement :: Failure stack trace: \n" + e.getMessage());
			
			throw e;
		}
    	
    	
    	Log.debug("Overridden findElement :: [" + m_strElementName + "] " + m_strElementType + " found \r\n Caller File: " + m_strCallerClass
    			     + ", \r\n line number: " + m_iCallerClassFile_LineNumber);
    	
    	return ele;
    }
	
	@Override
	public List<WebElement> findElements(SearchContext context) {
		Log.debug("Overridden findElements for :: [" + m_strElementName + "]");
		
		return context.findElements(By.name(m_strElementName));
	}
	
	@Override
    public String toString()
    {
		Log.debug("Overridden toString for :: [" + m_strElementName + "]");
		
		return "byName_Cust: " + m_strElementName;
    }
}

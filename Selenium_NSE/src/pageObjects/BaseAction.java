package pageObjects;
 
import org.openqa.selenium.WebDriver;

import utility.utilConstants;
import utility.Globals;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public abstract class BaseAction{
	protected WebDriver m_WebDriver;
	protected Globals m_objGlobalVars;
	protected Logger Log;
	
	public BaseAction( WebDriver webDriver, Globals objGlobalVars ){
		m_WebDriver = webDriver;
		m_objGlobalVars = objGlobalVars;
		Log = LogManager.getLogger(utilConstants.HTMLLogFile);
        
		Log.debug("BaseAction :: Constructor");
	}
	
    public abstract boolean Execute(String sTestCaseName, int iExcelDataId) throws Exception;
}

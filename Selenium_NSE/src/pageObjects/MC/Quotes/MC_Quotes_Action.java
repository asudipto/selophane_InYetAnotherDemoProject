package pageObjects.MC.Quotes;
 
import org.openqa.selenium.WebDriver;

import pageObjects.BaseAction;
import utility.Globals;


/**
 * description: Action class - can be used to compose various data entry
 * 		scenarios for the page class. Currently, a default read-all fields
 *		kind of function is defined at the page class. The action class
 *		
 */
public class MC_Quotes_Action extends BaseAction {
	
	public MC_Quotes_Action(WebDriver webDriver, Globals objGlobalVars){
		super(webDriver, objGlobalVars);
        
		Log.debug("MC_Quotes_Action :: Constructor");
	}
	
    public boolean Execute(String sTestCaseName, int iExcelDataId) throws Exception{
    	Log.info("MC_Quotes_Action.Execute :: Function ENTRY");
		
    	boolean bStatus = false;
    	MC_Quotes_Page pgMCQuotes;
		
		try {
			pgMCQuotes = 
					new MC_Quotes_Page(m_WebDriver, m_objGlobalVars);
			
			// data driving is possible at 2 places:
			//    either here, or at the page class
			//    for current scenario its a bit optimal at the page class
			for (int itr = 1; itr <= 1; itr++) {
				bStatus = pgMCQuotes.fnReadFields( sTestCaseName, iExcelDataId );
			}
		} catch (Exception e) {
			Log.error("MC_Quotes_Action.Execute :: Failed with stack trace: \n" + e.getMessage());
			
			return bStatus;
		}
		
		Log.info("MC_Quotes_Action.Execute :: Function EXIT - normal route");
		
		return bStatus;
    }
}


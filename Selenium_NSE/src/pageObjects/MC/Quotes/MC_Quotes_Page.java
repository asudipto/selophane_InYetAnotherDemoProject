/**
 * 
 */
package pageObjects.MC.Quotes;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selophane.elements.factory.api.*;
import org.selophane.elements.widget.*;
//import org.testng.Assert;

import pageObjects.BasePage;
import utility.utilConstants;
import utility.ExcelUtils;
import utility.Globals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.util.List;


/**
 * @author sudipaul
 *
 */
public class MC_Quotes_Page extends BasePage {
	// Test Data Sheet Columns
	private static int iColCount = -1;
	
	public static final int Col_TestCaseName = ++iColCount;
	public static final int Col_TestDataId = ++iColCount;

	public static final int Col_Automation_Run_Timestamp = ++iColCount;

	@FindBy(id="search_str")
	private static TextInput txt_StockName_SearchString;

	//@FindBy(xpath="//*[@id='topsearch']/a")
	@FindBy(xpath="/html/body/header/div[1]/div/div[2]/div[1]/div[4]/a")
	private static WebElement btn_Search;
	
	//@FindBy(xpath="//*[@id=\"mcpcp_addportfolio\"]/div[1]/a")
	//private static WebElement btn_AddToWatchlist;
	
	@FindBy(id="fvmkhide1")
	private static WebElement btn_CloseAdPopup;
	
	//@FindBy(xpath="//*[@id=\"aw0\"]/img")
	//private static Image img_AdPopup;
	
	@FindBy(id="fvcallout1")
	private static WebElement div_Popup;
		
	@FindBy(xpath="//*[@id=\"nChrtPrc\"]/div[4]/div[2]/span[1]/a")
	private static WebElement link_SetSMSAlert;
	
	@FindBy(id="mktdet_nav_1")
	private static WebElement btn_StandaloneMarketDetails;
	
	//@FindBy(id="mktdet_nav_2")
	//private static WebElement btn_ConsolidatedMarketDetails;

	@FindBy(name="n_instrument_type")
	private static Select selectbox_Futures_InstrumentType;
	
	@FindBy(name="n_sel_exp_date")
	private static Select selectbox_Futures_ExpiryDate;
	
	@FindBy(name="fno_element")
	private static WebElement frame_FnOQuote;
	
	//*[@id="topsearch"]
	@FindBy(name="topsearch")
	private static WebElement frame_TopSearchQuote;
	
	
	/*
	public WebElement lbl_generic_QuotePage(WebDriver driver, final String CallerClass, final int CallerFile_LineNumber){
        WebElement custElement = driver.findElement(new byName_Cust("userName", CallerClass, CallerFile_LineNumber));

        return custElement; 
    }		
	*/
	
	public WebElement lbl_generic_QuotePage(String sXPathToLabel){
        WebElement custElement = m_WebDriver.findElement(new By.ByXPath(sXPathToLabel));

        return custElement; 
    }

	/*
	@FindBy(name="password")
	WebElement txtPassword;
	*/

	
    public MC_Quotes_Page(WebDriver driver, Globals objGlobalVars) {
        super(driver, objGlobalVars);

    	ElementFactory.initElements(m_WebDriver, this);
    	
    	Log.debug("MC_Quotes_Page :: Constructor executed");
    }
    
	public boolean fnReadFields( String sTestCaseName, int iExcelDataId ) throws Exception{
		Log.info("MC_Quotes_Page.fnFillFields :: Function ENTRY");

		ExcelUtils objExcelUtils = m_objGlobalVars.get_objExcelUtils();
		boolean bFunctionExecutionStatus = false;
		String sCompanyBSECode = "";
		String sXPath = "";
		WebElement label = null;
		ArrayList<String> aryFieldValue = new ArrayList<String>();
		WebDriverWait wait = new WebDriverWait(m_WebDriver, 10);
		int itrCompanies = -1;
		int itrColumn = -1;
		String sFileWithFullPath = utilConstants.Path_TestData + utilConstants.File_TestData;
	    
		//for( itrCompanies = 1; itrCompanies <= 114; itrCompanies++ )
		for( itrCompanies = 79; itrCompanies <= 79; itrCompanies++ )
		{
			aryFieldValue.clear();
			
			try {
				//wait.until( ExpectedConditions.frameToBeAvailableAndSwitchToIt( frame_TopSearchQuote ) );
				m_WebDriver.switchTo().defaultContent();
				
				sCompanyBSECode = objExcelUtils.getCellData_MultiRefCols(sFileWithFullPath, 				
										    			utilConstants.ws_QuotesDetails, "TestCaseName|TestDataId",
										    			sTestCaseName + "|" + itrCompanies, "StockName_SearchString");
				
				//BSE code is assumed to be an integer
				if ( ! sCompanyBSECode.matches("[0-9]+") ){
					sCompanyBSECode = Integer.toString( (int) Double.parseDouble( sCompanyBSECode ));
				}
				
				//feed the stock name in textbox
				txt_StockName_SearchString.set(sCompanyBSECode, false);
				//Thread.sleep(2000);
				
			    //perform search
			    btn_Search.click();
			    
			    
			    //wait.until( ExpectedConditions.elementToBeClickable( btn_AddToWatchlist ) );
			    
			    //wait.until( ExpectedConditions.visibilityOf( div_Popup ) );
			    //wait.until( ExpectedConditions.elementToBeClickable( div_Popup ) );
			    //wait.until( ExpectedConditions.elementToBeClickable( btn_CloseAdPopup ) );
				//Thread.sleep(16000);
				
				//ElementFactory.initElements(m_WebDriver, this);
				
				//btn_CloseAdPopup.click();
			} catch (Exception e) {
				bFunctionExecutionStatus = false;
				e.printStackTrace();
				Log.error("MC_Quotes_Page.fnFillFields :: Exception desc : \n" + e.getMessage() );
				
				//Log.info("MC_Quotes_Page.fnFillFields :: Function EXIT - exception route");
				continue;
			}
			finally{
			
			}
			
	
			//wait.until( ExpectedConditions.visibilityOf( link_SetSMSAlert ) );
			try {
				wait.until( ExpectedConditions.elementToBeClickable( link_SetSMSAlert ) );
				btn_StandaloneMarketDetails.click();
			} catch (Exception e) {
				bFunctionExecutionStatus = false;
				e.printStackTrace();
				
				Log.warn("Could not locate company: " + sCompanyBSECode);
				continue;
			}

	/*		selectbox_Futures_InstrumentType.click();
		    WebElement objInstrumentType = selectbox_Futures_InstrumentType.getFirstSelectedOption();
			String sFuturesInstrumentType = objInstrumentType.getText();
			Assert.assertEquals(sFuturesInstrumentType, "Futures");
		    String sFuturesExpiryDate = selectbox_Futures_ExpiryDate.getFirstSelectedOption().getText();*/

		    //selectbox_Futures_InstrumentType.selectByVisibleText("Options");
			
			int iStartCol = m_objGlobalVars.get_xlpgQuotesDetailsSheet_HeaderMap().get("StockName_BSE");
			int iExcelRow = (int) objExcelUtils.getRowNumber( sFileWithFullPath, utilConstants.ws_QuotesDetails, 
															sTestCaseName, Col_TestCaseName, 
															itrCompanies, Col_TestDataId );
			for ( itrColumn = iStartCol; itrColumn <= 29; itrColumn++ ) {
				sXPath = objExcelUtils.getCellDataForTestDataId(sFileWithFullPath, utilConstants.ws_QuotesDetails, 
																	utilConstants.Row_XPathsInPage, 
																	0, itrColumn);
				
				if ( "-" == sXPath ) {
					continue;
				}
				
				try {
					label = m_WebDriver.findElement(new By.ByXPath( sXPath ));
	            
		            aryFieldValue.add(itrColumn - iStartCol, label.getText());
		            Log.debug("MC_Quotes_Page.fnFillFields :: Field Value = " + aryFieldValue.get(itrColumn - iStartCol));
				
		            objExcelUtils.setCellData( sFileWithFullPath, utilConstants.ws_QuotesDetails, 
			            							aryFieldValue.get(itrColumn - iStartCol), 
			            							iExcelRow, itrColumn );
				} catch (Exception e) {
					bFunctionExecutionStatus = false;
					e.printStackTrace();

					Log.warn("Could not locate XPath: " + sXPath + "\n for company: " + sCompanyBSECode);
					continue;
				}
			}
			
			//WebElement myDynamicElement = (new WebDriverWait(m_WebDriver, 10))
			//		  .until(ExpectedConditions.presenceOfElementLocated(By.id("fno_element")));
						
			try {
				m_WebDriver = wait.until( ExpectedConditions.frameToBeAvailableAndSwitchToIt( frame_FnOQuote ) );
				
				/*
				wait.until( new ExpectedCondition <Boolean>(){
					public Boolean apply(WebDriver drv){
						WebElement ele_FnOQuote = null;

						ele_FnOQuote = drv.findElement( By.id("fno_element") );
						
						if (ele_FnOQuote != null) {
							return true;
						}
						else{
							return false;
						}						
					}
				} );
				*/
				
				for ( itrColumn = 30; itrColumn <= 32; itrColumn++ ) {
					sXPath = objExcelUtils.getCellDataForTestDataId(sFileWithFullPath, 
																		utilConstants.ws_QuotesDetails, 
																		utilConstants.Row_XPathsInPage, 
																		0, itrColumn);
					if ( "-" == sXPath ) {
						continue;
					}
					
					try {
						label = m_WebDriver.findElement(new By.ByXPath( sXPath ));
			            
			            aryFieldValue.add(itrColumn - iStartCol, label.getText());
			            Log.debug("MC_Quotes_Page.fnFillFields :: Field Value = " + aryFieldValue.get(itrColumn - iStartCol));
					
			            objExcelUtils.setCellData( sFileWithFullPath, utilConstants.ws_QuotesDetails, 
						    							aryFieldValue.get(itrColumn - iStartCol), 
						    							iExcelRow, itrColumn );
					} catch (Exception e) {
						bFunctionExecutionStatus = false;

						Log.warn("Could not locate XPath: " + sXPath + "\n for company: " + sCompanyBSECode);
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				bFunctionExecutionStatus = false;

				Log.warn("Could not locate FnO section for company: " + sCompanyBSECode);
				e.printStackTrace();
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd, HH:mm:ss");
		    Date date = new Date();
		    String sDate = dateFormat.format(date);
			objExcelUtils.setCellData( sFileWithFullPath, utilConstants.ws_QuotesDetails, 
											sDate, iExcelRow, m_objGlobalVars.get_xlpgQuotesDetailsSheet_HeaderMap().get("Automation_run_timestamp") );

		}
		
		aryFieldValue.clear();

	    Log.info("MC_Quotes_Page.fnFillFields :: Function EXIT - normal route");
		
	    bFunctionExecutionStatus = true;
		return bFunctionExecutionStatus;
	}
}

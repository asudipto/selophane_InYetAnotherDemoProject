/**
 * 
 */
package utility;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.htmlunit.HTMLDriver;


/**
 * @author sudipaul
 *
 */
public class Utils {
	private WebDriver m_objWebDriver = null;
	private Globals m_objGlobalVars;
	private Logger Log;
	
	public Utils(Globals objGlobalVars){
		m_objGlobalVars = objGlobalVars;
		Log = LogManager.getLogger(utilConstants.HTMLLogFile);
	}
	
	public WebDriver getDriver(){
		if (m_objWebDriver != null) {
			return m_objWebDriver;
		} else {
			//Assert that driver is required!
			return null;    //this means openBrowser is not yet called
		}
	}

	public WebDriver openBrowser(String sTestCaseName, int iExcelDataId) throws Exception {
		String sBrowserName;
		String strURL;
		ExcelUtils objExcelUtils = m_objGlobalVars.get_objExcelUtils();
		String sFileWithFullPath = utilConstants.Path_TestData + utilConstants.File_TestData;

		try {
			sBrowserName = objExcelUtils.getCellDataForTestDataId(sFileWithFullPath, utilConstants.ws_Common, 
																		sTestCaseName, iExcelDataId, 
																		utilConstants.Col_Browser);
			
			String osName = System.getProperty("os.name");
			String osVersion = System.getProperty("os.version");
			String osArchitecture = System.getProperty("os.arch");
			File file = null;
			
			System.out.println("Operating system Name: " + osName);
			System.out.println("Operating system Version: " + osVersion);
			System.out.println("Operating system Architecture: " + osArchitecture);
			
			if (sBrowserName.equals("Chrome")) {				
				if(osName.toLowerCase().indexOf("mac") >= 0)
				{
					file = new File("/Users/asudipto/EclipseNeon_ws/chromedriver");
					System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
				}
				else if(osName.toLowerCase().indexOf("win") >= 0)
				{
					//following does not work and is not required for windows
					//file = new File("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
					//System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
				}				
				
				m_objWebDriver = new ChromeDriver();
			}else if (sBrowserName.equals("Firefox")) {				
				if(osName.toLowerCase().indexOf("mac") >= 0)
				{
				}
				else if(osName.toLowerCase().indexOf("win") >= 0)
				{
					System.setProperty("webdriver.gecko.driver", "E:/Selenium3/geckodriver.exe");
				}				
				
				m_objWebDriver = new FirefoxDriver();
			}
			else if (sBrowserName.equals("JavascriptExecutor")) {
				m_objWebDriver = new ChromeDriver();
				JavascriptExecutor js = (JavascriptExecutor) m_objWebDriver;
				js.executeScript("console.log('I logged something to the Javascript console');");

				//m_objWebDriver = new HTMLUnitDriver();
			}
			else if (sBrowserName.equals("IE")) {
				if(osName.toLowerCase().indexOf("mac") >= 0)
				{
				}
				else if(osName.toLowerCase().indexOf("win") >= 0)
				{
					//following does not work and is not required for windows
					//file = new File("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
					//System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
					System.setProperty("webdriver.ie.driver", "E:/Selenium3/IEDriverServer.exe");
				}				
				
				m_objWebDriver = new InternetExplorerDriver();
			}
			

			m_objGlobalVars.set_WebDriver(m_objWebDriver);
			Log.info("New m_objWebDriver instantiated");

			m_objWebDriver.manage().timeouts().implicitlyWait(utilConstants.iWaitGlobal, TimeUnit.SECONDS);
			Log.info("Implicit wait applied on the m_objWebDriver for " + utilConstants.iWaitGlobal + " seconds");

			strURL = objExcelUtils.getCellDataForTestDataId(sFileWithFullPath, utilConstants.ws_Common, 
																sTestCaseName, iExcelDataId, 
																utilConstants.Col_URL);
			Log.info("URL: " + strURL);
			
			m_objWebDriver.get(strURL);
			Log.info("Web application launched successfully");
			
			m_objWebDriver.manage().window().maximize();
			Log.info("Browser window maximized successfully");
		} catch (Exception e) {
			Log.error("Utils.OpenBrowser :: Exception desc : \n" + e.getMessage());
		}

		return m_objWebDriver;
	}
}
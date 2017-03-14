package scenarios;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import utility.utilConstants;
import utility.ExcelUtils;
import utility.Globals;
import utility.Utils;

import pageObjects.MC.Quotes.MC_Quotes_Action;


public class FindAQuote_TC {
	final String m_sTestCaseName = FindAQuote_TC.class.getName();
	final Logger Log = LogManager.getLogger(utilConstants.HTMLLogFile);
	final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	public WebDriver m_WebDriver;
	String sLogFileName = "";
	Globals m_objGlobalVars;
	ExcelUtils m_objExcelUtils;
	Utils m_objUtils;
	
	@BeforeClass
	public void beforeClass() {		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMMdd_HH_mm_ss");
	    Date date = new Date();
	    sLogFileName = "log_" + m_sTestCaseName + "_" + dateFormat.format(date) + ".html";
		System.setProperty("logFilename", sLogFileName);
		Log.info("beforeClass :: " + m_sTestCaseName);
	}

	@BeforeMethod
	public void beforeMethod(ITestContext testCntx, Method method) throws Exception {
		String sMethod = method.getName();
		m_objGlobalVars = new Globals();
		m_objGlobalVars.set_TestClassName( m_sTestCaseName );	
		m_objGlobalVars.set_Logger(Log);
		m_objExcelUtils = m_objGlobalVars.get_objExcelUtils( );
		m_objUtils = m_objGlobalVars.get_objUtils();
		
		ThreadContext.put("sTestClass_Method", m_sTestCaseName + "_" + sMethod);
	    
		/*
		 * Currently, different TestNG Debug/Run configurations exist for the 
		 * debug and normal runs.
		 * For debug run, use VM Argument: -Dlog4j.configurationFile=log4j2.debug.xml
		 * For normal run, use VM Argument: -Dlog4j.configurationFile=log4j2.xml
		 * Respective log4j2 xml files already exist in the project
		 * */
		
	    Log.info("beforeMethod :: Function ENTRY for: " + sMethod);
		Log.info("***********************Test start***********************");
		
		Log.info("beforeMethod :: Starting execution of test: " + m_objGlobalVars.get_TestClassName());

		m_objExcelUtils.setExcelFile(utilConstants.Path_TestData + utilConstants.File_TestData, utilConstants.ws_Common);
		Log.debug("beforeMethod :: Excel sheet is set");

		m_objGlobalVars.get_xlCommonSheet_HeaderMap();
		m_objGlobalVars.get_xlpgQuotesDetailsSheet_HeaderMap();
		
		m_WebDriver = m_objUtils.openBrowser(m_objGlobalVars.get_TestClassName(), 1); //1 = column number
		Log.info("beforeMethod :: Function EXIT for: " + sMethod);
	}
	
	@Test (priority = 1)
	public void FindQuote(Method method) throws Exception {
		Log.info("FindQuote :: Function ENTRY");
		
		boolean bStatus = false;
			
		MC_Quotes_Action actionQuoteFinder = 
				new MC_Quotes_Action(m_WebDriver, m_objGlobalVars);
		
		for( int itrCompanies = 1; itrCompanies <= 1; itrCompanies++ )
		{
			try {
				bStatus = actionQuoteFinder.Execute(m_objGlobalVars.get_TestClassName(), itrCompanies);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		actionQuoteFinder = null;
		AssertJUnit.assertEquals(bStatus, true);
	}

	@AfterMethod
	public void afterMethod(ITestResult testResult, Method method) throws Exception {
	    Log.info("afterMethod :: Function ENTRY");
		
	    String sFilePathToDisk = "";
		String sFileWithFullPath = utilConstants.Path_TestData + utilConstants.File_TestData;
		
	    if (testResult.getStatus() == ITestResult.FAILURE) {
	        File scrFile = ((TakesScreenshot)m_WebDriver).getScreenshotAs(OutputType.FILE);
	        sFilePathToDisk = System.getProperty("user.dir") + "\\test-output\\TCExitOnFailure_" + 
	        					m_objGlobalVars.get_TestClassName() + "." + method.getName() + ".jpg";
	        FileUtils.copyFile(scrFile, new File(sFilePathToDisk));
	    }
		
		m_WebDriver.quit();
		Log.debug("afterMethod :: Browser closed");

		try {
			int iRow = m_objExcelUtils.getRowNumber( sFileWithFullPath, utilConstants.ws_Common, 
														m_sTestCaseName, utilConstants.Col_TestCaseName, 
														1, utilConstants.Col_TestDataId );
			
			if(testResult.getStatus() != ITestResult.FAILURE)
			{
				m_objExcelUtils.setCellData(sFileWithFullPath, utilConstants.ws_Common, 
												"Pass", iRow, utilConstants.Col_Result);
				Log.info("afterMethod :: Test case execution ends for test: " + m_objGlobalVars.get_TestClassName());
				Log.info("afterMethod :: Function EXIT - test passed");
			}
			else
			{
				m_objExcelUtils.setCellData(sFileWithFullPath, utilConstants.ws_Common, 
												"Fail", iRow, utilConstants.Col_Result);
				Log.error("afterMethod :: Error image: " + "<img src=" + sFilePathToDisk + ">");
				Log.error("afterMethod :: Test case execution ends for test: " + m_objGlobalVars.get_TestClassName());
				Log.info("afterMethod :: Function EXIT - test failed");
			}

			Date todaysDate = new Date();
			m_objExcelUtils.setCellData(sFileWithFullPath, utilConstants.ws_Common, 
											(new Timestamp(todaysDate.getTime())).toString(), 
											iRow, utilConstants.Col_Timestamp);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("afterMethod :: Exception Stack trace: \n" + e.getMessage());
			Log.info("***********************Test end***********************");
			
			throw(e);
		} finally {
			Log.info("***********************Test end***********************");
			Log.info("");
		}
		
		m_objGlobalVars.closeExcelFile();
	}
}
package utility;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;


/**
 * description: this class is instantiated once and stores and provides global level objects
 *
 */
public class Globals {
	private WebDriver m_WebDriver = null;
	private String m_sTestClassName = "";
	private Logger Log = null;
	private Utils m_objUtils = null;
	private ExcelUtils m_objExcelUtils = null;
	private HashMap<String, Integer> map_ColHdrs_LocIdxs__Common_Sheet = null;
	private HashMap<String, Integer> map_ColHdrs_LocIdxs__pgQuotesDetails_Sheet = null;

	FileInputStream m_ExcelFile_InputStream = null;
	FileOutputStream m_ExcelFile_OutputStream = null;
	XSSFWorkbook m_Excel_Readable_WBook = null;
	String m_sFileWithFullPath = utilConstants.Path_TestData + utilConstants.File_TestData;
	
	public Globals(){
		try {
			m_ExcelFile_InputStream = new FileInputStream(m_sFileWithFullPath);
		} catch (FileNotFoundException e) {
			System.out.println("File not found: \n" + m_sFileWithFullPath);
			e.printStackTrace();
		}
    	
    	// Create workbook instance for XLSX file
    	try {
			m_Excel_Readable_WBook = new XSSFWorkbook (m_ExcelFile_InputStream);
		} catch (IOException e) {
			System.out.println("Unable to create workbook for file: \n" + m_sFileWithFullPath);
			e.printStackTrace();
		}
	};
	
	public WebDriver get_WebDriver() {
		return this.m_WebDriver;
	}

	public void set_WebDriver(WebDriver m_WebDriver) {
		this.m_WebDriver = m_WebDriver;
	}

	public String get_TestClassName() {
		return this.m_sTestClassName;
	}

	public void set_TestClassName(String sTestClassName) {
		this.m_sTestClassName = sTestClassName;
	}
	
	public FileInputStream getFileInputStream(String sFileWithFullPath){
		if(m_ExcelFile_InputStream == null){
			try {
				m_ExcelFile_InputStream = new FileInputStream(sFileWithFullPath);
			} catch (FileNotFoundException e) {
				Log.error("Globals.getFileInputStream :: FileNotFoundException occurred when trying to create input stream");
				e.printStackTrace();
			}
		}
		
		return this.m_ExcelFile_InputStream;
	}
	
	public FileOutputStream getFileOutputStream(String sFileWithFullPath){
		//if(m_ExcelFile_OutputStream == null){   // keep commented
			try {
				m_ExcelFile_OutputStream = new FileOutputStream(sFileWithFullPath);
			} catch (FileNotFoundException e) {
				Log.error("Globals.getFileOutputStream :: FileNotFoundException occurred when trying to create output stream");
				e.printStackTrace();
			}
		//}

		return this.m_ExcelFile_OutputStream;
	}
	
	public XSSFWorkbook getWorkbook(){
		return this.m_Excel_Readable_WBook;
	}

	public Logger get_Logger() {
		return this.Log;
	}

	public void set_Logger(Logger log) {
		this.Log = log;
	}

	public Utils get_objUtils() {
		if (m_objUtils == null){
			m_objUtils = new Utils(this);
		}
		
		return this.m_objUtils;
	}

	public ExcelUtils get_objExcelUtils() {
		if (m_objExcelUtils == null){
			m_objExcelUtils = new ExcelUtils(this);
		}
		
		return this.m_objExcelUtils;
	}

	public HashMap<String, Integer> get_xlCommonSheet_HeaderMap() throws NoSuchFieldException, IOException {
		if (map_ColHdrs_LocIdxs__Common_Sheet == null){			
			map_ColHdrs_LocIdxs__Common_Sheet = new HashMap<String, Integer>();
	    	
	    	// Get to the sheet from the XLSX workbook
	    	XSSFSheet xlSheet = m_Excel_Readable_WBook.getSheet(utilConstants.ws_QuotesDetails);
	    	get_objExcelUtils().readHeaderRowsAndLocationsToMap(m_Excel_Readable_WBook, xlSheet, 
																map_ColHdrs_LocIdxs__Common_Sheet);
		}
		
		return this.map_ColHdrs_LocIdxs__Common_Sheet;
	}

	public HashMap<String, Integer> get_xlpgQuotesDetailsSheet_HeaderMap() throws NoSuchFieldException, IOException {
		if (map_ColHdrs_LocIdxs__pgQuotesDetails_Sheet == null){
			map_ColHdrs_LocIdxs__pgQuotesDetails_Sheet = new HashMap<String, Integer>();
	    	
	    	// Get to the sheet from the XLSX workbook
	    	XSSFSheet xlSheet = m_Excel_Readable_WBook.getSheet(utilConstants.ws_QuotesDetails);
	    	get_objExcelUtils().readHeaderRowsAndLocationsToMap(m_Excel_Readable_WBook, xlSheet, 
																map_ColHdrs_LocIdxs__pgQuotesDetails_Sheet);
		}
		
		return this.map_ColHdrs_LocIdxs__pgQuotesDetails_Sheet;
	}
	
	public HashMap<String, Integer> getExcelSheet_HeaderMap(String sExcelSheetName) throws NoSuchFieldException, IOException {
		if(sExcelSheetName.equalsIgnoreCase(utilConstants.ws_Common)){
			return get_xlCommonSheet_HeaderMap();
		} else if(sExcelSheetName.equalsIgnoreCase(utilConstants.ws_QuotesDetails)){
			return get_xlpgQuotesDetailsSheet_HeaderMap();
		} else{
			return null;
		}
	}


    /**
     * description: close the workbook and file stream objects
     */
    public void closeExcelFile(){
    	if(m_Excel_Readable_WBook != null){
	    	try {
				m_Excel_Readable_WBook.close();
				m_Excel_Readable_WBook = null;
			} catch (IOException e) {
				Log.error("Globals.closeExcelFile :: IOException occurred when trying to close excel workbook");
				e.printStackTrace();
			}
    	}    	

    	if(m_ExcelFile_InputStream != null){
    		try {
				m_ExcelFile_InputStream.close();
				m_ExcelFile_InputStream = null;
			} catch (IOException e) {
				Log.error("Globals.closeExcelFile :: IOException occurred when trying to close excel input stream");
				e.printStackTrace();
			}
    	}

    	if(m_ExcelFile_OutputStream != null){
	    	try {
				m_ExcelFile_OutputStream.close();
				m_ExcelFile_OutputStream = null;
			} catch (IOException e) {
				Log.error("Globals.closeExcelFile :: IOException occurred when trying to close excel output stream");
				e.printStackTrace();
			}
    	}
    }
    
}

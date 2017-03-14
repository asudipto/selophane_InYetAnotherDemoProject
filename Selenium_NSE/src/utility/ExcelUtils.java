package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class ExcelUtils {
	private Globals m_objGlobalVars;
	private Logger Log;
	
	public ExcelUtils(Globals objGlobalVars){
		m_objGlobalVars = objGlobalVars;
		Log = LogManager.getLogger(utilConstants.HTMLLogFile);
		
		Log.trace("ExcelUtils :: Constructor");
	}
	
	public ExcelUtils() {
		// TODO Auto-generated constructor stub
	}

	private FileInputStream m_ExcelFile_InputStream;
	private FileOutputStream m_ExcelFile_OutputStream;
	private XSSFSheet m_ExcelWSheet;
    private XSSFWorkbook m_ExcelWBook;
    private XSSFCell m_Cell;
    private XSSFRow m_Row;


    /**
     * description: This method sets the File path and to open the Excel file
     * 
     * @param sXLSXFile - excel file from which data is to be read
     * @param sExcelSheetName - excel sheet from which data is to be read
     * @throws Exception
     */
    public void setExcelFile(String sXLSXFile, String sExcelSheetName) {
    	try {
    		// Open the Excel file
    		m_ExcelFile_InputStream = m_objGlobalVars.getFileInputStream(sXLSXFile);

    		// Access the required test data sheet
    		m_ExcelWBook = m_objGlobalVars.getWorkbook();
    		m_ExcelWSheet = m_ExcelWBook.getSheet(sExcelSheetName);
    		int iSheetIdx = m_ExcelWBook.getSheetIndex(sExcelSheetName);
    		m_ExcelWBook.setActiveSheet(iSheetIdx);

    		Log.debug("ExcelUtils.setExcelFile :: Sheet Name for m_ExcelWSheet is: " + sExcelSheetName);
    	} catch (Exception e){
    		Log.error("ExcelUtils.setExcelFile :: Exception message: " + e.getMessage());
    		e.printStackTrace();
    	}
    }

    /**
     * description: Given the test case name and the test id, fetch data from the cell with 
     * 				mentioned column and implied row
     * 
     * @param sXLSXFile - excel file from which data is to be read
     * @param sExcelSheetName - excel sheet from which data is to be read
     * @param sRefColData - data from TestCaseName column : usually the name of the test case,
     * 						 but sometimes the XPath
     * @param iTestDataId - the sequential numeric test data id for the data row for this test case,
     * 						 present in this sheet. Value zero reserved for getting the XPath 
     * @param iColNum - Column from which cell data is to be fetched
     * @return the cell data
     * @throws Exception
     */
    public String getCellDataForTestDataId(String sXLSXFile, String sExcelSheetName, String sRefColData, 
    										int iTestDataId, int iColNum) throws Exception{
    	String sCellData = "";
    	ArrayList<Integer> aryRowNums = null;
    	
    	setExcelFile(sXLSXFile, sExcelSheetName);
    	
    	try{
    		aryRowNums = getRowsForTest(sXLSXFile, sExcelSheetName, sRefColData);
    		
    		for (Integer iRowNum : aryRowNums) {
    			m_Cell = m_ExcelWSheet.getRow(iRowNum).getCell(utilConstants.Col_TestDataId);
    			
    			if ( m_Cell.getRawValue().equalsIgnoreCase("" + iTestDataId) ) {
        			m_Cell = m_ExcelWSheet.getRow(iRowNum).getCell(iColNum);
        			sCellData = getCellData(m_Cell);
				}
			}
    		
    		aryRowNums.clear();

    		return sCellData;
    	}catch (Exception e){
    		Log.error("ExcelUtils.getCellDataForTestDataId :: Exception desc: " + e.getMessage());
    		e.printStackTrace();
    	}
		
    	return "";
    }


    public static void main(String args[]){
    	ExcelUtils objExcelUtils = new ExcelUtils();
    	String sCellData = "";
    	
    	try {
    		sCellData = objExcelUtils.getCellData_MultiRefCols("./test-data/TestData.xlsx", 
						    			utilConstants.ws_QuotesDetails, 
						    			"TestCaseName|TestDataId", 
						    			"scenarios.FindAQuote_TC|3", 
						    			"StockName_DisplayedName");
    		
    		System.out.println("****************************** \n sCellData = " + sCellData);
		} catch (Exception e) {
			System.out.println("ExcelUtils.main :: Caught exception in getCellData_MultiRefCols: " + e.getMessage());
			e.printStackTrace();
		}
    }


    /**
     * description: get cell data, based on matching reference columns data
     * 
     * @param sXLSXFile - excel file from which data is to be read
     * @param sExcelSheetName - excel sheet from which data is to be read
     * @param sRefColsHeaders - pipe separated reference column headers
     * @param sRefCellsData - pipe separated reference column data
     * @param sActiveColHeader - column from which data is to be read
     * @return the cell data
     * @throws Exception
     */
	public String getCellData_MultiRefCols(String sXLSXFile, String sExcelSheetName, String sRefColsHeaders, 
    											String sRefCellsData, String sActiveColHeader) throws Exception{
    	setExcelFile(sXLSXFile, sExcelSheetName);

    	int iRowNumber = -1;
    	String sRefColHeader = "";
    	String sRefCellData = "";
    	String sCellData = "";
    	
    	int itrRefCellDataMatches = 0;
		boolean bExpectedRowIsFound = false;
		//int iPreciseDataRow = -1;

    	ArrayList<String> aryRefColsHeaders = new ArrayList<String>( Arrays.asList(sRefColsHeaders.split("[|]") ) );
    	String[] aryRefCellsData = sRefCellsData.split("[|]");
    	int iRefCellsCount = aryRefCellsData.length;
    	
    	XSSFSheet xlSheet = m_objGlobalVars.getWorkbook().getSheet(sExcelSheetName);
    	
    	try {
    		int rowCount = xlSheet.getLastRowNum() + 1;
    		
    		for ( iRowNumber = 1 ; iRowNumber < rowCount; iRowNumber++){
    			itrRefCellDataMatches = 0;
    			
    			for ( int itr = 0; itr < aryRefColsHeaders.size(); itr++ ) {
    				sRefColHeader = aryRefColsHeaders.get(itr);
    				sRefCellData = aryRefCellsData[itr];
    				
    				m_Cell = m_ExcelWSheet.getRow(iRowNumber).getCell(
    							m_objGlobalVars.getExcelSheet_HeaderMap(sExcelSheetName).get(sRefColHeader));

    				sCellData = getCellData(m_Cell);
    				
    				if( m_Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC ) {
    					sCellData = Integer.toString( (int) Double.parseDouble( getCellData(m_Cell) ));
    				}    				
    				
    				/*
    				if (sCellData.matches("[0-9]+")){
    					sCellData = Integer.toString( (int) Double.parseDouble( getCellData(m_Cell) ));
    				}
    				*/
    				
    				//sCellData = getExcelCellData( sXLSXFile, sExcelSheetName, iRowNumber, 
    				//		m_objGlobalVars.get_xlpgQuotesDetailsSheet_HeaderMap(), sRefColHeader )
	    			if (sCellData.equalsIgnoreCase(sRefCellData)){
	    				itrRefCellDataMatches++;

	    				if( itrRefCellDataMatches == iRefCellsCount ){
	    					bExpectedRowIsFound = true;
	    					//iPreciseDataRow = iRowNumber;
	    					
	    					break;
	    				}
	    			}
				}
    			
				if( true == bExpectedRowIsFound ){					
					break;
				}
    		}
    		
			if(bExpectedRowIsFound == true){
				m_Cell = m_ExcelWSheet.getRow(iRowNumber).getCell(
						m_objGlobalVars.getExcelSheet_HeaderMap(sExcelSheetName).get(sActiveColHeader));
				sCellData = getCellData(m_Cell);
	    	
				//sCellData = getExcelCellData( sXLSXFile, sExcelSheetName, iPreciseDataRow, 
				//								m_objGlobalVars.getExcelSheet_HeaderMap( sExcelSheetName ), 
				//								sActiveColHeader );
			}
    	}catch (Exception e){
    		Log.error("ExcelUtils.getCellData_MultiRefCols :: Exception desc: " + e.getMessage());
    		e.printStackTrace();
    	}
    	
    	Log.info("ExcelUtils.getCellData_MultiRefCols :: Expected cell data: sActiveCellData = " + sCellData);
    	
    	aryRefColsHeaders.clear();

    	return sCellData;
    }
    
    /**
     * description: populate the global map object for excel sheet, with the column
     * 				headers and corresponding location indices
     * 
     * @param xlWorkBook - excel workbook in which data is present
     * @param xlSheet - excel sheet whose columns need to be mapped
     * @param map_ColHdrs_LocIdxs - map to store the Column Headers and the m_Row Indices of those columns.
     * 								For each sheet, there's a separate map available globally at Globals class
     * @throws NoSuchFieldException
     */
    public void readHeaderRowsAndLocationsToMap(XSSFWorkbook xlWorkBook, 
    				XSSFSheet xlSheet, HashMap<String, Integer> map_ColHdrs_LocIdxs) throws NoSuchFieldException{
    	String sActiveCellData = "";
    	// Get iterator to all the rows in current sheet
    	Iterator<Row> rowIterator = xlSheet.iterator();
    	int iColIndex = -1;
    	
    	if (rowIterator.hasNext()) { 
    		Row row = rowIterator.next();
    		
    		// For header row, iterate through each column
    		Iterator<Cell> cellIterator = row.cellIterator(); 
    		
    		while (cellIterator.hasNext()) {
    			iColIndex++;
    			Cell cell = cellIterator.next();
	    	
    			sActiveCellData = getCellData(cell);
		    	
		    	//add all the headers along with their respective indices
				map_ColHdrs_LocIdxs.put(sActiveCellData, iColIndex);
	    	}
    	}
    	else{
    		Log.error("ExcelUtils.readHeaderRowsAndLocationsToMap :: Header row not found in excel sheet");
        	
    		throw new NoSuchFieldException();
    	}
    }
    
    public String getCellData(Cell cell){
    	String sActiveCellData = "";
    	
    	switch (cell.getCellType()) {
    	case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
    		sActiveCellData = cell.getStringCellValue();
    		//System.out.println( "String cell data: " + sActiveCellData );
    		break;
    	case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
    		sActiveCellData = Double.toString( cell.getNumericCellValue() );
    		//System.out.println( "Numeric cell data: " + sActiveCellData );
    		break;
    	case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
    		sActiveCellData = Boolean.toString( cell.getBooleanCellValue() );
    		//System.out.println( "Boolean cell data: " + sActiveCellData );
    		break;
    	default : 
    	}
    	
    	return sActiveCellData;
    }
    
    /**
     * description: get all the row numbers having data for this test case
     * 
     * @param sXLSXFile - excel file from which data is to be read
     * @param sExcelSheetName - excel sheet from which data is to be read
     * @param sTestCaseName - the name of the test case
     * @return an arraylist of integers having reference to all row numbers where the test's 
     * 			data is present
     * @throws Exception
     */
    public ArrayList<Integer> getRowsForTest(String sXLSXFile, String sExcelSheetName, String sTestCaseName) throws Exception{
    	int iRowNumber = -1;
    	String sCellData = "";
    	ArrayList<Integer> aryRows = new ArrayList<Integer>();
    	    	
    	setExcelFile(sXLSXFile, sExcelSheetName);
    	
    	try {
    		int rowCount = m_ExcelWSheet.getLastRowNum() + 1;
    		for ( iRowNumber = 0 ; iRowNumber < rowCount; iRowNumber++){
    			m_Cell = m_ExcelWSheet.getRow(iRowNumber).getCell(utilConstants.Col_TestCaseName);
    	    	sCellData = getCellData(m_Cell);
    			if (sCellData.equalsIgnoreCase(sTestCaseName)){
    				aryRows.add(iRowNumber);
    			}
    		}
    	}catch (Exception e){
    		Log.error("ExcelUtils.getRowsForTest :: Exception desc: " + e.getMessage());
    		e.printStackTrace();
    	}
		
    	return aryRows;
    }
        
    /**
     * description: get excel row number, given the test case name and test id
     * 
     * @param sXLSXFile - excel file from which data is to be read
     * @param sExcelSheetName - excel sheet from which data is to be read
     * @param sTestCaseName - the test case name
     * @param iCol_TestName - column index of the column for test case name
     * @param iTestDataId - the sequential numeric test data id for the data row 
     * 						for this test case, present in this sheet
     * @param iCol_TestDataId - column index of the column for test data id
     * @return the single row number that matches the input parameter values
     * @throws Exception
     */
    public int getRowNumber(String sXLSXFile, String sExcelSheetName, String sTestCaseName, int iCol_TestName, 
    							int iTestDataId, int iCol_TestDataId ) {
    	int iRowNumber = -1;
    	String sCellData = "";
    	
    	setExcelFile(sXLSXFile, sExcelSheetName);
    	
    	try {
    		int rowCount = m_ExcelWSheet.getLastRowNum();
    		for ( iRowNumber = 0 ; iRowNumber < rowCount; iRowNumber++){
    			m_Cell = m_ExcelWSheet.getRow(iRowNumber).getCell(utilConstants.Col_TestCaseName);
    	    	sCellData = getCellData(m_Cell);
    			if (sCellData.equalsIgnoreCase(sTestCaseName)){
    				m_Cell = m_ExcelWSheet.getRow(iRowNumber).getCell(utilConstants.Col_TestDataId);
        	    	sCellData = getCellData(m_Cell);
        	    	
    				int iCellData_TestDataId = (int) Double.parseDouble( sCellData );
    				if ( iCellData_TestDataId == iTestDataId ) {
    					break;
					}
    			}
    		}
    		return iRowNumber;
    	}catch (Exception e){
    		Log.error("ExcelUtil.getRowContains :: Exception desc: " + e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return -1;
    }
    
    /**
     * description: Use this method to write data to an Excel cell
     * 
     * @param sXLSXFile - excel file to which data is to be set
     * @param sExcelSheetName - excel sheet to which data is to be set
     * @param strCellData - the data to be set to the cell
     * @param iRowNum - excel row number of cell in which data is to be written
     * @param iColNum - excel column number of cell in which data is to be written
     * @throws Exception
     */
    public void setCellData(String sXLSXFile, String sExcelSheetName, 
    							String strCellData, int iRowNum, int iColNum) {
    	setExcelFile(sXLSXFile, sExcelSheetName);
    	
    	try{
    		m_Row  = m_ExcelWSheet.getRow(iRowNum);
    		m_Cell = m_Row.getCell(iColNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);

    		if (m_Cell == null) {
    			m_Cell = m_Row.createCell(iColNum);
    			m_Cell.setCellValue(strCellData);
    		} else {
    			m_Cell.setCellValue(strCellData);
    		}

    		// Constant variables Test Data path and Test Data file name
    		//m_ExcelFile_OutputStream = new FileOutputStream(utilConstants.Path_TestData + utilConstants.File_TestData);
    		m_ExcelFile_OutputStream = m_objGlobalVars.getFileOutputStream(utilConstants.Path_TestData + 
    																			utilConstants.File_TestData);
    		m_ExcelWBook.write(m_ExcelFile_OutputStream);
    		m_ExcelFile_OutputStream.flush();
    		m_ExcelFile_OutputStream.close();
    	}catch(Exception e){
    		Log.error("ExcelUtils.setCellData :: Exception desc: " + e.getMessage());
    		e.printStackTrace();
    	}
    }
}

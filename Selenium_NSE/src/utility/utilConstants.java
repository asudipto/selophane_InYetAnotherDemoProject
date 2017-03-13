/**
 * 
 */
package utility;

/**
 * @author sudipaul
 *
 */
public class utilConstants {
	public static final int iWaitGlobal = 5;	//30s wait, implemented as implicit wait, as well as explicit wait
	public static final String HTMLLogFile = "Routing";		//"HTMLLogFile";
	public static final String Path_TestData = System.getProperty("user.dir") + "/test-data/";
	//public static final String LINE_SEPARATOR = "\n";
	
	public static final String File_TestData = "TestData.xlsx";
	public static final String ws_Common = "Common";
	public static final String ws_QuotesDetails = "pgQuotesDetails";
	
	public static final String Row_XPathsInPage = "XPathsInPage";

	// Test Data Sheet Columns
	private static int iColCount = -1;
	
	public static final int Col_TestCaseName = ++iColCount;
	public static final int Col_TestDataId = ++iColCount;
	public static final int Col_URL = ++iColCount;
	public static final int Col_UserName = ++iColCount;
	public static final int Col_Password = ++iColCount;
	public static final int Col_Browser = ++iColCount;
	public static final int Col_Result = ++iColCount;
	public static final int Col_Timestamp = ++iColCount;
	
}
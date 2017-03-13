package org.selophane.elements.widget;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.ElementImpl;


/**
 * Table wrapper.
 */
public class TableImpl extends ElementImpl implements Table {
	/**
	 * Creates a Table for a given WebElement.
	 * 
	 * @param element
	 *            element to wrap up
	 */
	public TableImpl(WebElement element) {
        super(element);

		Log.trace("TableImpl :: Constructor");
	}

	@Override
	public int getRowCount() {
		int iRowCount = getRows().size();
		Log.debug("TableImpl :: RowCount = " + iRowCount);
		
		return iRowCount;
	}

	@Override
	public int getColumnCount() {
		int iColCount = findElement(By.cssSelector("tr")).findElements(
				By.cssSelector("*")).size();
		Log.debug("TableImpl :: ColumnCount = " + iColCount);
		
		return iColCount;
		// Would ideally do:
		// return findElements(By.cssSelector("tr:first-of-type > *"));
		// however HTMLUnitDriver does not support CSS3
	}

	@Override
	public WebElement getCellAtIndex(int rowIdx, int colIdx) {
		// Get the row at the specified index
		WebElement row = getRows().get(rowIdx);

		List<WebElement> cells;

		// Cells are most likely to be td tags
		if ((cells = row.findElements(By.tagName("td"))).size() > 0) {
			return cells.get(colIdx);
		}
		// Failing that try th tags
		else if ((cells = row.findElements(By.tagName("th"))).size() > 0) {
			return cells.get(colIdx);
		} else {
			final String error = String
					.format("Could not find cell at row: %s column: %s",
							rowIdx, colIdx);
			throw new RuntimeException(error);
		}
	}

	/**
	 * Gets all rows in the table in order header > body > footer
	 * 
	 * @return list of row WebElements
	 */
	private List<WebElement> getRows() {
		List<WebElement> rows = new ArrayList<WebElement>();
		
		//Header rows
		rows.addAll(findElements(By.cssSelector("thead tr")));
		
		//Body rows
		rows.addAll(findElements(By.cssSelector("tbody tr")));
		
		//Footer rows
		rows.addAll(findElements(By.cssSelector("tfoot tr")));

		return rows;
	}
	
	//wip
	public int getRowByColumnAndCellData(String sColumnHeader, String sCellData){
        final WebElement element = getWrappedElement();
        
		String sCellValue = "";
        
        int iRowCount = this.getRowCount();
        Log.debug("getFindRowByColumnAndCellData :: iRowCount = " + iRowCount);
        
        int iColCount = this.getColumnCount();
        Log.debug("getFindRowByColumnAndCellData :: iColCount = " + iColCount);
        
        int iRow = -1;
        int iCol = -1;
        boolean bCellIsLocated = false;
        
        //First loop will find the column header
		for (iCol = 1; iCol <= iColCount; iCol++){
			sCellValue = "";
			sCellValue = element.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table[1]/tbody/tr[1]/td[" 
													+ iCol + "]")).getText();
			
			if(sCellValue.equalsIgnoreCase(sColumnHeader)){
				for (iRow = 1; iRow <= iRowCount; iRow++){
					sCellValue = element.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table[1]/tbody/tr["
															+ iRow + "]/td[" + iCol + "]")).getText();
					
					if (sCellValue == sCellData) {
						bCellIsLocated = true;
						
						break;
					}
				}
			}
			
			if(true == bCellIsLocated){
				break;
			}
		}
	
		if (true == bCellIsLocated) {
			return iRow;
		} else {
			return -1;
		}		
	}
	
	//wip
	public boolean selectRadioBtnAtRowIndex(int iRowForRadioBtnSelection){
        final WebElement element = getWrappedElement();
		
		return false;
	}
	
	//wip
	public boolean selectRadioBtnAgainstCellValue(String sColumnHeader, String sCellData){
        //final WebElement element = getWrappedElement();
        
        int iRowOfMatchingCellData = -1;
        boolean bRadioBtnIsSelected = false;
        
        iRowOfMatchingCellData = getRowByColumnAndCellData(sColumnHeader, sCellData);
        
        bRadioBtnIsSelected = selectRadioBtnAtRowIndex(iRowOfMatchingCellData);
		
		return bRadioBtnIsSelected;
	}
}

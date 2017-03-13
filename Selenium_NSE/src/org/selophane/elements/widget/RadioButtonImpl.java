package org.selophane.elements.widget;

import org.selophane.elements.base.ElementImpl;
//import org.testng.Assert;
//import org.testng.asserts.*;

//import com.google.common.base.Verify;

import utility.utilConstants;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Wrapper class for basic radiobutton functionality.
 */
public class RadioButtonImpl extends ElementImpl implements RadioButton {
	
    /**
     * Wraps a WebElement with checkbox functionality.
     *
     * @param element to wrap up
     */
    public RadioButtonImpl(WebElement element) {
        super(element);

		Log.trace("RadioButtonImpl :: Constructor");
    }

    /**
     * 
     */
    public void selectRadioBtn(WebDriver webDriver, String sRadioIdentifierType, String sRadioIdentifierValue, 
    						String sRadioButtonAttribute, String sExpectedRadioButtonAttributeValue) throws TimeoutException {
    	String sRadioButtonValue = "";
    	
    	List<WebElement> radio_Buttons;
    	WebElement radio_Button;
    	
    	
    	if ("name" == sRadioIdentifierType) {
    		//radio_Buttons = (List<WebElement>) ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name( sRadioIdentifierValue ));
    		
    		radio_Buttons = webDriver.findElements(By.name( sRadioIdentifierValue ));
    		
	     	//assert that radio buttons are visible
	   		//ExpectedConditions.visibilityOfAllElements(radio_Buttons);
		}
    	else if ("xpath" == sRadioIdentifierType) {
    		radio_Buttons = webDriver.findElements(By.xpath( sRadioIdentifierValue ));
    		
	     	//assert that radio buttons are visible
    		ExpectedConditions.visibilityOfAllElements(radio_Buttons);
    	}
    	else {
    		hardAssert.assertEquals(true, false, "Unhandled case for RadioButton identifier type");
    		
    		return;
		}
    	
    	

    	int iNumberOfButtonsInRadio = radio_Buttons.size();
    	Log.debug("RadioButtonImpl.selectRadioBtn :: Number of Buttons in Radio = " + iNumberOfButtonsInRadio);

    	for( int i = 0; i < iNumberOfButtonsInRadio; i++ ){
	    	sRadioButtonValue = radio_Buttons.get(i).getAttribute( sRadioButtonAttribute );
	    	
	    	if (sRadioButtonValue.equalsIgnoreCase( sExpectedRadioButtonAttributeValue )) {
	    		//assert that radio button is visible and clickable
	    		//radio_Button = (WebElement) ExpectedConditions.elementToBeClickable(radio_Buttons.get(i));
	    		radio_Button = (new WebDriverWait(webDriver, utilConstants.iWaitGlobal)).until(ExpectedConditions.elementToBeClickable(radio_Buttons.get(i)));
	    		
	    		radio_Button.click();
	    		softAssert.assertEquals(ExpectedConditions.elementToBeSelected(radio_Button), true, "Radio button selection");
	    		
	    		//assert that radio button is selected
	    		
	    		Log.debug("RadioButtonImpl.selectRadioBtn :: Selected button at index: " + i +
	    					", with attribute: " + sRadioButtonAttribute +
	    					" having value: " + sRadioButtonValue);
	    		break;
	    	}
	    }
    }
}

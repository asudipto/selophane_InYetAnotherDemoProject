package org.selophane.elements.widget;

import org.selophane.elements.base.ElementImpl;
import org.openqa.selenium.WebElement;

/**
 * Wrapper class like Select that wraps basic checkbox functionality.
 */
public class CheckBoxImpl extends ElementImpl implements CheckBox {
	
    /**
     * Wraps a WebElement with checkbox functionality.
     *
     * @param element to wrap up
     */
    public CheckBoxImpl(WebElement element) {
        super(element);

		Log.trace("CheckBoxImpl :: Constructor");
    }

    public void toggle() {
        getWrappedElement().click();
    }

    public void check() {
        if (!isChecked()) {
            toggle();
            Log.debug("CheckBoxImpl.check :: Checkbox is checked");
        }
        else{
            Log.debug("CheckBoxImpl.check :: Checkbox was found to be checked");
        }
        
    }

    public void uncheck() {
        if (isChecked()) {
            toggle();
            Log.debug("CheckBoxImpl.check :: Checkbox is unchecked");
        }
        else{
        	Log.debug("CheckBoxImpl.check :: Checkbox was found to be unchecked");
        }
        
    }

    public boolean isChecked() {
    	boolean bIsCheckboxChecked = getWrappedElement().isSelected();
    	
    	if (true == bIsCheckboxChecked) {
            Log.debug("CheckBoxImpl.isChecked :: Checkbox is in checked state");
		} else {
	        Log.debug("CheckBoxImpl.isChecked :: Checkbox is in unchecked state");
		}
    	
        return bIsCheckboxChecked;
    }
}

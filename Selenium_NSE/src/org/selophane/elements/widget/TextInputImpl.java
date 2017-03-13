package org.selophane.elements.widget;

import org.openqa.selenium.WebElement;
import org.selophane.elements.base.ElementImpl;


/**
 * TextInput wrapper.
 */
public class TextInputImpl extends ElementImpl implements TextInput {
	/**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public TextInputImpl(WebElement element) {
        super(element);

		Log.trace("TextInputImpl :: Constructor");
    }

    @Override
    public void clear() {
        getWrappedElement().clear();
    }

    @Override
    public void set(String text) {
        WebElement element = getWrappedElement();
        
        element.clear();
        element.sendKeys(text);
        
        String sTextboxValue = element.getText();
        
        if (sTextboxValue.equals(text)) {
        	Log.debug("TextInputImpl.set :: text set is: " + sTextboxValue);
        }
        else {
			Log.error("Textbox value does not match Input string");
		}
        
    }
    
    public void set(String text, boolean bExactMatch) {
        WebElement element = getWrappedElement();
        
        element.clear();
        element.sendKeys(text);
        
        String sTextboxValue = element.getText();
        
        if (sTextboxValue.equals(text)) {
        	Log.debug("TextInputImpl.set :: text set is: " + sTextboxValue);
        }
        else {
        	if (true == bExactMatch) {
				Log.error("Textbox value does not match Input string");
			} else {
				Log.debug("TextInputImpl.set :: PARTIAL text set is: " + sTextboxValue);
			}			
		}        
    }

    /**
     * Gets the value of an input field.
     * @return String with the value of the field.
     */
    @Override
    public String getText() {
        return getWrappedElement().getAttribute("value");
    }
}

package org.selophane.elements.widget;

import org.selophane.elements.base.ElementImpl;
import org.openqa.selenium.WebElement;

/**
 * Wrapper class that wraps basic Image functionality.
 */
public class ImageImpl extends ElementImpl implements Image {

    /**
     * Wraps a WebElement with Image functionality.
     *
     * @param element to wrap up
     */
    public ImageImpl(WebElement element) {
        super(element);

		Log.trace("ImageImpl :: Constructor");
    }

    public void click() {
    	Log.debug("Clicking on Image: " + getWrappedElement().getAttribute("name"));
    	
    	getWrappedElement().click();    	
    }
}

package org.selophane.elements.widget;

import org.selophane.elements.base.ElementImpl;
import org.openqa.selenium.WebElement;

/**
 * Wraps a label on a html form with some behavior.
 */
public class LabelImpl extends ElementImpl implements Label {
	/**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    //public LabelImpl(WebElement element, Globals objGlobalVars) {
    //    super(element, objGlobalVars);
    public LabelImpl(WebElement element) {
    	super(element);
    	
		Log.trace("LabelImpl :: Constructor");
    }

    @Override
    public String getFor() {
        return getWrappedElement().getAttribute("for");
    }
}

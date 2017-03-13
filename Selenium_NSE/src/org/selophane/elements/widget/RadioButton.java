package org.selophane.elements.widget;

import org.openqa.selenium.WebDriver;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

/**
 * Interface that wraps a WebElement in RadioButton functionality.
 */
@ImplementedBy(RadioButtonImpl.class)
public interface RadioButton extends Element {
	public void selectRadioBtn(WebDriver webDriver, String sRadioIdentifierType, String sRadioIdentifierValue, 
			String sRadioButtonAttribute, String sExpectedRadioButtonAttributeValue);
}

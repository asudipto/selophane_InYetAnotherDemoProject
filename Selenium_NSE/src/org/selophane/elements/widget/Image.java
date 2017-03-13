package org.selophane.elements.widget;

import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

/**
 * Interface that wraps a WebElement in CheckBox functionality.
 */
@ImplementedBy(ImageImpl.class)
public interface Image extends Element {

    /**
     * Clicks on Image.
     */
    void click();

    
}

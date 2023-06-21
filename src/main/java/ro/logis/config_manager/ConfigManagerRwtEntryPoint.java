package ro.logis.config_manager;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ConfigManagerRwtEntryPoint extends AbstractEntryPoint {

    @Override
    protected void createContents(Composite parent) {
        var label = new Label(parent, SWT.NONE);
        label.setText("I'm an RWT standalone app running on Spring Boot. Pretty neat!");
    }
}

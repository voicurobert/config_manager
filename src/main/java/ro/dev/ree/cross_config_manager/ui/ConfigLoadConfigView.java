package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ConfigLoadConfigView {

    public void createContents(Composite parent) {
        SashForm sashForm = new SashForm(parent, 256);
        var mainLayout = new RowLayout();
        mainLayout.type = SWT.VERTICAL;
        mainLayout.center = true;
        mainLayout.justify = true;
        mainLayout.fill = true;


        sashForm.setLayout(mainLayout);

//        Button newConfigButton = new Button(parent, SWT.PUSH);
//        newConfigButton.setText("Load config222");

        var label = new Label(parent, SWT.NONE);
        label.setText("Load a config file");
    }
}

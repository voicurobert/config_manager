package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class ConfigManagerGui {
    public void createContents(Composite parent) {
        var mainLayout = new RowLayout();
        mainLayout.type = SWT.VERTICAL;
        mainLayout.center = true;
        mainLayout.fill = true;
        mainLayout.spacing = 10;

        parent.setLayout(mainLayout);

        int width = 150;
        int height = 40;

        Button newConfigButton = new Button(parent, SWT.PUSH);
        newConfigButton.setLayoutData(new RowData(width, height));
        newConfigButton.setText("New config");
        newConfigButton.addListener(SWT.Selection, event -> newConfigButtonClicked());

        Button loadConfigButton = new Button(parent, SWT.PUSH);
        loadConfigButton.setLayoutData(new RowData(width, height));
        loadConfigButton.setText("Load config");
        loadConfigButton.addListener(SWT.Selection, event ->  loadConfigButtonClicked());

        Button viewConfigsButton = new Button(parent, SWT.PUSH);
        viewConfigsButton.setLayoutData(new RowData(width, height));
        viewConfigsButton.setText("View configs");
        viewConfigsButton.addListener(SWT.Selection, event -> viewConfigsButtonClicked());

        mainLayout.marginLeft = (Display.getCurrent().getBounds().width / 2) - 50;
        mainLayout.marginTop = (Display.getCurrent().getBounds().height / 2) - height * 3;
    }

    public void newConfigButtonClicked() {
        NewConfigGui newConfigGui = new NewConfigGui();
        newConfigGui.open();
    }

    public void loadConfigButtonClicked() {
        LoadConfigGui loadConfigGui = new LoadConfigGui();
        loadConfigGui.open();
    }

    public void viewConfigsButtonClicked() {
        ConfigListViewGui configListViewGui = new ConfigListViewGui();
        configListViewGui.open();

    }

}

package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class LoadConfigGui {
    private Shell shell;

    public LoadConfigGui() {
        shell = new Shell(Display.getCurrent(), SWT.NO_TRIM);
    }

    public void open() {
        shell.setMaximized(true);
        var mainLayout = new RowLayout();
        mainLayout.type = SWT.VERTICAL;
        mainLayout.center = true;
        mainLayout.justify = true;
        mainLayout.fill = true;
        mainLayout.spacing = 10;

        shell.setLayout(mainLayout);

        int width = 150;
        int height = 40;

        Button backToMainButton = new Button(shell, SWT.PUSH);
        backToMainButton.setText("To config view gui");
        backToMainButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                ConfigViewGui configViewGUI = new ConfigViewGui();
                configViewGUI.open();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        mainLayout.marginLeft = (Display.getCurrent().getBounds().width / 2) - 50;
        mainLayout.marginTop = (Display.getCurrent().getBounds().height / 2) - height * 3;

        shell.open();
    }

}

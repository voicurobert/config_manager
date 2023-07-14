package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

public class ConfigListViewGui {
    private Shell shell;
    public ConfigListViewGui() {
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

        Button backToMainButton = new Button(shell, SWT.PUSH);

        backToMainButton.setText("Back to main menu");


        backToMainButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        shell.open();
    }
}

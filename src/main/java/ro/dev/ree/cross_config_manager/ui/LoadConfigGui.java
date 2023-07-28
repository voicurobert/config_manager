package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
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
        mainLayout.fill = true;
        mainLayout.spacing = 10;
        shell.setLayout(mainLayout);

        int width = 150;
        int height = 40;

        Button backtoMainButton = new Button(shell, SWT.PUSH);
        backtoMainButton.setLayoutData(new RowData(width, height));
        backtoMainButton.setText("Back to main menu");

        mainLayout.marginLeft = (Display.getCurrent().getBounds().width / 2) - 50;
        mainLayout.marginTop = (Display.getCurrent().getBounds().height / 2) - height * 3;

        shell.open();
    }


}

package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

public class NewConfigGui {

    private Shell shell;

    public NewConfigGui() {
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
        mainLayout.marginLeft = (Display.getCurrent().getBounds().width / 2) - 50;
        mainLayout.marginTop = (Display.getCurrent().getBounds().height / 2) - height * 3;

        shell.open();
    }

//    public void createContents(Composite parent) {
//        SashForm sashForm = new SashForm(parent, 256);
//        var mainLayout = new RowLayout();
//        mainLayout.type = SWT.VERTICAL;
//        mainLayout.center = true;
//        mainLayout.justify = true;
//        mainLayout.fill = true;
//
//
//        parent.setLayout(mainLayout);
//
////        Button newConfigButton = new Button(parent, SWT.PUSH);
////        newConfigButton.setText("New config222");
//        var label = new Label(parent, SWT.NONE);
//        label.setText("Add a new config");
//    }


}

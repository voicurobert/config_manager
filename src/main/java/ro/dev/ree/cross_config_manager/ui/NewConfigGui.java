package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigTypeService;


public class NewConfigGui {

    private final Shell shell;
    private final ConfigTypeService configTypeService = ConfigManagerContextProvider.getBean(ConfigTypeService.class);


    public NewConfigGui() {
        shell = new Shell(Display.getCurrent(), SWT.NO_TRIM);
    }

    public void open() {
        shell.setMaximized(true);
        var mainLayout = new RowLayout();
        var secondLayout = new RowLayout();
        mainLayout.type = SWT.VERTICAL;
        mainLayout.center = true;
        mainLayout.fill = true;
        mainLayout.spacing = 10;

        secondLayout.type = SWT.HORIZONTAL;
        secondLayout.center = true;
        secondLayout.fill = true;
        secondLayout.spacing = 10;

        int width = 150;
        int height = 40;

        mainLayout.marginLeft = (Display.getCurrent().getBounds().width / 2) - 140;
        mainLayout.marginTop = (Display.getCurrent().getBounds().height / 2) - height * 5;


        shell.setLayout(mainLayout);


        Label lblText = new Label(shell, SWT.NONE);
        lblText.setText("Set the config name");
        Text text = new Text(shell, SWT.BORDER);
        text.setLayoutData(new RowData(100, SWT.DEFAULT));

        Composite buttonComposite = new Composite(shell, SWT.NONE);
        buttonComposite.setLayout(secondLayout);
        Label secondLblText = new Label(shell, SWT.NONE);

        
        Button cancelButton = new Button(buttonComposite, SWT.PUSH);
        cancelButton.setLayoutData(new RowData(width, height));
        cancelButton.setText("Back");
        cancelButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        Button saveButton = new Button(buttonComposite, SWT.PUSH);
        saveButton.setLayoutData(new RowData(width, height));
        saveButton.setText("Save");
        saveButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                if (text.getText().equals("")) {
                    secondLblText.setText("You have to set the config name in order to save it!");
                } else {
                    ConfigDto configDto = new ConfigDto();
                    configDto.setName(text.getText());
                    configDto = configTypeService.save(configDto);
                    ConfigSingleton.getSingleton().setConfigDto(configDto);
                    text.setText("");
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });
        shell.open();
    }

}

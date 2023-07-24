package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.Config;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigTypeService;

import java.util.List;

public class ConfigListViewGui {
    private final Shell shell;
    private final ConfigTypeService configTypeService = ConfigManagerContextProvider.getBean(ConfigTypeService.class);

    public ConfigListViewGui() {
        shell = new Shell(Display.getCurrent(), SWT.NO_TRIM);
    }

    public void open() {
        List<Config> listConfigName = configTypeService.findAll();
//        Config[] v = new Config[listConfigName.size()];
//
//        for (int i = 0; i < listConfigName.size(); i++) {
//            v[i] = listConfigName.get(i);
//        }

        shell.setMaximized(true);
        var mainLayout = new RowLayout();
        mainLayout.type = SWT.VERTICAL;
        mainLayout.center = true;
        mainLayout.fill = true;
        mainLayout.spacing = 1;
        int width = 150;
        int height = 40;
        shell.setLayout(mainLayout);


        Table configTypeTable = new Table(shell, SWT.BORDER | SWT.CENTER);

        configTypeTable.setHeaderVisible(true);
        configTypeTable.setLinesVisible(true);
        configTypeTable.setLayoutData(new RowData(250, 150));
        String[] headerListView = {"Config name"};

        for (String header : headerListView) {
            TableColumn column = new TableColumn(configTypeTable, SWT.BORDER);
            column.setText(header);
            column.pack();
        }


        for (Config config : listConfigName) {
            TableItem item = new TableItem(configTypeTable, SWT.BORDER);
            item.setText(config.getName());
        }

        Menu menu = new Menu(configTypeTable);
        configTypeTable.setMenu(menu);
        var menuItem = new MenuItem(menu, SWT.NONE);
        menuItem.setText("Config detail");


        menuItem.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                // TODO get table selection and save config object to ConfigSingleton
                TableItem selection = configTypeTable.getSelection()[0];
                List<RecordDto> list = configTypeService.findByConfigName(selection.getText());

                ConfigSingleton.getSingleton().setConfigDto((ConfigDto) list.get(0));


                configView();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }

        });

        for (TableColumn column : configTypeTable.getColumns()) {
            column.pack();

        }


        Button backToMainButton = new Button(shell, SWT.PUSH);
        backToMainButton.setLayoutData(new RowData(width, height));
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


        mainLayout.marginLeft = (Display.getCurrent().getBounds().width / 2) - 150;
        mainLayout.marginTop = (Display.getCurrent().getBounds().height / 2) - height * 3;
        shell.open();
    }

    public void configView() {
        ConfigViewGui viewGui = new ConfigViewGui();
        viewGui.open();

    }


}

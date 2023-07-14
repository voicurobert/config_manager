package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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

        newConfigButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                newConfigButtonClicked();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        Button loadConfigButton = new Button(parent, SWT.PUSH);
        loadConfigButton.setLayoutData(new RowData(width, height));
        loadConfigButton.setText("Load config");

        loadConfigButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                loadConfigButtonClicked();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        Button viewConfigsButton = new Button(parent, SWT.PUSH);
        viewConfigsButton.setLayoutData(new RowData(width, height));
        viewConfigsButton.setText("View configs");

        viewConfigsButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                viewConfigsButtonClicked();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

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

//    public void createContents(Composite parent) {
//        var mainLayout = new GridLayout();
//        mainLayout.numColumns = 3;
//
//        parent.setLayout(mainLayout);
//
//        Button newConfigButton = new Button(parent, SWT.PUSH);
//        newConfigButton.setText("New config");
//
//        newConfigButton.addSelectionListener(new SelectionListener() {
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                NewConfigGui newConfigGui = new NewConfigGui();
//                newConfigGui.open();
//                //configNewConfigView.createContents(parent);
//
////                NodeTypeDto nodeTypeDto = new NodeTypeDto();
////                nodeTypeDto.setDiscriminator("test");
////                nodeTypeDto.setConfigId("123");
////                nodeTypeService.save(nodeTypeDto);
////
////                LinkTypeDto linkTypeDto = new LinkTypeDto();
////                linkTypeDto.setDiscriminator("test");
////                linkTypeDto.setConfigId("123");
////                linkTypeService.save(linkTypeDto);
//            }
//
//            @Override
//            public void widgetDefaultSelected(SelectionEvent e) {
//
//            }
//        });
//
//        Button loadConfigButton = new Button(parent, SWT.PUSH);
//        loadConfigButton.setText("Load config");
//
//        loadConfigButton.addSelectionListener(new SelectionListener() {
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                LoadConfigGui loadConfigGui = new LoadConfigGui();
//                loadConfigGui.open();
//            }
//
//            @Override
//            public void widgetDefaultSelected(SelectionEvent e) {
//
//            }
//        });
//
//        Button viewConfigsButton = new Button(parent, SWT.PUSH);
//        viewConfigsButton.setText("View configs");
//
//        viewConfigsButton.addSelectionListener(new SelectionListener() {
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                ConfigListViewGui configListViewGui = new ConfigListViewGui();
//                configListViewGui.open();
//
//            }
//
//            @Override
//            public void widgetDefaultSelected(SelectionEvent e) {
//
//            }
//        });
//
////        var menu = new Menu(parent);
////        parent.setMenu(menu);
////
////        var fileMenu = new Menu(menu);
////
////        var menuItem = new MenuItem(menu, SWT.CASCADE);
////        menuItem.setText("File");
////        menuItem.setMenu(fileMenu);
////
////        var menuItem2 = new MenuItem(fileMenu, SWT.NONE);
////        menuItem2.setText("haha2");
////
////        var menuItem3 = new MenuItem(fileMenu, SWT.NONE);
////        menuItem3.setText("haha2");
////
////        menu.setVisible(true);
//
//
////        var label = new Label(parent, SWT.NONE);
////        label.setText("I'm an RWT standalone app running on Spring Boot. Pretty neat!");
//    }

}

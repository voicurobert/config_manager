package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeService;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;

public class ConfigManagerGui {

    public NodeTypeService nodeTypeService = ConfigManagerContextProvider.getBean(NodeTypeService.class);
    public LinkTypeService linkTypeService = ConfigManagerContextProvider.getBean(LinkTypeService.class);

    public void createContents(Composite parent) {
        SashForm sashForm = new SashForm(parent, 256);
        var mainLayout = new RowLayout();
        mainLayout.type = SWT.VERTICAL;
        mainLayout.center = true;
        mainLayout.justify = true;
        mainLayout.fill = true;


        sashForm.setLayout(mainLayout);

        Button newConfigButton = new Button(parent, SWT.PUSH);
        newConfigButton.setText("New config");

        newConfigButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                ConfigNewConfigView configNewConfigView = new ConfigNewConfigView();
                configNewConfigView.createContents(parent);

                NodeTypeDto nodeTypeDto = new NodeTypeDto();
                nodeTypeDto.setDiscriminator("test");
                nodeTypeDto.setConfigId("123");
                nodeTypeService.save(nodeTypeDto);

                LinkTypeDto linkTypeDto = new LinkTypeDto();
                linkTypeDto.setDiscriminator("test");
                linkTypeDto.setConfigId("123");
                linkTypeService.save(linkTypeDto);

                parent.layout(true, true);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        Button loadConfigButton = new Button(parent, SWT.PUSH);
        loadConfigButton.setText("Load config");

        loadConfigButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                ConfigLoadConfigView configLoadConfigView = new ConfigLoadConfigView();
                configLoadConfigView.createContents(parent);

                parent.layout(true, true);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        Button viewConfigsButton = new Button(parent, SWT.PUSH);
        viewConfigsButton.setText("View configs");

        viewConfigsButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                ConfigViewConfigsView configViewConfigsView = new ConfigViewConfigsView();
                configViewConfigsView.createContents(parent);

                parent.layout(true, true);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

//        var menu = new Menu(parent);
//        parent.setMenu(menu);
//
//        var fileMenu = new Menu(menu);
//
//        var menuItem = new MenuItem(menu, SWT.CASCADE);
//        menuItem.setText("File");
//        menuItem.setMenu(fileMenu);
//
//        var menuItem2 = new MenuItem(fileMenu, SWT.NONE);
//        menuItem2.setText("haha2");
//
//        var menuItem3 = new MenuItem(fileMenu, SWT.NONE);
//        menuItem3.setText("haha2");
//
//        menu.setVisible(true);


//        var label = new Label(parent, SWT.NONE);
//        label.setText("I'm an RWT standalone app running on Spring Boot. Pretty neat!");
    }

}

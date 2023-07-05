package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;

public class ConfigManagerGui {

    public NodeTypeService nodeTypeService = ConfigManagerContextProvider.getBean(NodeTypeService.class);

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
                ConfigMainView configMainView = new ConfigMainView();
                configMainView.createContents(parent);

                parent.layout(true, true);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        Button loadConfigButton = new Button(parent, SWT.PUSH);
        loadConfigButton.setText("Load config");

        Button viewConfigsButton = new Button(parent, SWT.PUSH);
        viewConfigsButton.setText("View configs");

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

//        NodeTypeDto nodeTypeDto = new NodeTypeDto();
//        nodeTypeDto.setDiscriminator("test");
//        nodeTypeDto.setConfigId("123");
//        nodeTypeService.save(nodeTypeDto);

        //var label = new Label(parent, SWT.NONE);
        //label.setText("I'm an RWT standalone app running on Spring Boot. Pretty neat!");
    }

}

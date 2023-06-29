package ro.dev.ree.cross_config_manager;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;

public class CrossConfigManagerRwtEntryPoint extends AbstractEntryPoint {


    public NodeTypeService nodeTypeService = ConfigManagerContextProvider.getBean(NodeTypeService.class);

    @Override
    protected void createContents(Composite parent) {
        var menu = new Menu(parent);
        parent.setMenu(menu);

        var menuItem = new MenuItem(menu, SWT.CASCADE);
        menuItem.setText("haha");
        var menuItem2 = new MenuItem(menu, SWT.CASCADE);
        menuItem2.setText("haha2");

        menu.setVisible(true);

        System.out.println(nodeTypeService);

        NodeTypeDto nodeTypeDto = new NodeTypeDto();
        nodeTypeDto.setDiscriminator("test");
        nodeTypeDto.setConfigId("123");
        nodeTypeService.save(nodeTypeDto);

        //var label = new Label(parent, SWT.NONE);
        //label.setText("I'm an RWT standalone app running on Spring Boot. Pretty neat!");
    }
}

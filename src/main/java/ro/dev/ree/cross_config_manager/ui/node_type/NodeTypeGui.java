package ro.dev.ree.cross_config_manager.ui.node_type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;

import java.util.List;

public class NodeTypeGui extends TableComposite {

    // De facut cu service la toate
    private final NodeTypeService nodeTypeService = ConfigManagerContextProvider.getBean(NodeTypeService.class);

    @Override
    public String[] columns() {
        return new String[]{"IdNT","ConfigId", "Discriminator", "Name", "AppIcon", "MapIcon",
                "CapacityFull", "CapacityUnitName", "TypeClassPath",
                "RootType", "System","MultiparentAllowed", "UniquenessType"};
    }

    @Override
    public String tableName() {
        return "Node Types";
    }

    @Override
    public Composite createContents(Composite parent) {

        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);
        table.setToolTipText("NodeTypeTable");

        List<NodeTypeDto> allByConfigId = nodeTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigId());

        String[][] nodeTypeData = {
                {"idnt1", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"},
                {"idnt2", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"},
                {"idnt3", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"},
                {"idnt4", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"},
                {"idnt5", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"},
                {"idnt6", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"}
        };

        for (String[] row : nodeTypeData) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(row);
        }

        return table;
    }


}

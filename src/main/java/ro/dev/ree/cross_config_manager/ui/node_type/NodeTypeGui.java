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
        return new String[]{"IdNT", "ConfigId", "Discriminator", "Name", "AppIcon", "MapIcon",
                "CapacityFull", "CapacityUnitName", "TypeClassPath",
                "RootType", "System", "MultiparentAllowed", "UniquenessType"};
    }

    @Override
    public String tableName() {
        return "Node Types";
    }

    @Override
    public Composite createContents(Composite parent) {

        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);

        List<NodeTypeDto> allByConfigId = nodeTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (int i = 0; i < allByConfigId.size(); i++) {
            NodeTypeDto nodeTypeDto = allByConfigId.get(i);

            String[] vec = new String[columns().length];
            vec[0] = nodeTypeDto.getId();
            vec[1] = nodeTypeDto.getConfigId();
            vec[2] = nodeTypeDto.getDiscriminator();
            vec[3] = nodeTypeDto.getName();
            vec[4] = nodeTypeDto.getAppIcon();
            vec[5] = nodeTypeDto.getMapIcon();
            vec[6] = nodeTypeDto.getCapacityFull();
            vec[7] = nodeTypeDto.getCapacityUnitName();
            vec[8] = nodeTypeDto.getTypeClassPath();
            vec[9] = nodeTypeDto.getRootType();
            vec[10] = nodeTypeDto.getSystem();
            vec[11] = nodeTypeDto.getMultiparentAllowed();
            vec[12] = nodeTypeDto.getUniquenessType();

            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(vec);
        }

        return table;
    }


}

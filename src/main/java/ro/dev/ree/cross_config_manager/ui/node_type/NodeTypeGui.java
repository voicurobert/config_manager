package ro.dev.ree.cross_config_manager.ui.node_type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;

import java.util.List;

public class NodeTypeGui extends TableComposite {

    public static final String TABLE_NAME = "Node Type";

    private final NodeTypeService nodeTypeService = ConfigManagerContextProvider.getBean(NodeTypeService.class);

    @Override
    public String[] columns() {
        return new String[]{"Id", "Discriminator", "Name", "AppIcon", "MapIcon",
                "CapacityFull", "CapacityUnitName", "TypeClassPath",
                "RootType", "System", "MultiparentAllowed", "UniquenessType"};
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public ServiceRepository getServiceRepository() {
        return nodeTypeService;
    }

    @Override
    public Composite createContents(Composite parent) {

        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);

        List<RecordDto> allByConfigId = nodeTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = nodeTypeDto.getId();
            vec[1] = nodeTypeDto.getDiscriminator();
            vec[2] = nodeTypeDto.getName();
            vec[3] = nodeTypeDto.getAppIcon();
            vec[4] = nodeTypeDto.getMapIcon();
            vec[5] = nodeTypeDto.getCapacityFull();
            vec[6] = nodeTypeDto.getCapacityUnitName();
            vec[7] = nodeTypeDto.getTypeClassPath();
            vec[8] = nodeTypeDto.getRootType();
            vec[9] = nodeTypeDto.getSystem();
            vec[10] = nodeTypeDto.getMultiparentAllowed();
            vec[11] = nodeTypeDto.getUniquenessType();

            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(vec);
        }

        return table;
    }

    @Override
    public void delete(int[] index) {
        super.delete(index);
        List<RecordDto> allByConfigId = nodeTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        if (index.length != 0) {
            nodeTypeService.delete(allByConfigId.get(index[0]));
        }
    }
}

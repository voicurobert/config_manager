package ro.dev.ree.cross_config_manager.ui.node_type_rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesService;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;

import java.util.List;

public class NodeTypeRulesGui extends TreeComposite {

    public static final String TREE_NAME = "Node Type Rules";

    public static String ID = "";

    private final NodeTypeRulesService nodeTypeRulesService = ConfigManagerContextProvider.getBean(NodeTypeRulesService.class);

    @Override
    public String[] columns() {
        return new String[]{"Child", "Parent",
                "CapacityCalculatorName", "MandatoryParent"};
    }

    @Override
    public String treeName() {
        return TREE_NAME;
    }

    @Override
    public String ID() {
        return ID;
    }

    @Override
    public ServiceRepository getServiceRepository() {
        return nodeTypeRulesService;
    }

    @Override
    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Tree tree = (Tree) super.createContents(parent);

        List<RecordDto> allByConfigId = nodeTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            NodeTypeRulesDto nodeTypeRulesDto = (NodeTypeRulesDto) recordDto;
            String[] vec = new String[columns().length];

            ID = nodeTypeRulesDto.getId();
            vec[0] = nodeTypeRulesDto.getChild();
            vec[1] = nodeTypeRulesDto.getParent();
            vec[2] = nodeTypeRulesDto.getCapacityCalculatorName();
            vec[3] = nodeTypeRulesDto.getMandatoryParent();

            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(vec);
        }

        return tree;
    }

    @Override
    public void delete(int[] index) {
        super.delete(index);
        List<RecordDto> allByConfigId = nodeTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        if (index.length != 0) {
            nodeTypeRulesService.delete(allByConfigId.get(index[0]));
        }
    }

}

package ro.dev.ree.cross_config_manager.ui.link_type_node_type_rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesService;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;

import java.util.List;

public class LinkTypeNodeTypeRulesGui extends TreeComposite {

    public static final String TREE_NAME = "Link Types Node Type Rules";
    private final LinkTypeNodeTypeRulesService linkTypeNodeTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeNodeTypeRulesService.class);

    @Override
    public String[] columns() {
        return new String[]{"LinkType", "NodeType",
                "Quality"};
    }

    @Override
    public String treeName() {
        return TREE_NAME;
    }

    @Override
    public ServiceRepository getServiceRepository() {
        return linkTypeNodeTypeRulesService;
    }


    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Tree tree = (Tree) super.createContents(parent);


        List<RecordDto> allByConfigId = linkTypeNodeTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        for (RecordDto recordDto : allByConfigId) {

            LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = (LinkTypeNodeTypeRulesDto) recordDto;

            String[] vec = new String[columns().length];
//            vec[0] = linkTypeNodeTypeRulesDto.getId();
//            vec[1] = linkTypeNodeTypeRulesDto.getConfigId();
            vec[0] = linkTypeNodeTypeRulesDto.getLinkType();
            vec[1] = linkTypeNodeTypeRulesDto.getNodeType();
            vec[2] = linkTypeNodeTypeRulesDto.getQuality();

            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(vec);
        }


        return tree;
    }

    @Override
    public void delete(int[] index) {
        super.delete(index);
        List<RecordDto> allByConfigId = linkTypeNodeTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        if (index.length != 0) {
            linkTypeNodeTypeRulesService.delete(allByConfigId.get(index[0]));
        }
    }
}
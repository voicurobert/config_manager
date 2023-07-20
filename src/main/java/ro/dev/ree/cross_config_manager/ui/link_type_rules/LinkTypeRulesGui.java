package ro.dev.ree.cross_config_manager.ui.link_type_rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesService;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;

import java.util.List;

public class LinkTypeRulesGui extends TreeComposite {

    private final LinkTypeRulesService linkTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeRulesService.class);

    public static final String TREE_NAME = "Link Types Rules";


    @Override
    public String[] columns() {
        return new String[]{"IdLTR", "ConfigId", "Consumer", "Provider",
                "RoutingPolicy", "CapacityCalculatorName", "NumberOfChannels"};
    }

    @Override
    public String treeName() {
        return TREE_NAME;
    }

    @Override
    public ServiceRepository getServiceRepository() {
        return linkTypeRulesService;
    }

    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Tree tree = (Tree) super.createContents(parent);
        tree.setToolTipText("LinkTypeRulesTree");

        List<RecordDto> allByConfigId = linkTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        for (int i = 0; i < allByConfigId.size(); i++) {

            LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) allByConfigId.get(i);
            String[] vec = new String[columns().length];
            vec[0] = linkTypeRulesDto.getId();
            vec[1] = linkTypeRulesDto.getConfigId();
            // vec[2] = linkTypeRulesDto.getConsumer();
            //vec[3] = linkTypeRulesDto.getProvider();
            vec[4] = linkTypeRulesDto.getCapacityCalculatorName();
            vec[5] = linkTypeRulesDto.getNumberOfChannels();
            vec[6] = linkTypeRulesDto.getRoutingPolicy();
            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(vec);
        }


        return tree;
    }
}

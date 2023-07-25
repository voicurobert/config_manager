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

    public static final String TREE_NAME = "Link Types Rules";

    public static String ID = "";

    private final LinkTypeRulesService linkTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeRulesService.class);

    @Override
    public String[] columns() {
        return new String[]{"Consumer", "Provider",
                "RoutingPolicy", "CapacityCalculatorName", "NumberOfChannels"};
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
        return linkTypeRulesService;
    }

    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Tree tree = (Tree) super.createContents(parent);


        List<RecordDto> allByConfigId = linkTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        for (RecordDto recordDto : allByConfigId) {
            LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
            String[] vec = new String[columns().length];

            ID = linkTypeRulesDto.getId();
            vec[0] = linkTypeRulesDto.getConsumer();
            vec[1] = linkTypeRulesDto.getProvider();
            vec[2] = linkTypeRulesDto.getRoutingPolicy();
            vec[3] = linkTypeRulesDto.getCapacityCalculatorName();
            vec[4] = linkTypeRulesDto.getNumberOfChannels();

            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(vec);
        }

        return tree;
    }

    @Override
    public void delete(int[] index) {
        super.delete(index);
        List<RecordDto> allByConfigId = linkTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        if (index.length != 0) {
            linkTypeRulesService.delete(allByConfigId.get(index[0]));
        }
    }
}

package ro.dev.ree.cross_config_manager.ui.link_type_node_type_rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesService;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;
import ro.dev.ree.cross_config_manager.xml.writer.XmlWriter;

import java.util.List;

public class LinkTypeNodeTypeRulesGui extends TreeComposite implements XmlWriter {

    public static final String TREE_NAME = "Link Type Node Type Rules";

    private final LinkTypeNodeTypeRulesService linkTypeNodeTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeNodeTypeRulesService.class);

    @Override
    public String[] columns() {
        return new String[]{"Id", "LinkType", "NodeType",
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

            vec[0] = linkTypeNodeTypeRulesDto.getId();
            vec[1] = linkTypeNodeTypeRulesDto.getLinkType();
            vec[2] = linkTypeNodeTypeRulesDto.getNodeType();
            vec[3] = linkTypeNodeTypeRulesDto.getQuality();

            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(vec);
        }


        return tree;
    }

    @Override
    public void delete(String id) {

        RecordDto recordDto = linkTypeNodeTypeRulesService.findById(id);
        linkTypeNodeTypeRulesService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {
        List<RecordDto> allByConfigId = linkTypeNodeTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element linkTypeNodeTypeRules = document.createElement("linkTypeNodeTypeRules");
        rootElement.appendChild(linkTypeNodeTypeRules);

        for (RecordDto recordDto : allByConfigId) {
            LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = (LinkTypeNodeTypeRulesDto) recordDto;
            linkTypeNodeTypeRulesDto.asXml(document,linkTypeNodeTypeRules);
        }
    }
}
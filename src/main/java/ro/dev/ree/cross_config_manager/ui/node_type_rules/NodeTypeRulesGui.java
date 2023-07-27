package ro.dev.ree.cross_config_manager.ui.node_type_rules;

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
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesService;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;
import ro.dev.ree.cross_config_manager.xml.writer.XmlWriter;

import java.util.List;

public class NodeTypeRulesGui extends TreeComposite implements XmlWriter {

    public static final String TREE_NAME = "Node Type Rules";

    private final NodeTypeRulesService nodeTypeRulesService = ConfigManagerContextProvider.getBean(NodeTypeRulesService.class);

    @Override
    public String[] columns() {
        return new String[]{"Id", "Child", "Parent",
                "CapacityCalculatorName", "MandatoryParent"};
    }

    @Override
    public String treeName() {
        return TREE_NAME;
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

            vec[0] =nodeTypeRulesDto.getId();
            vec[1] = nodeTypeRulesDto.getChild();
            vec[2] = nodeTypeRulesDto.getParent();
            vec[3] = nodeTypeRulesDto.getCapacityCalculatorName();
            vec[4] = nodeTypeRulesDto.getMandatoryParent();

            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(vec);
        }

        return tree;
    }

    @Override
    public void delete(String id) {

        RecordDto recordDto = nodeTypeRulesService.findById(id);
        nodeTypeRulesService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {

        List<RecordDto> allByConfigId = nodeTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element nodeTypeRules = document.createElement("nodeTypeRules");
        rootElement.appendChild(nodeTypeRules);

        for (RecordDto recordDto : allByConfigId) {
            NodeTypeRulesDto nodeTypeRulesDto = (NodeTypeRulesDto) recordDto;
            nodeTypeRulesDto.asXml(document,nodeTypeRules);
        }
    }
}

package ro.dev.ree.cross_config_manager.ui.link_type_rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesService;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class LinkTypeRulesGui extends TreeComposite implements ManageableComponent, XmlRead {

    public static final String TREE_NAME = "Link Types Rules";

    private final LinkTypeRulesService linkTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeRulesService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "consumer", "provider",
                "routingPolicy", "capacityCalculatorName", "numberOfChannels"};
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


        List<RecordDto> allByConfigId = linkTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        for (RecordDto recordDto : allByConfigId) {
            LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = linkTypeRulesDto.getId();
            vec[1] = linkTypeRulesDto.getConsumer();
            vec[2] = linkTypeRulesDto.getProvider();
            vec[3] = linkTypeRulesDto.getRoutingPolicy();
            vec[4] = linkTypeRulesDto.getCapacityCalculatorName();
            vec[5] = linkTypeRulesDto.getNumberOfChannels();

            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(vec);
        }

        return tree;
    }

    @Override
    public void delete(String id) {

        RecordDto recordDto = linkTypeRulesService.findById(id);
        linkTypeRulesService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {

        List<RecordDto> allByConfigId = linkTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element linkTypeRules = document.createElement("linkTypeRules");
        rootElement.appendChild(linkTypeRules);

        for (RecordDto recordDto : allByConfigId) {
            LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
            linkTypeRulesDto.asXml(document, linkTypeRules);
        }
    }

    @Override
    public void readElement(Element element) {


        Node header = element.getElementsByTagName("linkTypeRules").item(0);
        if (header != null) {
            NodeList nodeList = ((Element) header).getElementsByTagName("linkTypeRule");
            for (int i = 0; i < nodeList.getLength(); i++) {
                LinkTypeRulesDto linkTypeRulesDto = new LinkTypeRulesDto();
                linkTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    for (int idx = 1; idx < columns().length; idx++) {

                        for (Method declaredMethod : linkTypeRulesDto.getClass().getDeclaredMethods()) {
                            if (declaredMethod.getName().toLowerCase().contains(columns()[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                                try {
                                    if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                        break;
                                    }
                                    declaredMethod.invoke(linkTypeRulesDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                                    break;
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                    }
                    linkTypeRulesService.insertOrUpdate(linkTypeRulesDto);
                }
            }


        }
    }
}

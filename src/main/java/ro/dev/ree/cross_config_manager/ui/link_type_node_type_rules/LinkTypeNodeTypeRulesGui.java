package ro.dev.ree.cross_config_manager.ui.link_type_node_type_rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesService;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkTypeNodeTypeRulesGui extends TreeComposite implements ManageableComponent, XmlRead {

    public static final String TREE_NAME = "Link Type Node Type Rules";

    private final LinkTypeNodeTypeRulesService linkTypeNodeTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeNodeTypeRulesService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "linkType", "nodeType",
                "quality"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("linkType", new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY));
        map.put("nodeType", new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY));
        map.put("quality", new Text(parent, SWT.BORDER));

        return map;
    }

    @Override
    public Map<String, Object> values(String action, Map<String, Widget> columns) {
        var map = new LinkedHashMap<String, Object>();

        AtomicInteger i = new AtomicInteger();

        for (String name : columns.keySet()) {
            Widget widget = columns.get(name);
            if (widget instanceof Text) {
                if (tree.getSelection().length == 0 || action.equals("Add")) {
                    ((Text) widget).setText("");
                } else {
                    ((Text) widget).setText(tree.getSelection()[0].getText(i.get()));
                }
            } else if (widget instanceof Combo) {
                // Add options to the Combo
                if (name.equals("nodeType")) {
                    for (NodeTypeDto nodeTypeDto : linkTypeNodeTypeRulesService.listOfNodeTypeDtos()) {
                        ((Combo) widget).add(nodeTypeDto.getDiscriminator());
                    }
                } else if (name.equals("linkType")) {
                    for (LinkTypeDto linkTypeDto : linkTypeNodeTypeRulesService.listOfLinkTypeDtos()) {
                        ((Combo) widget).add(linkTypeDto.getDiscriminator());
                    }
                }
                if (action.equals("Update") && !(tree.getSelection().length == 0)) {
                    ((Combo) widget).select(((Combo) widget).indexOf(tree.getSelection()[0].getText(i.get())));
                }
            }
            if (tree.getSelection().length == 0 || action.equals("Add")) {
                map.put(name, "");
            } else {
                map.put(name, tree.getSelection()[0].getText(i.get()));
            }

            i.getAndIncrement();
        }

        return map;
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

        for (TreeColumn column : tree.getColumns()) {
            column.pack();
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
            linkTypeNodeTypeRulesDto.asXml(document, linkTypeNodeTypeRules);
        }
    }

    @Override
    public void readElement(Element element) {

        Node header = element.getElementsByTagName("linkTypeNodeTypeRules").item(0);
        if (header == null) {
            return;
        }
        NodeList nodeList = ((Element) header).getElementsByTagName("linkTypeNodeTypeRule");
        for (int i = 0; i < nodeList.getLength(); i++) {
            LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = new LinkTypeNodeTypeRulesDto();
            linkTypeNodeTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element eElement = (Element) node;

            for (int idx = 1; idx < columns().length; idx++) {

                for (Method declaredMethod : linkTypeNodeTypeRulesDto.getClass().getDeclaredMethods()) {
                    if (declaredMethod.getName().toLowerCase().contains(columns()[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                        try {
                            if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                break;
                            }
                            declaredMethod.invoke(linkTypeNodeTypeRulesDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                            break;
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            linkTypeNodeTypeRulesService.insertOrUpdate(linkTypeNodeTypeRulesDto);

        }

    }
}
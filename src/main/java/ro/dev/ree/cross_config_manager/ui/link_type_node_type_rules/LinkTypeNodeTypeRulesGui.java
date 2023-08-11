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
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
        map.put("linkType", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("nodeType", new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY));
        map.put("quality", new Text(parent, SWT.BORDER));

        return map;
    }

    @Override
    public Map<String, Object> values(String action, Map<String, Widget> columns) {
        var map = new LinkedHashMap<String, Object>();

        AtomicInteger i = new AtomicInteger();
        // Am scos sa se poata adauga in tree fara selectarea unui root
        // tree.getSelection().length == 0 ||
        for (String name : columns.keySet()) {
            Widget widget = columns.get(name);
            if (widget instanceof Text) {
                if (action.equals("Add")) {
                    //TODO de vazut daca se iau asa rooturile aici
                    if(name.equals("linkType")){
                        // Get Text from child root
                        ((Text) widget).setText(tree.getSelection()[0].getText(i.get()));
                        map.put(name, tree.getSelection()[0].getText(i.get()));
                    } else {
                        ((Text) widget).setText("");
                        map.put(name, "");
                    }
                } else if (action.equals("Update") && !(tree.getSelection().length == 0)) {
                    ((Text) widget).setText(tree.getSelection()[0].getText(i.get()));
                    map.put(name, tree.getSelection()[0].getText(i.get()));
                }
            } else if (widget instanceof Combo) {
                // Add options to the Combo
                //TODO de vazut daca se iau asa variantele aici
                if (name.equals("nodeType")) {
                    // Aici nu sunt sigura daca ar trebui luate toate sau doar cele cu rootType = false
                    for (NodeTypeDto nodeTypeDto : linkTypeNodeTypeRulesService.listOfNodeTypeDtos()) {
                        ((Combo) widget).add(nodeTypeDto.getDiscriminator());
                    }
                }
//                else if (name.equals("linkType")) {
//                    for (LinkTypeDto linkTypeDto : linkTypeNodeTypeRulesService.listOfLinkTypeDtos()) {
//                        ((Combo) widget).add(linkTypeDto.getDiscriminator());
//                    }
//                }
                if(action.equals("Add")){
                    map.put(name, "");
                } else if (action.equals("Update") && !(tree.getSelection().length == 0)) {
                    ((Combo) widget).select(((Combo) widget).indexOf(tree.getSelection()[0].getText(i.get())));
                    map.put(name, tree.getSelection()[0].getText(i.get()));
                }
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
        createTitle(parent);

        Tree tree = (Tree) super.createContents(parent);

        List<RecordDto> allByConfigId = linkTypeNodeTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        //TODO de vazut daca se iau asa rooturile aici
        List<String> linkTypeRoots = new ArrayList<>();
        for (LinkTypeDto linkTypeDto: linkTypeNodeTypeRulesService.listOfLinkTypeDtos()) {
            linkTypeRoots.add(linkTypeDto.getDiscriminator());
        }
        // Adding in tree roots
        for(String linkTypeRoot : linkTypeRoots){
            TreeItem root = new TreeItem(tree, SWT.NONE);
            // Set parent and child the same for root
            root.setText(0, "parentNode");
            root.setText(1, linkTypeRoot);
            for (RecordDto recordDto : allByConfigId) {
                if(root.getText(1).equals(((LinkTypeNodeTypeRulesDto) recordDto).getLinkType())){
                    LinkTypeNodeTypeRulesDto linkTypeNodeTypeRulesDto = (LinkTypeNodeTypeRulesDto) recordDto;
                    String[] vec = new String[columns().length];

                    vec[0] = linkTypeNodeTypeRulesDto.getId();
                    vec[1] = linkTypeNodeTypeRulesDto.getLinkType();
                    vec[2] = linkTypeNodeTypeRulesDto.getNodeType();
                    vec[3] = linkTypeNodeTypeRulesDto.getQuality();

                    TreeItem childItem = new TreeItem(root, SWT.NONE);
                    childItem.setText(vec);
                }
            }
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
            linkTypeNodeTypeRulesService.insertOrUpdate(null, linkTypeNodeTypeRulesDto);
        }
    }
}
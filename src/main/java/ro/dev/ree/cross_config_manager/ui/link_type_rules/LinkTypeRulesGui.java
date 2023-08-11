package ro.dev.ree.cross_config_manager.ui.link_type_rules;

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
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesService;
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

public class LinkTypeRulesGui extends TreeComposite implements ManageableComponent, XmlRead {

    public static final String TREE_NAME = "Link Types Rules";

    private final LinkTypeRulesService linkTypeRulesService = ConfigManagerContextProvider.getBean(LinkTypeRulesService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "consumer", "provider",
                "routingPolicy", "capacityCalculatorName", "numberOfChannels"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("consumer", new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY));
        map.put("provider", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("routingPolicy", new Text(parent, SWT.BORDER));
        map.put("capacityCalculatorName", new Text(parent, SWT.BORDER));
        map.put("numberOfChannels", new Text(parent, SWT.BORDER));

        return map;
    }

    @Override
    public Map<String, Object> values(String action, Map<String, Widget> columns) {
        var map = new LinkedHashMap<String, Object>();

        AtomicInteger i = new AtomicInteger();
        // Am scos sa se poata adauga in tree fara selectarea unui root
        // tree.getSelection().length == 0 ||
        String consumer = "";
        for (String name : columns.keySet()) {
            Widget widget = columns.get(name);
            consumer = name.equals("consumer") ? tree.getSelection()[0].getText(i.get()) : consumer;
            if (widget instanceof Text) {
                if (action.equals("Add")) {
                    if(name.equals("provider")){
                        // Get Text from child root
                        ((Text) widget).setText(consumer);
                        map.put(name, consumer);
                    } else {
                        ((Text) widget).setText("");
                        map.put(name, "");
                    }
                } else if (action.equals("Update") && !(tree.getSelection().length == 0)){
                    ((Text) widget).setText(tree.getSelection()[0].getText(i.get()));
                    map.put(name, tree.getSelection()[0].getText(i.get()));
                }
            } else if (widget instanceof Combo) {
                // Add options to the Combo
                for (LinkTypeDto linkTypeDto : linkTypeRulesService.listOfLinkTypeDtos()) {
                    ((Combo) widget).add(linkTypeDto.getDiscriminator());
                }
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
        return linkTypeRulesService;
    }

    public Composite createContents(Composite parent) {
        createTitle(parent);

        Tree tree = (Tree) super.createContents(parent);

        List<RecordDto> allByConfigId = linkTypeRulesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        List<String> linkTypeRoots = new ArrayList<>();
        for (LinkTypeDto linkTypeDto: linkTypeRulesService.listOfLinkTypeDtos()) {
            linkTypeRoots.add(linkTypeDto.getDiscriminator());
        }
        // Adding in tree roots
        for(String linkTypeRoot : linkTypeRoots){
            TreeItem root = new TreeItem(tree, SWT.NONE);
            // Set parent and child the same for root
            root.setText(0, "parentNode");
            root.setText(1, linkTypeRoot);
            addChildrenRecursively(root, allByConfigId);
        }

        for (TreeColumn column : tree.getColumns()) {
            column.pack();
        }

        return tree;
    }

    private boolean checkParentChain(TreeItem root, String parentNode, String childNode) {
        if (root == null)
            return false;
        if (root.getText(1).equals(childNode) && root.getText(2).equals(parentNode))
            return true;
        return checkParentChain(root.getParentItem(), parentNode, childNode);
    }

    private void addChildrenRecursively(TreeItem root, List<RecordDto> allByConfigId) {
        for (RecordDto recordDto : allByConfigId) {

            if(root.getText(1).equals(((LinkTypeRulesDto) recordDto).getProvider()) && !checkParentChain(root, ((LinkTypeRulesDto) recordDto).getProvider(), ((LinkTypeRulesDto) recordDto).getConsumer())){
                LinkTypeRulesDto linkTypeRulesDto = (LinkTypeRulesDto) recordDto;
                String[] vec = new String[columns().length];

                vec[0] = linkTypeRulesDto.getId();
                vec[1] = linkTypeRulesDto.getConsumer();
                vec[2] = linkTypeRulesDto.getProvider();
                vec[3] = linkTypeRulesDto.getRoutingPolicy();
                vec[4] = linkTypeRulesDto.getCapacityCalculatorName();
                vec[5] = linkTypeRulesDto.getNumberOfChannels();

                TreeItem childItem = new TreeItem(root, SWT.NONE);
                childItem.setText(vec);

                // Recursively add children for this childItem
                addChildrenRecursively(childItem, allByConfigId);
            }
        }
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
        if (header == null) {
            return;
        }
        NodeList nodeList = ((Element) header).getElementsByTagName("linkTypeRule");
        for (int i = 0; i < nodeList.getLength(); i++) {
            LinkTypeRulesDto linkTypeRulesDto = new LinkTypeRulesDto();
            linkTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
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
            linkTypeRulesService.insertOrUpdate(null, linkTypeRulesDto);
        }
    }
}

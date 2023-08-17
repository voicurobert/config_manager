package ro.dev.ree.cross_config_manager.ui.technology_tree;

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
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.technology_tree.TechnologyTreeDto;
import ro.dev.ree.cross_config_manager.model.technology_tree.TechnologyTreeService;
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

public class TechnologyTreeGui extends TreeComposite implements ManageableComponent, XmlRead {

    public static final String TREE_NAME = "Technology Tree";

    private final TechnologyTreeService technologyTreeService = ConfigManagerContextProvider.getBean(TechnologyTreeService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "name", "nodeType", "linkType"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("name", new Text(parent, SWT.BORDER));
        map.put("nodeType", new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY));
        map.put("linkType", new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY));

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
                    map.put(name, "");
                } else if (action.equals("Update") && !(tree.getSelection().length == 0)) {
                    ((Text) widget).setText(tree.getSelection()[0].getText(i.get()));
                    map.put(name, tree.getSelection()[0].getText(i.get()));
                }
            } else if (widget instanceof Combo) {
                // Add options to the Combo
                if (name.equals("linkType")) {
                    for (LinkTypeDto linkTypeDto : technologyTreeService.listOfLinkTypeDtos()) {
                        ((Combo) widget).add(linkTypeDto.getDiscriminator());
                    }
                } else if (name.equals("nodeType")) {
                    for (NodeTypeDto nodeTypeDto : technologyTreeService.listOfNodeTypeDtos()) {
                        ((Combo) widget).add(nodeTypeDto.getDiscriminator());
                    }
                }
                if (tree.getSelection().length == 0 || action.equals("Add")) {
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
        return technologyTreeService;
    }

    @Override
    public Composite createContents(Composite parent) {
        createTitle(parent);

        Tree tree = (Tree) super.createContents(parent);

        List<RecordDto> allByConfigId = technologyTreeService.findAllRoots(ConfigSingleton.getSingleton().getConfigDto().getId());
        for (RecordDto root : allByConfigId) {
            TechnologyTreeDto tt = (TechnologyTreeDto) root;
            // List<RecordDto> records = technologyTreeService.findAllByNameAndConfigId(tt.getName(), ConfigSingleton.getSingleton().getConfigDto().getId());
            List<RecordDto> linkTypes = technologyTreeService.findLinkTypesByNameAndConfigIdNew(tt.getName(), ConfigSingleton.getSingleton().getConfigDto().getId());
            List<RecordDto> nodeTypes = technologyTreeService.findNodeTypesByNameAndConfigIdNew(tt.getName(), ConfigSingleton.getSingleton().getConfigDto().getId());


            TreeItem treeItem = new TreeItem(tree, SWT.NONE);
            treeItem.setText(0, tt.getId());
            treeItem.setText(1, tt.getName());

            for (RecordDto node :  nodeTypes) {
                TechnologyTreeDto technologyTreeDto = (TechnologyTreeDto) node;
                String[] vec = new String[columns().length];

                vec[0] = technologyTreeDto.getId();
                vec[1] = technologyTreeDto.getName();
                vec[2] = technologyTreeDto.getNodeType();
                vec[3] = technologyTreeDto.getLinkType();
                TreeItem childItem = new TreeItem(treeItem, SWT.NONE);
                childItem.setText(vec);

            }

            for (RecordDto link : linkTypes) {
                TechnologyTreeDto technologyTreeDto = (TechnologyTreeDto) link;
                String[] vec = new String[columns().length];

                vec[0] = technologyTreeDto.getId();
                vec[1] = technologyTreeDto.getName();
                vec[2] = technologyTreeDto.getNodeType();
                vec[3] = technologyTreeDto.getLinkType();
                TreeItem childItem = new TreeItem(treeItem, SWT.NONE);
                childItem.setText(vec);

            }




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


    @Override
    public void delete(String id) {
        RecordDto recordDto = technologyTreeService.findById(id);
        technologyTreeService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {
        List<RecordDto> allByConfigId = technologyTreeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element technologyTree = document.createElement("technologyTrees");
        rootElement.appendChild(technologyTree);

        for (RecordDto recordDto : allByConfigId) {
            TechnologyTreeDto serviceStatusDto = (TechnologyTreeDto) recordDto;
            serviceStatusDto.asXml(document, technologyTree);
        }
    }

    @Override
    public void readElement(Element element) {

        Node header = element.getElementsByTagName("technologyTrees").item(0);
        if (header == null) {
            return;
        }
        NodeList nodeList = ((Element) header).getElementsByTagName("technologyTree");

        // 1. for all nodeList:
        // 1.1 get name of technologyTree
        // 1.2 get all nodeTypes for technologyTree; create a technology dto and insert it in database
        // 1.3 get all LinkTypes for technologyTree; create a technology dto and insert it in database


        for (int i = 0; i < nodeList.getLength(); i++) {
            List<String> listWithLinks = new ArrayList<>();
            List<String> listWithNodes = new ArrayList<>();
            TechnologyTreeDto technologyTreeDto = new TechnologyTreeDto();
            technologyTreeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element eElement = (Element) node;
            int x = 0;

            for (int i1 = 1; i1 < columns().length; i1++) {
                x = eElement.getElementsByTagName(columns()[i1]).getLength();

                for (int j = 0; j < x; j++) {
                    for (Method declaredMethod : technologyTreeDto.getClass().getDeclaredMethods()) {
                        if (declaredMethod.getName().toLowerCase().contains(columns()[i1].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                            try {
                                if (eElement.getElementsByTagName(columns()[i1]).getLength() == 0) {
                                    break;
                                }
                                declaredMethod.invoke(technologyTreeDto, eElement.getElementsByTagName(columns()[i1]).item(j).getTextContent());
                                technologyTreeService.insertOrUpdate(null, technologyTreeDto);
                                break;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }

                        }


                    }

                }
                //technologyTreeService.insertOrUpdate(null, technologyTreeDto);
            }
            technologyTreeService.insertOrUpdate(null, technologyTreeDto);
        }


    }
}


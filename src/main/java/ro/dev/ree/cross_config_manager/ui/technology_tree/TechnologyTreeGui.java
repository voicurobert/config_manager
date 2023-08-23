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
        List<TechnologyTreeDto> added = new ArrayList<>(0);
        for (RecordDto root : allByConfigId) {
            TechnologyTreeDto tt = (TechnologyTreeDto) root;
            boolean exists = false;
            if (!added.isEmpty()) {
                for (TechnologyTreeDto add : added) {
                    if (add.getName().equals(tt.getName())) {
                        exists = true;
                        break;
                    }
                }
            }
            if (!exists) {
                added.add(tt);
                 List<RecordDto> linkTypes = technologyTreeService.findLinkTypesByNameAndConfigIdNew(tt.getName(), ConfigSingleton.getSingleton().getConfigDto().getId());
                List<RecordDto> nodeTypes = technologyTreeService.findNodeTypesByNameAndConfigIdNew(tt.getName(), ConfigSingleton.getSingleton().getConfigDto().getId());

                TreeItem treeItem = new TreeItem(tree, SWT.NONE);
                treeItem.setText(0, tt.getId());
                treeItem.setText(1, tt.getName());

                for (RecordDto node : nodeTypes) {
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

        }


        for (TreeColumn column : tree.getColumns()) {
            column.pack();
        }

        return tree;
    }


    @Override
    public void delete(String id) {
        RecordDto recordDto = technologyTreeService.findById(id);
        technologyTreeService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {
        // root element
        Element technologyTree = document.createElement("technologyTrees");
        rootElement.appendChild(technologyTree);

        List<TechnologyTreeDto> added = new ArrayList<>(0);

        List<RecordDto> allByConfigId = technologyTreeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());


        for (RecordDto recordDto : allByConfigId) {
            TechnologyTreeDto technologyTreeDto = (TechnologyTreeDto) recordDto;

            boolean exists = false;
            if (!added.isEmpty()) {
                for (TechnologyTreeDto add : added) {
                    if (add.getName().equals(technologyTreeDto.getName())) {
                        exists = true;
                        break;
                    }
                }
            }
            if (!exists) {
                added.add(technologyTreeDto);
                technologyTreeDto.asXml(document, technologyTree);
            }

            technologyTreeDto.asXml(document, technologyTree);
        }
    }

    @Override
    public void readElement(Element element) {

        Node header = element.getElementsByTagName("technologyTrees").item(0);
        if (header == null) {
            return;
        }
        NodeList nodeList = ((Element) header).getElementsByTagName("technologyTree");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element eElement = (Element) node;
            String name = eElement.getElementsByTagName("name").item(0).getTextContent();
            TechnologyTreeDto technologyTreeDto = new TechnologyTreeDto();
            technologyTreeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            technologyTreeDto.setName(name);

            technologyTreeService.insertOrUpdate(null, technologyTreeDto);
            createTechnologyTree(eElement, name, "nodeType");
            createTechnologyTree(eElement, name, "linkType");
        }
    }

    private void createTechnologyTree(Element element, String name, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        for (int j = 0; j < nodes.getLength(); j++) {
            String content = nodes.item(j).getTextContent();
            TechnologyTreeDto technologyTreeDto = new TechnologyTreeDto();
            technologyTreeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            technologyTreeDto.setName(name);
            if (tagName.equals("linkType")) {
                technologyTreeDto.setLinkType(content);
            } else {
                technologyTreeDto.setNodeType(content);
            }
            technologyTreeService.insertOrUpdate(null, technologyTreeDto);
        }
    }
}


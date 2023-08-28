package ro.dev.ree.cross_config_manager.ui.ca_definition_set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.ca_definition.CaDefinitionDto;
import ro.dev.ree.cross_config_manager.model.ca_definition_set.CaDefinitionSetDto;
import ro.dev.ree.cross_config_manager.model.ca_definition_set.CaDefinitionSetService;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CaDefinitionSetGui extends TreeComposite implements ManageableComponent, XmlRead {

    public static final String TREE_NAME = "Ca Definition Set";

    private final CaDefinitionSetService caDefinitionSetService = ConfigManagerContextProvider.getBean(CaDefinitionSetService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "type", "name", "caDefinitionName"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("type", new Text(parent, SWT.BORDER));
        map.put("name", new Text(parent, SWT.BORDER));
        map.put("caDefinitionName", new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY));
        return map;
    }

    @Override
    public Map<String, Object> values(String action, Map<String, Widget> columns) {
        var map = new LinkedHashMap<String, Object>();

        AtomicInteger i = new AtomicInteger();
        String type="";
        String nameCaDefinition="";

        for (String name : columns.keySet()) {
            Widget widget = columns.get(name);
            if(tree.getSelection().length != 0) {
                type = name.equals("type") ? tree.getSelection()[0].getText(i.get()) : type;
                nameCaDefinition = name.equals("name") ? tree.getSelection()[0].getText(i.get()) : nameCaDefinition;
            }

            if (widget instanceof Text) {
                if (tree.getSelection().length == 0 || action.equals("Add")) {
                    if(name.equals("name")){
                        // Get Text from child root
                        ((Text) widget).setText(nameCaDefinition);
                        map.put(name, nameCaDefinition);
                    } else if(name.equals("type")){
                        ((Text) widget).setText(type);
                        map.put(name, type);
                    }
                    else{
                            ((Text) widget).setText("");
                            map.put(name, "");
                        }
                } else if (action.equals("Update") && !(tree.getSelection().length == 0)) {
                    ((Text) widget).setText(tree.getSelection()[0].getText(i.get()));
                    map.put(name, tree.getSelection()[0].getText(i.get()));
                }
            } else if (widget instanceof Combo) {
                // Add options to the Combo
                for (CaDefinitionDto caDefinitionDto : caDefinitionSetService.listOfCaDefinitionDtos()) {
                    ((Combo) widget).add(caDefinitionDto.getAttributeName());
                }
                if (action.equals("Update") && !(tree.getSelection().length == 0)) {
                    ((Combo) widget).select(((Combo) widget).indexOf(tree.getSelection()[0].getText(i.get())));
                    map.put(name, tree.getSelection()[0].getText(i.get()));
                }
                else if(tree.getSelection().length == 0 || action.equals("Add")){
                    map.put(name, "");
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
        return caDefinitionSetService;
    }

    @Override
    public Composite createContents(Composite parent) {

        createTitle(parent);

        Tree tree = (Tree) super.createContents(parent);

        List<RecordDto> allByConfigId = caDefinitionSetService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        List<CaDefinitionSetDto> added = new ArrayList<>(0);
        for (RecordDto root : allByConfigId) {
            CaDefinitionSetDto caDefinitionSetDto = (CaDefinitionSetDto) root;
            // A method which verify if the parent node exists already
            boolean exists = false;
            if(!added.isEmpty()){
                for (CaDefinitionSetDto add: added) {
                    if (add.getName().equals(caDefinitionSetDto.getName()) && add.getType().equals(caDefinitionSetDto.getType())) {
                        exists = true;
                        break;
                    }
                }
            }
            if(!exists){
                added.add(caDefinitionSetDto);
                List<RecordDto> caDefinitionNames = caDefinitionSetService.findCaDefinitionNamesByNameTypeAndConfigId(caDefinitionSetDto.getName(), caDefinitionSetDto.getType(), ConfigSingleton.getSingleton().getConfigDto().getId());

                TreeItem treeItem = new TreeItem(tree, SWT.NONE);
                treeItem.setText(0, caDefinitionSetDto.getId());
                treeItem.setText(1, caDefinitionSetDto.getType());
                treeItem.setText(2, caDefinitionSetDto.getName());

                for (RecordDto node : caDefinitionNames) {
                    CaDefinitionSetDto caDefinitionSetDto1 = (CaDefinitionSetDto) node;
                    String[] vec = new String[columns().length];

                    vec[0] = caDefinitionSetDto1.getId();
                    vec[1] = caDefinitionSetDto1.getType();
                    vec[2] = caDefinitionSetDto1.getName();
                    vec[3] = caDefinitionSetDto1.getCaDefinitionName();

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
        RecordDto recordDto = caDefinitionSetService.findById(id);
        caDefinitionSetService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void readElement(Element element) {
        Node header = element.getElementsByTagName("caDefinitionSets").item(0);
        if (header == null) {
            return;
        }
        NodeList nodeList = ((Element) header).getElementsByTagName("caDefinitionSet");
        
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element eElement = (Element) node;
            String name = eElement.getElementsByTagName("name").item(0).getTextContent();
            String type = eElement.getElementsByTagName("type").item(0).getTextContent();
            CaDefinitionSetDto caDefinitionSetDto = new CaDefinitionSetDto();
            caDefinitionSetDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            caDefinitionSetDto.setName(name);
            caDefinitionSetDto.setType(type);

            caDefinitionSetService.insertOrUpdate(null, caDefinitionSetDto);
            createCaDefinitionSet(eElement, name, type);
        }
    }

    private void createCaDefinitionSet(Element element, String name, String type) {
        NodeList nodes = element.getElementsByTagName("caDefinitionName");
        for (int j = 0; j < nodes.getLength(); j++) {
            String content = nodes.item(j).getTextContent();
            CaDefinitionSetDto caDefinitionSetDto = new CaDefinitionSetDto();
            caDefinitionSetDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            caDefinitionSetDto.setName(name);
            caDefinitionSetDto.setType(type);
            caDefinitionSetDto.setCaDefinitionName(content);

            caDefinitionSetService.insertOrUpdate(null, caDefinitionSetDto);
        }
    }


    @Override
    public void xmlElements(Document document, Element rootElement) {
        // root element
        Element caDefinitionSets = document.createElement("caDefinitionSets");
        rootElement.appendChild(caDefinitionSets);
        List<CaDefinitionSetDto> added = new ArrayList<>(0);

        List<RecordDto> allByConfigId = caDefinitionSetService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            CaDefinitionSetDto caDefinitionSetDto = (CaDefinitionSetDto) recordDto;
            // A method which verify if the parent node exists already
            boolean exists = false;
            if(!added.isEmpty()){
                for (CaDefinitionSetDto add : added) {
                    if (add.getName().equals(caDefinitionSetDto.getName()) && add.getType().equals(caDefinitionSetDto.getType())) {
                        exists = true;
                        break;
                    }
                }
            }
            if(!exists){
                added.add(caDefinitionSetDto);
                caDefinitionSetDto.asXml(document, caDefinitionSets);
            }
        }
    }
}

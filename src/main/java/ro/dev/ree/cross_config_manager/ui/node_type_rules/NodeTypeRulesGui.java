package ro.dev.ree.cross_config_manager.ui.node_type_rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.class_type.ClassTypeDto;
import ro.dev.ree.cross_config_manager.model.class_type.ClassTypeService;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesService;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NodeTypeRulesGui extends TreeComposite implements ManageableComponent, XmlRead {

    public static final String TREE_NAME = "Node Type Rules";

    private final NodeTypeRulesService nodeTypeRulesService = ConfigManagerContextProvider.getBean(NodeTypeRulesService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "child", "parent",
                "capacityCalculatorName", "mandatoryParent"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", null);
        map.put("child", new Text(parent, SWT.BORDER));
        map.put("parent", new Text(parent, SWT.BORDER));
        map.put("capacityCalculatorName", new Text(parent, SWT.BORDER));
        map.put("mandatoryParent", new Text(parent, SWT.BORDER));

        return map;
    }

    @Override
    public Map<String, Object> values(String action, Map<String, Widget> columns) {
        var map = new LinkedHashMap<String, Object>();

        AtomicInteger i = new AtomicInteger();

        for (String name : columns.keySet()) {
            Widget widget = columns.get(name);
            if (widget instanceof Text) {
                // Sau sa las fara action.equals("Add") si sa ia totusi componentele de la el selectat
                if(tree.getSelection().length == 0 || action.equals("Add")){
                    ((Text)widget).setText("");
                }
                else{
                    ((Text)widget).setText(tree.getSelection()[0].getText(i.get()));
                }
            } else if (widget instanceof Combo) {
                ClassTypeService classTypeService = ConfigManagerContextProvider.getBean(ClassTypeService.class);
                List<ClassTypeDto> classTypeDtos = classTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                        map(recordDto -> (ClassTypeDto) recordDto).toList();

                // Add options to the Combo
                for (ClassTypeDto classTypeDto : classTypeDtos) {
                    ((Combo)widget).add(classTypeDto.getName());
                }
                if (action.equals("Update") && !(tree.getSelection().length == 0)) {
                    ((Combo)widget).select(((Combo)widget).indexOf(tree.getSelection()[0].getText(i.get())));
                }
            }
            if(tree.getSelection().length == 0){
                map.put(name, "");
            }
            else {
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

            vec[0] = nodeTypeRulesDto.getId();
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
            nodeTypeRulesDto.asXml(document, nodeTypeRules);
        }
    }

    @Override
    public void readElement(Element element) {


        Node header = element.getElementsByTagName("nodeTypeRules").item(0);
        if (header != null) {
            NodeList nodeList = ((Element) header).getElementsByTagName("nodeTypeRule");
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeTypeRulesDto nodeTypeRulesDto = new NodeTypeRulesDto();
                nodeTypeRulesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    for (int idx = 1; idx < columns().length; idx++) {

                        for (Method declaredMethod : nodeTypeRulesDto.getClass().getDeclaredMethods()) {
                            if (declaredMethod.getName().toLowerCase().contains(columns()[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                                try {
                                    if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                        break;
                                    }
                                    declaredMethod.invoke(nodeTypeRulesDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                                    break;
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                    }
                    nodeTypeRulesService.insertOrUpdate(nodeTypeRulesDto);
                }
            }


        }
    }
}

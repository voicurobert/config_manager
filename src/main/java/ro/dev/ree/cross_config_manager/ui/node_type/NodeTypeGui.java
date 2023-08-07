package ro.dev.ree.cross_config_manager.ui.node_type;

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
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NodeTypeGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Node Type";

    private final NodeTypeService nodeTypeService = ConfigManagerContextProvider.getBean(NodeTypeService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "discriminator", "name", "appIcon", "mapIcon",
                "capacityFull", "capacityUnitName", "typeClassPath",
                "rootType", "system", "multiparentAllowed", "uniquenessType"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("discriminator", new Text(parent, SWT.BORDER));
        map.put("name", new Text(parent, SWT.BORDER));
        map.put("appIcon", new Text(parent, SWT.BORDER));
        map.put("mapIcon", new Text(parent, SWT.BORDER));
        map.put("capacityFull", new Text(parent, SWT.BORDER));
        map.put("capacityUnitName", new Text(parent, SWT.BORDER));
        map.put("typeClassPath", new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY));
        map.put("rootType", new Button(parent, SWT.CHECK));
        map.put("system", new Button(parent, SWT.CHECK));
        map.put("multiparentAllowed", new Button(parent, SWT.CHECK));
        map.put("uniquenessType", new Text(parent, SWT.BORDER));

        return map;
    }

    @Override
    public Map<String, Object> values(String action, Map<String, Widget> columns) {
        var map = new LinkedHashMap<String, Object>();

        AtomicInteger i = new AtomicInteger();

        for (String name : columns.keySet()) {
            Widget widget = columns.get(name);
            if (widget instanceof Text) {
                if(table.getSelection().length == 0 || action.equals("Add")){
                    ((Text)widget).setText("");
                }
                else{
                    ((Text)widget).setText(table.getSelection()[0].getText(i.get()));
                }
            } else if (widget instanceof Combo) {
                // Add options to the Combo
                for (CoreClassTypeDto coreClassTypeDto : nodeTypeService.listOfCoreClassTypeDtos()) {
                        ((Combo)widget).add(coreClassTypeDto.getPath());
                }
                if (action.equals("Update") && !(table.getSelection().length == 0)) {
                    ((Combo)widget).select(((Combo)widget).indexOf(table.getSelection()[0].getText(i.get())));
                }
            } else if (widget instanceof Button) {
                if(table.getSelection().length == 0 || action.equals("Add")){
                    ((Button)widget).setText("");
                }
                else {
                    ((Button)widget).setText(table.getSelection()[0].getText(i.get()));
                    if(table.getSelection()[0].getText(i.get()).equals("true")){
                        ((Button)widget).setSelection(true);
                    }
                }
            }
            if(table.getSelection().length == 0 || action.equals("Add")){
                map.put(name, "");
            }
            else {
                map.put(name, table.getSelection()[0].getText(i.get()));
            }

            i.getAndIncrement();
        }

        return map;
    }


    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public ServiceRepository getServiceRepository() {
        return nodeTypeService;
    }

    @Override
    public Composite createContents(Composite parent) {

        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);

        List<RecordDto> allByConfigId = nodeTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = nodeTypeDto.getId();
            vec[1] = nodeTypeDto.getDiscriminator();
            vec[2] = nodeTypeDto.getName();
            vec[3] = nodeTypeDto.getAppIcon();
            vec[4] = nodeTypeDto.getMapIcon();
            vec[5] = nodeTypeDto.getCapacityFull();
            vec[6] = nodeTypeDto.getCapacityUnitName();
            vec[7] = nodeTypeDto.getTypeClassPath();
            vec[8] = nodeTypeDto.getRootType();
            vec[9] = nodeTypeDto.getSystem();
            vec[10] = nodeTypeDto.getMultiparentAllowed();
            vec[11] = nodeTypeDto.getUniquenessType();

            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(vec);
        }

        for (TableColumn column : table.getColumns()) {
            column.pack();
        }

        return table;
    }

    @Override
    public void delete(String id) {
        RecordDto recordDto = nodeTypeService.findById(id);
        nodeTypeService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {

        List<RecordDto> allByConfigId = nodeTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element nodeTypes = document.createElement("nodeTypes");
        rootElement.appendChild(nodeTypes);

        for (RecordDto recordDto : allByConfigId) {
            NodeTypeDto nodeTypeDto = (NodeTypeDto) recordDto;
            nodeTypeDto.asXml(document, nodeTypes);
        }
    }

    @Override
    public void readElement(Element element) {

        Node header = element.getElementsByTagName("nodeTypes").item(0);
        if (header != null) {
            NodeList nodeList = ((Element) header).getElementsByTagName("nodeType");
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeTypeDto nodeTypeDto = new NodeTypeDto();
                nodeTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    for (int idx = 1; idx < columns().length; idx++) {

                        for (Method declaredMethod : nodeTypeDto.getClass().getDeclaredMethods()) {
                            if (declaredMethod.getName().toLowerCase().contains(columns()[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                                try {
                                    if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                        break;
                                    }
                                    declaredMethod.invoke(nodeTypeDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                                    break;
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                    nodeTypeService.insertOrUpdate(nodeTypeDto);
                }
            }
        }
    }
}

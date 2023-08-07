package ro.dev.ree.cross_config_manager.ui.component_status;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.component_status.ComponentStatusDto;
import ro.dev.ree.cross_config_manager.model.component_status.ComponentStatusService;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ComponentStatusGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Component Status";

    private final ComponentStatusService componentStatusService = ConfigManagerContextProvider.getBean(ComponentStatusService.class);


    @Override
    public String[] columns() {
        return new String[]{"id", "name", "description", "color"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("name", new Text(parent, SWT.BORDER));
        map.put("discriminator", new Text(parent, SWT.BORDER));
        map.put("color", new Text(parent, SWT.BORDER));

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
                if (table.getSelection().length == 0 || action.equals("Add")) {
                    ((Text) widget).setText("");
                } else {
                    ((Text) widget).setText(table.getSelection()[0].getText(i.get()));
                }
            } else if (widget instanceof Combo) {
                ComponentStatusService componentStatusService = ConfigManagerContextProvider.getBean(ComponentStatusService.class);
                List<ComponentStatusDto> componentStatusDtos = componentStatusService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                        map(recordDto -> (ComponentStatusDto) recordDto).toList();

                // Add options to the Combo
                for (ComponentStatusDto componentStatusDto : componentStatusDtos) {
                    ((Combo) widget).add(componentStatusDto.getName());
                }
                if (action.equals("Update") && !(table.getSelection().length == 0)) {
                    ((Combo) widget).select(((Combo) widget).indexOf(table.getSelection()[0].getText(i.get())));
                }
            }
            if (table.getSelection().length == 0) {
                map.put(name, "");
            } else {
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
        return componentStatusService;
    }

    @Override
    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);
        List<RecordDto> allByConfigId = componentStatusService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            ComponentStatusDto componentStatusDto = (ComponentStatusDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = componentStatusDto.getId();
            vec[1] = componentStatusDto.getName();
            vec[2] = componentStatusDto.getDescription();
            vec[3] = componentStatusDto.getColor();

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
        RecordDto recordDto = componentStatusService.findById(id);
        componentStatusService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {

        List<RecordDto> allByConfigId = componentStatusService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element componentStatus = document.createElement("componentStatus");
        rootElement.appendChild(componentStatus);

        for (RecordDto recordDto : allByConfigId) {
            ComponentStatusDto componentStatusDto = (ComponentStatusDto) recordDto;
            componentStatusDto.asXml(document, componentStatus);
        }
    }

    @Override
    public void readElement(Element element) {

        Node header = element.getElementsByTagName("serviceComponentStatuses").item(0);
        if (header != null) {
            NodeList nodeList = ((Element) header).getElementsByTagName("componentStatus");

            for (int i = 0; i < nodeList.getLength(); i++) {
                ComponentStatusDto componentStatusDto = new ComponentStatusDto();
                componentStatusDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;

                    columnsMap().keySet().forEach(name -> {
                        for (Method declaredMethod : componentStatusDto.getClass().getDeclaredMethods()) {
                            if (declaredMethod.getName().toLowerCase().contains(name.toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                                try {
                                    if (eElement.getElementsByTagName(name).getLength() == 0) {
                                        break;
                                    }
                                    declaredMethod.invoke(componentStatusDto, eElement.getElementsByTagName(name).item(0).getTextContent());
                                    break;
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });

                    componentStatusService.insertOrUpdate(componentStatusDto);
                }
            }
        }
    }
}
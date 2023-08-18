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
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CaDefinitionSetGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Ca Definition Set";

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

        for (String name : columns.keySet()) {
            Widget widget = columns.get(name);
            if (widget instanceof Text) {
                if (table.getSelection().length == 0 || action.equals("Add")) {
                    ((Text) widget).setText("");
                    map.put(name, "");
                } else if (action.equals("Update") && !(table.getSelection().length == 0)) {
                    ((Text) widget).setText(table.getSelection()[0].getText(i.get()));
                    map.put(name, table.getSelection()[0].getText(i.get()));
                }
            } else if (widget instanceof Combo) {
                // Add options to the Combo
                for (CaDefinitionDto caDefinitionDto : caDefinitionSetService.listOfCaDefinitionDtos()) {
                    ((Combo) widget).add(caDefinitionDto.getAttributeName());
                }
                if (action.equals("Update") && !(table.getSelection().length == 0)) {
                    ((Combo) widget).select(((Combo) widget).indexOf(table.getSelection()[0].getText(i.get())));
                    map.put(name, table.getSelection()[0].getText(i.get()));
                }
                else if(table.getSelection().length == 0 || action.equals("Add")){
                    map.put(name, "");
                }
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
        return caDefinitionSetService;
    }

    @Override
    public Composite createContents(Composite parent) {

        createTitle(parent);

        Table table = (Table) super.createContents(parent);

        List<RecordDto> allByConfigId = caDefinitionSetService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            CaDefinitionSetDto caDefinitionSetDto = (CaDefinitionSetDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = caDefinitionSetDto.getId();
            vec[1] = caDefinitionSetDto.getType();
            vec[2] = caDefinitionSetDto.getName();
            vec[3] = caDefinitionSetDto.getCaDefinitionName();

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
        RecordDto recordDto = caDefinitionSetService.findById(id);
        caDefinitionSetService.delete(recordDto);
        super.delete(id);
    }

    //TODO de modificat citirea din XML sa-mi ia mai multe atribute sub acelasi nume
    @Override
    public void readElement(Element element) {
        Node header = element.getElementsByTagName("caDefinitionSets").item(0);
        if (header == null) {
            return;
        }
        NodeList nodeList = ((Element) header).getElementsByTagName("caDefinitionSet");
        for (int i = 0; i < nodeList.getLength(); i++) {
            CaDefinitionSetDto caDefinitionSetDto = new CaDefinitionSetDto();
            caDefinitionSetDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element eElement = (Element) node;

            for (int idx = 1; idx < columns().length; idx++) {

                for (Method declaredMethod : caDefinitionSetDto.getClass().getDeclaredMethods()) {
                    if (declaredMethod.getName().toLowerCase().contains(columns()[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                        try {
                            if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                break;
                            }
                            declaredMethod.invoke(caDefinitionSetDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                            break;
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            caDefinitionSetService.insertOrUpdate(null, caDefinitionSetDto);
        }
    }


    @Override
    public void xmlElements(Document document, Element rootElement) {
        List<RecordDto> allByConfigId = caDefinitionSetService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element caDefinitionSets = document.createElement("caDefinitionSets");
        rootElement.appendChild(caDefinitionSets);

        for (RecordDto recordDto : allByConfigId) {
            CaDefinitionSetDto caDefinitionSetDto = (CaDefinitionSetDto) recordDto;
            //TODO de modificat in asXml sa am mai multe atribute in acelasi caDefinitionSet
            caDefinitionSetDto.asXml(document, caDefinitionSets);
        }
    }
}

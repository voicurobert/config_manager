package ro.dev.ree.cross_config_manager.ui.ca_definition_and_message;

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
import ro.dev.ree.cross_config_manager.model.ca_definition.CaDefinitionService;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.message.MessageDto;
import ro.dev.ree.cross_config_manager.model.message.MessageService;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaDefinitionAndMessageGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Ca Definition And Message";

    private final CaDefinitionService caDefinitionService = ConfigManagerContextProvider.getBean(CaDefinitionService.class);

    private final MessageService messageService = ConfigManagerContextProvider.getBean(MessageService.class);


    @Override
    public String[] columns() {
        return new String[]{"id", "attributeName", "attributeClass", "message"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("attributeName", new Text(parent, SWT.BORDER));
        map.put("attributeClass", new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY));
        map.put("message", new Text(parent, SWT.BORDER));
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
                String[] attributeClasses = {"com.cross_ni.cross.db.pojo.ca.CAAttachmentSet",
                        "com.cross_ni.cross.db.pojo.address.Address", "java.lang.Boolean", "java.lang.String",
                        "java.lang.Integer", "java.lang.Double", "java.lang.Long", "java.time.LocalDateTime"};
                for (String attributeClass : attributeClasses) {
                    ((Combo) widget).add(attributeClass);
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

    //TODO de vazut cum fac cu asta
    @Override
    public ServiceRepository getServiceRepository() {
        return caDefinitionService;
    }

    public ServiceRepository getAnotherServiceRepository() {
        return messageService;
    }

    @Override
    public Composite createContents(Composite parent) {

        createTitle(parent);

        Table table = (Table) super.createContents(parent);

        List<RecordDto> allByConfigId1 = caDefinitionService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        List<RecordDto> allByConfigId2 = messageService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto1 : allByConfigId1) {
            CaDefinitionDto caDefinitionDto = (CaDefinitionDto) recordDto1;
            String[] vec = new String[columns().length];
            for(RecordDto recordDto2 : allByConfigId2){
                MessageDto messageDto = (MessageDto) recordDto2;

                // Define a regular expression pattern
                String pattern = "customAttribute\\.(.*?)\\.name";

                // Create a Pattern object
                Pattern regex = Pattern.compile(pattern);

                // Create a Matcher object
                Matcher matcher = regex.matcher(messageDto.getCode());

                // Check if a match is found
                if (matcher.find()) {
                    // Extract the captured group (substring between customAttribute. and .name)
                    String extracted = matcher.group(1);
                    if(caDefinitionDto.getAttributeName().equals(extracted)) {

                        vec[0] = caDefinitionDto.getId() + "," + messageDto.getId();
                        vec[1] = caDefinitionDto.getAttributeName();
                        vec[2] = caDefinitionDto.getAttributeClass();
                        vec[3] = messageDto.getMessage();

                        TableItem item = new TableItem(table, SWT.NONE);
                        item.setText(vec);
                    }
                }
            }
        }

        for (TableColumn column : table.getColumns()) {
            column.pack();
        }

        return table;
    }

    @Override
    public void delete(String id) {
        RecordDto recordDto1 = caDefinitionService.findById(id.split(",")[0]);
        RecordDto recordDto2 = messageService.findById(id.split(",")[1]);
        caDefinitionService.delete(recordDto1);
        messageService.delete(recordDto2);
        super.delete(id);
    }

    @Override
    public void readElement(Element element) {
        Node header1 = element.getElementsByTagName("caDefinitions").item(0);
        Node header2 = element.getElementsByTagName("messages").item(0);
        if (header1 == null && header2 == null) {
            return;
        }
        if(header1 != null){
            NodeList nodeList = ((Element) header1).getElementsByTagName("caDefinition");
            for (int i = 0; i < nodeList.getLength(); i++) {
                CaDefinitionDto caDefinitionDto = new CaDefinitionDto();
                caDefinitionDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
                Node node = nodeList.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element eElement = (Element) node;

                String[] columns = {"id", "attributeName", "attributeClass", "unique"};
                for (int idx = 1; idx < columns.length ; idx++) {

                    for (Method declaredMethod : caDefinitionDto.getClass().getDeclaredMethods()) {
                        if (declaredMethod.getName().toLowerCase().contains(columns[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                            try {
                                if (eElement.getElementsByTagName(columns[idx]).getLength() == 0) {
                                    break;
                                }
                                declaredMethod.invoke(caDefinitionDto, eElement.getElementsByTagName(columns[idx]).item(0).getTextContent());
                                break;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                caDefinitionService.insertOrUpdate(null, caDefinitionDto);
            }
        }
        if (header2 != null) {
            NodeList nodeList = ((Element) header2).getElementsByTagName("message");
            for (int i = 0; i < nodeList.getLength(); i++) {
                MessageDto messageDto = new MessageDto();
                messageDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
                Node node = nodeList.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element eElement = (Element) node;

                String[] columns = {"id", "code", "locale", "message"};
                for (int idx = 1; idx < columns.length; idx++) {

                    for (Method declaredMethod : messageDto.getClass().getDeclaredMethods()) {
                        if (declaredMethod.getName().toLowerCase().contains(columns[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                            try {
                                if (eElement.getElementsByTagName(columns[idx]).getLength() == 0) {
                                    break;
                                }
                                declaredMethod.invoke(messageDto, eElement.getElementsByTagName(columns[idx]).item(0).getTextContent());
                                break;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                if(messageDto.getCode() == null){
                    continue;
                }
                messageService.insertOrUpdate(null, messageDto);
            }
        }
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {
        List<RecordDto> allByConfigId1 = caDefinitionService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element caDefinitions = document.createElement("caDefinitions");
        rootElement.appendChild(caDefinitions);

        for (RecordDto recordDto : allByConfigId1) {
            CaDefinitionDto caDefinitionDto = (CaDefinitionDto) recordDto;
            caDefinitionDto.asXml(document, caDefinitions);
        }

        List<RecordDto> allByConfigId2 = messageService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element messages = document.createElement("messages");
        rootElement.appendChild(messages);
        for (RecordDto recordDto : allByConfigId2) {
            MessageDto MessageDto = (MessageDto) recordDto;
            MessageDto.asXml(document, messages);
        }
    }
}

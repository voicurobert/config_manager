package ro.dev.ree.cross_config_manager.ui.link_status;

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
import ro.dev.ree.cross_config_manager.model.link_status.LinkStatusDto;
import ro.dev.ree.cross_config_manager.model.link_status.LinkStatusService;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkStatusGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Link Status";

    private final LinkStatusService linkStatusService = ConfigManagerContextProvider.getBean(LinkStatusService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "discriminator", "name", "colorCode", "capacityConsumer",
                "consumerCandidate", "providerCandidate"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("discriminator", new Text(parent, SWT.BORDER));
        map.put("name", new Text(parent, SWT.BORDER));
        map.put("colorCode", new Text(parent, SWT.BORDER));
        map.put("capacityConsumer", new Button(parent, SWT.CHECK));
        map.put("consumerCandidate", new Button(parent, SWT.CHECK));
        map.put("providerCandidate", new Button(parent, SWT.CHECK));

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

            } else if (widget instanceof Button) {
                if (table.getSelection().length == 0 || action.equals("Add")) {
                    ((Button) widget).setText("");
                } else {
                    ((Button) widget).setText(table.getSelection()[0].getText(i.get()));
                    if (table.getSelection()[0].getText(i.get()).equals("true")) {
                        ((Button) widget).setSelection(true);
                    }
                }
            }
            if (table.getSelection().length == 0 || action.equals("Add")) {
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
        return linkStatusService;
    }

    @Override
    public Composite createContents(Composite parent) {
        createTitle(parent);

        Table table = (Table) super.createContents(parent);

        List<RecordDto> allByConfigId = linkStatusService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            LinkStatusDto linkStatusDto = (LinkStatusDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = linkStatusDto.getId();
            vec[1] = linkStatusDto.getDiscriminator();
            vec[2] = linkStatusDto.getName();
            vec[3] = linkStatusDto.getColorCode();
            vec[4] = linkStatusDto.getCapacityConsumer();
            vec[5] = linkStatusDto.getConsumerCandidate();
            vec[6] = linkStatusDto.getProviderCandidate();


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
        RecordDto recordDto = linkStatusService.findById(id);
        linkStatusService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {
        List<RecordDto> allByConfigId = linkStatusService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element linkStatus = document.createElement("linkStatuses");
        rootElement.appendChild(linkStatus);

        for (RecordDto recordDto : allByConfigId) {
            LinkStatusDto linkStatusDto = (LinkStatusDto) recordDto;
            linkStatusDto.asXml(document, linkStatus);
        }
    }

    @Override
    public void readElement(Element element) {

        Node header = element.getElementsByTagName("linkStatuses").item(0);
        if (header == null) {
            return;
        }
        NodeList nodeList = ((Element) header).getElementsByTagName("linkStatus");
        for (int i = 0; i < nodeList.getLength(); i++) {
            LinkStatusDto linkStatusDto = new LinkStatusDto();
            linkStatusDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element eElement = (Element) node;

            for (int idx = 1; idx < columns().length; idx++) {

                for (Method declaredMethod : linkStatusDto.getClass().getDeclaredMethods()) {
                    if (declaredMethod.getName().toLowerCase().contains(columns()[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                        try {
                            if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                break;
                            }
                            declaredMethod.invoke(linkStatusDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                            break;
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            linkStatusService.insertOrUpdate(null, linkStatusDto);
        }
    }
}

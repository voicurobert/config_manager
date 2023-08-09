package ro.dev.ree.cross_config_manager.ui.service_status;

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
import ro.dev.ree.cross_config_manager.model.service_status.ServiceStatusDto;
import ro.dev.ree.cross_config_manager.model.service_status.ServiceStatusService;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class ServiceStatusGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Service Status";

    private final ServiceStatusService serviceStatusService = ConfigManagerContextProvider.getBean(ServiceStatusService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "name", "description", "color"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        return null;
    }

    @Override
    public Map<String, Object> values(String action, Map<String, Widget> columns) {
        return null;
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public ServiceRepository getServiceRepository() {
        return serviceStatusService;
    }

    @Override
    public Composite createContents(Composite parent) {
        createTitle(parent);

        Table table = (Table) super.createContents(parent);

        List<RecordDto> allByConfigId = serviceStatusService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            ServiceStatusDto serviceStatusDto = (ServiceStatusDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = serviceStatusDto.getId();
            vec[1] = serviceStatusDto.getName();
            vec[2] = serviceStatusDto.getDescription();
            vec[3] = serviceStatusDto.getColor();

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
        RecordDto recordDto = serviceStatusService.findById(id);
        serviceStatusService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {
        List<RecordDto> allByConfigId = serviceStatusService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element nodeStatus = document.createElement("nodeStatuses");
        rootElement.appendChild(nodeStatus);

        for (RecordDto recordDto : allByConfigId) {
            ServiceStatusDto serviceStatusDto = (ServiceStatusDto) recordDto;
            serviceStatusDto.asXml(document, nodeStatus);
        }
    }

    @Override
    public void readElement(Element element) {

        Node header = element.getElementsByTagName("serviceStatuses").item(0);
        if (header == null) {
            return;
        }
        NodeList nodeList = ((Element) header).getElementsByTagName("serviceStatus");
        for (int i = 0; i < nodeList.getLength(); i++) {
            ServiceStatusDto serviceStatusDto = new ServiceStatusDto();
            serviceStatusDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element eElement = (Element) node;

            for (int idx = 1; idx < columns().length; idx++) {

                for (Method declaredMethod : serviceStatusDto.getClass().getDeclaredMethods()) {
                    if (declaredMethod.getName().toLowerCase().contains(columns()[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                        try {
                            if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                break;
                            }
                            declaredMethod.invoke(serviceStatusDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                            break;
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            serviceStatusService.insertOrUpdate(null, serviceStatusDto);
        }
    }
}



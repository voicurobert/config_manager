package ro.dev.ree.cross_config_manager.ui.node_status;

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
import ro.dev.ree.cross_config_manager.model.node_status.NodeStatusDto;
import ro.dev.ree.cross_config_manager.model.node_status.NodeStatusService;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class NodeStatusGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Node Status";

    private final NodeStatusService nodeStatusService = ConfigManagerContextProvider.getBean(NodeStatusService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "discriminator", "name", "colorCode", "capacityConsumer"};
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
        return nodeStatusService;
    }

    @Override
    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);

        List<RecordDto> allByConfigId = nodeStatusService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            NodeStatusDto nodeStatusDto = (NodeStatusDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = nodeStatusDto.getId();
            vec[1] = nodeStatusDto.getDiscriminator();
            vec[2] = nodeStatusDto.getName();
            vec[3] = nodeStatusDto.getColorCode();
            vec[4] = nodeStatusDto.getCapacityConsumer();


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
        RecordDto recordDto = nodeStatusService.findById(id);
        nodeStatusService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {
        List<RecordDto> allByConfigId = nodeStatusService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element nodeStatus = document.createElement("nodeStatuses");
        rootElement.appendChild(nodeStatus);

        for (RecordDto recordDto : allByConfigId) {
            NodeStatusDto nodeStatusDto = (NodeStatusDto) recordDto;
            nodeStatusDto.asXml(document, nodeStatus);
        }
    }

    @Override
    public void readElement(Element element) {

        Node header = element.getElementsByTagName("nodeStatuses").item(0);
        if (header != null) {
            NodeList nodeList = ((Element) header).getElementsByTagName("nodeStatus");
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeStatusDto nodeStatusDto = new NodeStatusDto();
                nodeStatusDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    for (int idx = 1; idx < columns().length; idx++) {

                        for (Method declaredMethod : nodeStatusDto.getClass().getDeclaredMethods()) {
                            if (declaredMethod.getName().toLowerCase().contains(columns()[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                                try {
                                    if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                        break;
                                    }
                                    declaredMethod.invoke(nodeStatusDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                                    break;
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                    nodeStatusService.insertOrUpdate(nodeStatusDto);
                }
            }
        }
    }
}

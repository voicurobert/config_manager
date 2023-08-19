package ro.dev.ree.cross_config_manager.ui.technologies;

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
import ro.dev.ree.cross_config_manager.model.technologies.TechnologiesDto;
import ro.dev.ree.cross_config_manager.model.technologies.TechnologiesService;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TechnologiesGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Technologies";

    private final TechnologiesService technologiesService = ConfigManagerContextProvider.getBean(TechnologiesService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "name", "technologyTree", "parentTechnology"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", new Text(parent, SWT.READ_ONLY | SWT.BORDER));
        map.put("name", new Text(parent, SWT.BORDER));
        map.put("technologyTree", new Text(parent, SWT.BORDER));
        map.put("parentTechnology", new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY));

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
                // Add options to the Combo
                String TechnologyTree = "";
                for (TechnologiesDto technologiesDto : technologiesService.listOfTechnologyType()) {

                    if (TechnologyTree.equals(technologiesDto.getTechnologyTree())) {
                        continue;
                    }
                    ((Combo) widget).add(technologiesDto.getTechnologyTree());
                    TechnologyTree = technologiesDto.getTechnologyTree();
                }
                if (action.equals("Update") && !(table.getSelection().length == 0)) {
                    ((Combo) widget).select(((Combo) widget).indexOf(table.getSelection()[0].getText(i.get())));
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
        return technologiesService;
    }

    @Override
    public Composite createContents(Composite parent) {
        createTitle(parent);

        Table table = (Table) super.createContents(parent);

        List<RecordDto> allByConfigId = technologiesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            TechnologiesDto technologiesDto = (TechnologiesDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = technologiesDto.getId();
            vec[1] = technologiesDto.getName();
            vec[2] = technologiesDto.getTechnologyTree();
            vec[3] = technologiesDto.getParentTechnology();


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
        RecordDto recordDto = technologiesService.findById(id);
        technologiesService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {
        List<RecordDto> allByConfigId = technologiesService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element technologies = document.createElement("technologies");
        rootElement.appendChild(technologies);

        for (RecordDto recordDto : allByConfigId) {
            TechnologiesDto technologiesDto = (TechnologiesDto) recordDto;
            technologiesDto.asXml(document, technologies);
        }
    }

    @Override
    public void readElement(Element element) {

        Node header = element.getElementsByTagName("technologies").item(0);
        if (header == null) {
            return;
        }
        NodeList nodeList = ((Element) header).getElementsByTagName("technology");
        for (int i = 0; i < nodeList.getLength(); i++) {
            TechnologiesDto technologiesDto = new TechnologiesDto();
            technologiesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element eElement = (Element) node;

            for (int idx = 1; idx < columns().length; idx++) {

                for (Method declaredMethod : technologiesDto.getClass().getDeclaredMethods()) {
                    if (declaredMethod.getName().toLowerCase().contains(columns()[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                        try {
                            if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                break;
                            }
                            declaredMethod.invoke(technologiesDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                            break;
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            technologiesService.insertOrUpdate(null, technologiesDto);
        }
    }
}

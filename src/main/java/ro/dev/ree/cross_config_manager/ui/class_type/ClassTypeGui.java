package ro.dev.ree.cross_config_manager.ui.class_type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
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
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.util.List;

public class ClassTypeGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Class Type";

    private final ClassTypeService classTypeService = ConfigManagerContextProvider.getBean(ClassTypeService.class);


    @Override
    public String[] columns() {
        return new String[]{"id", "name", "path", "parentPath"};
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public ServiceRepository getServiceRepository() {
        return classTypeService;
    }

    @Override
    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);
        List<RecordDto> allByConfigId = classTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            ClassTypeDto classTypeDto = (ClassTypeDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = classTypeDto.getId();
            vec[1] = classTypeDto.getName();
            vec[2] = classTypeDto.getPath();
            vec[3] = classTypeDto.getParentPath();

            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(vec);
        }

        return table;
    }

    @Override
    public void delete(String id) {
        RecordDto recordDto = classTypeService.findById(id);
        classTypeService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {

        List<RecordDto> allByConfigId = classTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element classTypes = document.createElement("classTypes");
        rootElement.appendChild(classTypes);

        for (RecordDto recordDto : allByConfigId) {
            ClassTypeDto classTypeDto = (ClassTypeDto) recordDto;
            classTypeDto.asXml(document, classTypes);
        }
    }

    @Override
    public void readElement(Element document) {
        ClassTypeDto classTypeDto = new ClassTypeDto();
        classTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());


        NodeList nodeList = document.getElementsByTagName("classTypes");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            System.out.println("\nnode name:" + node.getNodeName());
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                for (int idx = 1; idx < columns().length; idx++) {
                    // String textContent = eElement.getElementsByTagName(columns()[1]).item(0).getTextContent();
                    // call method columns[1] on classTypeDto using reflection
                    switch (columns()[idx]) {
                        case "name" ->
                                classTypeDto.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                        case "path" ->
                                classTypeDto.setPath(eElement.getElementsByTagName("path").item(0).getTextContent());
                        case "parentPath" ->
                                classTypeDto.setParentPath(eElement.getElementsByTagName("parentPath").item(0).getTextContent());
                        default -> {
                        }
                    }
                }
                System.out.println("name" + eElement.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("path " + eElement.getElementsByTagName("path").item(0).getTextContent());
                System.out.println("parentPath " + eElement.getElementsByTagName("parentPath").item(0).getTextContent());


            }


        }
        classTypeService.insertOrUpdate(classTypeDto);

        System.out.println("root element: ");


    }
}


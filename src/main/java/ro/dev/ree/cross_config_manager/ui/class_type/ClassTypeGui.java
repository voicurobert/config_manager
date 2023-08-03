package ro.dev.ree.cross_config_manager.ui.class_type;

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
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClassTypeGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Class Type";

    private final ClassTypeService classTypeService = ConfigManagerContextProvider.getBean(ClassTypeService.class);


    @Override
    public String[] columns() {
        return new String[]{"id", "name", "path", "parentPath"};
    }

    @Override
    public Map<String, Widget> columnsMap() {
        var map = new LinkedHashMap<String, Widget>();

        map.put("id", null);
        map.put("name", new Text(parent, SWT.BORDER));
        map.put("path", new Text(parent, SWT.BORDER));
        // parentPath probabil facut cu Combo si el
        map.put("parentPath", new Text(parent, SWT.BORDER));

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
                if(table.getSelection().length == 0 || action.equals("Add")){
                    ((Text)widget).setText("");
                }
                else{
                    ((Text)widget).setText(table.getSelection()[0].getText(i.get()));
                }
            } else if (widget instanceof Combo) {
                ClassTypeService classTypeService = ConfigManagerContextProvider.getBean(ClassTypeService.class);
                List<ClassTypeDto> classTypeDtos = classTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                        map(recordDto -> (ClassTypeDto) recordDto).toList();

                // Add options to the Combo
                for (ClassTypeDto classTypeDto : classTypeDtos) {
                    ((Combo)widget).add(classTypeDto.getName());
                }
                if (action.equals("Update") && !(table.getSelection().length == 0)) {
                    ((Combo)widget).select(((Combo)widget).indexOf(table.getSelection()[0].getText(i.get())));
                }
            }
            if(table.getSelection().length == 0){
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
        return classTypeService;
    }

    @Override
    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);
        List<RecordDto> allByConfigId = classTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            ClassTypeDto classTypeDto = (ClassTypeDto) recordDto;
            String[] vec = new String[columnsMap().size()];

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
    public void readElement(Element element) {


        Node header = element.getElementsByTagName("classTypes").item(0);
        if (header != null) {
            NodeList nodeList = ((Element) header).getElementsByTagName("classType");

            for (int i = 0; i < nodeList.getLength(); i++) {
                ClassTypeDto classTypeDto = new ClassTypeDto();
                classTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;


                    for (int idx = 1; idx < columnsMap().size(); idx++) {

                        for (Method declaredMethod : classTypeDto.getClass().getDeclaredMethods()) {
                            if (declaredMethod.getName().toLowerCase().contains(columns()[idx].toLowerCase()) && declaredMethod.getName().toLowerCase().contains("set")) {
                                try {
                                    if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                        break;
                                    }
                                    declaredMethod.invoke(classTypeDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                                    break;
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                    }
                    classTypeService.insertOrUpdate(classTypeDto);
                }
            }


        }
    }
}


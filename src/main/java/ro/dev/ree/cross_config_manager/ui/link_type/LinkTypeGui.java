package ro.dev.ree.cross_config_manager.ui.link_type;

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
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeService;
import ro.dev.ree.cross_config_manager.ui.utils.ManageableComponent;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class LinkTypeGui extends TableComposite implements ManageableComponent, XmlRead {

    public static final String TABLE_NAME = "Link Type";

    private final LinkTypeService linkTypeService = ConfigManagerContextProvider.getBean(LinkTypeService.class);

    @Override
    public String[] columns() {
        return new String[]{"id", "discriminator", "name", "appIcon", "mapIcon",
                "capacityFull", "capacityUnitName", "typeClassPath",
                "system", "unique"};
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public ServiceRepository getServiceRepository() {
        return linkTypeService;
    }

    @Override
    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);

        List<RecordDto> allByConfigId = linkTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto recordDto : allByConfigId) {
            LinkTypeDto linkTypeDto = (LinkTypeDto) recordDto;
            String[] vec = new String[columns().length];

            vec[0] = linkTypeDto.getId();
            vec[1] = linkTypeDto.getDiscriminator();
            vec[2] = linkTypeDto.getName();
            vec[3] = linkTypeDto.getAppIcon();
            vec[4] = linkTypeDto.getMapIcon();
            vec[5] = linkTypeDto.getCapacityFull();
            vec[6] = linkTypeDto.getCapacityUnitName();
            vec[7] = linkTypeDto.getTypeClassPath();
            vec[8] = linkTypeDto.getSystem();
            vec[9] = linkTypeDto.getUnique();

            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(vec);
        }

        return table;
    }

    @Override
    public void delete(String id) {
        RecordDto recordDto = linkTypeService.findById(id);
        linkTypeService.delete(recordDto);
        super.delete(id);
    }

    @Override
    public void xmlElements(Document document, Element rootElement) {
        List<RecordDto> allByConfigId = linkTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());

        // root element
        Element linkTypes = document.createElement("linkTypes");
        rootElement.appendChild(linkTypes);

        for (RecordDto recordDto : allByConfigId) {
            LinkTypeDto linkTypeDto = (LinkTypeDto) recordDto;
            linkTypeDto.asXml(document, linkTypes);
        }
    }

    @Override
    public void readElement(Element element) {

        NodeList nodeList = element.getElementsByTagName("linkType");
        System.out.println(nodeList.getLength());
        for (int i = 0; i < 21; i++) {
            LinkTypeDto linkTypeDto = new LinkTypeDto();
            linkTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
            Node node = nodeList.item(i);

            System.out.println("\nnode name:" + node.getNodeName());
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                for (int idx = 1; idx < columns().length; idx++) {
                    // String textContent = eElement.getElementsByTagName(columns()[1]).item(0).getTextContent();
                    // call method columns[1] on classTypeDto using reflection


                    for (Method declaredMethod : linkTypeDto.getClass().getDeclaredMethods()) {
                        if (declaredMethod.getName().toLowerCase().contains(columns()[idx])) {
                            try {
                                if (eElement.getElementsByTagName(columns()[idx]).getLength() == 0) {
                                    return;
                                }
                                String var = eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent();
                                declaredMethod.invoke(linkTypeDto, eElement.getElementsByTagName(columns()[idx]).item(0).getTextContent());
                                break;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }

//                    switch (columns()[idx]) {
//                        case "discriminator" ->
//                                linkTypeDto.setDiscriminator(eElement.getElementsByTagName("discriminator").item(0).getTextContent());
//                        case "name" ->
//                                linkTypeDto.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
//                        case "appIcon" ->
//                                linkTypeDto.setAppIcon(eElement.getElementsByTagName("appIcon").item(0).getTextContent());
//                        case "mapIcon" ->
//                                linkTypeDto.setMapIcon(eElement.getElementsByTagName("mapIcon").item(0).getTextContent());
//                        case "capacityFull" ->
//                                linkTypeDto.setCapacityFull(eElement.getElementsByTagName("capacityFull").item(0).getTextContent());
//                        case "capacityUnitName" -> {
//                            if (eElement.getElementsByTagName("capacityUnitName").item(0).getTextContent() == null) {
//                                //linkTypeDto.setCapacityUnitName(eElement.getElementsByTagName("capacityUnitName").item(0).getTextContent());
//                            } else {
//                                linkTypeDto.setCapacityUnitName(eElement.getElementsByTagName("capacityUnitName").item(0).getTextContent());
//                            }
//                        }
//                        case "typeClassPath" ->
//                                linkTypeDto.setTypeClassPath(eElement.getElementsByTagName("typeClassPath").item(0).getTextContent());
//
//                        case "system" ->
//                                linkTypeDto.setSystem(eElement.getElementsByTagName("system").item(0).getTextContent());
//                        case "unique" ->
//                                linkTypeDto.setUnique(eElement.getElementsByTagName("unique").item(0).getTextContent());
//                        default -> {
//
//                        }
//                    }
                    linkTypeService.insertOrUpdate(linkTypeDto);
                }
                System.out.println("name" + eElement.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("path " + eElement.getElementsByTagName("appIcon").item(0).getTextContent());
                System.out.println("parentPath " + eElement.getElementsByTagName("mapIcon").item(0).getTextContent());


            }


        }


        System.out.println("root element: " + element.getNodeName());


    }
}

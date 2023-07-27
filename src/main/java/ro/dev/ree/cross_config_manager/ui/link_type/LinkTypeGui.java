package ro.dev.ree.cross_config_manager.ui.link_type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeService;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;
import ro.dev.ree.cross_config_manager.xml.writer.XmlWriter;

import java.util.List;

public class LinkTypeGui extends TableComposite implements XmlWriter {

    public static final String TABLE_NAME = "Link Type";

    private final LinkTypeService linkTypeService = ConfigManagerContextProvider.getBean(LinkTypeService.class);

    @Override
    public String[] columns() {
        return new String[]{"Id", "Discriminator", "Name", "AppIcon", "MapIcon",
                "CapacityFull", "CapacityUnitName", "TypeClassPath",
                "System", "Unique"};
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
            linkTypeDto.asXml(document,linkTypes);
        }
    }
}

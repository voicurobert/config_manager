package ro.dev.ree.cross_config_manager.ui.link_type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeService;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;

import java.util.List;

public class LinkTypeGui extends TableComposite {

    public static final String TABLE_NAME = "Link Type";

    public static String ID = "";

    private final LinkTypeService linkTypeService = ConfigManagerContextProvider.getBean(LinkTypeService.class);

    @Override
    public String[] columns() {
        return new String[]{"Discriminator", "Name", "AppIcon", "MapIcon",
                "CapacityFull", "CapacityUnitName", "TypeClassPath",
                "System", "Unique"};
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public String ID() {
        return ID;
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

            ID = linkTypeDto.getId();
            vec[0] = linkTypeDto.getDiscriminator();
            vec[1] = linkTypeDto.getName();
            vec[2] = linkTypeDto.getAppIcon();
            vec[3] = linkTypeDto.getMapIcon();
            vec[4] = linkTypeDto.getCapacityFull();
            vec[5] = linkTypeDto.getCapacityUnitName();
            vec[6] = linkTypeDto.getTypeClassPath();
            vec[7] = linkTypeDto.getSystem();
            vec[8] = linkTypeDto.getUnique();

            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(vec);
        }

        return table;
    }

    @Override
    public void delete(int[] index) {
        super.delete(index);
        List<RecordDto> allByConfigId = linkTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        if (index.length != 0) {
            linkTypeService.delete(allByConfigId.get(index[0]));
        }
    }
}

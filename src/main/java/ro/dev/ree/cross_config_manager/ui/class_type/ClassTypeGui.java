package ro.dev.ree.cross_config_manager.ui.class_type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.class_type.ClassTypeDto;
import ro.dev.ree.cross_config_manager.model.class_type.ClassTypeService;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;

import java.util.List;

public class ClassTypeGui extends TableComposite {

    public static final String TABLE_NAME = "Class Type";

    private final ClassTypeService classTypeService = ConfigManagerContextProvider.getBean(ClassTypeService.class);


    @Override
    public String[] columns() {
        return new String[]{"Name", "Path", "ParentPath"};
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

            vec[0] = classTypeDto.getName();
            vec[1] = classTypeDto.getPath();
            vec[2] = classTypeDto.getParentPath();

            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(vec);
        }

        return table;
    }

    @Override
    public void delete(int[] index) {
        super.delete(index);
        List<RecordDto> allByConfigId = classTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        if (index.length != 0) {
            classTypeService.delete(allByConfigId.get(index[0]));
        }
    }

}

package ro.dev.ree.cross_config_manager.ui.class_type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.class_type.ClassType;
import ro.dev.ree.cross_config_manager.model.class_type.ClassTypeService;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;

public class ClassTypeGui extends TableComposite {

    private final ClassTypeService classTypeService = ConfigManagerContextProvider.getBean(ClassTypeService.class);

    @Override
    public String[] columns() {
        return new String[]{"Index", "Name", "Path", "parentPath"};
    }

    @Override
    public String tableName() {
        return "Class Types";
    }

    @Override
    public ServiceRepository getServiceRepository() {
        return null;
    }

    @Override
    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);
        ClassType classType = new ClassType();
        // List<ClassTypeDto> allByConfigId = classTypeService.findAllByConfigId(classType.getConfigId());

        String[][] classTypeData = {
                {"idnt1", "Index", "Name", "Path", "parentPath"},
                {"idnt2", "Index", "Name", "Path", "parentPath"},
                {"idnt3", "Index", "Name", "Path", "parentPath"},
                {"idnt4", "Index", "Name", "Path", "parentPath"},
                {"idnt5", "Index", "Name", "Path", "parentPath"},
                {"idnt6", "Index", "Name", "Path", "parentPath"}
        };

        for (String[] row : classTypeData) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(row);
        }

        return table;
    }


}

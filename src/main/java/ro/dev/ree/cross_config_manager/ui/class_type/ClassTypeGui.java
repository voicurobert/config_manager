package ro.dev.ree.cross_config_manager.ui.class_type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;

public class ClassTypeGui extends TableComposite {
    @Override
    public String[] columns() {
        return new String[]{"IdCT", "ConfigId", "Name", "Path", "ParentPath"};
    }

    @Override
    public String tableName() {
        return "Class Types";
    }

    @Override
    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);
        table.setToolTipText("ClassTypeTable");

        String[][] classTypeData = {
                {"idct1", "configId", "name", "path", "parentPath"},
                {"idct2", "configId", "name", "path", "parentPath"},
                {"idct3", "configId", "name", "path", "parentPath"},
                {"idct4", "configId", "name", "path", "parentPath"},
                {"idct5", "configId", "name", "path", "parentPath"},
                {"idct6", "configId", "name", "path", "parentPath"}
        };

        for (String[] row : classTypeData) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(row);
        }

        return table;
    }
}

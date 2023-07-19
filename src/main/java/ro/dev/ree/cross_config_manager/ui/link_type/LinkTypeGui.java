package ro.dev.ree.cross_config_manager.ui.link_type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import ro.dev.ree.cross_config_manager.ui.utils.TableComposite;

public class LinkTypeGui extends TableComposite {

    @Override
    public String[] columns() {
        return new String[]{"IdLT", "ConfigId", "Discriminator", "Name", "AppIcon", "MapIcon",
                "CapacityFull", "CapacityUnitName", "TypeClassPath",
                "System", "Unique"};
    }

    @Override
    public String tableName() {
        return "Link Types";
    }

    @Override
    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Table table = (Table) super.createContents(parent);
        table.setToolTipText("LinkTypeTable");

        String[][] linkTypeData = {
                {"idlt1", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idlt2", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idlt3", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idlt4", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idlt5", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idlt6", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"}
        };

        for (String[] row : linkTypeData) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(row);
        }

        return table;
    }
}

package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.ui.core_class_type.CoreClassTypeGui;
import ro.dev.ree.cross_config_manager.ui.link_type.LinkTypeGui;
import ro.dev.ree.cross_config_manager.ui.node_type.NodeTypeGui;
import ro.dev.ree.cross_config_manager.xml.writer.XmlWriter;

import java.util.List;
import java.util.Map;

public abstract class TableComposite implements Drawable, XmlWriter {

    protected Composite parent;

    protected Table table;

    private Menu menu;

    public abstract String[] columns();

    public abstract Map<String, Widget> columnsMap();

    public abstract Map<String, Object> values(String action, Map<String, Widget> columns);

    public abstract String tableName();

    public abstract ServiceRepository getServiceRepository();

    public Composite createContents(Composite parent) {
        this.parent = parent;
        table = new Table(parent, SWT.BORDER | SWT.CENTER);
        GridData gd_table = new GridData(1000, 150);
        gd_table.horizontalAlignment = SWT.CENTER;
        table.setLayoutData(gd_table);
        table.setToolTipText(tableName());
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        for (String key : columnsMap().keySet()) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(key);
            column.pack();
        }


        menu = new Menu(table);
        table.setMenu(menu);
        Menu fileMenu = new Menu(menu);
        MenuItem menuHeadline = new MenuItem(menu, SWT.CASCADE);
        menuHeadline.setText(tableName() + " Menu");
        menuHeadline.setMenu(fileMenu);
        MenuItem addMenu = new MenuItem(fileMenu, SWT.PUSH);
        addMenu.setText("Add new " + tableName());
        MenuItem updateMenu = new MenuItem(fileMenu, SWT.PUSH);
        updateMenu.setText("Update " + tableName());
        MenuItem deleteMenu = new MenuItem(fileMenu, SWT.PUSH);
        deleteMenu.setText("Delete " + tableName());

        menu.setEnabled(false);
        table.setEnabled(false);
        addMenu.addListener(SWT.Selection, event -> openDialogEditor("Add"));
        updateMenu.addListener(SWT.Selection, event -> openDialogEditor("Update"));
        deleteMenu.addListener(SWT.Selection, event -> deleteSelection());

        return table;
    }

    private void openDialogEditor(String action) {
        GenericEditorDialog dialog = new GenericEditorDialog(table.getParent().getShell(), action);
        dialog.setInputData(columnsMap());
        dialog.setDataValues(values(action, dialog.getInputData()));

        dialog.setActionPerformed(updatedValues -> {
            updatedValues.set(0, insertOrUpdateRecord(updatedValues, action));
            if (action.equals("Update")) {
                table.getSelection()[0].setText(updatedValues.toArray(new String[]{}));
            } else {
                TableItem tableItem = new TableItem(table, SWT.NONE);
                tableItem.setText(updatedValues.toArray(new String[]{}));
                table.setSelection(tableItem);
            }
            for (TableColumn column : table.getColumns()) {
                column.pack();
            }
        });

        dialog.open();
    }

    private String insertOrUpdateRecord(List<String> columnValues, String action) {
        return switch (table.getToolTipText()) {
            case CoreClassTypeGui.TABLE_NAME ->
                    getServiceRepository().insertOrUpdate(CoreClassTypeDto.InsertOrUpdateFromItems(columnValues, action));
            case NodeTypeGui.TABLE_NAME ->
                    getServiceRepository().insertOrUpdate(NodeTypeDto.InsertOrUpdateFromItems(columnValues, action));
            case LinkTypeGui.TABLE_NAME ->
                    getServiceRepository().insertOrUpdate(LinkTypeDto.InsertOrUpdateFromItems(columnValues, action));
            default -> "";
        };
    }


    private void deleteSelection() {
        delete(table.getSelection()[0].getText(0));
    }

    protected void createCheckbox(Composite parent) {
        Button button = new Button(parent, SWT.CHECK);
        button.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));
        button.setText(tableName());
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                table.setEnabled(button.getSelection());
                menu.setEnabled(button.getSelection());
            }
        });
        button.setSelection(false);
    }

    public void delete(String id) {
        table.remove(table.getSelectionIndex());
    }
}

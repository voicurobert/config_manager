package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.xml.writer.XmlWriter;

import java.util.HashMap;
import java.util.Map;

public abstract class TableComposite implements Drawable, XmlWriter {

    protected Composite parent;

    protected Table table;

    private Menu menu;

    public abstract String[] columns();

    public abstract Map<String, Widget> columnsMap();

    public Map<String, Object> values(String action) {
        return new HashMap<>();
    }

    public abstract String tableName();

    public abstract ServiceRepository getServiceRepository();

    public Composite createContents(Composite parent) {
        this.parent = parent;
        table = new Table(parent, SWT.BORDER | SWT.CENTER);
        GridData gd_table = new GridData(-1, 150);
        gd_table.horizontalAlignment = 2;
        table.setLayoutData(gd_table);
        table.setToolTipText(tableName());
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        for (String header : columns()) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(header);
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
        dialog.setDataValues(values(action));

        dialog.setActionPerformed(updatedValues -> {
            // insert or update
//            private String insertOrUpdateRecord(String[] columnValues) {
//                return switch (control.getToolTipText()) {
//                    case ClassTypeGui.TABLE_NAME ->
//                            serviceRepository.insertOrUpdate(ClassTypeDto.InsertOrUpdateFromItems(columnValues, action));
//                    case NodeTypeGui.TABLE_NAME ->
//                            serviceRepository.insertOrUpdate(NodeTypeDto.InsertOrUpdateFromItems(columnValues, action));
//                    case LinkTypeGui.TABLE_NAME ->
//                            serviceRepository.insertOrUpdate(LinkTypeDto.InsertOrUpdateFromItems(columnValues, action));
//                    case NodeTypeRulesGui.TREE_NAME ->
//                            serviceRepository.insertOrUpdate(NodeTypeRulesDto.InsertOrUpdateFromItems(columnValues, action));
//                    case LinkTypeRulesGui.TREE_NAME ->
//                            serviceRepository.insertOrUpdate(LinkTypeRulesDto.InsertOrUpdateFromItems(columnValues, action));
//                    case LinkTypeNodeTypeRulesGui.TREE_NAME ->
//                            serviceRepository.insertOrUpdate(LinkTypeNodeTypeRulesDto.InsertOrUpdateFromItems(columnValues, action));
//                    default -> "";
//                };
//            }
            //serviceRepository.insertOrUpdate(ClassTypeDto.InsertOrUpdateFromItems(columnValues, action));

            // add a new row and set items to this table

        });

        dialog.setServiceRepository(getServiceRepository());
        dialog.open();
//        EditorDialog dialog = new EditorDialog(table.getParent().getShell(), table, action);
//
//        dialog.setServiceRepository(getServiceRepository());
//        dialog.open();
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

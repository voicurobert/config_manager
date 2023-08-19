package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.ca_definition.CaDefinitionDto;
import ro.dev.ree.cross_config_manager.model.ca_definition_set.CaDefinitionSetDto;
import ro.dev.ree.cross_config_manager.model.component_status.ComponentStatusDto;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.message.MessageDto;
import ro.dev.ree.cross_config_manager.model.node_status.NodeStatusDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.service_status.ServiceStatusDto;
import ro.dev.ree.cross_config_manager.model.technologies.TechnologiesDto;
import ro.dev.ree.cross_config_manager.ui.ca_definition_and_message.CaDefinitionAndMessageGui;
import ro.dev.ree.cross_config_manager.ui.ca_definition_set.CaDefinitionSetGui;
import ro.dev.ree.cross_config_manager.ui.component_status.ComponentStatusGui;
import ro.dev.ree.cross_config_manager.ui.core_class_type.CoreClassTypeGui;
import ro.dev.ree.cross_config_manager.ui.link_type.LinkTypeGui;
import ro.dev.ree.cross_config_manager.ui.node_status.NodeStatusGui;
import ro.dev.ree.cross_config_manager.ui.node_type.NodeTypeGui;
import ro.dev.ree.cross_config_manager.ui.service_status.ServiceStatusGui;
import ro.dev.ree.cross_config_manager.ui.technologies.TechnologiesGui;
import ro.dev.ree.cross_config_manager.xml.writer.XmlWriter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        GridData gd_table = new GridData(1000, 300);
        gd_table.horizontalAlignment = SWT.CENTER;
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

        addMenu.addListener(SWT.Selection, event -> openDialogEditor("Add"));
        updateMenu.addListener(SWT.Selection, event -> openDialogEditor("Update"));
        deleteMenu.addListener(SWT.Selection, event -> deleteSelection());

        return table;
    }

    private void openDialogEditor(String action) {
        GenericEditorDialog dialog = new GenericEditorDialog(parent.getShell(), action);
        dialog.setInputData(columnsMap());
        dialog.setDataValues(values(action, dialog.getInputData()));

        dialog.setActionPerformed(updatedValues -> {
            updatedValues.set(0, insertOrUpdateRecord(dialog.getDataValues(), updatedValues, action));
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

    private String insertOrUpdateRecord(Map<String, Object> oldColumnValues, List<String> columnValues, String action) {
        switch (table.getToolTipText()) {
            case CoreClassTypeGui.TABLE_NAME:
                return getServiceRepository().insertOrUpdate(oldColumnValues, CoreClassTypeDto.InsertOrUpdateFromItems(columnValues, action));
            case NodeTypeGui.TABLE_NAME:
                return getServiceRepository().insertOrUpdate(oldColumnValues, NodeTypeDto.InsertOrUpdateFromItems(columnValues, action));
            case LinkTypeGui.TABLE_NAME:
                return getServiceRepository().insertOrUpdate(oldColumnValues, LinkTypeDto.InsertOrUpdateFromItems(columnValues, action));
            case ServiceStatusGui.TABLE_NAME:
                return getServiceRepository().insertOrUpdate(oldColumnValues, ServiceStatusDto.InsertOrUpdateFromItems(columnValues, action));
            case NodeStatusGui.TABLE_NAME:
                return getServiceRepository().insertOrUpdate(oldColumnValues, NodeStatusDto.InsertOrUpdateFromItems(columnValues, action));
            case ComponentStatusGui.TABLE_NAME:
                return getServiceRepository().insertOrUpdate(oldColumnValues, ComponentStatusDto.InsertOrUpdateFromItems(columnValues, action));
            case CaDefinitionSetGui.TABLE_NAME:
                return getServiceRepository().insertOrUpdate(oldColumnValues, CaDefinitionSetDto.InsertOrUpdateFromItems(columnValues, action));
            case TechnologiesGui.TABLE_NAME:
                return getServiceRepository().insertOrUpdate(oldColumnValues, TechnologiesDto.InsertOrUpdateFromItems(columnValues, action));


            case CaDefinitionAndMessageGui.TABLE_NAME: {
                Class<?> targetClass = CaDefinitionAndMessageGui.class;
                String methodName = "getAnotherServiceRepository";
                Method method = null;
                try {
                    method = targetClass.getMethod(methodName);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                Object instance = null;
                try {
                    instance = targetClass.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                Object result = null;
                try {
                    result = method.invoke(instance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                String id = getServiceRepository().insertOrUpdate(oldColumnValues, CaDefinitionDto.InsertOrUpdateFromItems(columnValues, action));
                if (result instanceof ServiceRepository) {
                    id += "," + ((ServiceRepository) result).insertOrUpdate(oldColumnValues, MessageDto.InsertOrUpdateFromItems(columnValues, action));
                    return id;
                }
            }
            default:
                return "";
        }

    }


    private void deleteSelection() {
        delete(table.getSelection()[0].getText(0));
    }

    protected void createTitle(Composite parent) {
        Label tableTitle = new Label(parent, SWT.TITLE);
        GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, true, false);
        gridData.verticalSpan = 10;
        tableTitle.setLayoutData(gridData);
        tableTitle.setText(tableName());
    }

    public void delete(String id) {
        table.remove(table.getSelectionIndex());
    }
}

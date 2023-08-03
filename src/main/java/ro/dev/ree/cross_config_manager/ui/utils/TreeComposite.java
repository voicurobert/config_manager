package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.ui.link_type_node_type_rules.LinkTypeNodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.link_type_rules.LinkTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.node_type_rules.NodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.xml.writer.XmlWriter;

import java.util.List;
import java.util.Map;

public abstract class TreeComposite implements Drawable, XmlWriter {

    protected Composite parent;

    protected Tree tree;

    private Menu menu;

    public abstract String[] columns();

    public abstract Map<String, Widget> columnsMap();

    public abstract Map<String, Object> values(String action, Map<String, Widget> columns);

    public abstract String treeName();

    public abstract ServiceRepository getServiceRepository();


    @Override
    public Composite createContents(Composite parent) {
        this.parent = parent;
        tree = new Tree(parent, SWT.BORDER | SWT.CENTER);
        GridData gd_tree = new GridData(-1, 150);
        gd_tree.horizontalAlignment = 2;
        tree.setLayoutData(gd_tree);
        tree.setToolTipText(treeName());
        tree.setHeaderVisible(true);
        tree.setLinesVisible(true);

        for (String header : columns()) {
            TreeColumn column = new TreeColumn(tree, SWT.NONE);
            column.setText(header);
            column.pack();
        }

        menu = new Menu(tree);
        tree.setMenu(menu);
        Menu fileMenu = new Menu(menu);
        MenuItem menuHeadline = new MenuItem(menu, SWT.CASCADE);
        menuHeadline.setText(treeName() + " Menu");
        menuHeadline.setMenu(fileMenu);
        MenuItem addMenu = new MenuItem(fileMenu, SWT.PUSH);
        addMenu.setText("Add new " + treeName());
        MenuItem updateMenu = new MenuItem(fileMenu, SWT.NONE);
        updateMenu.setText("Update " + treeName());
        MenuItem deleteMenu = new MenuItem(fileMenu, SWT.NONE);
        deleteMenu.setText("Delete " + treeName());

        menu.setEnabled(false);
        tree.setEnabled(false);

        addMenu.addListener(SWT.Selection, event -> openDialogEditor("Add"));
        updateMenu.addListener(SWT.Selection, event -> openDialogEditor("Update"));
        deleteMenu.addListener(SWT.Selection, event -> deleteSelection());

        return tree;
    }

    private void deleteSelection() {
        TreeItem[] treeItems = tree.getSelection();
        delete(treeItems[0].getText(0));
        for (TreeItem treeitem : treeItems) {
            treeitem.removeAll(); // Remove all childrens
            treeitem.dispose();   // Remove actual parent
        }
    }

    private void openDialogEditor(String action) {
        GenericEditorDialog dialog = new GenericEditorDialog(tree.getParent().getShell(), action);
        dialog.setInputData(columnsMap());
        dialog.setDataValues(values(action, dialog.getInputData()));

        dialog.setActionPerformed(updatedValues -> {
            updatedValues.set(0, insertOrUpdateRecord(updatedValues, action));
            if(action.equals("Update")) {
                tree.getSelection()[0].setText(updatedValues.toArray(new String[]{}));
            }
            else {
                TreeItem treeItem = new TreeItem(tree, SWT.NONE);
                treeItem.setText(updatedValues.toArray(new String[]{}));
                tree.setSelection(treeItem);
            }
        });

        dialog.open();
    }

    private String insertOrUpdateRecord(List<String> columnValues, String action) {
        return switch (tree.getToolTipText()) {
            case NodeTypeRulesGui.TREE_NAME ->
                    getServiceRepository().insertOrUpdate(NodeTypeRulesDto.InsertOrUpdateFromItems(columnValues, action));
            case LinkTypeRulesGui.TREE_NAME ->
                    getServiceRepository().insertOrUpdate(LinkTypeRulesDto.InsertOrUpdateFromItems(columnValues, action));
            case LinkTypeNodeTypeRulesGui.TREE_NAME ->
                    getServiceRepository().insertOrUpdate(LinkTypeNodeTypeRulesDto.InsertOrUpdateFromItems(columnValues, action));
            default -> "";
        };
    }

    protected void createCheckbox(Composite parent) {
        Button button = new Button(parent, SWT.CHECK);
        button.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));
        button.setText(treeName());
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                tree.setEnabled(button.getSelection());
                menu.setEnabled(button.getSelection());
            }
        });
        button.setSelection(false);
    }

    public void delete(String id) {

    }
}

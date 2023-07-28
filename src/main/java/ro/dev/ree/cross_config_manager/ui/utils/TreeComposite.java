package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.xml.writer.XmlWriter;

public abstract class TreeComposite implements Drawable, XmlWriter {

    private Tree tree;

    private Menu menu;

    public abstract String[] columns();

    public abstract String treeName();

    public abstract ServiceRepository getServiceRepository();


    @Override
    public Composite createContents(Composite parent) {


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
        EditorDialog dialog = new EditorDialog(tree.getParent().getShell(), tree, action);
        dialog.setServiceRepository(getServiceRepository());
        dialog.open();
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

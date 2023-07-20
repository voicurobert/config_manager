package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

public abstract class TreeComposite implements Drawable {

    private Tree tree;

    private Menu menu;

    public abstract String[] columns();

    public abstract String treeName();

    public abstract ServiceRepository getServiceRepository();


    @Override
    public Composite createContents(Composite parent) {

        tree = new Tree(parent, SWT.BORDER | SWT.CENTER);
        tree.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));

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

        addMenu.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                ModifyElementDialog dialog = new ModifyElementDialog(tree.getParent().getShell(), tree, "Add");
                dialog.setServiceRepository(getServiceRepository());

                dialog.open();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        updateMenu.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                ModifyElementDialog dialog = new ModifyElementDialog(tree.getParent().getShell(), tree, "Update");
                dialog.open();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        deleteMenu.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                TreeItem[] treeItems = tree.getSelection();
                for (TreeItem treeitem : treeItems) {
                    treeitem.removeAll(); // Remove all childrens
                    treeitem.dispose();   // Remove actual parent
                }
                tree.pack();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        return tree;
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

    protected void delete(int[] index) {
        tree.removeAll();
        tree.pack();
    }
}

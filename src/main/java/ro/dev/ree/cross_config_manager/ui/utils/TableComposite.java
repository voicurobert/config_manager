package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

public abstract class TableComposite implements Drawable {

    private Table table;

    private Menu menu;

    public abstract String[] columns();

    public abstract String tableName();

    public abstract ServiceRepository getServiceRepository();

    public Composite createContents(Composite parent) {
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
        MenuItem updateMenu = new MenuItem(fileMenu, SWT.NONE);
        updateMenu.setText("Update " + tableName());
        MenuItem deleteMenu = new MenuItem(fileMenu, SWT.NONE);
        deleteMenu.setText("Delete " + tableName());

        menu.setEnabled(false);
        table.setEnabled(false);

        addMenu.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                EditorDialog dialog = new EditorDialog(table.getParent().getShell(), table, "Add");
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
                EditorDialog dialog = new EditorDialog(table.getParent().getShell(), table, "Update");
                dialog.setServiceRepository(getServiceRepository());
                dialog.open();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        deleteMenu.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                delete(table.getSelectionIndices());
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        return table;
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

    public void delete(int[] index) {
        table.remove(index);
        table.pack();

    }
}

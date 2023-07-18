package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public abstract class TableComposite implements Drawable {

    private Table table;

    public abstract String[] columns();

    public abstract String tableName();

    public Composite createContents(Composite parent) {
        table = new Table(parent, SWT.BORDER | SWT.CENTER);
        table.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));

        table.setToolTipText(tableName());
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        for (String header : columns()) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(header);
            column.pack();
        }
        table.setVisible(false);
        return table;
    }

    protected void createCheckbox(Composite parent) {
        Button button = new Button(parent, SWT.CHECK);
        button.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));
        button.setText(tableName());
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                table.setVisible(button.getSelection());
            }
        });
        button.setSelection(false);
    }
}

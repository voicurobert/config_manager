package ro.dev.ree.cross_config_manager.ui.utils;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

public class ModifyElementDialog extends Dialog {

    private Composite control; // either tree or table

    private Table table;
    private Tree tree;
    private Shell shell;
    private Text[] inputTexts;
    private String action;

    private ServiceRepository serviceRepository;

    public ModifyElementDialog(Shell parent, Table table, String action) {
        super(parent, SWT.APPLICATION_MODAL);
        this.table = table;
        this.action = action;
    }

    public ModifyElementDialog(Shell parent, Tree tree, String action) {
        super(parent, SWT.APPLICATION_MODAL);
        this.tree = tree;
        this.action = action;
    }

    public ModifyElementDialog(Shell parent, Composite composite, String action) {
        super(parent, SWT.APPLICATION_MODAL);
        this.control = composite;
        this.action = action;
    }

    public void open() {
        shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText("Modify Items Dialog");
        shell.setLayout(new FillLayout());

        createContent();

        shell.pack();
        shell.open();
    }

    private void createContent() {
        Composite composite = new Composite(shell, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 10;
        layout.marginHeight = 10;
        composite.setLayout(layout);

        // Get the selected item in the table or tree
        TableItem tableSelectedItem;
        TreeItem treeSelectedItem;
        if (table != null) {
            tableSelectedItem = table.getSelection()[0];

            // Get the number of columns in the table
            int numColumns = table.getColumnCount();

            // Create an array of Text for each column of the selected item
            inputTexts = new Text[numColumns];
            for (int i = 0; i < numColumns; i++) {
                Label label = new Label(composite, SWT.NONE);
                label.setText(table.getColumn(i).getText() + ":");

                inputTexts[i] = new Text(composite, SWT.BORDER);
                inputTexts[i].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

                // Set initial value for Text Input
                if (action == "Update")
                    inputTexts[i].setText(tableSelectedItem.getText(i));
            }

            Button actionButton = new Button(composite, SWT.PUSH);
            actionButton.setText((action == "Update") ? "Update" : "Add");
            actionButton.addListener(SWT.Selection, event -> handleAction(tableSelectedItem, action));
        } else if (tree != null) {
            treeSelectedItem = tree.getSelection()[0];

            // Get the number of columns in the table
            int numColumns = tree.getColumnCount();

            // Create an array of Text for each column of the selected item
            inputTexts = new Text[numColumns];
            for (int i = 0; i < numColumns; i++) {
                Label label = new Label(composite, SWT.NONE);
                label.setText(tree.getColumn(i).getText() + ":");

                inputTexts[i] = new Text(composite, SWT.BORDER);
                inputTexts[i].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            }

            Button actionButton = new Button(composite, SWT.PUSH);
            actionButton.setText((action == "Update") ? "Update" : "Add");
            actionButton.addListener(SWT.Selection, event -> handleAction(treeSelectedItem, action));
        }

        Button cancelButton = new Button(composite, SWT.PUSH);
        cancelButton.setText("Cancel");
        cancelButton.addListener(SWT.Selection, event -> shell.close());

    }

    private void handleAction(TableItem selectedItem, String action) {
        if (inputTexts != null) {
            if (action == "Add") {
                String[] items = new String[table.getColumnCount()];
                for (int i = 0; i < inputTexts.length; i++) {
                    items[i] = inputTexts[i].getText().trim();
                }
                TableItem tableItem = new TableItem(table, SWT.NONE);
                tableItem.setText(items);
                for (TableColumn column : table.getColumns()) column.pack();
            } else {
                for (int i = 0; i < inputTexts.length; i++) {
                    String text = inputTexts[i].getText().trim();
                    selectedItem.setText(i, text);
                }
                for (TableColumn column : table.getColumns()) column.pack();
            }
        }
        shell.close();
    }

    private void handleAction(TreeItem selectedItem, String action) {
        if (inputTexts == null) {
            return;
        }

        if (action.equals("Add")) {
            String[] items = new String[tree.getColumnCount()];
            for (int i = 0; i < inputTexts.length; i++) {
                items[i] = inputTexts[i].getText().trim();
            }

//            if (control instanceof Tree) {
//                TreeItem treeItem = new TreeItem(tree, SWT.NONE);
//                treeItem.setText(items);
//            } else {
//                TableItem tableItem = new TableItem(table, SWT.NONE);
//                tableItem.setText(items);
//            }


            for (TreeColumn column : tree.getColumns()) {
                column.pack();
            }
        } else {
            for (int i = 0; i < inputTexts.length; i++) {
                String text = inputTexts[i].getText().trim();
                selectedItem.setText(i, text);
            }
            for (TreeColumn column : tree.getColumns()) column.pack();
        }

    }

}

package ro.dev.ree.cross_config_manager.ui.utils;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.ui.link_type_node_type_rules.LinkTypeNodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.node_type.NodeTypeGui;

public class ModifyElementDialog extends Dialog {

    private final Composite control; // either tree or table
    private Shell shell;
    private Text[] inputTexts;
    private final String action;

    private ServiceRepository serviceRepository;

    public ModifyElementDialog(Shell parent, Composite composite, String action) {
        super(parent, SWT.APPLICATION_MODAL);
        this.control = composite;
        this.action = action;
    }

    public void setServiceRepository(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
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
        Widget selectedItem;
        int numColumns;

        selectedItem = (control instanceof Table) ? ((Table) control).getSelection()[0] : ((Tree) control).getSelection()[0];
        numColumns = (control instanceof Table) ? ((Table) control).getColumnCount() : ((Tree) control).getColumnCount();

        // Create an array of Text for each column of the selected item
        inputTexts = new Text[numColumns];
        for (int i = 0; i < numColumns; i++) {
            Label label = new Label(composite, SWT.NONE);
            label.setText((control instanceof Table) ? ((Table) control).getColumn(i).getText() + ":" : ((Tree) control).getColumn(i).getText() + ":");
            inputTexts[i] = new Text(composite, SWT.BORDER);
            inputTexts[i].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

            // Set initial value for Text Input
            if (action.equals("Update"))
                inputTexts[i].setText((selectedItem instanceof TableItem) ? ((TableItem) selectedItem).getText(i) : ((TreeItem) selectedItem).getText(i));
        }

        Button actionButton = new Button(composite, SWT.PUSH);
        actionButton.setText((action.equals("Update")) ? "Update" : "Add");
        actionButton.addListener(SWT.Selection, event -> handleAction(selectedItem, action));

        Button cancelButton = new Button(composite, SWT.PUSH);
        cancelButton.setText("Cancel");
        cancelButton.addListener(SWT.Selection, event -> shell.close());
    }

    private void handleAction(Widget selectedItem, String action) {
        if (inputTexts == null) {
            return;
        }

        if (action.equals("Add")) {
            String[] items = new String[(control instanceof Table) ? ((Table) control).getColumnCount() : ((Tree) control).getColumnCount()];
            for (int i = 0; i < inputTexts.length; i++) {
                items[i] = inputTexts[i].getText().trim();
            }
            Widget newItem;
            if ((selectedItem instanceof TableItem)) {
                assert control instanceof Table;
                newItem = new TableItem((Table) control, SWT.NONE);

                insertRecord(control.getToolTipText(), items);

            } else {
                assert control instanceof Tree;
                newItem = new TreeItem((Tree) control, SWT.NONE);
            }
            if (selectedItem instanceof TableItem) {
                ((TableItem) newItem).setText(items);
                for (TableColumn column : ((Table) control).getColumns()) {
                    column.pack();
                }
            } else {
                ((TreeItem) newItem).setText(items);
                for (TreeColumn column : ((Tree) control).getColumns()) {
                    column.pack();
                }
            }
        } else {
            for (int i = 0; i < inputTexts.length; i++) {
                String text = inputTexts[i].getText().trim();
                if (selectedItem instanceof TableItem) {
                    ((TableItem) selectedItem).setText(i, text);
                } else {
                    ((TreeItem) selectedItem).setText(i, text);
                }
            }
            if (selectedItem instanceof TableItem) {
                for (TableColumn column : ((Table) control).getColumns()) {
                    column.pack();
                }
            } else {
                for (TreeColumn column : ((Tree) control).getColumns()) {
                    column.pack();
                }
            }
        }
    }

    private void insertRecord(String tableName, String[] columnValues) {
        switch (tableName) {
            case NodeTypeGui.TABLE_NAME:
                serviceRepository.insert(NodeTypeDto.newFromItems(columnValues));
            case LinkTypeNodeTypeRulesGui.TREE_NAME:
                serviceRepository.insert(NodeTypeDto.newFromItems(columnValues));
        }
    }
}

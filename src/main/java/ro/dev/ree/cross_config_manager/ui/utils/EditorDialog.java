package ro.dev.ree.cross_config_manager.ui.utils;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.class_type.ClassTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.ui.class_type.ClassTypeGui;
import ro.dev.ree.cross_config_manager.ui.link_type.LinkTypeGui;
import ro.dev.ree.cross_config_manager.ui.link_type_node_type_rules.LinkTypeNodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.link_type_rules.LinkTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.node_type.NodeTypeGui;
import ro.dev.ree.cross_config_manager.ui.node_type_rules.NodeTypeRulesGui;

import java.util.Arrays;

public class EditorDialog extends Dialog {

    private final Composite control; // either tree or table
    private Shell shell;
    private Text[] inputTexts;
    private final String action;

    private ServiceRepository serviceRepository;

    public EditorDialog(Shell parent, Composite composite, String action) {
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
        int numColumns = (control instanceof Table) ? ((Table) control).getColumnCount() : ((Tree) control).getColumnCount();

        if((control instanceof Table) ? ((Table) control).getSelection().length == 0 : ((Tree) control).getSelection().length == 0) {
            Widget newItem;
            newItem = (control instanceof Table) ? new TableItem((Table) control, SWT.NONE) : new TreeItem((Tree) control, SWT.NONE);
            String[]text = new String[numColumns];
            Arrays.fill(text, "");
            if ((control instanceof Table)) {
                ((TableItem) newItem).setText(text);
                ((Table) control).setSelection((TableItem) newItem);
            } else {
                ((TreeItem) newItem).setText(text);
                ((Tree) control).setSelection((TreeItem) newItem);
            }
        }
        selectedItem = (control instanceof Table) ? ((Table) control).getSelection()[0] : ((Tree) control).getSelection()[0];
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
            else
                inputTexts[i].setText("");
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
            if ((control instanceof Table)) {
                newItem =((TableItem) selectedItem).getText().equals("") ?  (TableItem) selectedItem : new TableItem((Table) control, SWT.NONE);
            } else {
                newItem =((TreeItem) selectedItem).getText().equals("") ?  (TreeItem) selectedItem : new TreeItem((Tree) control, SWT.NONE);
            }
            insertRecord(control.getToolTipText(), items);
            if ((control instanceof Table)) {
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
            case ClassTypeGui.TABLE_NAME:
                serviceRepository.insert(ClassTypeDto.newFromItems(columnValues));
            case NodeTypeGui.TABLE_NAME:
                serviceRepository.insert(NodeTypeDto.newFromItems(columnValues));
            case LinkTypeGui.TABLE_NAME:
                serviceRepository.insert(LinkTypeDto.newFromItems(columnValues));
            case NodeTypeRulesGui.TREE_NAME:
                serviceRepository.insert(NodeTypeRulesDto.newFromItems(columnValues));
            case LinkTypeRulesGui.TREE_NAME:
                serviceRepository.insert(LinkTypeRulesDto.newFromItems(columnValues));
            case LinkTypeNodeTypeRulesGui.TREE_NAME:
                serviceRepository.insert(LinkTypeNodeTypeRulesDto.newFromItems(columnValues));
        }
    }
}

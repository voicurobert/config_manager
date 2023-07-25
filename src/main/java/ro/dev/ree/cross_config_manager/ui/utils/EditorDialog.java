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

    private final String ID;

    private ServiceRepository serviceRepository;

    public EditorDialog(Shell parent, Composite composite, String ID, String action) {
        super(parent, SWT.APPLICATION_MODAL);
        this.control = composite;
        this.action = action;
        this.ID = ID;
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
        String[] items = new String[(control instanceof Table) ? ((Table) control).getColumnCount() : ((Tree) control).getColumnCount()];
        if (action.equals("Add")) {
            Widget newItem;
            for (int i = 0; i < inputTexts.length; i++) {
                items[i] = inputTexts[i].getText().trim();
            }
            if (control instanceof Table) {
                newItem = ((TableItem) selectedItem).getText().equals("") ? (TableItem) selectedItem : new TableItem((Table) control, SWT.NONE);
                ((TableItem) newItem).setText(items);
            } else {
                newItem = ((TreeItem) selectedItem).getText().equals("") ? (TreeItem) selectedItem : new TreeItem((Tree) control, SWT.NONE);
                ((TreeItem) newItem).setText(items);
            }
        } else {
            for (int i = 0; i < inputTexts.length; i++) {
                items[i] = inputTexts[i].getText().trim();
                if (control instanceof Table) {
                    ((TableItem) selectedItem).setText(i, items[i]);
                } else {
                    ((TreeItem) selectedItem).setText(i, items[i]);
                }
            }
        }
        if (control instanceof Table) {
            for (TableColumn column : ((Table) control).getColumns()) {
                column.pack();
            }
        } else {
            for (TreeColumn column : ((Tree) control).getColumns()) {
                column.pack();
            }
        }
        insertOrUpdateRecord(items);
    }

    private void insertOrUpdateRecord(String[] columnValues) {
        switch (control.getToolTipText()) {
            case ClassTypeGui.TABLE_NAME:
                serviceRepository.insertOrUpdate(ClassTypeDto.NewOrUpdateFromItems(columnValues, action, ID));
                break;
            case NodeTypeGui.TABLE_NAME:
                serviceRepository.insertOrUpdate(NodeTypeDto.NewOrUpdateFromItems(columnValues, action, ID));
                break;
            case LinkTypeGui.TABLE_NAME:
                serviceRepository.insertOrUpdate(LinkTypeDto.NewOrUpdateFromItems(columnValues, action, ID));
                break;
            case NodeTypeRulesGui.TREE_NAME:
                serviceRepository.insertOrUpdate(NodeTypeRulesDto.NewOrUpdateFromItems(columnValues, action, ID));
                break;
            case LinkTypeRulesGui.TREE_NAME:
                serviceRepository.insertOrUpdate(LinkTypeRulesDto.NewOrUpdateFromItems(columnValues, action, ID));
                break;
            case LinkTypeNodeTypeRulesGui.TREE_NAME:
                serviceRepository.insertOrUpdate(LinkTypeNodeTypeRulesDto.NewOrUpdateFromItems(columnValues, action, ID));
                break;
            default:
                break;
        }
    }

}

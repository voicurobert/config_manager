package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.model.component_status.ComponentStatusDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeDto;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeService;
import ro.dev.ree.cross_config_manager.model.link_status.LinkStatusDto;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeDto;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.node_status.NodeStatusDto;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeDto;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesDto;
import ro.dev.ree.cross_config_manager.model.service_status.ServiceStatusDto;
import ro.dev.ree.cross_config_manager.ui.component_status.ComponentStatusGui;
import ro.dev.ree.cross_config_manager.ui.core_class_type.CoreClassTypeGui;
import ro.dev.ree.cross_config_manager.ui.link_status.LinkStatusGui;
import ro.dev.ree.cross_config_manager.ui.link_type.LinkTypeGui;
import ro.dev.ree.cross_config_manager.ui.link_type_node_type_rules.LinkTypeNodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.link_type_rules.LinkTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.node_status.NodeStatusGui;
import ro.dev.ree.cross_config_manager.ui.node_type.NodeTypeGui;
import ro.dev.ree.cross_config_manager.ui.node_type_rules.NodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.service_status.ServiceStatusGui;

import java.util.Arrays;
import java.util.List;

public class EditorDialog extends Dialog {

    private final Composite control; // either tree or table
    private Shell shell;
    private Control[] inputTexts;
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

        if ((control instanceof Table) ? ((Table) control).getSelection().length == 0 : ((Tree) control).getSelection().length == 0) {
            Widget newItem;
            newItem = (control instanceof Table) ? new TableItem((Table) control, SWT.NONE) : new TreeItem((Tree) control, SWT.NONE);
            String[] text = new String[numColumns];
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
        inputTexts = new Control[numColumns];

        for (int i = 1; i < numColumns; i++) {
            Label label = new Label(composite, SWT.NONE);
            label.setText((control instanceof Table) ? ((Table) control).getColumn(i).getText() + ":" : ((Tree) control).getColumn(i).getText() + ":");

            if ((control instanceof Table) ? ((Table) control).getColumn(i).getText().equals("typeClassPath") : ((Tree) control).getColumn(i).getText().equals("typeClassPath")) {
                Combo combo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
                combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

                CoreClassTypeService coreClassTypeService = ConfigManagerContextProvider.getBean(CoreClassTypeService.class);
                List<CoreClassTypeDto> coreClassTypeDtos = coreClassTypeService.findAllByConfigId(ConfigSingleton.getSingleton().getConfigDto().getId()).stream().
                        map(recordDto -> (CoreClassTypeDto) recordDto).toList();

                // Add options to the Combo
                for (CoreClassTypeDto coreClassTypeDto : coreClassTypeDtos) {
                    combo.add(coreClassTypeDto.getName());
                }

                // Set initial selection based on the existing value in the selected item
                if (action.equals("Update")) {
                    String selectedValue = (selectedItem instanceof TableItem) ? ((TableItem) selectedItem).getText(i) : ((TreeItem) selectedItem).getText(i);
                    combo.select(combo.indexOf(selectedValue));
                }

                inputTexts[i] = combo;

            } else {
                Text text = new Text(composite, SWT.BORDER);
                text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

                // Set initial value for Text Input
                if (action.equals("Update"))
                    text.setText((selectedItem instanceof TableItem) ? ((TableItem) selectedItem).getText(i) : ((TreeItem) selectedItem).getText(i));

                inputTexts[i] = text;
            }
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
            for (int i = 1; i < inputTexts.length; i++) {
                if (inputTexts[i] instanceof Text) {
                    items[i] = ((Text) inputTexts[i]).getText().trim();
                } else if (inputTexts[i] instanceof Combo) {
                    items[i] = ((Combo) inputTexts[i]).getText().trim();
                }
            }
            items[0] = insertOrUpdateRecord(items);
            if (selectedItem instanceof TableItem && control instanceof Table) {
                newItem = ((TableItem) selectedItem).getText().equals("") ? (TableItem) selectedItem : new TableItem((Table) control, SWT.NONE);
                ((TableItem) newItem).setText(items);
            } else if (selectedItem instanceof TreeItem && control instanceof Tree) {
                newItem = ((TreeItem) selectedItem).getText().equals("") ? (TreeItem) selectedItem : new TreeItem((Tree) control, SWT.NONE);
                ((TreeItem) newItem).setText(items);
            }
        } else {
            items[0] = (selectedItem instanceof TableItem) ? ((TableItem) selectedItem).getText(0) : ((TreeItem) selectedItem).getText(0);
            for (int i = 1; i < inputTexts.length; i++) {
                if (inputTexts[i] instanceof Text) {
                    items[i] = ((Text) inputTexts[i]).getText().trim();
                } else if (inputTexts[i] instanceof Combo) {
                    items[i] = ((Combo) inputTexts[i]).getText().trim();
                }
                if (selectedItem instanceof TableItem && control instanceof Table) {
                    ((TableItem) selectedItem).setText(i, items[i]);
                } else if (selectedItem instanceof TreeItem && control instanceof Tree) {
                    ((TreeItem) selectedItem).setText(i, items[i]);
                }
            }
            items[0] = insertOrUpdateRecord(items);
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

    }

    private String insertOrUpdateRecord(String[] columnValues) {
        return switch (control.getToolTipText()) {
            case CoreClassTypeGui.TABLE_NAME ->
                    serviceRepository.insertOrUpdate(CoreClassTypeDto.InsertOrUpdateFromItems(columnValues, action));
            case NodeTypeGui.TABLE_NAME ->
                    serviceRepository.insertOrUpdate(NodeTypeDto.insertOrUpdateFromItems(columnValues, action));
            case LinkTypeGui.TABLE_NAME ->
                    serviceRepository.insertOrUpdate(LinkTypeDto.InsertOrUpdateFromItems(columnValues, action));
            case LinkStatusGui.TABLE_NAME ->
                    serviceRepository.insertOrUpdate(LinkStatusDto.InsertOrUpdateFromItems(columnValues, action));
            case NodeStatusGui.TABLE_NAME ->
                    serviceRepository.insertOrUpdate(NodeStatusDto.InsertOrUpdateFromItems(columnValues, action));
            case NodeTypeRulesGui.TREE_NAME ->
                    serviceRepository.insertOrUpdate(NodeTypeRulesDto.InsertOrUpdateFromItems(columnValues, action));
            case LinkTypeRulesGui.TREE_NAME ->
                    serviceRepository.insertOrUpdate(LinkTypeRulesDto.InsertOrUpdateFromItems(columnValues, action));
            case LinkTypeNodeTypeRulesGui.TREE_NAME ->
                    serviceRepository.insertOrUpdate(LinkTypeNodeTypeRulesDto.InsertOrUpdateFromItems(columnValues, action));
            case ServiceStatusGui.TABLE_NAME ->
                    serviceRepository.insertOrUpdate(ServiceStatusDto.InsertOrUpdateFromItems(columnValues, action));
            case ComponentStatusGui.TABLE_NAME ->
                    serviceRepository.insertOrUpdate(ComponentStatusDto.InsertOrUpdateFromItems(columnValues, action));
            default -> "";
        };
    }
}

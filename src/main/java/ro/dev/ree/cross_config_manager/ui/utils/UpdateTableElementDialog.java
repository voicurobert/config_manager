package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class UpdateTableElementDialog extends Dialog {
    private Table table;
    private Shell shell;
    private Text inputText;

    public UpdateTableElementDialog(Shell parent, Table table) {
        super(parent, SWT.APPLICATION_MODAL);
        this.table = table;
    }

    public void open() {
        shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText("Update Items");
        shell.setLayout(new FillLayout());

        createContent();

        shell.pack();
        shell.open();
    }

    private void createContent() {
        Composite composite = new Composite(shell, SWT.NONE);
        composite.setLayout(new FillLayout());

        Label label = new Label(composite, SWT.NONE);
        label.setText("Modify row items (separated by ','): ");

        inputText = new Text(composite, SWT.BORDER);
        String txt = "";
        TableItem[] tableItems = table.getSelection();
        for (TableItem tableItem:tableItems) {
            txt = txt + tableItem.getText() + ",";
//            TableItem[] data = (TableItem[]) tableItem.getData();
//            for(TableItem tableItem1:data){
//                txt = txt + tableItem1.getText() + ",";
//            }
        }

        inputText.setText(txt);
        Button addButton = new Button(composite, SWT.PUSH);
        addButton.setText("Update");
        addButton.addListener(SWT.Selection, event -> updateElement());

        Button cancelButton = new Button(composite, SWT.PUSH);
        cancelButton.setText("Cancel");
        cancelButton.addListener(SWT.Selection, event -> shell.close());
    }

    private void updateElement() {
        String[] elements = inputText.getText().split(",");
        if (elements.length == table.getColumnCount()) {
            TableItem[] tableItems = table.getSelection();
            for (TableItem tableItem:tableItems) {
                tableItem.setText(elements);
            }
            for (TableColumn column : table.getColumns()) column.pack();
            shell.close();
        }
    }
}
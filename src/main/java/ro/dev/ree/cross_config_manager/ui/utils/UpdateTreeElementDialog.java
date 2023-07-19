package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class UpdateTreeElementDialog extends Dialog {
    private Tree tree;
    private Shell shell;
    private Text inputText;

    public UpdateTreeElementDialog(Shell parent, Tree tree) {
        super(parent, SWT.APPLICATION_MODAL);
        this.tree = tree;
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
        TreeItem[] treeItems = tree.getSelection();
        for (TreeItem treeItem:treeItems) {
            txt = txt + treeItem.getText() + ",";
            System.out.println(txt);
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
        if (elements.length == tree.getColumnCount()) {
            TreeItem[] treeItems = tree.getSelection();
            for (TreeItem treeItem:treeItems) {
                treeItem.setText(elements);
            }
            for (TreeColumn column : tree.getColumns()) column.pack();
            shell.close();
        }
    }
}
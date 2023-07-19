package ro.dev.ree.cross_config_manager.ui.utils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class AddTreeElementDialog extends Dialog {
    private Tree tree;
    private Shell shell;
    private Text inputText;

    public AddTreeElementDialog(Shell parent, Tree tree) {
        super(parent, SWT.APPLICATION_MODAL);
        this.tree = tree;
    }

    public void open() {
        shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText("Add Items");
        shell.setLayout(new FillLayout());

        createContent();

        shell.pack();
        shell.open();
    }

    private void createContent() {
        Composite composite = new Composite(shell, SWT.NONE);
        composite.setLayout(new FillLayout());

        Label label = new Label(composite, SWT.NONE);
        label.setText("Enter row items (separated by ','): ");

        inputText = new Text(composite, SWT.BORDER);

        Button addButton = new Button(composite, SWT.PUSH);
        addButton.setText("Add");
        addButton.addListener(SWT.Selection, event -> addElement());

        Button cancelButton = new Button(composite, SWT.PUSH);
        cancelButton.setText("Cancel");
        cancelButton.addListener(SWT.Selection, event -> shell.close());
    }

    private void addElement() {
        String[] elements = inputText.getText().split(",");
        if (elements.length == tree.getColumnCount()) {
            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(elements);
            for (TreeColumn column : tree.getColumns()) column.pack();
            shell.close();
        }
    }
}

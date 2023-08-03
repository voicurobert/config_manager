package ro.dev.ree.cross_config_manager.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericEditorDialog extends Dialog {

    private Shell shell;
    private Widget[] inputTexts;

    private Map<String, Widget> inputData;

    private Map<String, Object> dataValues;

    private final String action;

    private ServiceRepository serviceRepository;

    private EditorDialogActionPerformed actionPerformed;

    public GenericEditorDialog(Shell parent, String action) {
        super(parent, SWT.APPLICATION_MODAL);
        this.action = action;
    }

    public void setServiceRepository(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public void setInputData(Map<String, Widget> inputData) {
        var input = new HashMap<String, Widget>();
        inputData.forEach((s, widget) -> {
            if (widget == null) {
                return;
            }
            input.put(s, widget);
        });
        this.inputData = input;
    }

    public void setDataValues(Map<String, Object> dataValues) {
        this.dataValues = dataValues;
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

        // this method should iterate over the inputData map and display the widget


        Button actionButton = new Button(composite, SWT.PUSH);
        actionButton.setText("Apply");
        actionButton.addListener(SWT.Selection, event -> handleAction(action));

        Button cancelButton = new Button(composite, SWT.PUSH);
        cancelButton.setText("Cancel");
        cancelButton.addListener(SWT.Selection, event -> shell.close());
    }

    private void handleAction(String action) {

        List<String> newValues = new ArrayList<>();

        // get from inputData the widget values and add id based on action

        inputData.forEach((s, widget) -> {
            // get text from widget casting it to specific class (text, combo, etc)
            //newValues.add((String) widget.getData()) ;
        });

        if (actionPerformed != null) {
            actionPerformed.actionPerformed(newValues);
        }

    }

    public void setActionPerformed(EditorDialogActionPerformed actionPerformed) {
        this.actionPerformed = actionPerformed;
    }
}

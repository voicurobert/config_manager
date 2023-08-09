package ro.dev.ree.cross_config_manager.ui.utils;

import lombok.Getter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.ui.ConfigViewGui;

import java.util.*;
import java.util.List;

public class GenericEditorDialog extends Dialog {

    private Shell shell;

    @Getter
    private Map<String, Widget> inputData;

    @Getter
    private Map<String, Object> dataValues;

    private final String action;

    private EditorDialogActionPerformed actionPerformed;

    public GenericEditorDialog(Shell parent, String action) {
        super(parent, SWT.APPLICATION_MODAL);
        this.action = action;
    }

    public void setInputData(Map<String, Widget> inputData) {
        var input = new LinkedHashMap<String, Widget>();

        for (String key : inputData.keySet()) {
            Widget widget = inputData.get(key);
            if(widget == null) {
                continue;
            }
            input.put(key, widget);
        }
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

        for (String key : inputData.keySet()) {
            Widget widget = inputData.get(key);

            Label label = new Label(composite, SWT.NONE);
            label.setText(key);

            if (widget instanceof Text) {
                ((Text)widget).setParent(composite);
                ((Text)widget).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            } else if (widget instanceof Combo) {
                ((Combo)widget).setParent(composite);
                ((Combo)widget).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            } else if (widget instanceof Button) {
                ((Button)widget).setParent(composite);
                ((Button)widget).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
                ((Button)widget).addListener(SWT.Selection, event ->  {
                    checkBoxSelected(((Button) widget));
                });
            }
        }

        Button actionButton = new Button(composite, SWT.PUSH);
        actionButton.setText(action);
        actionButton.addListener(SWT.Selection, event -> handleAction());

        Button cancelButton = new Button(composite, SWT.PUSH);
        cancelButton.setText("Cancel");
        cancelButton.addListener(SWT.Selection, event -> shell.close());
    }

    private void checkBoxSelected(Button checkBox) {
        if(checkBox.getSelection())
        {
            checkBox.setText("true");
        }
        else {
            checkBox.setText("false");
        }
    }

    private void handleAction() {
        List<String> newValues = new ArrayList<>();

        // get from inputData the widget values and add id based on action
        for (String key : inputData.keySet()) {
            Widget widget = inputData.get(key);
            if (widget instanceof Text) {
                newValues.add(((Text) widget).getText());
            } else if (widget instanceof Combo) {
                newValues.add(((Combo) widget).getText());
            } else if (widget instanceof Button) {
                newValues.add(((Button) widget).getText());
            }
        }

        if (actionPerformed != null) {
            actionPerformed.actionPerformed(newValues);
            // Reopen ConfigViewGui after updates
            if(action.equals("Update")){
                ConfigViewGui configViewGui = new ConfigViewGui();
                // Close Dialog after "Update" action
                shell.dispose();
                // Close main shell
                getParent().dispose();
                // Open ConfigViewGui
                configViewGui.open();
            }
            else {
                // Close Dialog after "Add" action
                shell.dispose();
            }
        }
    }

    public void setActionPerformed(EditorDialogActionPerformed actionPerformed) {
        this.actionPerformed = actionPerformed;
    }
}

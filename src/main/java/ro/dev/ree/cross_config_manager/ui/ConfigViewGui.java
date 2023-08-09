package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.ui.utils.Drawable;
import ro.dev.ree.cross_config_manager.ui.utils.EditorDialogActionPerformed;

import java.util.List;

public class ConfigViewGui {
    private final Shell shell;

    public ConfigViewGui() {
        shell = new Shell(Display.getCurrent(), SWT.NO_TRIM);
        shell.setLayout(new FillLayout());
        shell.setMaximized(true);
    }

    public void open() {
        ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        Composite composite = new Composite(scrolledComposite, SWT.NONE);
        scrolledComposite.setContent(composite);

        composite.setLayout(new GridLayout(1, true));
        composite.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));

        Label title = new Label(composite, SWT.CENTER);
        title.setText("CROSS Config - " + ConfigSingleton.getSingleton().getConfigDto().getName());
        title.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));

        ConfigManagerComposites composites = new ConfigManagerComposites();
        for (Drawable comp : composites.getComposites()) {
            comp.createContents(composite);
        }

        Button backToLoadConfigButton = new Button(composite, SWT.PUSH);
        backToLoadConfigButton.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));
        backToLoadConfigButton.setText("Back to load config gui");
        backToLoadConfigButton.addListener(SWT.Selection, event -> shell.dispose());

        scrolledComposite.setAlwaysShowScrollBars(true);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
        scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        shell.open();
    }
}

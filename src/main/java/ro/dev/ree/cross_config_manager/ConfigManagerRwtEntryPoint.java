package ro.dev.ree.cross_config_manager;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.widgets.Composite;
import ro.dev.ree.cross_config_manager.ui.ConfigManagerGui;

public class ConfigManagerRwtEntryPoint extends AbstractEntryPoint {

    @Override
    protected void createContents(Composite parent) {
        var configManagerGui = new ConfigManagerGui();
        configManagerGui.createContents(parent);
    }
}

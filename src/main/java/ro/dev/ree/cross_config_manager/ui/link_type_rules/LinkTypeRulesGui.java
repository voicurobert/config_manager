package ro.dev.ree.cross_config_manager.ui.link_type_rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;

public class LinkTypeRulesGui extends TreeComposite{

    @Override
    public String[] columns() {
        return new String[]{"IdLTR", "ConfigId", "Consumer", "Provider",
                "RoutingPolicy", "CapacityCalculatorName", "NumberOfChannels"};
    }

    @Override
    public String treeName() {
        return "Link Types Rules";
    }

    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Tree tree = (Tree) super.createContents(parent);
        tree.setToolTipText("LinkTypeRulesTree");

        String[][] linkTypeRulesData = {
                {"idltr1", "configId", "consumer", "provider",
                        "routingPolicy", "capacityCalculatorName", "numberOfChannels"},
                {"idltr2", "configId", "consumer", "provider",
                        "routingPolicy", "capacityCalculatorName", "numberOfChannels"},
                {"idltr3", "configId", "consumer", "provider",
                        "routingPolicy", "capacityCalculatorName", "numberOfChannels"},
                {"idltr4", "configId", "consumer", "provider",
                        "routingPolicy", "capacityCalculatorName", "numberOfChannels"},
                {"idltr5", "configId", "consumer", "provider",
                        "routingPolicy", "capacityCalculatorName", "numberOfChannels"},
                {"idltr6", "configId", "consumer", "provider",
                        "routingPolicy", "capacityCalculatorName", "numberOfChannels"}
        };

        for (String[] row : linkTypeRulesData) {
            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(row);
        }

        return tree;
    }
}

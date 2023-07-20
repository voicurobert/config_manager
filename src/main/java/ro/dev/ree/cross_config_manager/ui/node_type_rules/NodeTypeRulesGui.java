package ro.dev.ree.cross_config_manager.ui.node_type_rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import ro.dev.ree.cross_config_manager.model.ServiceRepository;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;

public class NodeTypeRulesGui extends TreeComposite {

    @Override
    public String[] columns() {
        return new String[]{"IdNTR", "ConfigId", "Child", "Parent",
                "CapacityCalculatorName", "MandatoryParent"};
    }

    @Override
    public String treeName() {
        return "Node Types Rules";
    }

    @Override
    public ServiceRepository getServiceRepository() {
        return null;
    }

    @Override
    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Tree tree = (Tree) super.createContents(parent);
        tree.setToolTipText("NodeTypeRulesTree");

        String[][] nodeTypeRulesData = {
                {"idntr1", "configId", "child", "parent",
                        "capacityCalculatorName", "mandatoryParent"},
                {"idntr2", "configId", "child", "parent",
                        "capacityCalculatorName", "mandatoryParent"},
                {"idntr3", "configId", "child", "parent",
                        "capacityCalculatorName", "mandatoryParent"},
                {"idntr4", "configId", "child", "parent",
                        "capacityCalculatorName", "mandatoryParent"},
                {"idntr5", "configId", "child", "parent",
                        "capacityCalculatorName", "mandatoryParent"},
                {"idntr6", "configId", "child", "parent",
                        "capacityCalculatorName", "mandatoryParent"}
        };

        for (String[] row : nodeTypeRulesData) {
            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(row);
        }

        return tree;
    }

}

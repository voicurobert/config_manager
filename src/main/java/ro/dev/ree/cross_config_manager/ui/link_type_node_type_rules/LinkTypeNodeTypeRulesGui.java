package ro.dev.ree.cross_config_manager.ui.link_type_node_type_rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import ro.dev.ree.cross_config_manager.ui.utils.TreeComposite;

public class LinkTypeNodeTypeRulesGui extends TreeComposite {

    @Override
    public String[] columns() {
        return new String[]{"IdLTNTR", "ConfigId", "LinkType", "NodeType",
                "Quality"};
    }

    @Override
    public String treeName() {
        return "Link Types Node Type Rules";
    }

    public Composite createContents(Composite parent) {
        createCheckbox(parent);

        Tree tree = (Tree) super.createContents(parent);
        tree.setToolTipText("LinkTypeNodeTypeRulesTree");

        String[][] linkTypeNodeTypeRulesData = {
                {"idltntr1", "configId", "linkType", "nodeType",
                        "quality"},
                {"idltntr2", "configId", "linkType", "nodeType",
                        "quality"},
                {"idltntr3", "configId", "linkType", "nodeType",
                "quality"},
                {"idltntr4", "configId", "linkType", "nodeType",
                "quality"},
                {"idltntr5", "configId", "linkType", "nodeType",
                "quality"},
                {"idltntr6", "configId", "linkType", "nodeType",
                "quality"},
        };

        for (String[] row : linkTypeNodeTypeRulesData) {
            TreeItem item = new TreeItem(tree, SWT.NONE);
            item.setText(row);
        }

        return tree;
    }
}
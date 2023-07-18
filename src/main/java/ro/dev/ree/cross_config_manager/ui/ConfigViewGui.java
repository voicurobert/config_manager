package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ro.dev.ree.cross_config_manager.ui.utils.Drawable;

public class ConfigViewGui {
    private final Shell shell;

    public ConfigViewGui() {
        shell = new Shell(Display.getCurrent(), SWT.NO_TRIM);
        shell.setLayout(new FillLayout());
        shell.setMaximized(true);
    }

    public void open() {
        ScrolledComposite sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        Composite c = new Composite(sc, SWT.NONE);
        sc.setContent(c);

        c.setLayout(new GridLayout(1, true));
        c.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));
        sc.setAlwaysShowScrollBars(true);

        Label title = new Label(c, SWT.CENTER);
        title.setText("CROSS Config - ");
        title.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));

        ConfigManagerComposites composites = new ConfigManagerComposites();
        for (Drawable composite : composites.getComposites()) {
            composite.createContents(c);
        }

        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);
        sc.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        shell.open();
    }

    public void openOld() {
        var shellLayout = new GridLayout();
        shellLayout.marginHeight = 20;
        shellLayout.marginWidth = 20;
        shell.setLayout(shellLayout);
        shell.setFullScreen(true);
        shell.setMaximized(true);

        ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.CENTER | SWT.V_SCROLL | SWT.H_SCROLL);
        scrolledComposite.setLayout(new GridLayout());

        Composite contentComposite = new Composite(scrolledComposite, SWT.VERTICAL | SWT.CENTER);
        contentComposite.setLayout(new GridLayout());

//        // Create the main tree
//        Tree MainTree = new Tree(contentComposite, SWT.VERTICAL | SWT.CENTER);
//        MainTree.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT));
//        MainTree.setToolTipText("Main Tree");
//        MainTree.setHeaderVisible(true);
//        TreeColumn mainColumn = new TreeColumn(MainTree, SWT.NONE);
//        mainColumn.setText("Collections");
//        TreeItem mainParent1 = new TreeItem(MainTree, SWT.NONE);
//        mainParent1.setText("Node Type Collection");
//
//        TreeItem mainParent2 = new TreeItem(MainTree, SWT.NONE);
//        mainParent2.setText("Link Type Collection");
//
//        TreeItem mainParent3 = new TreeItem(MainTree, SWT.NONE);
//        mainParent3.setText("Class Type Collection");
//
//        TreeItem mainParent4 = new TreeItem(MainTree, SWT.NONE);
//        mainParent4.setText("Node Type Rules Collection");
//
//        TreeItem mainParent5 = new TreeItem(MainTree, SWT.NONE);
//        mainParent5.setText("Link Type Rules Collection");
//
//        TreeItem mainParent6 = new TreeItem(MainTree, SWT.NONE);
//        mainParent6.setText("Link Type Node Type Rules Collection");

        // Create Tables
        Table nodeTypeTable = new Table(contentComposite, SWT.BORDER | SWT.CENTER);
        nodeTypeTable.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT));
        nodeTypeTable.setToolTipText("NodeTypeTable");

        Table linkTypeTable = new Table(contentComposite, SWT.BORDER | SWT.CENTER);
        linkTypeTable.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT));
        linkTypeTable.setToolTipText("LinkTypeTable");

        Table classTypeTable = new Table(contentComposite, SWT.BORDER | SWT.CENTER);
        classTypeTable.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT));
        classTypeTable.setToolTipText("ClassTypeTable");

        // Create Trees
        Tree nodeTypeRulesTree = new Tree(contentComposite, SWT.BORDER | SWT.CENTER);
        nodeTypeRulesTree.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT));
        nodeTypeRulesTree.setToolTipText("NodeTypeRulesTree");

        Tree linkTypeRulesTree = new Tree(contentComposite, SWT.BORDER | SWT.CENTER);
        linkTypeRulesTree.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT));
        linkTypeRulesTree.setToolTipText("LinkTypeRulesTree");

        Tree linkTypeNodeTypeRulesTree = new Tree(contentComposite, SWT.BORDER | SWT.CENTER);
        linkTypeRulesTree.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT));
        linkTypeRulesTree.setToolTipText("LinkTypeNodeTypeRulesTree");

        // Set Header&Lines visible for tables and trees
        nodeTypeTable.setHeaderVisible(true);
        nodeTypeTable.setLinesVisible(true);

        linkTypeTable.setHeaderVisible(true);
        linkTypeTable.setLinesVisible(true);

        classTypeTable.setHeaderVisible(true);
        classTypeTable.setLinesVisible(true);

        nodeTypeRulesTree.setHeaderVisible(true);
        nodeTypeRulesTree.setLinesVisible(true);

        linkTypeRulesTree.setHeaderVisible(true);
        linkTypeRulesTree.setLinesVisible(true);

        linkTypeNodeTypeRulesTree.setHeaderVisible(true);
        linkTypeNodeTypeRulesTree.setLinesVisible(true);

        // Create table columns with headers
        String[] nodeTypeHeaders = {"IdNT", "ConfigId", "Discriminator", "Name", "AppIcon", "MapIcon",
                "CapacityFull", "CapacityUnitName", "TypeClassPath",
                "RootType", "System", "MultiparentAllowed", "UniquenessType"};
        for (String header : nodeTypeHeaders) {
            TableColumn column = new TableColumn(nodeTypeTable, SWT.NONE);
            column.setText(header);
        }

        // Create table columns with headers
        String[] linkTypeHeaders = {"IdLT", "ConfigId", "Discriminator", "Name", "AppIcon", "MapIcon",
                "CapacityFull", "CapacityUnitName", "TypeClassPath",
                "System", "Unique"};
        for (String header : linkTypeHeaders) {
            TableColumn column = new TableColumn(linkTypeTable, SWT.NONE);
            column.setText(header);
        }

        // Create table columns with headers
        String[] classTypeHeaders = {"IdCT", "ConfigId", "Name", "Path", "ParentPath"};
        for (String header : classTypeHeaders) {
            TableColumn column = new TableColumn(classTypeTable, SWT.NONE);
            column.setText(header);
        }

        // Create tree columns with headers
        String[] NodeTypeRulesHeaders = {"IdNTR", "ConfigId", "Child", "Parent",
                "CapacityCalculatorName", "MandatoryParent"};
        for (String header : NodeTypeRulesHeaders) {
            TreeColumn column = new TreeColumn(nodeTypeRulesTree, SWT.NONE);
            column.setText(header);
        }

        // Create tree columns with headers
        String[] LinkTypeRulesHeaders = {"IdLTR", "ConfigId", "Consumer", "Provider",
                "RoutingPolicy", "CapacityCalculatorName", "NumberOfChannels"};
        for (String header : LinkTypeRulesHeaders) {
            TreeColumn column = new TreeColumn(linkTypeRulesTree, SWT.NONE);
            column.setText(header);
        }

        // Create tree columns with headers
        String[] LinkTypeNodeTypeRulesHeaders = {"IdLTNTR", "ConfigId", "LinkType", "NodeType",
                "Quality"};
        for (String header : LinkTypeNodeTypeRulesHeaders) {
            TreeColumn column = new TreeColumn(linkTypeNodeTypeRulesTree, SWT.NONE);
            column.setText(header);
        }

        // Create table rows with data
        String[][] nodeTypeData = {
                {"idnt1", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"},
                {"idnt2", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"},
                {"idnt3", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"},
                {"idnt4", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"},
                {"idnt5", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"},
                {"idnt6", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system", "multiparentAllowed", "uniquenessType"}
        };

        // Create table rows with data
        String[][] linkTypeData = {
                {"idlt1", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idlt2", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idlt3", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idlt4", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idlt5", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idlt6", "configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"}
        };

        // Create table rows with data
        String[][] classTypeData = {
                {"idct1", "configId", "name", "path", "parentPath"},
                {"idct2", "configId", "name", "path", "parentPath"},
                {"idct3", "configId", "name", "path", "parentPath"},
                {"idct4", "configId", "name", "path", "parentPath"},
                {"idct5", "configId", "name", "path", "parentPath"},
                {"idct6", "configId", "name", "path", "parentPath"}
        };

        // Set table rows with data
        for (String[] row : nodeTypeData) {
            TableItem item = new TableItem(nodeTypeTable, SWT.NONE);
            item.setText(row);
        }

        for (String[] row : linkTypeData) {
            TableItem item = new TableItem(linkTypeTable, SWT.NONE);
            item.setText(row);
        }

        for (String[] row : classTypeData) {
            TableItem item = new TableItem(classTypeTable, SWT.NONE);
            item.setText(row);
        }


        // Create parent nodes
        TreeItem ntrParent1 = new TreeItem(nodeTypeRulesTree, SWT.NONE);
        ntrParent1.setText(new String[]{"idntr1", "configId", "child", "parent",
                "capacityCalculatorName", "mandatoryParent"});

        TreeItem ntrParent2 = new TreeItem(nodeTypeRulesTree, SWT.NONE);
        ntrParent2.setText(new String[]{"idntr2", "configId", "child", "parent",
                "capacityCalculatorName", "mandatoryParent"});

        // Create child nodes under parent 1
        TreeItem ntrChild1 = new TreeItem(ntrParent1, SWT.NONE);
        ntrChild1.setText(new String[]{"idntr3", "configId", "child", "parent",
                "capacityCalculatorName", "mandatoryParent"});

        TreeItem ntrChild2 = new TreeItem(ntrParent1, SWT.NONE);
        ntrChild2.setText(new String[]{"idntr4", "configId", "child", "parent",
                "capacityCalculatorName", "mandatoryParent"});

        // Create child nodes under parent 2
        TreeItem ntrChild3 = new TreeItem(ntrParent2, SWT.NONE);
        ntrChild3.setText(new String[]{"idntr5", "configId", "child", "parent",
                "capacityCalculatorName", "mandatoryParent"});

        TreeItem ntrChild4 = new TreeItem(ntrParent2, SWT.NONE);
        ntrChild4.setText(new String[]{"idntr6", "configId", "child", "parent",
                "capacityCalculatorName", "mandatoryParent"});


        // Create parent nodes
        TreeItem ltrParent1 = new TreeItem(linkTypeRulesTree, SWT.NONE);
        ltrParent1.setText(new String[]{"idltr1", "configId", "consumer", "provider",
                "routingPolicy", "capacityCalculatorName", "numberOfChannels"});

        TreeItem ltrParent2 = new TreeItem(linkTypeRulesTree, SWT.NONE);
        ltrParent2.setText(new String[]{"idltr2", "configId", "consumer", "provider",
                "routingPolicy", "capacityCalculatorName", "numberOfChannels"});

        // Create child nodes under parent 1
        TreeItem ltrChild1 = new TreeItem(ltrParent1, SWT.NONE);
        ltrChild1.setText(new String[]{"idltr3", "configId", "consumer", "provider",
                "routingPolicy", "capacityCalculatorName", "numberOfChannels"});

        TreeItem ltrChild2 = new TreeItem(ltrParent1, SWT.NONE);
        ltrChild2.setText(new String[]{"idltr4", "configId", "consumer", "provider",
                "routingPolicy", "capacityCalculatorName", "numberOfChannels"});

        // Create child nodes under parent 2
        TreeItem ltrChild3 = new TreeItem(ltrParent2, SWT.NONE);
        ltrChild3.setText(new String[]{"idltr5", "configId", "consumer", "provider",
                "routingPolicy", "capacityCalculatorName", "numberOfChannels"});

        TreeItem ltrChild4 = new TreeItem(ltrParent2, SWT.NONE);
        ltrChild4.setText(new String[]{"idltr6", "configId", "consumer", "provider",
                "routingPolicy", "capacityCalculatorName", "numberOfChannels"});

        // Create parent nodes
        TreeItem ltNtrParent1 = new TreeItem(linkTypeNodeTypeRulesTree, SWT.NONE);
        ltNtrParent1.setText(new String[]{"idltntr1", "configId", "linkType", "nodeType",
                "quality"});

        TreeItem ltNtrParent2 = new TreeItem(linkTypeNodeTypeRulesTree, SWT.NONE);
        ltNtrParent2.setText(new String[]{"idltntr2", "configId", "linkType", "nodeType",
                "quality"});

        // Create child nodes under parent 1
        TreeItem ltNtrChild1 = new TreeItem(ltNtrParent1, SWT.NONE);
        ltNtrChild1.setText(new String[]{"idltntr3", "configId", "linkType", "nodeType",
                "quality"});

        TreeItem ltNtrChild2 = new TreeItem(ltNtrParent1, SWT.NONE);
        ltNtrChild2.setText(new String[]{"idltntr4", "configId", "linkType", "nodeType",
                "quality"});

        // Create child nodes under parent 2
        TreeItem ltNtrChild3 = new TreeItem(ltNtrParent2, SWT.NONE);
        ltNtrChild3.setText(new String[]{"idltntr5", "configId", "linkType", "nodeType",
                "quality"});

        TreeItem ltNtrChild4 = new TreeItem(ltNtrParent2, SWT.NONE);
        ltNtrChild4.setText(new String[]{"idltntr6", "configId", "linkType", "nodeType",
                "quality"});


        // Resize columns to fit content
        for (TableColumn column : nodeTypeTable.getColumns()) column.pack();

        for (TableColumn column : linkTypeTable.getColumns()) column.pack();

        for (TableColumn column : classTypeTable.getColumns()) column.pack();

        for (TreeColumn column : nodeTypeRulesTree.getColumns()) column.pack();

        for (TreeColumn column : linkTypeRulesTree.getColumns()) column.pack();

        for (TreeColumn column : linkTypeNodeTypeRulesTree.getColumns()) column.pack();

//        for (TreeColumn column : MainTree.getColumns()) column.pack();


        // Create Menu for each component
        var nodeTypeMenu = new Menu(nodeTypeTable);
        nodeTypeTable.setMenu(nodeTypeMenu);
        var linkTypeMenu = new Menu(linkTypeTable);
        linkTypeTable.setMenu(linkTypeMenu);
        var classTypeMenu = new Menu(classTypeTable);
        classTypeTable.setMenu(classTypeMenu);
        var nodeTypeRulesMenu = new Menu(nodeTypeRulesTree);
        nodeTypeRulesTree.setMenu(nodeTypeRulesMenu);
        var linkTypeRulesMenu = new Menu(linkTypeRulesTree);
        linkTypeRulesTree.setMenu(linkTypeRulesMenu);
        var linkTypeNodeTypeRulesMenu = new Menu(linkTypeNodeTypeRulesTree);
        linkTypeNodeTypeRulesTree.setMenu(linkTypeNodeTypeRulesMenu);

        // Create FileMenu
        var nodeTypeFileMenu = new Menu(nodeTypeMenu);
        var linkTypeFileMenu = new Menu(linkTypeMenu);
        var classTypeFileMenu = new Menu(classTypeMenu);
        var nodeTypeRulesFileMenu = new Menu(nodeTypeRulesMenu);
        var linkTypeRulesFileMenu = new Menu(linkTypeRulesMenu);
        var linkTypeNodeTypeRulesFileMenu = new Menu(linkTypeNodeTypeRulesMenu);

        // Create Menu Items
        // Set Headline for menus
        var nodeTypeMenuHeadline = new MenuItem(nodeTypeMenu, SWT.CASCADE);
        nodeTypeMenuHeadline.setText("Node Type Menu");
        nodeTypeMenuHeadline.setMenu(nodeTypeFileMenu);

        var linkTypeMenuHeadline = new MenuItem(linkTypeMenu, SWT.CASCADE);
        linkTypeMenuHeadline.setText("Link Type Menu");
        linkTypeMenuHeadline.setMenu(linkTypeFileMenu);

        var classTypeMenuHeadline = new MenuItem(classTypeMenu, SWT.CASCADE);
        classTypeMenuHeadline.setText("Class Type Menu");
        classTypeMenuHeadline.setMenu(classTypeFileMenu);

        var nodeTypeRulesMenuHeadline = new MenuItem(nodeTypeRulesMenu, SWT.CASCADE);
        nodeTypeRulesMenuHeadline.setText("Node Type Rules Menu");
        nodeTypeRulesMenuHeadline.setMenu(nodeTypeRulesFileMenu);

        var linkTypeRulesMenuHeadline = new MenuItem(linkTypeRulesMenu, SWT.CASCADE);
        linkTypeRulesMenuHeadline.setText("Link Type Rules Menu");
        linkTypeRulesMenuHeadline.setMenu(linkTypeRulesFileMenu);

        var linkTypeNodeTypeRulesMenuHeadline = new MenuItem(linkTypeNodeTypeRulesMenu, SWT.CASCADE);
        linkTypeNodeTypeRulesMenuHeadline.setText("Link Type Node Type Rules Menu");
        linkTypeNodeTypeRulesMenuHeadline.setMenu(linkTypeNodeTypeRulesFileMenu);

        // Set Menu Options
        var nodeTypeMenuAdd = new MenuItem(nodeTypeFileMenu, SWT.NONE);
        nodeTypeMenuAdd.setText("Add new node type");
        var nodeTypeMenuUpdate = new MenuItem(nodeTypeFileMenu, SWT.NONE);
        nodeTypeMenuUpdate.setText("Update node type");
        var nodeTypeMenuDelete = new MenuItem(nodeTypeFileMenu, SWT.NONE);
        nodeTypeMenuDelete.setText("Delete node type");

        var linkTypeMenuAdd = new MenuItem(linkTypeFileMenu, SWT.NONE);
        linkTypeMenuAdd.setText("Add new link type");
        var linkTypeMenuUpdate = new MenuItem(linkTypeFileMenu, SWT.NONE);
        linkTypeMenuUpdate.setText("Update link type");
        var linkTypeMenuDelete = new MenuItem(linkTypeFileMenu, SWT.NONE);
        linkTypeMenuDelete.setText("Delete link type");

        var classTypeMenuAdd = new MenuItem(classTypeFileMenu, SWT.NONE);
        classTypeMenuAdd.setText("Add new class type");
        var classTypeMenuUpdate = new MenuItem(classTypeFileMenu, SWT.NONE);
        classTypeMenuUpdate.setText("Update class type");
        var classTypeMenuDelete = new MenuItem(classTypeFileMenu, SWT.NONE);
        classTypeMenuDelete.setText("Delete class type");

        var nodeTypeRulesMenuAdd = new MenuItem(nodeTypeRulesFileMenu, SWT.NONE);
        nodeTypeRulesMenuAdd.setText("Add new node type rules");
        var nodeTypeRulesMenuUpdate = new MenuItem(nodeTypeRulesFileMenu, SWT.NONE);
        nodeTypeRulesMenuUpdate.setText("Update node type rules");
        var nodeTypeRulesMenuDelete = new MenuItem(nodeTypeRulesFileMenu, SWT.NONE);
        nodeTypeRulesMenuDelete.setText("Delete node type rules");

        var linkTypeRulesMenuAdd = new MenuItem(linkTypeRulesFileMenu, SWT.NONE);
        linkTypeRulesMenuAdd.setText("Add new link type rules");
        var linkTypeRulesMenuUpdate = new MenuItem(linkTypeRulesFileMenu, SWT.NONE);
        linkTypeRulesMenuUpdate.setText("Update link type rules");
        var linkTypeRulesMenuDelete = new MenuItem(linkTypeRulesFileMenu, SWT.NONE);
        linkTypeRulesMenuDelete.setText("Delete link type rules");

        var linkTypeNodeTypeRulesMenuAdd = new MenuItem(linkTypeNodeTypeRulesFileMenu, SWT.NONE);
        linkTypeNodeTypeRulesMenuAdd.setText("Add new link type node type rules");
        var linkTypeNodeTypeRulesMenuUpdate = new MenuItem(linkTypeNodeTypeRulesFileMenu, SWT.NONE);
        linkTypeNodeTypeRulesMenuUpdate.setText("Update link type node type rules");
        var linkTypeNodeTypeRulesMenuDelete = new MenuItem(linkTypeNodeTypeRulesFileMenu, SWT.NONE);
        linkTypeNodeTypeRulesMenuDelete.setText("Delete link type node type rules");

        // Set as visible
        nodeTypeMenu.setVisible(true);

        linkTypeMenu.setVisible(true);

        classTypeMenu.setVisible(true);

        nodeTypeRulesMenu.setVisible(true);

        linkTypeRulesMenu.setVisible(true);

        linkTypeNodeTypeRulesMenu.setVisible(true);

        // Create back_to_main button
        Button backToMainButton = new Button(contentComposite, SWT.PUSH);
        backToMainButton.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT));
        backToMainButton.setText("Back to main menu");

        backToMainButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });
        contentComposite.pack();

        scrolledComposite.setContent(contentComposite);
        scrolledComposite.pack();
        scrolledComposite.setAlwaysShowScrollBars(true);

        shell.open();
    }
}

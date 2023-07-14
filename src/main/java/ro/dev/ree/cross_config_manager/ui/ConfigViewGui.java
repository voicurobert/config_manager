package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

public class ConfigViewGui {
    private Shell shell;
    public ConfigViewGui() {
        shell = new Shell(Display.getCurrent(), SWT.NO_TRIM);
    }

    public void open() {

        shell.setMaximized(true);
        var mainLayout = new RowLayout();
        mainLayout.type = SWT.VERTICAL;
        mainLayout.center = true;
        mainLayout.justify = true;
        mainLayout.fill = true;
        mainLayout.spacing = 10;

        shell.setLayout(mainLayout);

//        ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
//        scrolledComposite.setExpandHorizontal(true);
//        scrolledComposite.setExpandVertical(true);

        Table nodeTypeTable = new Table (shell, SWT.VIRTUAL | SWT.BORDER);
        Table linkTypeTable = new Table (shell, SWT.VIRTUAL | SWT.BORDER);
        //Table configTypeTable = new Table(shell, SWT.VIRTUAL|SWT.BORDER);
        //Table classTypeTable = new Table(shell, SWT.VIRTUAL|SWT.BORDER);

        Tree nodeTypeRulesTree = new Tree(shell, SWT.BORDER);

        nodeTypeTable.setHeaderVisible(true);
        nodeTypeTable.setLinesVisible(true);

        linkTypeTable.setHeaderVisible(true);
        linkTypeTable.setLinesVisible(true);

        // configTypeTable.setHeaderVisible(true);
        // configTypeTable.setLinesVisible(true);

//        classTypeTable.setHeaderVisible(true);
//        classTypeTable.setLinesVisible(true);

        nodeTypeRulesTree.setHeaderVisible(true);

        // Create table columns with headers
        String[] nodeTypeHeaders = {"idn","configId", "discriminator", "name", "appIcon", "mapIcon",
                "capacityFull", "capacityUnitName", "typeClassPath",
                "rootType", "system","multiparentAllowed", "uniquenessType"};
        for (String header : nodeTypeHeaders) {
            TableColumn column = new TableColumn(nodeTypeTable, SWT.NONE);
            column.setText(header);
        }

        // Create table columns with headers
        String[] linkTypeHeaders = {"idl","configId", "discriminator", "name", "appIcon", "mapIcon",
                "capacityFull", "capacityUnitName", "typeClassPath",
                "system", "unique"};
        for (String header : linkTypeHeaders) {
            TableColumn column = new TableColumn(linkTypeTable, SWT.NONE);
            column.setText(header);
        }

//        // Create table columns with headers !!!DE MODIFICAT
//        String[] classTypeHeaders = {"idl","configId", "discriminator", "name", "appIcon", "mapIcon",
//                "capacityFull", "capacityUnitName", "typeClassPath",
//                "system", "unique"};
//        for (String header : classTypeHeaders) {
//            TableColumn column = new TableColumn(classTypeTable, SWT.NONE);
//            column.setText(header);
//        }

        // Create tree columns with headers
        String[] NodeTypeRulesHeaders = {"Item", "Description", "Quantity"};
        for (String header : NodeTypeRulesHeaders) {
            TreeColumn column = new TreeColumn(nodeTypeRulesTree, SWT.NONE);
            column.setText(header);
        }

        // Create table rows with data
        String[][] nodeTypeData = {
                {"idn1","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system","multiparentAllowed", "uniquenessType"},
                {"idn2","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system","multiparentAllowed", "uniquenessType"},
                {"idn3","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system","multiparentAllowed", "uniquenessType"},
                {"idn4","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system","multiparentAllowed", "uniquenessType"},
                {"idn5","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system","multiparentAllowed", "uniquenessType"},
                {"idn6","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "rootType", "system","multiparentAllowed", "uniquenessType"}
        };

        // Create table rows with data
        String[][] linkTypeData = {
                {"idl1","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idl2","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idl3","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idl4","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idl5","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idl6","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"}
        };

        // Create table rows with data !!!DE MODIFICAT
        String[][] classTypeData = {
                {"idl1","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idl2","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idl3","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idl4","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idl5","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"},
                {"idl6","configId", "discriminator", "name", "appIcon", "mapIcon",
                        "capacityFull", "capacityUnitName", "typeClassPath",
                        "system", "unique"}
        };

        for (String[] row : nodeTypeData) {
            TableItem item = new TableItem(nodeTypeTable, SWT.NONE);
            item.setText(row);
        }

        for (String[] row : linkTypeData) {
            TableItem item = new TableItem(linkTypeTable, SWT.NONE);
            item.setText(row);
        }

//        for (String[] row : classTypeData) {
//            TableItem item = new TableItem(classTypeTable, SWT.NONE);
//            item.setText(row);
//        }

        // Create parent nodes
        TreeItem parent1 = new TreeItem(nodeTypeRulesTree, SWT.NONE);
        parent1.setText(new String[] {"Parent 1", "Description 1", "10"});

        TreeItem parent2 = new TreeItem(nodeTypeRulesTree, SWT.NONE);
        parent2.setText(new String[] {"Parent 2", "Description 2", "5"});

        // Create child nodes under parent 1
        TreeItem child1 = new TreeItem(parent1, SWT.NONE);
        child1.setText(new String[] {"Child 1", "Description 1", "3"});

        TreeItem child2 = new TreeItem(parent1, SWT.NONE);
        child2.setText(new String[] {"Child 2", "Description 2", "2"});

        // Create child nodes under parent 2
        TreeItem child3 = new TreeItem(parent2, SWT.NONE);
        child3.setText(new String[] {"Child 3", "Description 3", "2"});

        TreeItem child4 = new TreeItem(parent2, SWT.NONE);
        child4.setText(new String[] {"Child 4", "Description 4", "3"});


        // Resize columns to fit content
        for (TableColumn column : nodeTypeTable.getColumns()) {
            column.pack();
        }

        // Resize columns to fit content
        for (TableColumn column : linkTypeTable.getColumns()) {
            column.pack();
        }

//        // Resize columns to fit content
//        for (TableColumn column : classTypeTable.getColumns()) {
//            column.pack();
//        }

        // Resize columns to fit content
        for (TreeColumn column : nodeTypeRulesTree.getColumns()) {
            column.pack();
        }

        Button newConfigButton = new Button(shell, SWT.PUSH);
        Button updateSelectedButton = new Button(shell, SWT.PUSH);
        Button deleteSelectedButton = new Button(shell, SWT.PUSH);
        Button backToMainButton = new Button(shell, SWT.PUSH);

        newConfigButton.setText("Add new config");
        updateSelectedButton.setText("Update selected items");
        deleteSelectedButton.setText("Delete selected items");
        backToMainButton.setText("Back to main menu");

        newConfigButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                newConfigButtonClicked();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        updateSelectedButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        deleteSelectedButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        backToMainButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        shell.open();
    }
    public void newConfigButtonClicked() {
        NewConfigGui newConfigGui = new NewConfigGui();
        newConfigGui.open();
    }
}

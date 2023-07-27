package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.Config;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigTypeService;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesDto;
import ro.dev.ree.cross_config_manager.ui.class_type.ClassTypeGui;
import ro.dev.ree.cross_config_manager.ui.link_type.LinkTypeGui;
import ro.dev.ree.cross_config_manager.ui.link_type_node_type_rules.LinkTypeNodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.link_type_rules.LinkTypeRulesGui;
import ro.dev.ree.cross_config_manager.ui.node_type.NodeTypeGui;
import ro.dev.ree.cross_config_manager.ui.node_type_rules.NodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.xml.writer.XmlElement;
import ro.dev.ree.cross_config_manager.xml.writer.XmlWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class ConfigListViewGui {
    private final Shell shell;
    private final ConfigTypeService configTypeService = ConfigManagerContextProvider.getBean(ConfigTypeService.class);

    public ConfigListViewGui() {
        shell = new Shell(Display.getCurrent(), SWT.NO_TRIM);
    }

    public void open() {
        List<Config> listConfigName = configTypeService.findAll();

        shell.setMaximized(true);
        var mainLayout = new RowLayout();
        mainLayout.type = SWT.VERTICAL;
        mainLayout.center = true;
        mainLayout.fill = true;
        mainLayout.spacing = 1;
        int width = 150;
        int height = 40;
        shell.setLayout(mainLayout);

        Table configTypeTable = new Table(shell, SWT.BORDER | SWT.CENTER);

        configTypeTable.setHeaderVisible(true);
        configTypeTable.setLinesVisible(true);
        configTypeTable.setLayoutData(new RowData(250, 150));
        String[] headerListView = {"Config name"};

        for (String header : headerListView) {
            TableColumn column = new TableColumn(configTypeTable, SWT.BORDER);
            column.setText(header);
            column.pack();
        }


        for (Config config : listConfigName) {
            TableItem item = new TableItem(configTypeTable, SWT.BORDER);
            item.setText(config.getName());
        }

        Menu menu = new Menu(configTypeTable);
        configTypeTable.setMenu(menu);
        var ConfigDetailMenuItem = new MenuItem(menu, SWT.PUSH);
        ConfigDetailMenuItem.setText("Config detail");
        var ExportConfigMenuItem = new MenuItem(menu, SWT.PUSH);
        ExportConfigMenuItem.setText("Export as XML file");

        ConfigDetailMenuItem.addListener(SWT.Selection, event -> loadConfigView(configTypeTable));
        ExportConfigMenuItem.addListener(SWT.Selection, event -> {
            try {
                exportConfig(configTypeTable);
            } catch (ParserConfigurationException | TransformerException e) {
                throw new RuntimeException(e);
            }
        });

        for (TableColumn column : configTypeTable.getColumns()) {
            column.pack();
        }

        Button backToMainButton = new Button(shell, SWT.PUSH);
        backToMainButton.setLayoutData(new RowData(width, height));
        backToMainButton.setText("Back to main menu");
        backToMainButton.addListener(SWT.Selection, event -> dispose());

        mainLayout.marginLeft = (Display.getCurrent().getBounds().width / 2) - 150;
        mainLayout.marginTop = (Display.getCurrent().getBounds().height / 2) - height * 3;
        shell.open();
    }

    private void loadConfigView(Table configTypeTable) {
        TableItem selection = configTypeTable.getSelection()[0];
        List<RecordDto> list = configTypeService.findByConfigName(selection.getText());

        ConfigSingleton.getSingleton().setConfigDto((ConfigDto) list.get(0));

        configView();
    }

    public void configView() {
        ConfigViewGui viewGui = new ConfigViewGui();
        viewGui.open();
    }

    private void exportConfig(Table configTypeTable) throws ParserConfigurationException, TransformerException {
        TableItem selection = configTypeTable.getSelection()[0];
        List<RecordDto> list = configTypeService.findByConfigName(selection.getText());

        ConfigSingleton.getSingleton().setConfigDto((ConfigDto) list.get(0));

        exportAsXml();
    }

    private void exportAsXml() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // root element
        Element rootElement = document.createElement("Types");
        document.appendChild(rootElement);

        ClassTypeGui classTypeGui = new ClassTypeGui();
        classTypeGui.xmlElements(document, rootElement);

        NodeTypeGui nodeTypeGui = new NodeTypeGui();
        nodeTypeGui.xmlElements(document, rootElement);

        LinkTypeGui linkTypeGui = new LinkTypeGui();
        linkTypeGui.xmlElements(document, rootElement);

        NodeTypeRulesGui nodeTypeRulesGui = new NodeTypeRulesGui();
        nodeTypeRulesGui.xmlElements(document, rootElement);

        LinkTypeRulesGui linkTypeRulesGui = new LinkTypeRulesGui();
        linkTypeRulesGui.xmlElements(document, rootElement);

        LinkTypeNodeTypeRulesGui linkTypeNodeTypeRulesGui = new LinkTypeNodeTypeRulesGui();
        linkTypeNodeTypeRulesGui.xmlElements(document, rootElement);

        // Aici trebuie creat un fisier text si modificat cu calea spre el
        try (FileOutputStream output =
                     new FileOutputStream("C:\\Users\\Bianca\\OneDrive - Universitatea Politehnica Bucuresti\\Desktop\\test")) {
            writeXml(document, output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //writeXml(document, System.out);
    }

    private void writeXml(Document document, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//
//        // pretty print
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//
//        DOMSource source = new DOMSource(document);
//        StreamResult result = new StreamResult(output);
//
//        transformer.transform(source, result);

    }

    private void dispose() {
        shell.dispose();
    }




}

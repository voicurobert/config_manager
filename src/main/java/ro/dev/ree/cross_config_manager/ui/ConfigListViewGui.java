package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.component_status.ComponentStatus;
import ro.dev.ree.cross_config_manager.model.component_status.ComponentStatusDto;
import ro.dev.ree.cross_config_manager.model.component_status.ComponentStatusService;
import ro.dev.ree.cross_config_manager.model.config_type.Config;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigTypeService;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassType;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeDto;
import ro.dev.ree.cross_config_manager.model.core_class_type.CoreClassTypeService;
import ro.dev.ree.cross_config_manager.model.link_status.LinkStatusService;
import ro.dev.ree.cross_config_manager.model.link_type.LinkTypeService;
import ro.dev.ree.cross_config_manager.model.link_type_node_type_rules.LinkTypeNodeTypeRulesService;
import ro.dev.ree.cross_config_manager.model.link_type_rules.LinkTypeRulesService;
import ro.dev.ree.cross_config_manager.model.node_status.NodeStatusService;
import ro.dev.ree.cross_config_manager.model.node_type.NodeTypeService;
import ro.dev.ree.cross_config_manager.model.node_type_rules.NodeTypeRulesService;
import ro.dev.ree.cross_config_manager.model.service_status.ServiceStatusService;
import ro.dev.ree.cross_config_manager.ui.node_type_rules.NodeTypeRulesGui;
import ro.dev.ree.cross_config_manager.xml.writer.XmlWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        var DeleteMenuItem = new MenuItem(menu, SWT.PUSH);
        DeleteMenuItem.setText("Delete config");

        ConfigDetailMenuItem.addListener(SWT.Selection, event -> loadConfigView(configTypeTable));
        ExportConfigMenuItem.addListener(SWT.Selection, event -> {
            try {
                exportConfig(configTypeTable);
            } catch (ParserConfigurationException | TransformerException e) {
                throw new RuntimeException(e);
            }
        });

        DeleteMenuItem.addListener(SWT.Selection, event -> deleteConfig(configTypeTable));

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

        exportAsXml(((ConfigDto) list.get(0)).getName());
    }

    private void exportAsXml(String name) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // root element
        Element rootElement = document.createElement("crossConfiguration");
        document.appendChild(rootElement);

        ConfigManagerComposites composites = new ConfigManagerComposites();
        for (XmlWriter xmlWriter : composites.getComposites()) {
            xmlWriter.xmlElements(document, rootElement);
        }

        String path = System.getProperty("user.home") + "\\" + name + ".xml";
        try (FileOutputStream output = new FileOutputStream(path)) {
            writeXml(document, output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MessageBox messageBox = new MessageBox(shell);
        messageBox.setMessage("You exported XML file in " + path + "!");
        messageBox.open();
    }

    private void writeXml(Document document, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }

    private void deleteConfig(Table configTypeTable) {
        List<RecordDto> list = configTypeService.findByConfigName(configTypeTable.getSelection()[0].getText());
        configTypeService.delete((ConfigDto) list.get(0));
        configTypeTable.remove(configTypeTable.getSelectionIndices());
    }

    private void dispose() {
        shell.dispose();
    }


}

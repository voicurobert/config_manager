package ro.dev.ree.cross_config_manager.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigTypeService;
import ro.dev.ree.cross_config_manager.xml.reader.XmlRead;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ConfigManagerGui {
    private final ConfigTypeService configTypeService = ConfigManagerContextProvider.getBean(ConfigTypeService.class);

    public void createContents(Composite parent) {
        var mainLayout = new RowLayout();
        mainLayout.type = SWT.VERTICAL;
        mainLayout.center = true;
        mainLayout.fill = true;
        mainLayout.spacing = 10;

        parent.setLayout(mainLayout);

        int width = 150;
        int height = 40;

        Button newConfigButton = new Button(parent, SWT.PUSH);
        newConfigButton.setLayoutData(new RowData(width, height));
        newConfigButton.setText("New config");
        newConfigButton.addListener(SWT.Selection, event -> newConfigButtonClicked());


        Button loadConfigButton = new Button(parent, SWT.PUSH);
        loadConfigButton.setLayoutData(new RowData(width, height));
        loadConfigButton.setText("Load config");
        loadConfigButton.addListener(SWT.Selection, event -> {
            try {
                loadConfigButtonClicked(parent);
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        });


        Button viewConfigsButton = new Button(parent, SWT.PUSH);
        viewConfigsButton.setLayoutData(new RowData(width, height));
        viewConfigsButton.setText("View configs");
        viewConfigsButton.addListener(SWT.Selection, event -> viewConfigsButtonClicked());

        mainLayout.marginLeft = (Display.getCurrent().getBounds().width / 2) - 50;
        mainLayout.marginTop = (Display.getCurrent().getBounds().height / 2) - height * 3;
    }

    public void newConfigButtonClicked() {
        NewConfigGui newConfigGui = new NewConfigGui();
        newConfigGui.open();
    }

    public void loadConfigButtonClicked(Composite parent) throws ParserConfigurationException, IOException, SAXException {
        final FileDialog fileDialog = new FileDialog(parent.getShell(), SWT.APPLICATION_MODAL);
        fileDialog.open();
        File file = new File(fileDialog.getFileName());
        String name = file.getName();
        if (name.equals("")) {
            return;
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        Element rootElement = doc.getDocumentElement();
        if (name.indexOf(".") > 0)
            name = name.substring(0, name.lastIndexOf("."));

        ConfigDto configDto = new ConfigDto();
        configDto.setName(name);
        configDto = configTypeService.save(configDto);
        ConfigSingleton.getSingleton().setConfigDto(configDto);


        ConfigManagerComposites composites = new ConfigManagerComposites();
        for (XmlRead xmlRead : composites.getComposites()) {
            xmlRead.readElement(rootElement);
        }
    }

    public void viewConfigsButtonClicked() {
        ConfigListViewGui configListViewGui = new ConfigListViewGui();
        configListViewGui.open();

    }


}



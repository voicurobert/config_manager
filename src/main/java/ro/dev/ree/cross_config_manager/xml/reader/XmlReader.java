package ro.dev.ree.cross_config_manager.xml.reader;


import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

public class XmlReader {

    Shell shell;


    public void open() throws IOException {

        shell = new Shell();
        var mainLayout = new RowLayout();
        mainLayout.type = SWT.VERTICAL;
        mainLayout.center = true;
        mainLayout.fill = true;
        mainLayout.spacing = 10;
        int width = 150;
        int height = 40;
        shell.setLayout(mainLayout);
        FileUpload fileUpload = new FileUpload(shell, SWT.NONE);

        fileUpload.setText("Choose the XML file");

        fileUpload.addListener(SWT.Selection, event -> a(fileUpload));


        mainLayout.marginLeft = (Display.getCurrent().getBounds().width / 2 - 375);
        mainLayout.marginTop = (Display.getCurrent().getBounds().height / 2) - height * 3;
        shell.open();


    }

    private void a(FileUpload fileUpload) {
        try {
            File file = new File(System.getProperty("user.home") + "\\Desktop\\" + fileUpload.getFileName());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("root element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("nodeTypes");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println("\nnode name:" + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    System.out.println("discriminator " + eElement.getElementsByTagName("discriminator").item(0).getTextContent());
                    System.out.println("name " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("appIcon " + eElement.getElementsByTagName("appIcon").item(0).getTextContent());
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

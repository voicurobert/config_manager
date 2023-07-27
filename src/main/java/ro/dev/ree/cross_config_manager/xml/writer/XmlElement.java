package ro.dev.ree.cross_config_manager.xml.writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XmlElement {

    void asXml(Document document, Element rootElement);
}

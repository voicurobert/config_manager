package ro.dev.ree.cross_config_manager.model.class_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.xml.writer.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassTypeDto extends RecordDto implements XmlElement {

    private String configId;

    private String name;

    private String path;

    private String parentPath;

    public static ClassTypeDto InsertOrUpdateFromItems(String[] columnValues, String action) {
        var classTypeDto = new ClassTypeDto();
        if(action.equals("Update"))
        {
            classTypeDto.setId(columnValues[0]);
        }
        classTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        classTypeDto.setName(columnValues[1]);
        classTypeDto.setPath(columnValues[2]);
        classTypeDto.setParentPath(columnValues[3]);

        return classTypeDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element classType = document.createElement("classType");
        // add classType to root
        rootElement.appendChild(classType);
        // add xml attribute
        classType.setAttribute("id", getId());

        Element name = document.createElement("name");
        name.setTextContent(this.name);
        classType.appendChild(name);

        Element path = document.createElement("name");
        path.setTextContent(this.path);
        classType.appendChild(path);

        Element parentPath = document.createElement("name");
        parentPath.setTextContent(this.parentPath);
        classType.appendChild(parentPath);

    }
}

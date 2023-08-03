package ro.dev.ree.cross_config_manager.model.class_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.xml.writer.XmlElement;

import java.lang.reflect.Field;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassTypeDto extends RecordDto implements XmlElement {

    private String configId;

    private String name;

    private String path;

    private String parentPath;

    public static ClassTypeDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var classTypeDto = new ClassTypeDto();
        if (action.equals("Update")) {
            classTypeDto.setId(columnValues.get(0));
        }
        classTypeDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        classTypeDto.setName(columnValues.get(1));
        classTypeDto.setPath(columnValues.get(2));
        classTypeDto.setParentPath(columnValues.get(3));

        return classTypeDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element classType = document.createElement("classType");
        // add classType to root
        rootElement.appendChild(classType);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "name" -> name.setTextContent(this.name);
                case "path" -> name.setTextContent(this.path);
                case "parentPath" -> name.setTextContent(this.parentPath);
                default -> {
                }
            }
            classType.appendChild(name);
        }
    }
}

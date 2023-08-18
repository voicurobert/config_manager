package ro.dev.ree.cross_config_manager.model.ca_definition;

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
public class CaDefinitionDto extends RecordDto implements XmlElement {

    private String configId;

    private String attributeName;

    private String attributeClass;

    private String unique;

    public static CaDefinitionDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var caDefinitionDto = new CaDefinitionDto();
        if(action.equals("Update"))
        {
            caDefinitionDto.setId(columnValues.get(0).split(",")[0]);
        }

        caDefinitionDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        caDefinitionDto.setAttributeName(columnValues.get(1));
        caDefinitionDto.setAttributeClass(columnValues.get(2));
        caDefinitionDto.setUnique("false");

        return caDefinitionDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element caDefinition = document.createElement("caDefinition");
        // add classType to root
        rootElement.appendChild(caDefinition);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "attributeName" -> name.setTextContent(this.attributeName);
                case "attributeClass" -> name.setTextContent(this.attributeClass);
                case "unique" -> name.setTextContent(this.unique);
                default -> {
                }
            }
            caDefinition.appendChild(name);
        }
    }
}

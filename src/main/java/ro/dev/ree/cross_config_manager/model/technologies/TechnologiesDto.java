package ro.dev.ree.cross_config_manager.model.technologies;

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
public class TechnologiesDto extends RecordDto implements XmlElement {
    private String configId;
    private String name;
    private String technologyTree;
    private String parentTechnology;


    public static TechnologiesDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var technologiesDto = new TechnologiesDto();
        if (action.equals("Update")) {
            technologiesDto.setId(columnValues.get(0));
        }
        technologiesDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        technologiesDto.setName(columnValues.get(1));
        technologiesDto.setTechnologyTree(columnValues.get(2));
        technologiesDto.setParentTechnology(columnValues.get(3));


        return technologiesDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {

        // add xml elements
        Element technologies = document.createElement("technology");
        // add classType to root
        rootElement.appendChild(technologies);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "name" -> name.setTextContent(this.name);
                case "technologyTree" -> name.setTextContent(this.technologyTree);
                case "parentTechnology" -> name.setTextContent(this.parentTechnology);

                default -> {
                }
            }
            technologies.appendChild(name);
        }
    }
}

package ro.dev.ree.cross_config_manager.model.ca_definition_set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.dev.ree.cross_config_manager.ConfigManagerContextProvider;
import ro.dev.ree.cross_config_manager.model.RecordDto;
import ro.dev.ree.cross_config_manager.model.config_type.ConfigSingleton;
import ro.dev.ree.cross_config_manager.xml.writer.XmlElement;

import java.lang.reflect.Field;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaDefinitionSetDto extends RecordDto implements XmlElement {

    private String configId;

    private String type;

    private String name;

    private String caDefinitionName;

    public static CaDefinitionSetDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var caDefinitionSetDto = new CaDefinitionSetDto();
        if(action.equals("Update"))
        {
            caDefinitionSetDto.setId(columnValues.get(0));
        }

        caDefinitionSetDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        caDefinitionSetDto.setType(columnValues.get(1));
        caDefinitionSetDto.setName(columnValues.get(2));
        caDefinitionSetDto.setCaDefinitionName(columnValues.get(3));

        return caDefinitionSetDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {
        // add xml elements
        Element caDefinitionSet = document.createElement("caDefinitionSet");
        // add classType to root
        rootElement.appendChild(caDefinitionSet);

        Element type = document.createElement("type");
        type.setTextContent(this.type);
        caDefinitionSet.appendChild(type);

        Element name = document.createElement("name");
        name.setTextContent(this.name);
        caDefinitionSet.appendChild(name);

        CaDefinitionSetService caDefinitionSetService = ConfigManagerContextProvider.getBean(CaDefinitionSetService.class);
        List<RecordDto> caDefinitionNames = caDefinitionSetService.findCaDefinitionNamesByNameTypeAndConfigId(this.name, this.type, ConfigSingleton.getSingleton().getConfigDto().getId());

        for (RecordDto node : caDefinitionNames) {
            CaDefinitionSetDto caDefinitionSetDto1 = (CaDefinitionSetDto) node;
            Element caDefinitionName = document.createElement("caDefinitionName");
            caDefinitionName.setTextContent(caDefinitionSetDto1.getCaDefinitionName());
            caDefinitionName.setAttribute("defaultValue", "");
            caDefinitionName.setAttribute("orderNumber", String.valueOf(caDefinitionNames.indexOf(node)));
            caDefinitionName.setAttribute("roleForWrite", "CUSTOM_ATTRIBUTE_WRITE");
            caDefinitionName.setAttribute("uniquenessType", "NONE");
            caDefinitionSet.appendChild(caDefinitionName);
        }
    }
}

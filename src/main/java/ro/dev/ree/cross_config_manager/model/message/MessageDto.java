package ro.dev.ree.cross_config_manager.model.message;

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
public class MessageDto extends RecordDto implements XmlElement {

    private String configId;

    private String code;

    private String locale;

    private String message;

    public static MessageDto InsertOrUpdateFromItems(List<String> columnValues, String action) {
        var messageDto = new MessageDto();
        if(action.equals("Update"))
        {
            messageDto.setId(columnValues.get(0).split(",")[1]);
        }

        messageDto.setConfigId(ConfigSingleton.getSingleton().getConfigDto().getId());
        messageDto.setCode("customAttribute." + columnValues.get(1) + ".name");
        messageDto.setLocale("en");
        messageDto.setMessage(columnValues.get(3));

        return messageDto;
    }

    @Override
    public void asXml(Document document, Element rootElement) {
        // add xml elements
        Element message = document.createElement("message");
        // add classType to root
        rootElement.appendChild(message);

        Field[] fields = getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            Element name = document.createElement(fields[i].getName());
            switch (fields[i].getName()) {
                case "code" -> name.setTextContent(this.code);
                case "locale" -> name.setTextContent(this.locale);
                case "message" -> name.setTextContent(this.message);
                default -> {
                }
            }
            message.appendChild(name);
        }
    }
}

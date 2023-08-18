package ro.dev.ree.cross_config_manager.model.ca_definition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ca_definitions")
public class CaDefinition {

    @Id
    private String id;

    private String configId;

    private String attributeName;

    private String attributeClass;

    private String unique;
}

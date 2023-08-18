package ro.dev.ree.cross_config_manager.model.ca_definition_set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ca_definition_sets")
public class CaDefinitionSet {

    @Id
    private String id;

    private String configId;

    private String type;

    private String name;

    private String caDefinitionName;
}

package ro.dev.ree.cross_config_manager.model.component_status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "service_component_statuses")
public class ComponentStatus {
    @Id
    private String id;
    private String configId;
    private String name;
    private String description;
    private String color;

}

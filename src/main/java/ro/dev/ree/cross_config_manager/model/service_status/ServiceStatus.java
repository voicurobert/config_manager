package ro.dev.ree.cross_config_manager.model.service_status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "service_statuses")
public class ServiceStatus {
    @Id
    private String id;
    private String configId;
    private String name;
    private String description;
    private String color;
}

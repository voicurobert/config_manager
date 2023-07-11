package ro.dev.ree.cross_config_manager.model.class_type;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "class_types")
public class ClassType {
    @Id
    private String id;
    private String configId;
}

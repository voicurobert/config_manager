package ro.dev.ree.cross_config_manager.model.class_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "class_types")
public class ClassType {
    @Id
    private String id;
    private String configId;

    private String name;

    private String path;

    private String parentPath;
}
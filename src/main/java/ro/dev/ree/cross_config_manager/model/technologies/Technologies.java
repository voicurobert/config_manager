package ro.dev.ree.cross_config_manager.model.technologies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "technologies")
public class Technologies {
    @Id
    private String id;
    private String configId;
    private String name;
    private String technologyTree;
    private String parentTechnology;

}
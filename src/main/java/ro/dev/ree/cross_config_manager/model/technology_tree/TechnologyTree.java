package ro.dev.ree.cross_config_manager.model.technology_tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "technology_trees")
public class TechnologyTree {
    @Id
    private String id;
    private String configId;
    private String name;
    private String nodeType;
    private String linkType;
}

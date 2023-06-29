package ro.dev.ree.cross_config_manager.model.node_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "node_types")
public class NodeType {

    @Id
    private String id;

    private String configId;

    private String discriminator;


}

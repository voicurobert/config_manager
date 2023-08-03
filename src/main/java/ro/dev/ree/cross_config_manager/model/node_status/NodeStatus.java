package ro.dev.ree.cross_config_manager.model.node_status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "node_statuses")
public class NodeStatus {
    @Id
    private String id;

    private String configId;
    private String discriminator;
    private String name;
    private String colorCode;
    private String capacityConsumer;

}

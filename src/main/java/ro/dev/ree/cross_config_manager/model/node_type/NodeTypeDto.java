package ro.dev.ree.cross_config_manager.model.node_type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTypeDto {
    private String configId;

    private String discriminator;
}

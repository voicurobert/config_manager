package ro.dev.ree.cross_config_manager.model.node_type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTypeDto {

    private String id;
    private String configId;

    private String discriminator;

    private String name;

    private String appIcon;

    private String mapIcon;

    private String capacityFull;

    private String capacityUnitName;

    private String typeClassPath;

    private String rootType;

    private String system;

    private String multiparentAllowed;

    private String uniquenessType;
}

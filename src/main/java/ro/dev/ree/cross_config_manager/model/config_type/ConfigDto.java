package ro.dev.ree.cross_config_manager.model.config_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ConfigDto {

    private String configId;

    private String name;
}

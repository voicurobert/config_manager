package ro.dev.ree.cross_config_manager.model.config_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.dev.ree.cross_config_manager.model.RecordDto;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ConfigDto extends RecordDto {

    private String name;
}

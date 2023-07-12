package ro.dev.ree.cross_config_manager.model.config_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "configs")
public class Config {
    
    @Id
    private String id;
    private String name;

}

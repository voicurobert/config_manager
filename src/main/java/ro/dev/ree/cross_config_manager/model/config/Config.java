package ro.dev.ree.cross_config_manager.model.config;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config")
public class Config {
    @Id
    private String id;
    private String name;


}

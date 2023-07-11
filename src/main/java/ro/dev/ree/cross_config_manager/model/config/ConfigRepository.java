package ro.dev.ree.cross_config_manager.model.config;

import ro.dev.ree.cross_config_manager.model.node_type.NodeType;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ConfigRepository extends MongoRepository<Config,String>{
}

package ro.dev.ree.cross_config_manager.model.config_type;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<Config, String> {
}

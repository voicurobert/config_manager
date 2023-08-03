package ro.dev.ree.cross_config_manager.model.component_status;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComponentStatusRepository extends MongoRepository<ComponentStatus, String> {
}

package ro.dev.ree.cross_config_manager.model.technologies;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TechnologiesRepository extends MongoRepository<Technologies, String> {
}

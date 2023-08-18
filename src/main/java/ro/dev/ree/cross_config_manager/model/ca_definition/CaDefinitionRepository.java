package ro.dev.ree.cross_config_manager.model.ca_definition;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CaDefinitionRepository extends MongoRepository<CaDefinition, String> {
}

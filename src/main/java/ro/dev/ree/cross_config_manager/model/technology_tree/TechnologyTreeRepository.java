package ro.dev.ree.cross_config_manager.model.technology_tree;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TechnologyTreeRepository extends MongoRepository<TechnologyTree, String> {
}
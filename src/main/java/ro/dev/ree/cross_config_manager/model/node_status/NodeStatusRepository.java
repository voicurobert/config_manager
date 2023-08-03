package ro.dev.ree.cross_config_manager.model.node_status;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface NodeStatusRepository extends MongoRepository<NodeStatus, String> {
}

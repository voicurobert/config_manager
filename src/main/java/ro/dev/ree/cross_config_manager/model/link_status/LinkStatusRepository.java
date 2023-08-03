package ro.dev.ree.cross_config_manager.model.link_status;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LinkStatusRepository extends MongoRepository<LinkStatus, String> {
}


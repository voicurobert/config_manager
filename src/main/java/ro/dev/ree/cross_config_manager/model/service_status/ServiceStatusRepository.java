package ro.dev.ree.cross_config_manager.model.service_status;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceStatusRepository extends MongoRepository<ServiceStatus, String> {
}

package ro.dev.ree.cross_config_manager.model;

import java.util.List;

public interface ServiceRepository {

    String insertOrUpdate(RecordDto recordDto);

    void delete(RecordDto recordDto);

    List<RecordDto> findAllByConfigId(String configId);

    RecordDto findById(String Id);
}

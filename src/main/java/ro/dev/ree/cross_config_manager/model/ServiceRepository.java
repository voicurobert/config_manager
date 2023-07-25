package ro.dev.ree.cross_config_manager.model;

import java.util.List;

public interface ServiceRepository {

    void insertOrUpdate(String[] columnValues, String action, int index);

    void delete(RecordDto recordDto);

    List<RecordDto> findAllByConfigId(String configId);
}

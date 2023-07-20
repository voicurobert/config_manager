package ro.dev.ree.cross_config_manager.model;

import java.util.List;

public interface ServiceRepository {

    RecordDto insert(RecordDto recordDto);

    RecordDto update(RecordDto recordDto);

    void delete(RecordDto recordDto);

    List<RecordDto> findAll();

    List<RecordDto> findAllByConfigId(String configId);
}

package ro.dev.ree.cross_config_manager.model.config;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class ConfigTypeService {
    private final ConfigRepository repository;


    public ConfigTypeService(ConfigRepository repository) {
        this.repository = repository;
    }

    public Config save(ConfigDto configDto) {
        Config configType = new Config();
        BeanUtils.copyProperties(configDto, configType);
        return repository.save(configType);

    }
    public void update(ConfigDto configDto) {
        Config configType = new Config();
        BeanUtils.copyProperties(configDto, configType);
        repository.save(configType);
    }
    public void delete(ConfigDto configDto) {
        Config configType = new Config();
        BeanUtils.copyProperties(configDto, configType);
        repository.delete(configType);
    }
}


package ro.dev.ree.cross_config_manager.model.config_type;

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
        Config config = repository.save(configType);

        ConfigSingleton.getSingleton().setConfigId(config.getId());

        return config;

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


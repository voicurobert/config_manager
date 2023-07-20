package ro.dev.ree.cross_config_manager.model.config_type;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConfigTypeService {
    private final ConfigRepository repository;


    public ConfigTypeService(ConfigRepository repository) {
        this.repository = repository;
    }

    public ConfigDto save(ConfigDto configDto) {
        Config configType = new Config();
        BeanUtils.copyProperties(configDto, configType);
        Config config = repository.save(configType);
        configDto.setId(config.getId());
        ConfigSingleton.getSingleton().setConfigId(config.getId());

        return configDto;

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

    public List<Config> findAll() {

        return repository.findAll();
    }

}


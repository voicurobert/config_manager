package ro.dev.ree.cross_config_manager.model.config_type;

public class ConfigSingleton {

    private static ConfigSingleton singleton;

    private ConfigDto configDto;

    private ConfigSingleton() {

    }

    public static ConfigSingleton getSingleton() {
        if (singleton == null) {
            singleton = new ConfigSingleton();
        }
        return singleton;
    }

    public ConfigDto getConfigDto() {
        return configDto;
    }

    public void setConfigDto(ConfigDto configDto) {
        this.configDto = configDto;
    }

}

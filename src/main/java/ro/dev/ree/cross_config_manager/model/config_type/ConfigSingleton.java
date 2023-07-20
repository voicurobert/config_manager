package ro.dev.ree.cross_config_manager.model.config_type;

public class ConfigSingleton {

    private static ConfigSingleton singleton;

    private ConfigDto config;

    private ConfigSingleton() {

    }

    public static ConfigSingleton getSingleton() {
        if (singleton == null) {
            singleton = new ConfigSingleton();
        }
        return singleton;
    }

    public ConfigDto getConfigDto() {
        return config;
    }

    public void setConfigDto(ConfigDto config) {
        this.config = config;
    }

}

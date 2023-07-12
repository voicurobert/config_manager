package ro.dev.ree.cross_config_manager.model.config_type;

public class ConfigSingleton {

    private static ConfigSingleton singleton;

    private String configId;

    private ConfigSingleton() {

    }

    public static ConfigSingleton getSingleton() {
        if (singleton == null) {
            singleton = new ConfigSingleton();
        }
        return singleton;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}

package fr.hyriode.build.config.nested;

import fr.hyriode.api.config.MongoDBConfig;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.config.ConfigEntry;

/**
 * Created by AstFaster
 * on 22/02/2023 at 16:48
 */
public class MongoDBConfigImpl extends MongoDBConfig {

    private final ConfigEntry.StringEntry usernameEntry;
    private final ConfigEntry.StringEntry passwordEntry;
    private final ConfigEntry.StringEntry hostnameEntry;
    private final ConfigEntry.IntegerEntry portEntry;

    public MongoDBConfigImpl() {
        super("", "",  "", -1);

        this.usernameEntry = new ConfigEntry.StringEntry("production-mongodb.username", HyriBuild.get().getConfig());
        this.passwordEntry = new ConfigEntry.StringEntry("production-mongodb.password", HyriBuild.get().getConfig());
        this.hostnameEntry = new ConfigEntry.StringEntry("production-mongodb.hostname", HyriBuild.get().getConfig());
        this.portEntry = new ConfigEntry.IntegerEntry("production-mongodb.port", HyriBuild.get().getConfig());
    }

    @Override
    public String getUsername() {
        return this.usernameEntry.get();
    }

    @Override
    public String getPassword() {
        return this.passwordEntry.get();
    }

    @Override
    public String getHostname() {
        return this.hostnameEntry.get();
    }

    @Override
    public int getPort() {
        return this.portEntry.get();
    }

    public String toURL() {
        String url = "mongodb://";

        if (this.getUsername() != null && !this.getUsername().equals("")) {
            url += this.getUsername();
        }

        if (this.getPassword() != null && !this.getPassword().equals("")) {
            url += ":" + this.getPassword();
        }

        if ((this.getUsername() != null && !this.getUsername().equals("")) || (this.getPassword() != null && !this.getPassword().equals(""))) {
            url += "@";
        }

        return url + (this.getHostname() + ":" + this.getPort());
    }

}

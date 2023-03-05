package fr.hyriode.build.util;

import fr.hyriode.build.map.Environment;

/**
 * Created by AstFaster
 * on 23/02/2023 at 15:55
 */
public class Filters {

    public static final Filters DEFAULT_FILTERS = new Filters();

    private Environment environment = Environment.ALL;
    private Order dateOrder = Order.NONE;
    private State state = State.ALL;
    private Config config = Config.ALL;

    public Environment getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Order getDateOrder() {
        return this.dateOrder;
    }

    public void setDateOrder(Order dateOrder) {
        this.dateOrder = dateOrder;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Config getConfig() {
        return this.config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public enum Order {

        NONE("Non"),
        ASCENDING("Croissant"),
        DESCENDING("Décroissant");

        private final String display;

        Order(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return this.display;
        }

    }

    public enum State {

        ALL("Toutes"),
        ENABLED("Maps activées"),
        DISABLED("Maps désactivées");

        private final String display;

        State(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return this.display;
        }

    }

    public enum Config {

        ALL("Toutes"),
        DONE("Configurations faites"),
        TODO("Configurations à faire");

        private final String display;

        Config(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return this.display;
        }

    }

}

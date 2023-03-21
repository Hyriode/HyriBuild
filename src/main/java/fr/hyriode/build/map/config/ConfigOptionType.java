package fr.hyriode.build.map.config;

import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.*;

import java.util.function.Function;

/**
 * Created by AstFaster
 * on 01/03/2023 at 15:52
 */
public enum ConfigOptionType {

    BOOLEAN(BooleanHandler::new),

    LOCATION(process -> new LocationHandler(process, false, false)),
    BLOCK_LOCATION(process -> new LocationHandler(process, false, true)),
    PLAYER_LOCATION(process -> new LocationHandler(process, true, false)),

    LOCATIONS(process -> new LocationsHandler(process, false, false)),
    BLOCK_LOCATIONS(process -> new LocationsHandler(process, false, true)),
    PLAYER_LOCATIONS(process -> new LocationsHandler(process, true, false)),

    AREA(AreaHandler::new),

    WAITING_ROOM_LEADERBOARDS(WRLeaderboardsHandler::new),


    ;

    private final Function<ConfigProcess<?>, ConfigOptionHandler<?>> handlerProvider;

    ConfigOptionType(Function<ConfigProcess<?>, ConfigOptionHandler<?>> handlerProvider) {
        this.handlerProvider = handlerProvider;
    }

    public ConfigOptionHandler<?> newHandler(ConfigProcess<?> process) {
        return this.handlerProvider.apply(process);
    }

}

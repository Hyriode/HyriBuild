package fr.hyriode.build.permission;

import fr.hyriode.api.rank.IHyriRank;
import fr.hyriode.api.rank.StaffRank;
import fr.hyriode.build.map.Environment;
import org.bukkit.entity.Player;

import java.util.function.BiPredicate;

/**
 * Created by AstFaster
 * on 24/02/2023 at 14:48
 */
public class Permissions {

    private final Player player;
    private final IHyriRank rank;

    protected Permissions(Player player, IHyriRank rank) {
        this.player = player;
        this.rank = rank;
    }

    public boolean has(Action action, Environment environment) {
        return action.getPermission().test(this.rank, environment);
    }

    public boolean hasWithError(Action action, Environment environment) {
        final boolean result = this.has(action, environment);

        if (!result) {
            this.player.sendMessage("§cErreur: Vous n'avez pas la permission d'effectuer cette action !");
        }

        return result;
    }

    public enum Action {

        DELETE((rank, environment) -> rank.isSuperior(StaffRank.MANAGER)),
        UPLOAD((rank, environment) -> rank.isSuperior(StaffRank.MANAGER) || rank.is(StaffRank.BUILDER)),
        RE_UPLOAD((rank, environment) -> {
            if (environment == Environment.PRODUCTION) {
                return rank.isSuperior(StaffRank.MANAGER);
            } else if (environment == Environment.DEVELOPMENT) {
                return rank.isSuperior(StaffRank.MANAGER) || rank.is(StaffRank.BUILDER);
            }
            return false;
        }),
        IMPORT((rank, environment) -> rank.isSuperior(StaffRank.MANAGER) || rank.is(StaffRank.BUILDER)),
        TRANSFER((rank, environment) -> rank.isSuperior(StaffRank.MANAGER)),
        CHANGE_STATE((rank, environment) -> {
            if (environment == Environment.PRODUCTION) {
                return rank.isSuperior(StaffRank.MANAGER);
            } else if (environment == Environment.DEVELOPMENT) {
                return rank.isSuperior(StaffRank.MANAGER) || rank.is(StaffRank.BUILDER);
            }
            return false;
        }),
        CHANGE_AUTHORS((rank, environment) -> {
            if (environment == Environment.PRODUCTION) {
                return rank.isSuperior(StaffRank.MANAGER);
            } else if (environment == Environment.DEVELOPMENT) {
                return rank.isSuperior(StaffRank.MANAGER) || rank.is(StaffRank.BUILDER);
            }
            return false;
        }),
        CREATE_CONFIG((rank, environment) -> {
            if (environment == Environment.PRODUCTION) {
                return rank.isSuperior(StaffRank.MANAGER);
            } else if (environment == Environment.DEVELOPMENT) {
                return rank.isSuperior(StaffRank.MANAGER) || rank.is(StaffRank.BUILDER);
            }
            return false;
        }),

        ;

        private final BiPredicate<IHyriRank, Environment> permission;

        Action(BiPredicate<IHyriRank, Environment> permission) {
            this.permission = permission;
        }

        public BiPredicate<IHyriRank, Environment> getPermission() {
            return this.permission;
        }

    }

}

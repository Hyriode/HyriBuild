package fr.hyriode.build.permission;

import fr.hyriode.api.player.IHyriPlayer;
import org.bukkit.entity.Player;

/**
 * Created by AstFaster
 * on 24/02/2023 at 14:48
 */
public class PermissionsProvider {

    public static Permissions getPermissions(Player player) {
        return new Permissions(player, IHyriPlayer.get(player.getUniqueId()).getRank());
    }

}

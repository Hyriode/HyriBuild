package fr.hyriode.build.listener;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.api.player.IHyriPlayer;
import fr.hyriode.api.rank.IHyriRank;
import fr.hyriode.api.rank.StaffRank;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.hyrame.listener.HyriListener;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by AstFaster
 * on 23/02/2023 at 14:21
 */
public class PlayerListener extends HyriListener<HyriBuild> {

    public PlayerListener(HyriBuild plugin) {
        super(plugin);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        final IHyriPlayer account = IHyriPlayer.get(event.getPlayer().getUniqueId());
        final IHyriRank rank = account.getRank();

        // Check server access (only builders, managers and admins)
        if (!rank.isSuperior(StaffRank.MANAGER) && !rank.is(StaffRank.BUILDER) && !HyriAPI.get().getPlayerManager().getWhitelistManager().isWhitelisted(event.getPlayer().getName())) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(ChatColor.RED + "Vous n'avez pas la permission de rejoindre ce serveur!");
        }
    }

}

package fr.hyriode.build.command;

import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.gui.MainGUI;
import fr.hyriode.hyrame.command.HyriCommand;
import fr.hyriode.hyrame.command.HyriCommandContext;
import fr.hyriode.hyrame.command.HyriCommandInfo;
import org.bukkit.entity.Player;

/**
 * Created by AstFaster
 * on 23/02/2023 at 14:20
 */
public class MainCommand extends HyriCommand<HyriBuild> {

    public MainCommand(HyriBuild plugin) {
        super(plugin, new HyriCommandInfo("hyribuild")
                .withAliases("panel"));
    }

    @Override
    public void handle(HyriCommandContext ctx) {
        new MainGUI((Player) ctx.getSender()).open();
    }

}

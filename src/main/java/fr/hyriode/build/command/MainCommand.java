package fr.hyriode.build.command;

import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.gui.MainGUI;
import fr.hyriode.hyrame.command.CommandContext;
import fr.hyriode.hyrame.command.CommandInfo;
import fr.hyriode.hyrame.command.HyriCommand;

/**
 * Created by AstFaster
 * on 23/02/2023 at 14:20
 */
public class MainCommand extends HyriCommand<HyriBuild> {

    public MainCommand(HyriBuild plugin) {
        super(plugin, new CommandInfo("hyribuild")
                .withAliases("panel"));
    }

    @Override
    public void handle(CommandContext ctx) {
        new MainGUI(ctx.getSender()).open();
    }

}

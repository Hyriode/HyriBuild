package fr.hyriode.build.map.config.handler.models;

import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.hyrame.signgui.SignGUI;
import fr.hyriode.hyrame.utils.PrimitiveType;
import org.bukkit.Bukkit;

/**
 * Created by AstFaster
 * on 01/06/2022 at 13:36
 */
public class IntegerHandler extends ConfigOptionHandler<Integer> {

    public IntegerHandler(ConfigProcess<?> process) {
        super(process);
    }

    @Override
    public void handle() {
        Bukkit.getScheduler().runTaskLater(HyriBuild.get(), () -> new SignGUI((player, lines) -> {
            final String input = lines[0];

            if (PrimitiveType.INTEGER.isValid(input) && PrimitiveType.INTEGER.parse(input) > 0) {
                System.out.println("a");
                this.complete(PrimitiveType.INTEGER.parse(input));
                System.out.println("b");
            } else {
                this.process.sendMessage(builder -> builder.append("§fLa valeur est §cinvalide §f!"));

                Bukkit.getScheduler().runTaskLater(HyriBuild.get(), this::handle, 1L);
            }
        }).withLines("", "^^^^^^^^", "Valeur", "").open(this.player), 5L);
    }

}

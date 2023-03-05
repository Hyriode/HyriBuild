package fr.hyriode.build.discord;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.discord.embed.DiscordEmbed;
import fr.hyriode.build.discord.embed.DiscordEmbedAuthor;
import fr.hyriode.build.discord.embed.DiscordEmbedField;
import fr.hyriode.build.discord.embed.DiscordEmbedFooter;
import fr.hyriode.build.map.Map;
import fr.hyriode.hyrame.utils.TimeUtil;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Date;

/**
 * Created by AstFaster
 * on 23/02/2023 at 10:14
 */
public class DiscordWebhook {

    public void sendMapMessage(Player player, Map map, String title, String description, int color) {
        this.sendMessage(new DiscordMessage()
                .addEmbeds(new DiscordEmbed()
                        .withTitle("`" + map.getEnvironment().getDisplay() + "` " + title)
                        .withColor(color)
                        .withAuthor(new DiscordEmbedAuthor()
                                .withName(player.getName())
                                .withIconUrl("https://crafatar.com/renders/head/" + player.getUniqueId() + "?size=512&scale=10"))
                        .addField(new DiscordEmbedField()
                                .withName("Map")
                                .withValue(map.getHandle().getName())
                                .withInline(true))
                        .addField(new DiscordEmbedField()
                                .withName("Jeu")
                                .withValue(map.getCategory().getDisplay())
                                .withInline(true))
                        .addField(new DiscordEmbedField()
                                .withName("Type")
                                .withValue(map.getHandle().getCategory())
                                .withInline(true))
                        .addField(new DiscordEmbedField()
                                .withName("Description")
                                .withValue(description)
                                .withInline(false))
                        .withFooter(new DiscordEmbedFooter(TimeUtil.formatDate(new Date())))));
    }

    public void sendMessage(DiscordMessage message) throws RuntimeException {
        try {
            final HttpPost post = new HttpPost(HyriBuild.get().getConfiguration().getWebhook());

            post.addHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(HyriAPI.GSON.toJson(message), ContentType.APPLICATION_JSON));

            HyriAPI.get().getHttpRequester().getClient().execute(post);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

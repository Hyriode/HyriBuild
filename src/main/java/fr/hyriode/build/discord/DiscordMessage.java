package fr.hyriode.build.discord;

import fr.hyriode.build.discord.embed.DiscordEmbed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by AstFaster
 * on 23/02/2023 at 13:13
 */
public class DiscordMessage {

    private String content;
    private List<DiscordEmbed> embeds = new ArrayList<>();

    public DiscordMessage() {}

    public DiscordMessage(String content, List<DiscordEmbed> embeds) {
        this.content = content;
        this.embeds = embeds;
    }

    public String getContent() {
        return this.content;
    }

    public DiscordMessage withContent(String content) {
        this.content = content;
        return this;
    }

    public List<DiscordEmbed> getEmbeds() {
        return this.embeds;
    }

    public DiscordMessage withEmbeds(List<DiscordEmbed> embeds) {
        this.embeds = embeds;
        return this;
    }

    public DiscordMessage addEmbeds(DiscordEmbed... embeds) {
        Collections.addAll(this.embeds, embeds);
        return this;
    }

}

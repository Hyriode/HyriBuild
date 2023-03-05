package fr.hyriode.build.discord.embed;

/**
 * Created by AstFaster
 * on 23/02/2023 at 11:01
 */
public class DiscordEmbedFooter {

    private String text;

    public DiscordEmbedFooter() {}

    public DiscordEmbedFooter(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public DiscordEmbedFooter withText(String text) {
        this.text = text;
        return this;
    }

}

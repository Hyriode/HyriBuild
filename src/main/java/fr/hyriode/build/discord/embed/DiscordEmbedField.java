package fr.hyriode.build.discord.embed;

/**
 * Created by AstFaster
 * on 05/03/2023 at 10:16
 */
public class DiscordEmbedField {

    private String name;
    private String value;
    private boolean inline;

    public DiscordEmbedField() {}

    public DiscordEmbedField(String name, String value, boolean inline) {
        this.name = name;
        this.value = value;
        this.inline = inline;
    }

    public String getName() {
        return this.name;
    }

    public DiscordEmbedField withName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public DiscordEmbedField withValue(String value) {
        this.value = value;
        return this;
    }

    public boolean isInline() {
        return this.inline;
    }

    public DiscordEmbedField withInline(boolean inline) {
        this.inline = inline;
        return this;
    }

}

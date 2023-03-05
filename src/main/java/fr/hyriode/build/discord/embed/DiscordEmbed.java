package fr.hyriode.build.discord.embed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by AstFaster
 * on 23/02/2023 at 10:59
 */
public class DiscordEmbed {

    private String title;
    private String description;
    private String url;
    private int color;
    private final List<DiscordEmbedField> fields = new ArrayList<>();
    private DiscordEmbedAuthor author;
    private DiscordEmbedFooter footer;

    public DiscordEmbed() {}

    public DiscordEmbed(String title, String description, String url, int color, DiscordEmbedAuthor author, DiscordEmbedFooter footer) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.color = color;
        this.author = author;
        this.footer = footer;
    }

    public String getTitle() {
        return this.title;
    }

    public DiscordEmbed withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public DiscordEmbed withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public DiscordEmbed withUrl(String url) {
        this.url = url;
        return this;
    }

    public int getColor() {
        return this.color;
    }

    public DiscordEmbed withColor(int color) {
        this.color = color;
        return this;
    }

    public DiscordEmbed addField(DiscordEmbedField field) {
        this.fields.add(field);
        return this;
    }

    public List<DiscordEmbedField> getFields() {
        return this.fields;
    }

    public DiscordEmbedAuthor getAuthor() {
        return this.author;
    }

    public DiscordEmbed withAuthor(DiscordEmbedAuthor author) {
        this.author = author;
        return this;
    }

    public DiscordEmbedFooter getFooter() {
        return this.footer;
    }

    public DiscordEmbed withFooter(DiscordEmbedFooter footer) {
        this.footer = footer;
        return this;
    }

}

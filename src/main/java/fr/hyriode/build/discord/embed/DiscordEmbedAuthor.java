package fr.hyriode.build.discord.embed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AstFaster
 * on 23/02/2023 at 11:01
 */
public class DiscordEmbedAuthor {

    private String name;
    @SerializedName("icon_url")
    private String iconUrl;

    public DiscordEmbedAuthor() {}

    public DiscordEmbedAuthor(String name, String iconUrl) {
        this.name = name;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return this.name;
    }

    public DiscordEmbedAuthor withName(String name) {
        this.name = name;
        return this;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public DiscordEmbedAuthor withIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

}

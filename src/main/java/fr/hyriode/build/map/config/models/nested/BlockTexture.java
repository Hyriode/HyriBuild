package fr.hyriode.build.map.config.models.nested;

import org.bukkit.Material;

/**
 * Created by AstFaster
 * on 02/04/2023 at 21:20
 */
public class BlockTexture {

    private final Material material;
    private final byte data;

    public BlockTexture(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    public Material getMaterial() {
        return this.material;
    }

    public byte getData() {

        return this.data;
    }
}

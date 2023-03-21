package fr.hyriode.build.util;

import fr.hyriode.hyrame.item.ItemHead;

/**
 * Created by AstFaster
 * on 23/02/2023 at 15:29
 */
public enum BuildHead implements ItemHead {

    BOB_THE_BUILDER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjdmNTQ1YTEyNzI1YjdhMzhlZTEzZjMyYjRkZDEyY2M4Nzk3MzJkMTdiNjQ4NDQ1MGVmNTEyYWEyYTU0ODgifX19"),
    GLOBE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZTU0Y2JlODc4NjdkMTRiMmZiZGYzZjE4NzA4OTQzNTIwNDhkZmVjZDk2Mjg0NmRlYTg5M2IyMTU0Yzg1In19fQ=="),
    BLUE_SHEEP("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM5ZWZjNGI0ZWFkZWM0ODU3NmE1NzAwZWM4MTIzOTU1MTAzMjdlNWQxZTdjMTA4ZmQ4YWJjNzc5NjY4NWFhMyJ9fX0="),
    STICKY_PISTON("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjExNzZjNGQ2Mzk1ZmY1NzY3YTc0YTM2OWZlMzg2ZDA2Y2M2MGEyMDk3YmM1YTUzYmQwMDVlYWRkMGE3Y2JkNCJ9fX0="),
    UPLOAD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MwNTFhZDk5NGIyZWExYmU5ZGYzZGJjNGJiNjI1NGViZDk2MjAxYzc5NDZkMDhhZTFlYWM0MjE0MWMwYzE3YSJ9fX0="),

    MONITOR_DOWN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2YzOWNjYjVhNWNkOWJhYmIxMzA5N2I0ZjhkYmM5ZjE0ZGMzOGFjMTcwMmEzMGQ5N2UyYWFiZjkwN2JmMTVjIn19fQ=="),
    MONITOR_UP("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDVjMGNkNzE3Y2E5MjI4ODQ5NjUyYThjY2RmNTI4MWE4NjBjYjJlNzk2NDZiNTZhMjJiYzU5MTEwZjM2MWRhIn19fQ==")

    ;

    private final String texture;

    BuildHead(String texture) {
        this.texture = texture;
    }

    @Override
    public String getTexture() {
        return this.texture;
    }

}

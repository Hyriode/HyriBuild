package fr.hyriode.build.map.config;

import fr.hyriode.build.map.config.handler.ConfigOptionHandler;

import java.lang.reflect.Field;

/**
 * Created by AstFaster.
 * on 01/06/2022 at 14:58.<br>
 *
 * This is an object that represents a step to run during the process.<br>
 * It contains all information about the field to set and the {@linkplain ConfigOptionHandler handler} to use.
 */
public class ConfigStep {

    /** The category of the option */
    private final ConfigOptionCategory category;
    /** The object of the category */
    private final Object categoryObject;
    /** The identifier of the element if it is in a list */
    private final String listMemberId;
    /** The option of the config field to set */
    private final ConfigOption option;
    /** The field to set */
    private final Field field;

    /** The handler of the step */
    private ConfigOptionHandler<?> handler;

    /**
     * Constructor of {@link ConfigStep}
     *
     * @param category       The category of the option
     * @param categoryObject The object of the category
     * @param listMemberId   The identifier of the element in a list
     * @param option         The option
     * @param field          The field
     */
    public ConfigStep(ConfigOptionCategory category, Object categoryObject, String listMemberId, ConfigOption option, Field field) {
        this.categoryObject = categoryObject;
        this.listMemberId = listMemberId;
        this.option = option;
        this.category = category;
        this.field = field;
    }

    /**
     * Get the config option with all information about the field
     *
     * @return The {@linkplain ConfigOption config option}
     */
    public ConfigOption getOption() {
        return this.option;
    }

    /**
     * Get the category of the option
     *
     * @return A {@link ConfigOptionCategory}
     */
    public ConfigOptionCategory getCategory() {
        return this.category;
    }

    /**
     * Get the object that represents the category
     *
     * @return An object
     */
    public Object getCategoryObject() {
        return this.categoryObject;
    }

    public String getListMemberId() {
        return this.listMemberId;
    }

    /**
     * Get the field related to the config option
     *
     * @return A {@link Field}; cannot be null
     */
    public Field getField() {
        return this.field;
    }

    /**
     * Get the handler of the step
     *
     * @return An {@link ConfigOptionHandler} instance
     */
    public ConfigOptionHandler<?> getHandler() {
        return this.handler;
    }

    void setHandler(ConfigOptionHandler<?> handler) {
        this.handler = handler;
    }

}

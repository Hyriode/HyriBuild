package fr.hyriode.build.map.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by AstFaster
 * on 01/06/2022 at 07:15
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigOption {

    /**
     * Get the type of the option
     *
     * @return A {@link ConfigOptionType}
     */
    ConfigOptionType type();

    /**
     * The identifier of the option
     *
     * @return An identifier
     */
    String id();

    /**
     * Get the name of the option.<br>
     * This name will be shown to the builder.
     *
     * @return A name
     */
    String name();

    /**
     * Get the description of the option.<br>
     * This description needs to describe as much as possible what the option is and in which way it needs to be configured.
     *
     * @return A description
     */
    String description();

}

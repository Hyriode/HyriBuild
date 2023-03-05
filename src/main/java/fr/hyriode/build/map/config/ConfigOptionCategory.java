package fr.hyriode.build.map.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by AstFaster
 * on 27/02/2023 at 17:23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigOptionCategory {

    /**
     * The identifier of the category
     *
     * @return An identifier
     */
    String id();

    /**
     * Get the name of the category.<br>
     * This name will be shown to the builder.
     *
     * @return A name
     */
    String name();

}

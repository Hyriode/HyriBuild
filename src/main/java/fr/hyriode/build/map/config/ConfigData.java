package fr.hyriode.build.map.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by AstFaster
 * on 01/06/2022 at 07:15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigData {

    /**
     * Get the name of the config.<br>
     * This name will be shown to the builder.
     *
     * @return A name
     */
    String name();

}

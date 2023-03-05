package fr.hyriode.build.map.config.handler;

import fr.hyriode.build.map.config.ConfigProcess;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

/**
 * Created by AstFaster
 * on 01/06/2022 at 07:16.<br>
 *
 * An option handler is like a protocol to follow.<br>
 * This handler is used to set all objects with the same type during configuration process
 */
public abstract class ConfigOptionHandler<T> {

    /** The current process that ran the handler */
    protected final ConfigProcess<?> process;
    /** The player running the process */
    protected final Player player;
    /** The completion to call when the handler has finished its job to set a config's value */
    private final CompletableFuture<T> completion;

    /**
     * Constructor of {@link ConfigOptionHandler}
     *
     * @param process The process that ran the handler
     */
    public ConfigOptionHandler(ConfigProcess<?> process) {
        this.process = process;
        this.player = this.process.getPlayer();
        this.completion = new CompletableFuture<>();
    }

    /**
     * This method is called just after the handler being initialized.<br>
     * It's after this method has been called, that an object needs to have a value.
     */
    public abstract void handle();

    /**
     * Complete the handler by giving the object to set.<br>
     * The process will never continue if this method is not fired.
     *
     * @param value The value to set
     */
    public void complete(T value) {
        this.completion.complete(value);
    }

    /**
     * Get the completion of the handler
     *
     * @return A {@link CompletableFuture}
     */
    public CompletableFuture<T> getCompletion() {
        return this.completion;
    }

}

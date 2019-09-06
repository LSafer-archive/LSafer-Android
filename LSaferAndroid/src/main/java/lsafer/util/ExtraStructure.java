package lsafer.util;

import android.os.Bundle;

import java.io.Serializable;
import java.lang.Object;
import java.lang.Class;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * An extra methods that supports structures dealing with android utils.
 *
 * @author LSaferSE
 * @version 2 alpha (06-Sep-19)
 * @since 16-Aug-19
 */
public interface ExtraStructure extends Structure {

    /**
     * Copy all (key-value) links from the given map to this.
     *
     * <ul>
     *     <li>uses: heavy {@link Set#forEach(Consumer)}.</li>
     *     <li>uses: repetitive {@link #put(Object, Object)}.</li>
     * </ul>
     *
     * @param bundle to copy from
     * @param <A> this
     * @return this
     */
    default <A extends ExtraStructure> A putAll(Bundle bundle){
        bundle.keySet().forEach(key -> this.put(key, bundle.get(key)));
        return (A) this;
    }

    /**
     * Get a bundle image of this structure.
     *
     * <ul>
     *     <li>uses: {@link #map(Class, Class)}.</li>
     *     <li>uses: heavy {@link Map#forEach(BiConsumer)}.</li>
     * </ul>
     *
     * @return a map from this structure's entries
     */
    default Bundle bundle(){
        Bundle bundle = new Bundle();

        this.map(String.class, Serializable.class).forEach(bundle::putSerializable);

        return bundle;
    }

}

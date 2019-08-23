package lsafer.util;

import android.os.Bundle;

import java.io.Serializable;
import java.util.HashMap;

import lsafer.io.File;

/**
 * @author LSaferSE
 * @version 1 alpha (16-Aug-19)
 * @since 16-Aug-19
 */
public interface ExtraStructure extends Structure {

    /**
     * @param bundle
     *
     * @param <A>
     * @return
     */
    default <A extends ExtraStructure> A putAll(Bundle bundle){
        bundle.keySet().forEach(key -> this.put(key, bundle.get(key)));
        return (A) this;
    }

    /**
     * @return
     */
    default Bundle bundle(){
        Bundle bundle = new Bundle();

        this.forEach((k, v)-> {
            if (k instanceof String && v instanceof Serializable)
                bundle.putSerializable((String) k, (Serializable) v);

        });

        return bundle;
    }

}

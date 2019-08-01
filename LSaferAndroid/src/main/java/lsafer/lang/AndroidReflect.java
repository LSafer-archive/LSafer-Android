package lsafer.lang;

import android.content.Context;
import dalvik.system.DexClassLoader;

/**
 * an extender class of {@link Reflect}
 * to support android {@link DexClassLoader}.
 *
 * @author LSaferSE
 * @version 1
 * @since 14-Jul-19
 */
final public class AndroidReflect {

    /**
     * get a class that matches the given name and
     * stored at the given APK path.
     *
     * @param context to invoke {@link Context#getCodeCacheDir() code cache directory}
     * @param name   of the class
     * @param path   of the apk that contains the class
     * @param <TYPE> of the class
     * @return the class that matches the given parameters
     */
    public static <TYPE> Class<TYPE> getClass(Context context, String name, String path) {
        //get a class from the classes map or apply a function
        //that well generate the class case not have been loaded already
        return (Class<TYPE>) Reflect.getClass(name, (n1) -> {
            try {
                //get a class loader from the class loaders map or apply a function
                        //that well generate the class loader case not have been generated already
                return Reflect.getClassLoader(path, (n2) ->
                        new DexClassLoader(path, context.getCodeCacheDir().getAbsolutePath(),
                                null, context.getClassLoader())
                ).loadClass(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

}

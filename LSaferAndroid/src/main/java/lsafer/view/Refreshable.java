package lsafer.view;

/**
 * defines that the {@link android.view.View view} that implements this
 * can be refreshed with it's data.
 *
 * @author LSaferSE
 * @version 1 alpha (30-Jul-19)
 * @since 30-Jul-19
 */
public interface Refreshable {
    /**
     * refresh view with the new data.
     */
    void refresh();
}

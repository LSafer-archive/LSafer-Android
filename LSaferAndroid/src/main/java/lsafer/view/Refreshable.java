package lsafer.view;

/**
 * Defines that the {@link android.view.View view} that implements this. Can be refreshed with it's data.
 *
 * @author LSaferSE
 * @version 2 alpha (06-Sep-19)
 * @since 30-Jul-19
 */
@SuppressWarnings("unused")
public interface Refreshable {
	/**
	 * Refresh view with the new data.
	 */
	void refresh();
}

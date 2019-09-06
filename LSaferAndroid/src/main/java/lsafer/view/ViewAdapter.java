package lsafer.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * View adapter interface.
 *
 * @author LSaferSE
 * @version 2 alpha (06-Sep-19)
 * @since 29-Jul-19
 */
@SuppressWarnings({"WeakerAccess"})
public abstract class ViewAdapter {

    /**
     * The adapted view.
     */
    private View mView;

    /**
     * Application context.
     */
    private Context mContext;

    /**
     * Initialize this.
     *
     * @param context of application
     * @param parent to be attached to
     */
    final public void initialize(Context context, ViewGroup parent) {
        this.mContext = context;
        this.onCreate();
        this.mView = this.onCreateView(LayoutInflater.from(context), parent);
        this.mContext = this.mView.getContext();
        this.onCreated();
    }

    /**
     * Get the context that have been stored in this.
     *
     * @return the context of the adapted view
     */
    public Context getContext() {
        return this.mContext;
    }

    /**
     * Get the adapted view.
     *
     * @return the adapted view
     */
    public View getView() {
        return this.mView;
    }

    /**
     * Create the view to start adapt.
     *
     * @param inflater to inflate the view with
     * @param parent that have been passed in {@link #initialize(Context, ViewGroup)}
     * @return a view to be adapted.
     */
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup parent);

    /**
     * This method get called before {@link #onCreateView(LayoutInflater, ViewGroup)}.
     */
    public void onCreate(){
    }

    /**
     * This method get called after the end of initializing this.
     */
    public void onCreated() {
    }
}

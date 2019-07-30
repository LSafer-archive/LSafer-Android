package lsafer.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * view adapter.
 *
 * @author LSaferSE
 * @version 1 alpha (29-Jul-19)
 * @since 29-Jul-19
 */
@SuppressWarnings({"WeakerAccess"})
public abstract class ViewAdapter {

    /**
     * adapted view.
     */
    private View mView;

    /**
     * application context.
     */
    private Context mContext;

    /**
     * init this.
     *
     * @param context of application
     */
    public void init(Context context){
        this.mContext = context;
        this.onCreate();
        this.mView = this.onCreateView(LayoutInflater.from(context));
        this.mContext = this.mView.getContext();
        this.mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                ViewAdapter.this.onAttach(view);
            }
            @Override
            public void onViewDetachedFromWindow(View view) {
                ViewAdapter.this.onDetach(view);
            }
        });
    }

    /**
     * get the context.
     *
     * @return the context of the adapted view
     */
    public Context getContext() {
        return this.mContext;
    }

    /**
     * get the adapted view.
     *
     * @return the adapted view
     */
    public View getView() {
        return this.mView;
    }

    /**
     * get called each time this view get attached to another view.
     *
     * @param parent view that this view get attached to
     */
    public void onAttach(View parent){

    }

    /**
     * get called each time this view get detached from another view.
     *
     * @param parent view that this view get detached from
     */
    public void onDetach(View parent){

    }

    /**
     * create the view to start adapt.
     *
     * @param inflater to inflate the view with
     * @return a view to be adapted.
     */
    public View onCreateView(LayoutInflater inflater){
        return null;
    }

    /**
     * this get called after initializing and before {@link #onCreateView(LayoutInflater)}.
     */
    public void onCreate(){

    }

}

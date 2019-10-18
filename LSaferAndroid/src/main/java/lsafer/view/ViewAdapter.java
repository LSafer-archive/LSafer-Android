/*
 * Copyright (c) 2019, LSafer, All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * -You can edit this file (except the header).
 * -If you have change anything in this file. You
 *  shall mention that this file has been edited.
 *  By adding a new header (at the bottom of this header)
 *  with the word "Editor" on top of it.
 */
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
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ViewAdapter {
	/**
	 * Application context.
	 */
	private Context mContext;

	/**
	 * The adapted view.
	 */
	private View mView;

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
	 * Initialize this.
	 *
	 * @param context of application
	 * @param parent  to be attached to
	 */
	final public void initialize(Context context, ViewGroup parent) {
		this.mContext = context;
		this.onCreate();
		this.mView = this.onCreateView(LayoutInflater.from(context), parent);
		this.mContext = this.mView.getContext();
		this.onCreated();
	}

	/**
	 * This method get called before {@link #onCreateView(LayoutInflater, ViewGroup)}.
	 */
	public void onCreate() {
	}

	/**
	 * This method get called after the end of initializing this.
	 */
	public void onCreated() {
	}

	/**
	 * Set a random id for the given view.
	 *
	 * @param view to have a new id
	 * @param <V> the type of the given view
	 * @return the same view that have been given
	 */
	public <V extends View> V randomId(V view) {
		View adapted = this.getView();
		view.setId(view.getId() + (adapted == null ? 0 : adapted.getId()) + this.hashCode());
		return view;
	}

	/**
	 * Create the view to start adapt.
	 *
	 * @param inflater to inflate the view with
	 * @param parent   that have been passed in {@link #initialize(Context, ViewGroup)}
	 * @return a view to be adapted.
	 */
	public abstract View onCreateView(LayoutInflater inflater, ViewGroup parent);
}

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;

import lsafer.android.R;

/**
 * An adapter for a preferences section view.
 *
 * @author LSaferSE
 * @version 1 release (19-Oct-2019)
 * @since 19-Oct-2019
 */
public class SectionView extends ViewAdapter {
	/**
	 * The label view of this.
	 */
	protected TextView mLabel;
	/**
	 * The title.
	 */
	protected String mLabelRes;
	/**
	 * The layout contains {@link EditEntry entry-edit-views} on it.
	 */
	protected LinearLayout mLayout;

	/**
	 * init this.
	 *
	 * @param context of application
	 * @param parent  to be attached to
	 * @param label   resource id for this secession
	 */
	public SectionView(Context context, ViewGroup parent, @StringRes int label) {
		this.mLabelRes = context.getString(label);
		this.initialize(context, parent);
	}

	/**
	 * init this.
	 *
	 * @param context of application
	 * @param parent  to be attached to
	 * @param label   name of this secession
	 */
	public SectionView(Context context, ViewGroup parent, String label) {
		this.mLabelRes = label;
		this.initialize(context, parent);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_section, parent, false);
		//init members
		this.mLabel = this.randomId(view.findViewById(R.id.label0));
		this.mLayout = this.randomId(view.findViewById(R.id.layout0));

		//- Set the title
		//- Set the listener to hide/show the section
		this.mLabel.setText(this.mLabelRes);
		this.mLabel.setOnClickListener(v -> this.mLayout.setVisibility(this.mLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));
//			//A way to do it with a circular animation but I realized it's better without animation
//			this.label.setOnClickListener(v-> {
//				int lbh = this.label.getHeight(), lbw = this.label.getWidth();
//				float fr = (float) Math.hypot(view.getHeight(),  view.getWidth());
//				if (this.layout.getVisibility() == View.VISIBLE) {
//					Animator animator = ViewAnimationUtils.createCircularReveal(this.getView(), lbw/2, lbh/2, fr, lbw/2);
//					animator.addListener(new AnimatorListenerAdapter() {
//						@Override
//						public void onAnimationEnd(Animator animation) {
//							PreferenceSectionViewAdapter.this.layout.setVisibility(View.GONE);
//						}
//					});
//					animator.start();
//				} else {
//					Animator animator = ViewAnimationUtils.createCircularReveal(this.getView(), lbw/2, lbh/2, lbw/2, fr);
//					PreferenceSectionViewAdapter.this.layout.setVisibility(View.VISIBLE);
//					animator.start();
//				}
//			});

		parent.addView(view);
		return view;
	}

	/**
	 * Get the layout of this section.
	 *
	 * @return the layout
	 */
	public LinearLayout getLayout() {
		return this.mLayout;
	}
}

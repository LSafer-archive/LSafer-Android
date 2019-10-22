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
import android.widget.Switch;
import android.widget.TextView;

import java.util.Map;

import lsafer.android.R;

/**
 * An edit-entry that allows the user to toggle between boolean data.
 *
 * @author LSaferSE
 * @version 2 release (19-Oct-2019)
 * @since 08-Oct-2019
 */
public class EditBoolean extends EditEntry<EditEntry.FieldConfig, Object, Boolean> {
	/**
	 * The text-view that holds the description.
	 */
	protected TextView mDescriptionTextView;
	/**
	 * The text-view that holds the key.
	 */
	protected TextView mKeyTextView;
	/**
	 * The switch that holds the value.
	 */
	protected Switch mValueSwitch;

	/**
	 * Initialize this.
	 *
	 * @param context  of application
	 * @param groups   to switch then attach this to
	 * @param entry    to be edited
	 * @param listener to call as a listener
	 */
	public EditBoolean(Context context, ViewGroup[] groups, Map.Entry<Object, Boolean> entry, EventListener listener) {
		this.initialize(context, groups, entry, listener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_edit_boolean, parent, false);
		this.mValueSwitch = this.randomId(view.findViewById(R.id.value));
		this.mKeyTextView = this.randomId(view.findViewById(R.id.key));
		this.mDescriptionTextView = this.randomId(view.findViewById(R.id.description));

		this.mKeyTextView.setText(this.mKeyDescRes[0]);
		this.mDescriptionTextView.setText(this.mKeyDescRes[1]);
		this.mValueSwitch.setChecked(this.getValue());

		this.mValueSwitch.setOnClickListener(v -> this.setValue(this.mValueSwitch.isChecked()));

		parent.addView(view);
		return view;
	}
}

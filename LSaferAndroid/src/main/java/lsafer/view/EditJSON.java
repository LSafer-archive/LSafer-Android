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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import lsafer.android.R;
import lsafer.json.JSON;
import lsafer.util.JSObject;

/**
 * An editor that's allow the user to write objects directly using {@link JSON}.
 *
 * @author LSaferSE
 * @version 2 release (19-Oct-2019)
 * @since 08-Oct-2019
 */
public class EditJSON extends EditEntry<EditEntry.FieldConfig, String, Object> {
	/**
	 * The text-view that holds the description.
	 */
	protected TextView mDescriptionTextView;
	/**
	 * The text-view that holds the key.
	 */
	protected TextView mKeyTextView;
	/**
	 * The text-view that holds the type.
	 */
	protected TextView mTypeTextView;
	/**
	 * The edit-text that holds the value.
	 */
	protected EditText mValueTextView;

	/**
	 * Initialize this.
	 *
	 * @param context of application
	 * @param groups  to switch then attach this to
	 * @param entry   to be edited
	 */
	public EditJSON(Context context, ViewGroup[] groups, Map.Entry<String, Object> entry) {
		this.initialize(context, groups, entry);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_edit_json, parent, false);
		this.mKeyTextView = this.randomId(view.findViewById(R.id.key));
		this.mDescriptionTextView = this.randomId(view.findViewById(R.id.description));
		this.mValueTextView = this.randomId(view.findViewById(R.id.value));
		this.mTypeTextView = this.randomId(view.findViewById(R.id.type));

		this.mKeyTextView.setText(this.mKeyDescRes[0]);
		this.mDescriptionTextView.setText(this.mKeyDescRes[1]);
		this.mValueTextView.setText(JSON.Stringify(this.mEntry.getValue()));

		this.mValueTextView.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable editable) {
				try {
					EditJSON.this.mEntry.setValue(JSON.instance.parse(mValueTextView.getText().toString()));
				} catch (Exception ignored) {
				}
			}

			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}
		});

		if (this.mEntry instanceof JSObject.Entry && ((JSObject.Entry) this.mEntry).field != null)
			this.mTypeTextView.setText(((JSObject.Entry) this.mEntry).field.getType().getSimpleName());

		parent.addView(view);
		return view;
	}
}

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

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ArrayRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import lsafer.android.R;
import lsafer.util.Arrays;

/**
 * An edit-entry that allows the user to choose from different strings. Or make hem put the string directly.
 *
 * @author LSaferSE
 * @version 2 release (19-Oct-2019)
 * @since 08-Oct-2019
 */
@EditEntry.Configurations(fieldConfig = EditString.FieldConfig.class)
public class EditString extends EditEntry<EditString.FieldConfig, Object, String> {
	/**
	 * The text-view that holds the description.
	 */
	protected TextView mDescriptionTextView;
	/**
	 * The text-view that holds the key.
	 */
	protected TextView mKeyTextView;
	/**
	 * The resources strings for the possible values.
	 * It asserts that this strings are sorted the same as the possible values
	 */
	protected String[] mStrings;
	/**
	 * The text-view that holds the value.
	 */
	protected TextView mValueTextView;
	/**
	 * The possible values sorted as defined on the field config.
	 */
	protected String[] mValues;

	/**
	 * Initialize this.
	 *
	 * @param context of application
	 * @param groups  to switch then attach this to
	 * @param entry   to be edited
	 */
	public EditString(Context context, ViewGroup[] groups, Map.Entry<Object, String> entry) {
		this.initialize(context, groups, entry);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.mValues = this.mFieldConfig.values();
		this.mStrings = this.getContext().getResources().getStringArray(this.mFieldConfig.res());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_edit_string, parent, false);
		this.mKeyTextView = this.randomId(view.findViewById(R.id.key));
		this.mDescriptionTextView = this.randomId(view.findViewById(R.id.description));
		this.mValueTextView = this.randomId(view.findViewById(R.id.value));

		this.mKeyTextView.setText(this.mKeyDescRes[0]);
		this.mDescriptionTextView.setText(this.mKeyDescRes[1]);
		this.mValueTextView.setText(this.resource(this.mEntry.getValue()));

		this.mValueTextView.setOnLongClickListener(v -> {
			LinearLayout layout = new LinearLayout(this.getContext());
			EditText text = new EditText(this.getContext());
			layout.addView(text);

			text.setLayoutParams(new LinearLayout.LayoutParams(700, ViewGroup.LayoutParams.WRAP_CONTENT, 0) {
				{
					this.topMargin = 50;
					this.leftMargin = 25;
				}
			});
			text.setGravity(Gravity.CENTER);
			text.setBackground(null);
			text.setText(this.mEntry.getValue());
			text.requestFocus();

			new AlertDialog.Builder(this.getContext(), R.style.KroovAlertDialogTheme)
					.setView(layout)
					.setPositiveButton(R.string.confirm, (d, w) -> {
						String nv = text.getText().toString();
						this.mEntry.setValue(nv);
						this.mValueTextView.setText(nv);
					})
					.show();
			return true;
		});
		this.mValueTextView.setOnClickListener(v ->
				new AlertDialog.Builder(this.getContext(), R.style.KroovAlertDialogTheme)
						.setItems(this.mStrings, (d, i) -> {
							this.mEntry.setValue(this.mValues[i]);
							this.mValueTextView.setText(this.mStrings[i]);
						})
						.show()
		);

		parent.addView(view);
		return view;
	}

	/**
	 * Get the resource string form the given value string.
	 *
	 * @param value to get the resource string from
	 * @return the resource string from the given value string
	 */
	public String resource(String value) {
		int index = Arrays.indexOf(value, this.mValues);
		return index == -1 ? String.valueOf(value) : this.mStrings[index];
	}

	/**
	 * Field configurations for the entry-fields those set to be edited by {@link EditString}.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface FieldConfig {
		/**
		 * The resources string array for the values.
		 *
		 * @return the resource string array for the values
		 */
		@ArrayRes
		int res();

		/**
		 * The values for each item in the {@link #res()} array.
		 *
		 * @return the values for the resources array
		 */
		String[] values();
	}
}

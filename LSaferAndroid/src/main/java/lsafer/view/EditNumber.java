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
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.ArrayRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

import lsafer.android.R;
import lsafer.util.JSObject;

/**
 * An edit-entry that allows the user to chose a number as a preference.
 *
 * @author LSaferSE
 * @version 2 release (19-Oct-2019)
 * @since 09-Oct-2019
 */
@EditEntry.Configurations(fieldConfig = EditNumber.FieldConfig.class)
public class EditNumber extends EditEntry<EditNumber.FieldConfig, Object, Number> {
	/**
	 * The fix value.
	 */
	protected int mFix;
	/**
	 * The value to split the numbers with on the number-picker.
	 */
	protected int mSplit;
	/**
	 * Minimum fixed value.
	 */
	protected int mFMin;
	/**
	 * Maximum fixed value.
	 */
	protected int mFMax;
	/**
	 * The number picker that holds the value of this.
	 */
	protected NumberPicker mValueNumberPicker;
	/**
	 * The text-view that holds the description.
	 */
	protected TextView mDescriptionTextView;
	/**
	 * The text-view that holds the key.
	 */
	protected TextView mKeyTextView;

	/**
	 * Initialize this.
	 *
	 * @param context of application
	 * @param groups to switch then attach this to
	 * @param entry to be edited
	 */
	public EditNumber(Context context, ViewGroup[] groups, JSObject.Entry<Object, Number> entry) {
		this.initialize(context, groups, entry);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.mFix = this.mFieldConfig.fix();
		this.mSplit = this.mFieldConfig.split();
		this.mFMin = (int) ((float) mFix * this.mFieldConfig.min());
		this.mFMax = (int) ((float) mFix * this.mFieldConfig.max());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.view_edit_number, parent, false);
		this.mKeyTextView = this.randomId(view.findViewById(R.id.key));
		this.mDescriptionTextView = this.randomId(view.findViewById(R.id.description));
		this.mValueNumberPicker = this.randomId(view.findViewById(R.id.value));

		this.mKeyTextView.setText(this.mKeyDescRes[0]);
		this.mDescriptionTextView.setText(this.mKeyDescRes[1]);
		this.mValueNumberPicker.setValue(this.position(this.mEntry.getValue()));
//		this.value.setWrapSelectorWheel(false);
		this.mValueNumberPicker.setMinValue(this.mFMin / this.mSplit);
		this.mValueNumberPicker.setMaxValue(this.mFMax / this.mSplit);

		this.mValueNumberPicker.setOnValueChangedListener((picker, oi, ni) -> this.mEntry.setValue(this.value(ni)));

		if (this.mFieldConfig.res() != -1)
			this.mValueNumberPicker.setDisplayedValues(this.getContext().getResources().getStringArray(this.mFieldConfig.res()));

		parent.addView(view);
		return view;
	}

	/**
	 * Get the position of the given value.
	 *
	 * @param value to get the position of
	 * @return the position of the given value
	 */
	protected int position(Number value) {
		float v = Float.valueOf(value.toString());

		int position = (int) (float) (v * this.mFix) / this.mSplit;
		return position < this.mFMin ? this.mFMin : position > this.mFMax ? this.mFMax : position;
	}

	/**
	 * Get all the possible positions on a string array.
	 *
	 * @return all the possible positions of this
	 */
	protected String[] positions() {
		ArrayList<String> list = new ArrayList<>();

		for (int i = this.mFMin; i <= this.mFMax; i += this.mSplit)
			list.add(String.valueOf((float) i / this.mFix));

		return list.toArray(new String[0]);
	}

	/**
	 * Get the value of the given position.
	 *
	 * @param position the position to get the value of
	 * @return the value of the given position
	 */
	protected float value(int position) {
		position = position < this.mFMin ? this.mFMin : position > this.mFMax ? this.mFMax : position;
		return (float) (position * mSplit) / this.mFix;
	}

	/**
	 * Field configurations for the entry-fields those set to be edited by {@link EditNumber}.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface FieldConfig {
		/**
		 * The resolution of the number to be put in.
		 *
		 * default 1 (aka the same number)
		 * fixes should be tens and more than zero
		 *
		 * @return the fix of the field
		 */
		int fix() default 1; // >0 & x10

		/**
		 * The maximum value to be available to the user.
		 *
		 * @return the maximum value to be available to the user
		 */
		float max(); // >= 0

		/**
		 * The minimum value to be available to the user.
		 *
		 * @return the minimum value to be available to the user
		 */
		float min() default 0; // >=0

		/**
		 * The resources to be shown instead of the numbers.
		 *
		 * @return the array resources
		 */
		@ArrayRes
		int res() default -1;

		/**
		 * The split of the allowed numbers to be in.
		 *
		 * @return the split rate
		 */
		int split() default 1; // > 0
	}
}

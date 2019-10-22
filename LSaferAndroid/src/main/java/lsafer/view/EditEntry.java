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
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ArrayRes;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import lsafer.android.R;
import lsafer.util.Configurable;
import lsafer.util.JSObject;

/**
 * An abstract for an entry editor.
 *
 * @param <F> the type of the field configurations
 * @param <K> the type of keys
 * @param <V> the type of values
 * @author LSaferSE
 * @version 2 release (19-Oct-2019)
 * @since 08-Oct-2019
 */
@EditEntry.Configurations
public abstract class EditEntry<F extends Annotation, K, V> extends ViewAdapter implements Configurable {
	/**
	 * Base field configurations.
	 */
	protected FieldConfig mBaseFieldConfig;
	/**
	 * The entry to be edited.
	 */
	protected Map.Entry<K, V> mEntry;
	/**
	 * Implementation editor field config.
	 */
	protected F mFieldConfig;
	/**
	 * The resources strings for this entry (gotten from {@link FieldConfig#res()}).
	 */
	protected String[] mKeyDescRes;
	/**
	 * The actions listener of this.
	 */
	protected EventListener mListener;

	/**
	 * Get a new edit-entry instance for the given parameters.
	 *
	 * @param context  of application
	 * @param groups   to switch then attach this edit-entry to
	 * @param entry    to be edited
	 * @param listener to call as a listener
	 * @return a new instance for the given parameters
	 */
	public static EditEntry<?, ?, ?> newInstance(Context context, ViewGroup[] groups, Map.Entry<?, ?> entry, EventListener listener) {
		if (entry instanceof JSObject.Entry && ((JSObject.Entry) entry).field != null)
			try {
				//noinspection ConstantConditions
				return ((JSObject.Entry) entry).field.getAnnotation(FieldConfig.class).editor()
						.getConstructor(Context.class, ViewGroup[].class, Map.Entry.class, EventListener.class)
						.newInstance(context, groups, entry, listener);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		else return new EditJSON(context, groups, (Map.Entry<String, Object>) entry, listener);
	}

	/**
	 * Get the value of the entry of this.
	 *
	 * @return the value
	 */
	public V getValue() {
		return this.mEntry.getValue();
	}

	/**
	 * Set the value to the entry of this.
	 *
	 * @param value to be set
	 */
	public void setValue(V value) {
		this.mEntry.setValue(value);
		this.mListener.onEntryChanged(this, value);
	}

	/**
	 * Initialize this.
	 *
	 * @param context  of application
	 * @param groups   to switch then attach this to
	 * @param entry    to be edited by this
	 * @param listener to call as a listener
	 */
	final public void initialize(Context context, ViewGroup[] groups, Map.Entry<K, V> entry, EventListener listener) {
		this.mEntry = entry;
		this.mListener = listener;

		if (entry instanceof JSObject.Entry && ((JSObject.Entry) entry).field != null) {
			this.mBaseFieldConfig = ((JSObject.Entry) entry).field.getAnnotation(FieldConfig.class);
			this.mFieldConfig = (F) ((JSObject.Entry) entry).field.getAnnotation(
					this.configurations(Configurations.class, EditEntry.class).fieldConfig()
			);

			this.mKeyDescRes = context.getResources().getStringArray(this.mBaseFieldConfig.res());
		} else {
			this.mKeyDescRes = new String[]{String.valueOf(entry.getKey()),
											context.getResources().getString(R.string.plh__default_value, String.valueOf(entry.getValue()))};
		}

		this.initialize(context, groups[this.mBaseFieldConfig == null ? 0 : this.mBaseFieldConfig.group()]);

		if (this.mListener != null && !this.mListener.isEntryTriggered(this))
			this.getView().setVisibility(View.GONE);
	}

	/**
	 * Set the listener of this.
	 *
	 * @param listener the new listener
	 */
	public void setListener(EventListener listener) {
		this.mListener = listener;
	}

	/**
	 * The configurations for {@link EditEntry}s.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE, ElementType.TYPE_USE})
	@Inherited
	public @interface Configurations {
		/**
		 * The field config type of the annotated {@link EditEntry}.
		 *
		 * @return the field config type of the annotated edit-entry
		 */
		Class<? extends Annotation> fieldConfig() default FieldConfig.class;
	}

	/**
	 * An interface for an object to tell this edit-entry what to do when specific events happens.
	 */
	public interface EventListener {
		/**
		 * Check whether the given entry is triggered to be visible to the user or not.
		 *
		 * @param entry to be checked
		 * @return whether the given entry is triggered or not
		 */
		default boolean isEntryTriggered(EditEntry<?, ?, ?> entry) {
			return true;
		}

		/**
		 * Get called when an entry get changed.
		 *
		 * @param entry the entry that have been changed
		 * @param value the new value
		 */
		default void onEntryChanged(EditEntry<?, ?, ?> entry, Object value) {
		}
	}

	/**
	 * The annotation to till the {@link EditEntry} what to do with the annotated entry-field.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface FieldConfig {
		/**
		 * The editor to be used for the annotated entry-field.
		 *
		 * @return the editor to be used
		 */
		Class<? extends EditEntry> editor();

		/**
		 * The group index for the annotated entry-field.
		 *
		 * @return the group index
		 */
		int group();

		/**
		 * The resources id for the name/description of the annotated entry-field.
		 *
		 * @return the resource id
		 */
		@ArrayRes int res();
	}
}

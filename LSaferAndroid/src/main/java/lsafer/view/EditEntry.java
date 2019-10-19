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
import java.lang.reflect.InvocationTargetException;
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
	 * Get a new edit-entry instance for the given parameters.
	 *
	 * @param context  of application
	 * @param groups to switch then attach this edit-entry to
	 * @param entry    to be edited
	 * @param <F> the type of the field configurations
	 * @param <K> the type of keys
	 * @param <V> the type of values
	 * @return a new instance for the given parameters
	 */
	public static <F extends Annotation, K, V> EditEntry<F, K, V> newInstance(Context context, ViewGroup[] groups, JSObject.Entry<K, V> entry) {
		if (entry.field == null)
			return (EditEntry<F, K, V>) new EditJSON(context, groups, (Map.Entry<String, Object>) entry);
		try {
			//noinspection ConstantConditions,unchecked
			return entry.field.getAnnotation(FieldConfig.class).editor()
					.getConstructor(Context.class, ViewGroup[].class, JSObject.Entry.class)
					.newInstance(context, groups, entry);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Initialize this.
	 *
	 * @param context of application
	 * @param groups  to switch then attach this to
	 * @param entry   to be edited by this
	 */
	final public void initialize(Context context, ViewGroup[] groups, Map.Entry<K, V> entry) {
		this.mEntry = entry;

		if (entry instanceof JSObject.Entry && ((JSObject.Entry) entry).field != null) {
			this.mBaseFieldConfig = ((JSObject.Entry) entry).field.getAnnotation(FieldConfig.class);
			this.mFieldConfig = (F) ((JSObject.Entry) entry).field.getAnnotation(
					this.configurations(Configurations.class, EditEntry.class).fieldConfig()
			);

			this.mKeyDescRes = context.getResources().getStringArray(this.mBaseFieldConfig.res());
		} else {
			this.mKeyDescRes = new String[]{String.valueOf(entry.getKey()), context.getResources().getString(R.string.plh__default_value, String.valueOf(entry.getValue()))};
		}

		this.initialize(context, groups[this.mBaseFieldConfig == null ? 0 : this.mBaseFieldConfig.group()]);

		if (this.mBaseFieldConfig != null) {
			if (this.mBaseFieldConfig.trigger() != Object.class)
				try {
					boolean triggered = (boolean) this.mBaseFieldConfig.trigger().getMethod("isTriggered", Map.Entry.class).invoke(null, this.mEntry);

					if (!triggered) this.getView().setVisibility(View.GONE);
				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
		}
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

		/**
		 * What the class to be asked about is this entry is triggered or not.
		 *
		 * Note: the class should have a static method named 'isTriggered' and a parameters of (Map.Entry) and returns boolean
		 * ex.
		 * <pre>
		 * public static boolean isTriggered(Map.Entry entry) {
		 * 	return !entry.getValue().equals("hide me!");
		 * }
		 * </pre>
		 *
		 * @return the class to ask weather the annotated entry is triggered or not
		 */
		Class<?> trigger() default Object.class;
	}
}

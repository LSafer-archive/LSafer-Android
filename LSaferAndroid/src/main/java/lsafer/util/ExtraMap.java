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
package lsafer.util;

import android.os.Bundle;

import java.io.Serializable;
import java.util.Map;

/**
 * An extra methods that supports maps dealing with android utils.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author LSaferSE
 * @version 2 alpha (06-Sep-19)
 * @since 16-Aug-19
 */
public interface ExtraMap<K, V> extends Map<K, V> {
	/**
	 * Get a bundle image of this map.
	 *
	 * @return a map from this structure's entries
	 */
	default Bundle bundle() {
		Bundle bundle = new Bundle();

		for (Entry<K, V> entry : this.entrySet()) {
			K key = entry.getKey();

			if (key instanceof String) {
				V value = entry.getValue();
				if (value instanceof Serializable)
					bundle.putSerializable((String) key, (Serializable) value);
			}
		}

		return bundle;
	}

	/**
	 * Copy all (key-value) links from the given map to this.
	 *
	 * @param bundle to copy from
	 */
	default void putAll(Bundle bundle) {
		bundle.keySet().forEach(key -> this.put((K) key, (V) bundle.get(key)));
	}
}

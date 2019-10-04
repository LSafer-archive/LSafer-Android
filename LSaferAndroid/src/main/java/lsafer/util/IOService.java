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

import android.app.Service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import lsafer.io.IOMap;

/**
 * An abstract for services with the needed methods for the interfaces {@link JSObject}, {@link Serializable} and {@link IOMap}.
 *
 * @param <R> type of the remote of the third IO-port container.
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author LSaferSE
 * @version 1 alpha (05-Oct-19)
 * @since 05-Oct-19
 */
public abstract class IOService<R, K, V> extends Service implements Serializable, JSObject<K, V>, IOMap<R, K, V> {
	/**
	 * The secondary container.
	 */
	private transient Map<K, JSObject.Entry<K, V>> entries = new HashMap<>();

	/**
	 * The 3rd IO-container's remote.
	 */
	protected transient R remote;

	@Override
	public Map<K, JSObject.Entry<K, V>> entries() {
		return this.entries;
	}

	@Override
	public R remote() {
		return remote;
	}

	@Override
	public R remote(R remote) {
		R old = this.remote();
		this.remote = remote;
		return old;
	}

	@Override
	public String toString() {
		Iterator entries = this.entrySet().iterator();

		if (!entries.hasNext()) {
			return "{}";
		} else {
			StringBuilder builder = new StringBuilder("{");

			while (true) {
				Map.Entry entry = (Map.Entry) entries.next();
				Object key = entry.getKey();
				Object value = entry.getValue();

				builder.append(key == this ? "(this Map)" : key);
				builder.append('=');
				builder.append(value == this ? "(this Map)" : value);

				if (!entries.hasNext()) {
					return builder.append('}').toString();
				}

				builder.append(',').append(' ');
			}
		}
	}

	/**
	 * Backdoor initializing method, or custom deserialization method.
	 *
	 * @param stream to initialize this using
	 * @throws ClassNotFoundException if the class of a serialized object could not be found.
	 * @throws IOException            if an I/O error occurs.
	 */
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
		this.entries = this.entries == null ? new HashMap<>() : this.entries;
		int size = stream.readInt();

		for (int i = 0; i < size; i++) {
			K key = (K) stream.readObject();
			V value = (V) stream.readObject();
			this.put(key, value);
		}
	}

	/**
	 * Custom JSObject serialization method.
	 *
	 * @param stream to use to serialize this
	 * @throws IOException if an I/O error occurs
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException {
		Set<Map.Entry<K, V>> entries = this.entrySet();
		stream.writeInt(entries.size());

		for (Map.Entry<K, V> entry : entries) {
			stream.writeObject(entry.getKey());
			stream.writeObject(entry.getValue());
		}
	}
}

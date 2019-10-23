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

import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;

import java.util.Map;

import lsafer.android.R;

/**
 * A view-adapter to edit maps.
 *
 * @author LSaferSE
 * @version 1 beta (22-Oct-2019)
 * @since 22-Oct-2019
 */
public class EditMap extends ViewAdapter implements Refreshable {
	/**
	 * All active edit-entries objects of this.
	 */
	protected EditEntry[] mEditEntries;
	/**
	 * The main layout contains the sections layouts of this.
	 */
	protected LinearLayout mSectionsLayout;
	/**
	 * View groups that responsible to contain edit-entries of this. (aka the layouts of the {@link #mSections sections} of this)
	 */
	protected ViewGroup[] mGroups;
	/**
	 * The event listener of edit-entries created by this.
	 */
	protected EditEntry.EventListener mListener;
	/**
	 * The map associated with this.
	 */
	protected Map<?, ?> mMap;
	/**
	 * All active section objects of this.
	 */
	protected SectionView[] mSections;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.fragment_edit_map, container);
		this.mSectionsLayout = view.findViewById(R.id.sections);
		return view;
	}

	@Override
	public void refresh() {
		for (ViewGroup group : this.mGroups)
			group.removeAllViews();

		int size = this.mMap.size();
		Context context = this.getContext();
		this.mEditEntries = new EditEntry[this.mMap.size()];

		int i = 0;
		for (Map.Entry<?, ?> entry : this.mMap.entrySet())
			this.mEditEntries[i++] = EditEntry.newInstance(context, this.mGroups, entry, this.mListener);
	}

	/**
	 * Set the event listener to be used with the edit-entries.
	 *
	 * Note: you need to call {@link #refresh()} to take effect
	 *
	 * @param listener to be set
	 * @return this
	 */
	public EditMap setListener(EditEntry.EventListener listener) {
		this.mListener = listener;

		if (this.mEditEntries != null)
			for (EditEntry entry : this.mEditEntries)
				entry.setListener(listener);

		return this;
	}

	/**
	 * Set the source for this.
	 *
	 * Note: you need to call {@link #refresh()} to take effect
	 *
	 * @param map the map
	 * @return this
	 */
	public EditMap setMap(Map<?, ?> map) {
		this.mMap = map;
		return this;
	}

	/**
	 * Set the sections of this. Sorted by index. And provided by the id of it's name resources.
	 *
	 * Note: you need to call {@link #refresh()} to take effect
	 *
	 * @param sections to be set (sorted)
	 * @return this
	 */
	public EditMap setSections(@StringRes int[] sections) {
		this.mSectionsLayout.removeAllViews();
		this.mSections = new SectionView[sections.length];
		this.mGroups = new ViewGroup[sections.length];

		for (int i = 0; i < sections.length; i++)
			this.mGroups[i] = (this.mSections[i] = new SectionView(this.getContext(), this.mSectionsLayout, sections[i])).getLayout();

		return this;
	}

	/**
	 * Set the sections of this. Sorted by index. And provided by the id of it's name.
	 *
	 * Note: you need to call {@link #refresh()} to take effect
	 *
	 * @param sections to be set (sorted)
	 * @return this
	 */
	public EditMap setSections(String[] sections) {
		this.mSectionsLayout.removeAllViews();
		this.mSections = new SectionView[sections.length];
		this.mGroups = new ViewGroup[sections.length];

		for (int i = 0; i < sections.length; i++)
			this.mGroups[i] = (this.mSections[i] = new SectionView(this.getContext(), this.mSectionsLayout, sections[i])).getLayout();

		return this;
	}

	/**
	 * Set the sections of this. Sorted by index. And provided by the id of an array resources contains it's names.
	 *
	 * Note: you need to call {@link #refresh()} to take effect
	 *
	 * @param sections to be set (sorted)
	 * @return this
	 */
	public EditMap setSections(@ArrayRes int sections) {
		String[] sections1 = this.getContext().getResources().getStringArray(sections);
		this.mSectionsLayout.removeAllViews();
		this.mSections = new SectionView[sections1.length];
		this.mGroups = new ViewGroup[sections1.length];

		for (int i = 0; i < sections1.length; i++)
			this.mGroups[i] = (this.mSections[i] = new SectionView(this.getContext(), this.mSectionsLayout, sections1[i])).getLayout();

		return this;
	}
}

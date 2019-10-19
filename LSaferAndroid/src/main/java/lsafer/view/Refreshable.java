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

/**
 * Defines that the {@link android.view.View view} that implements this. Can be refreshed with it's data.
 *
 * @author LSaferSE
 * @version 2 alpha (06-Sep-19)
 * @since 30-Jul-19
 */
public interface Refreshable {
	/**
	 * Refresh view with the new data.
	 */
	void refresh();
}

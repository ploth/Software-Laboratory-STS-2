package io;

import data.PERSTDatabase;

// This is the AbstractConverter class which all converters extend.
public abstract class AbstractConverter {

	private static PERSTDatabase db_ = PERSTDatabase.getInstance();

	public static PERSTDatabase getDb_() {
		return db_;
	}
}

package io;

import data.PERSTDatabase;

public abstract class AbstractConverter {

	private static PERSTDatabase db_ = PERSTDatabase.getInstance();

	public static PERSTDatabase getDb_() {
		return db_;
	}
}

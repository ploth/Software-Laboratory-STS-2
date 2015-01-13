package io;

import data.PERSTDatabase;

public abstract class AbstractConverter {
	private PERSTDatabase db_ = PERSTDatabase.getInstance();

	public PERSTDatabase getDb_() {
		return db_;
	}
}

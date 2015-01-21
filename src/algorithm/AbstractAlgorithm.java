package algorithm;

import data.PERSTDatabase;

// This is the AbstractAlgorithm class which every Algorithm extend.
public abstract class AbstractAlgorithm {

	private static PERSTDatabase db_ = PERSTDatabase.getInstance();

	public static PERSTDatabase getDb_() {
		return db_;
	}

	public final static int SQR_EUCLID = 0;
	public final static int MANHATTAN = 1;

	// It is possbile to call doAlgorithm with corresponding parameters to call
	// the right function
	public void doAlgorithm(int type, int k) throws AlgorithmException {
		if (type == SQR_EUCLID)
			doSqrEuclid(k);
		else if (type == MANHATTAN)
			doManhattan(k);
	}

	public abstract void doSqrEuclid(int k) throws AlgorithmException;

	public abstract void doManhattan(int k) throws AlgorithmException;
}

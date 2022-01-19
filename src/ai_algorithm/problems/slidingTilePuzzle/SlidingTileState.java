package ai_algorithm.problems.slidingTilePuzzle;

import java.util.Arrays;

import ai_algorithm.State;
import ai_algorithm.problems.Problem;

public class SlidingTileState extends State {
	private int[][] field;

	public int[][] getField() {
		return field;
	}

	protected static int[][] arrayDeepCoppy(int[][] from) {
		if (from == null) {
			return null;

		}
		if (from.length == 0) {
			return new int[0][];
		}
		int[][] to = new int[from.length][];

		for (int i = 0; i < from.length; i++) {
			to[i] = new int[from[i].length];
			for (int j = 0; j < from[i].length; j++) {
				to[i][j] = from[i][j];
			}
		}
		return to;
	}

	public void setField(int[][] field) {
		if (field == null) {
			return;
		}

		this.field = arrayDeepCoppy(field);
	}

	public SlidingTileState() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(field);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SlidingTileState other = (SlidingTileState) obj;
		return Arrays.deepEquals(field, other.field);
	}

	@Override
	public Problem getProblem() {
		return this.getProblem();
	}
}

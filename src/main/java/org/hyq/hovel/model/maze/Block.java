package org.hyq.hovel.model.maze;

class Block {
	int row;
	int col;

	public Block(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Block up() {
		if (this.row - 1 >= 0) {
			return new Block(this.row-1, this.col);
		}
		return null;
	}
	public Block down() {
		if (this.row + 1 < Maze.ROW) {
			return new Block(this.row+1, this.col);
		}
		return null;
	}
	public Block left() {
		if (this.col - 1 >= 0) {
			return new Block(this.row, this.col-1);
		}
		return null;
	}
	public Block right() {
		if (this.col + 1 < Maze.COL) {
			return new Block(this.row, this.col+1);
		}
		return null;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Block) {
			Block that = (Block)object;
			return (this.row==that.row && this.col==that.col);
		}
		return false;
	}
}
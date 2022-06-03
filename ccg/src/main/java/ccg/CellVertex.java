package ccg;

import java.awt.Point;

public class CellVertex {

	public int content;
	private Point l;

	public CellVertex(Point l) {
		this(l, 0);
	}

	public CellVertex(Point l, int content) {
		this.l = l;
		this.content = content;
	}

	public Point getLocation() {
		return this.l;
	}

	public int getContent() {
		return this.content;
	}

	@Override
	public boolean equals(Object v) {
		return ((CellVertex) v).hashCode() == this.hashCode();
	}

	@Override
	public int hashCode() {
		return (this.l.y*GraphFacilities.W)+this.l.x;
	}
}

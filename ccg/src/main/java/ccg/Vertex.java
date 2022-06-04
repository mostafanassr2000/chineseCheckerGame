package ccg;

import java.awt.Point;

public class Vertex implements Cloneable {

	public int content;
	private Point point;
	private boolean visited;
	public double score;
	public boolean arrived = false;

	public Vertex(Point l) {
		this(l, 0);
	}

	public Vertex(Point point, int content) {
		this.point = point;
		this.content = content;
		this.visited = false;
	}

	public void setLocation(Point point) {
		this.point = point;
	}

	public Point getLocation() {
		return this.point;
	}

	public int getContent() {
		return this.content;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}


	public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }




	/* 
	@Override
	public boolean equals(Object v) {
		return ((CellVertex) v).hashCode() == this.hashCode();
	}

	@Override
	public int hashCode() {
		return (this.l.y*GraphFacilities.W)+this.l.x;
	}*/
}

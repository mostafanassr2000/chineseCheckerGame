package ccg;

public class CCEdge {
	private CellVertex srcVertx;//source vertex
	private CellVertex destVertx;//destination vertex
	public CCEdge(CellVertex srcVertx, CellVertex destVertex)//ctor
	{
		this.srcVertx=srcVertx;
		this.destVertx=destVertex;
		//weight=1;
	}
	//getters and setters
	public CellVertex getSrcVertx() {
		return srcVertx;
	}
	public void setSrcVertx(CellVertex srcVertx) {
		this.srcVertx = srcVertx;
	}
	public CellVertex getDestVertx() {
		return destVertx;
	}
	public void setDestVertx(CellVertex destVertx) {
		this.destVertx = destVertx;
	}

}

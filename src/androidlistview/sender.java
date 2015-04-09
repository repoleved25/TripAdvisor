package androidlistview;

import java.io.Serializable;

@SuppressWarnings("serial")
public class sender implements Serializable{
	private PathsMapper p;
	private GraphCity g;
	public GraphCity getG() {
		return g;
	}
	public void setG(GraphCity g) {
		this.g = g;
	}
	public PathsMapper getP() {
		return p;
	}
	public void setP(PathsMapper p) {
		this.p = p;
	}
	
	
	
	

}

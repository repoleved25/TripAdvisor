package androidlistview;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PathsMapper implements Serializable {
	private  int[][] paths =new int[20][20];
	private int[] mini=new int[20];
	private int size_places;
	public int[][] getPaths() {
		return paths;
	}
	public void setPaths(int[][] paths) {
		this.paths = paths;
	}
	public int[] getMini() {
		return mini;
	}
	public void setMini(int[] mini) {
		this.mini = mini;
	}
	public int getSize_places() {
		return size_places;
	}
	public void setSize_places(int size_places) {
		this.size_places = size_places;
	}
	
}

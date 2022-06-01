import java.util.Comparator;
import java.util.TreeSet;



public class UserSolution {
	TreeSet<Node> tsArray [];
	
	void init() {
		tsArray = new TreeSet[102];
		for(int i = 0 ; i <= 101; i++) {
			tsArray[i] = new TreeSet<Node>(new ComNode());
		}
		
	}
	
	void add(int x, int y) {
		tsArray[x].add(new Node(y, x+1));
		tsArray[x+1].add(new Node(y, x));
	}
	
	int participant(int x, int y) {
		Node tmp = tsArray[x].floor(new Node(y, x));
		int res = x;
		while(tmp != null) {
			int tsIndex = tmp.tsIndex;
			int ty = tmp.y - 1;
			Node sNode = new Node(ty, tsIndex);
			tmp = tsArray[tsIndex].floor(sNode);
			res = tsIndex;
		}
		return res;
	}
	
	void remove(int x, int y) {
		
		tsArray[x].remove( new Node(y, x));
		tsArray[x+1].remove( new Node(y, x+1));
		tsArray[x-1].remove( new Node(y, x-1));
	}
	
	int numberOfCross(int id) {
		Node tmp = tsArray[id].ceiling(new Node(0, id));
		int count = 0;
		while(tmp != null) {
			int tsIndex = tmp.tsIndex;
			int ty = tmp.y + 1;
			Node sNode = new Node(ty, tsIndex);
			tmp = tsArray[tsIndex].ceiling(sNode);
			count++;
		}
		
		return count;
	}
}

class Node{
	Integer y, tsIndex;
	public Node(Integer y, Integer tsIndex) {
		this.y = y;
		this.tsIndex = tsIndex;
	}
}
class ComNode implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		int comY = o1.y.compareTo(o2.y);
		return comY;
	}
	
}

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class TreeSetMain {

	public static void main(String[] args) {
		UserSolution us = new UserSolution();
		us.init(30);
		int r = us.allocate(13);
		us.print(r);
		r = us.release(5);
		us.print(r);
		r = us.allocate(7);
		us.print(r);
		r = us.allocate(3);
		us.print(r);
		r = us.allocate(10);
		us.print(r);
		r = us.release(13);
		us.print(r);
		r = us.allocate(3);
		us.print(r);
		r = us.release(0);
		us.print(r);
		r = us.allocate(3);
		us.print(r);
		r = us.release(20);
		us.print(r);
		r = us.allocate(8);
		us.print(r);
		r = us.release(0);
		us.print(r);

	}

}

class UserSolution{
	
	TreeSet<Memory> treeSet ;
	TreeSet<Memory> treeSetStart;
	TreeSet<Memory> treeSetEnd;
	HashMap<Integer, Memory> map;
	
	void init(int N) {
		treeSet = new TreeSet<>(new ComMain());
		treeSetStart = new TreeSet<>(new ComStart());
		treeSetEnd = new TreeSet<>(new ComEnd());
		map = new HashMap<>();
		treeSet.add(new Memory(0, N-1, N));
		treeSetStart.add(new Memory(0, N-1, N));
		treeSetEnd.add(new Memory(0, N-1, N));
	}
	
	int allocate(int N) {
		Memory m = treeSet.ceiling(new Memory(-1, -1, N));
		if(m==null) return -1;
		treeSet.remove(m);
		treeSetStart.remove(m);
		treeSetEnd.remove(m);
		treeSet.add(new Memory(m.start + N, m.end, m.size - N));
		treeSetStart.add(new Memory(m.start + N, m.end, m.size - N));
		treeSetEnd.add(new Memory(m.start + N, m.end, m.size - N));
		map.put(m.start, new Memory(m.start, m.start + N - 1 , N));
		return m.start;
	}
	
	
	int release(int add) {
		if(!map.containsKey(add)) return -1;
	
		Memory m = map.get(add);
		int ret = m.size;
		map.remove(add);
		Memory tmp = m;
		Memory leftMemory = 
				treeSetEnd.ceiling(new Memory(-1, m.start - 1, -1));
		if(leftMemory!=null && leftMemory.end+1==m.start) {
			tmp.start = leftMemory.start;
			tmp.end = m.end;
			tmp.size = leftMemory.size + m.size;
			treeSetEnd.add(leftMemory);
			treeSetStart.add(leftMemory);
			treeSet.add(leftMemory);
		}
		Memory rightMemory = 
				treeSetStart.ceiling(new Memory(m.end + 1, -1, -1));
		if(rightMemory!=null && rightMemory.start == m.end+1) {
			tmp.end = rightMemory.end;
			tmp.size = tmp.size + rightMemory.size;
			treeSetStart.add(rightMemory);
			treeSetEnd.add(rightMemory);
			treeSet.add(rightMemory);
		}
		treeSet.add(tmp);
		treeSetStart.add(tmp);
		treeSetEnd.add(tmp);
		
		return ret;
	}
	void print(int n) {
		System.out.println(n);
	}
}


class Memory{
	Integer start, end, size;

	public Memory(Integer start, Integer end, Integer size) {
		this.start = start;
		this.end = end;
		this.size = size;
	}
}
class ComMain implements Comparator<Memory>{

	@Override
	public int compare(Memory o1, Memory o2) {
		int firstCom = o1.size.compareTo(o2.size);
		int secondCom = o1.start.compareTo(o2.start);
		return firstCom == 0 ? secondCom : firstCom;
	}
}
class ComStart implements Comparator<Memory>{

	@Override
	public int compare(Memory o1, Memory o2) {
		int comFirst = o1.start.compareTo(o2.start);
		return comFirst;
	}
	
}
class ComEnd implements Comparator<Memory>{

	@Override
	public int compare(Memory o1, Memory o2) {
		int comEnd = o1.end.compareTo(o2.end);
		return comEnd;
	}
	
}

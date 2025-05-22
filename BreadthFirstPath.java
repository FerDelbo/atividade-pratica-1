import java.util.Queue;

public class BreadthFirstPath  {
   private boolean[] marked;
   private int[] edgeTo;
   private final int s;
   
   public BreadthFirstPath(Graph G, int s){
      marked = new boolean[G.V()];
      edgeTo = new int[G.V()];
      this.s = s;
      bfs(G, s);
   }

   private void bfs(Graph G, int s){
      Queue<Integer> queue = new Queue<Integer>();
      marked[s] = true;
      queue.add(s);
      while(!queue.isEmpty()){
         int v = queue.peek();
         for(int w : G.adj(v))
            if(!marked[w]){
               edgeTo[w] = v;
               marked[w] = true;
               queue.add(w);
            }
      }
   }
}

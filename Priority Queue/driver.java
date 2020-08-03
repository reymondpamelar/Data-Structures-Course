// TestUnorderedArrayPriorityQueue class implementation
package data_structures;
import java.util.Iterator;
public class driver
{
   public static void main(String[] args)
   {
	   OrderedArrayPriorityQueue<Integer> upq = new OrderedArrayPriorityQueue<Integer>(8);
      
       upq.insert(5);
       upq.insert(7);
       upq.insert(4);
       upq.insert(6);
       upq.insert(2);
       upq.insert(7);
       upq.insert(7);
       upq.insert(6);
       
      
       System.out.println("Unordered PQ: ");      
       Iterator<Integer> itr = upq.iterator();      
       while(itr.hasNext())
       {
           System.out.println(itr.next());          
       }
      
       System.out.println("\nsize: " + upq.size());
       System.out.println("Removed : " + upq.remove()); 
       System.out.println("delete: " + upq.delete(7));
       System.out.println("Peek : " + upq.peek());
       System.out.println("empty" + upq.isEmpty());
      
       System.out.println("\n new Unordered PQ: ");
       itr = upq.iterator();      
       while(itr.hasNext())
       {
           System.out.println(itr.next());
       }
      
       System.out.println("\nsize : " + upq.size());
       System.out.println("Removed : " + upq.remove()); 
       System.out.println("Peek : " + upq.peek());   
       System.out.println("empty: " + upq.isEmpty());
   }
}

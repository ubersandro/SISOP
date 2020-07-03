package monitorNativi;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Collezioni {
    public static void main(String[] args) {
        Vector<Integer> v = new Vector<>(10);
        Hashtable<Integer, Double> a = new Hashtable<>();
        ConcurrentHashMap<String, Double > cm  = new ConcurrentHashMap<>();
        cm.forEach((k,t)-> System.out.println(""+k+t));
        LinkedList<Integer> ll = new LinkedList<>();
        Collection<Integer> c = Collections.synchronizedCollection(ll);
        System.out.println(c.getClass());
        ArrayBlockingQueue<Integer> awerwer = new ArrayBlockingQueue<>(2,true);
    }
}

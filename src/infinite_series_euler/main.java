package infinite_series_euler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
public class main {
    public double euler = 2.71828182845904523536028747135266249775724709369995;
    public static final int NUM_THREADS = 10;
    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        
    }
}

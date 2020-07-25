package infinite_series_euler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class mainCache {
    // Aplicando 10 termos no calculo
    public static final int NUM_TERMOS = 10;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Instanciando um executor do tipo Work Stealing Pool
        ExecutorService executorCache = Executors.newCachedThreadPool();

        //Instanciando uma lista de futures para realizar a soma mais a frente
        List<Future<Double>> resultsCache = new ArrayList<Future<Double>>();

        //Inicializando o medidor de tempo
        long start = System.currentTimeMillis();
        //Inicializando as threads com a pool e utilizando future para compartilhar as informações do cálculo
        for (int i = 0; i < NUM_TERMOS; i++){
            Callable<Double> calculator = new Serie_calculator(i);
            Future<Double> element = executorCache.submit(calculator);
            System.out.print("The current number of thread with cacheThreadPool is:"+ Thread.activeCount() +"\n");

            // Adicionando os resultados na lista
            resultsCache.add(element);

        }
        long end = start - System.currentTimeMillis();

        //Finalizando o procedimento com a soma dos termos
        Double soma = Double.valueOf(0);
        try {
            for (Future<Double> result : resultsCache) {
                System.out.print("Currently valor of the serie is  : ");
                System.out.print(result.get() + "\n");
                // Computando a soma total
                soma += result.get();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.print("The value of the infinite series is: " + soma + "\n");
            System.out.print("Execute for :" + end + " milliseconds\n");
            executorCache.shutdown();
        }
    }
}

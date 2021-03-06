package infinite_series_euler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.List;

public class mainFixed {
    // Número de threads
    public static final int NUM_THREADS_FIXED = 20;
    //Número de termos da série
    public static final int NUM_TERMOS = 50;

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        //Inicializando a lista que vai ter o número de threads nos teste a fim de ter o número máximo de threads
        ArrayList<Integer> threadsnumber = new ArrayList<Integer>();

        //Incializando a lista que irá guardar o tempo dos teste
        ArrayList<Long> times = new ArrayList<Long>();

        //Variavel que irá guardar o resultado
        Double save_result = null;

        for(int j = 0; j < 30; j++){
            //Instanciando um executor do tipo Fixed
            ExecutorService executorFixed = Executors.newFixedThreadPool(NUM_THREADS_FIXED);
            //Instanciando uma lista de futures para realizar a soma mais a frente
            List<Future<Double>> resultsFixed = new ArrayList<Future<Double>>();
            //Inicializando o medidor de tempo
            Long start = Long.valueOf(System.currentTimeMillis());
            //Inicializando as threads com a pool e utilizando future para compartilhar as informações do cálculo
            for (int i = 0; i < NUM_TERMOS; i++){
                Callable<Double> calculator = new Serie_calculator(i);
                Future<Double> element = executorFixed.submit(calculator);
                System.out.print("The current number of thread with FixedPool is:"+ Thread.activeCount() +"\n");
                threadsnumber.add(Thread.activeCount());
                // Adicionando os resultados na lista
                resultsFixed.add(element);

            }
            Long end =  Long.valueOf(System.currentTimeMillis()) - start;
            times.add(end);
            //Finalizando o procedimento com a soma dos termos
            Double soma = Double.valueOf(0);
            try {
                for (Future<Double> result : resultsFixed) {
                    // Computando a soma total
                    soma += result.get();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                save_result = soma;
                executorFixed.shutdown();
            }
        }

        Long sum_times = Long.valueOf(0);
        for(int i = 0; i < times.size(); i++){
            sum_times += times.get(i);
        }
        Long mean_times = sum_times/times.size();

        Integer sum_threadsnumber = 0;
        for(int i = 0; i < threadsnumber.size(); i++){
            sum_threadsnumber += threadsnumber.get(i);
        }
        Integer mean_threadsnumber = sum_threadsnumber/threadsnumber.size();

        FileWriter fw = new FileWriter("output_fixed.txt");
        fw.write("O resultado foi: " + String.valueOf(save_result) + "\n");
        fw.write("O tempo médio foi: "+ String.valueOf(mean_times) + " milisegundos\n");
        fw.write("O número de threads foram: " + String.valueOf(mean_threadsnumber) + " threads\n");
        fw.close();
    }
}

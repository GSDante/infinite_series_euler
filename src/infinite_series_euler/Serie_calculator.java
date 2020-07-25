package infinite_series_euler;
import java.util.concurrent.Callable;

//Classe do future que recebe o termo para o cálculo do mesmo.
// Na main será computado a somatória de todos os termos
public class Serie_calculator implements Callable<Double>{

    private int term;

    //Construtor da future que receberá o termo com parâmetro
    public Serie_calculator(int term){
        this.term = term;
    }

    @Override
    public Double call() throws Exception {
        long id = Thread.currentThread().getId();
        System.out.println("[Thread #"+ id + "] ");

        return term_value(term);
    }

    // Função que retorna o valor do termo (1/n!)
    private double term_value(int term){
        double result =  1 / factorial(term);
        return result;
    }

   //Fazendo o cálculo fatorial do denominado do respectivo termo que é passado como parâmetro(number)
    private double factorial(int number){
        if(number <= 1){
            return 1;
        }else{
            return number * factorial(number-1);
        }

    }
}

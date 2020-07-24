package infinite_series_euler;

public class Task implements Runnable{

    private String id;

    public Task(String id){
        this.id = id;
    }

    public void run()
    {
        System.out.println("Thread" + id + "is executing");
    }

}

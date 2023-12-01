package parsers;

import org.example.ParserWorker;

public class Completed implements ParserWorker.OnCompleted {
    @Override
    public void onCompleted(Object sender){
        System.out.println("Загрузка завершена");
    }
}

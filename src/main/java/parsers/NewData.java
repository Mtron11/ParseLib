package parsers;

import org.example.ParserWorker;

public class NewData<T> implements ParserWorker.OnNewDataHandler<T> {
    @Override
    public void onNewData(Object sender, T args) {
        System.out.println(args);
    }
}
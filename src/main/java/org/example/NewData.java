package org.example;


public class NewData<T> implements ParserWorker.OnNewDataHandler<T>{
    public void onNewData(Object sender, T args) {
            System.out.println(args);
    }
}

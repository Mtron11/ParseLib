package org.example;

public class Completed implements ParserWorker.OnCompleted {
    @Override
    public void onCompleted(Object sender){
        System.out.println("Загрузка завершена");
    }
}

package fr.kizyow.watcher;

public class Application {

    public static void main(String[] args){

        if(args.length <= 0){
            throw new IllegalArgumentException("The bot token wasn't found on launch arguments");

        }

        String token = args[0];
        Watcher watcher = new Watcher(token);
        watcher.registerLogger();
        watcher.registerCommands();
        watcher.cache();
        watcher.activity("vous surveiller !");
        watcher.build();

    }

}

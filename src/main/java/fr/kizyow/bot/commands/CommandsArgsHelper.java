package fr.kizyow.bot.commands;

public class CommandsArgsHelper {
    public static boolean argsSetCorrectly(String[] args, int argsLength){
        return args.length < argsLength;
    }

    CommandsArgsHelper(){

    }
}

package fr.kizyow.watcher.loggers;

import java.util.*;
import java.util.regex.Pattern;

public class LoggerManager {

    private final Map<Long, LoggerObject> loggerMap = new HashMap<>();
    private final List<Long> ignoreChannels = new ArrayList<>();
    private final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    public void log(long id, LoggerObject loggerObject){
        loggerMap.put(id, loggerObject);

    }

    public void remove(long id){
        loggerMap.remove(id);

    }

    public Optional<LoggerObject> retrieveData(long id){
        if(loggerMap.containsKey(id)){
            LoggerObject loggerObject = loggerMap.get(id);
            return Optional.of(loggerObject);

        }

        return Optional.empty();

    }

    public void ignoreChannel(long id){
        ignoreChannels.add(id);

    }

    public void verbChannel(long id){
        ignoreChannels.remove(id);

    }

    public boolean isIgnored(long id){
        return ignoreChannels.stream().anyMatch(channelID -> channelID == id);

    }

    public boolean isURL(String url){
        String[] urlSplit = url.split(" ");
        for(String eachURL : urlSplit){
            if(Pattern.compile(URL_REGEX).matcher(eachURL).find()){
                return true;

            }

        }

        return false;

    }

}

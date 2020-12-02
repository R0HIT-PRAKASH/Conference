package event;


import java.time.LocalDateTime;
import java.util.List;

public class EventFactory {

    public Event getEvent(String eventType, String name, LocalDateTime time, Integer duration, int roomNumber, int capacity,
                          int requiredComputers, boolean requiredProjector, int requiredChairs, int requiredTables,
                          List<String> creators, boolean vipEvent, String speaker, List<String> speakers, String tag) {

        if(eventType.equalsIgnoreCase("Party")){
            return new Party( name,  time,  duration,  roomNumber,  capacity, requiredComputers,  requiredProjector,
                    requiredChairs, requiredTables, creators,  vipEvent, tag);
        }

        else if(eventType.equalsIgnoreCase("Talk")){
            return new Talk(name,  time,  duration,  roomNumber,  capacity, requiredComputers,  requiredProjector,
                    requiredChairs, requiredTables, creators, vipEvent, speaker, tag);
        }

        else if(eventType.equalsIgnoreCase("Panel")){
            return new Panel(name,  time,  duration,  roomNumber,  capacity, requiredComputers,  requiredProjector,
                    requiredChairs, requiredTables, creators, vipEvent, speakers, tag);
        }
        else{
            return null;
        }


    }
}
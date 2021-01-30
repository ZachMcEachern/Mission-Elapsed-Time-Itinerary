package edu.fullerton.ecs.metinerary;

import android.app.Application;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * Created by zachm on 11/10/2017.
 */

public class ItineraryApp extends Application {
    private String eventDescription;
    private Long eventId;
    final static String TAG = "MyActivity";
    private Boolean editMode;
    private int resultCodeIterator = 0;
    List<HashMap<String, String>> hashevents;
    //SimpleAdapter storedadapter;

    Calendar start;
    private List<Event> events;

    @Override
    public void onCreate() {
        super.onCreate();
        events = new ArrayList<Event>();

        //Event e = new Event("My cool event", 15, 13);
        //events.add(e);
    }

    public List<Event> getEvents() { return events; }

    public void storeCalandar(Calendar current)
    {
        start = current;

    }

    public Calendar getCalendar()
    {
        return start;
    }

    public void updateEvent(int position, String description, int min, int hour)
    {
        events.get(position).setEventDescription(description);
        Log.d(TAG,"This is Min Description "  +  events.get(position).getEventDescription());

        events.get(position).setHour(hour);
        Log.d(TAG, "This is Min Hour "  + String.valueOf(events.get(position).getHour()));

        events.get(position).setMin(min);
        Log.d(TAG, "This is Min " + String.valueOf(events.get(position).getMin()));
    }


    public int getCode()
    {
        return resultCodeIterator;
    }

    public void iterateCode()
    {
        resultCodeIterator++;
    }

    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void addToEvents(Event e)
    {
        events.add(e);
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }


    public Event alarmset(int position)
    {
        return events.get(position);
    }
    public void listRemove(int position)
    {
        events.remove(position);
    }

}



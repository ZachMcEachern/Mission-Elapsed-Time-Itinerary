package edu.fullerton.ecs.metinerary;

/**
 * Created by zachm on 11/10/2017.
 */

public class Event
{
    private String eventDescription;
    private long taskId;
    private long listId;
    private int hour;
    private int minute;


    public Event(String eventDescription, int minute, int hour)
    {       this.eventDescription = eventDescription;
        this.minute = minute;
        this.hour = hour;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public int getMin()
    {
        return minute;
    }
    public void setMin(int min)
    {
        this.minute = min;
    }

    public void setHour(int hour)
    {
        this.hour = hour;
    }

    public int getHour()
    {
        return hour;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}



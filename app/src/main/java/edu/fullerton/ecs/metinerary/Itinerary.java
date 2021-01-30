package edu.fullerton.ecs.metinerary;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Itinerary extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static ListView itineraryListView;
    private ItineraryApp app;

    final static String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
        Intent intent = getIntent();

        app = (ItineraryApp) getApplication();

        itineraryListView = (ListView) findViewById(R.id.itineraryListView);
        itineraryListView.setOnItemClickListener(this);
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_itinerary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_new_event:
                Toast.makeText(this, "New Event", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ItineraryItem.class);
                startActivityForResult(intent, app.getCode());
                return true;
            case R.id.menu_set_alarms:
                Log.d(TAG, "Entered set alarm case");
                Toast.makeText(this, "Set Alarms", Toast.LENGTH_SHORT).show();
                Calendar current =  Calendar.getInstance();
                app.storeCalandar(current);
                Event start = app.alarmset(0);

                current.add(Calendar.HOUR, start.getHour());
                current.add(Calendar.MINUTE, start.getMin());

                //Log.d(TAG, "THis is Hour:"  + String.valueOf(current.get(Calendar.HOUR)));
                // Log.d(TAG, "This is Minute: " +  String.valueOf(current.get(Calendar.MINUTE)));
                //Log.d(TAG, "This is AM or PM " + String.valueOf(current.get(Calendar.AM_PM)));
                // Log.d(TAG, "This is the event hour " + String.valueOf(start.getHour()));
                //Log.d(TAG, "This is the event minute " + String.valueOf(start.getMin()));


                Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                alarm.putExtra(AlarmClock.EXTRA_HOUR, current.get(Calendar.HOUR_OF_DAY));
                alarm.putExtra(AlarmClock.EXTRA_MINUTES, current.get(Calendar.MINUTE));
                alarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                alarm.putExtra(AlarmClock.EXTRA_VIBRATE, true);
                //alarm.putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE, AlarmClock.ALARM_SEARCH_MODE_TIME);
                startActivityForResult(alarm, app.getCode());



                return true;
            case  R.id.menu_cancel_alarms:
                Toast.makeText(this, "Cancel Alarms", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<Event> list = new ArrayList<Event>();

        for (Event event :app.getEvents())
        {
            list.add(event);
        }

        Event e = list.get(position);

        Intent intent = new Intent(this, ItineraryItem.class);

        intent.putExtra("Description", e.getEventDescription());


        intent.putExtra("Min", e.getMin());
        intent.putExtra("Hour", e.getHour());
        intent.putExtra("position", position);

        intent.putExtra("Boolean", true);

        startActivityForResult(intent, app.getCode());
    }

    public void updateList() {
        List<HashMap<String, String>> arrayOfEvents = new ArrayList<HashMap<String, String>>();

        for (Event event : app.getEvents()) {
            HashMap<String, String> item = new HashMap<>();
            item.put("description", event.getEventDescription());
            StringBuilder time = new StringBuilder(String.valueOf(event.getHour() + ":" +String.valueOf(event.getMin() )));


            item.put("Time", time.toString());
            arrayOfEvents.add(item);
        }

        String[] from = {"description", "Time"};
        int[] to = {R.id.eventTitleTextView, R.id.MET};

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                arrayOfEvents,
                R.layout.listview_item,
                from,
                to
        );
        itineraryListView.setAdapter(adapter);

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Log.d(TAG, String.valueOf(data.getIntExtra("Position", 0)));
        Log.d(TAG, "ALARM ACTIVITY WENT OFF 0");

        if(requestCode == app.getCode())
        {
            if(data == null)
            {
                app.iterateCode();
                if (app.alarmset(app.getCode()) != null) {
                    Event previous = app.alarmset(app.getCode() - 1);
                    Event next = app.alarmset(app.getCode());
                    Calendar newTime = app.getCalendar();


                    int hour = next.getHour() - previous.getHour();
                    int min = next.getMin() - previous.getMin();

                    newTime.add(Calendar.HOUR, hour);
                    Log.d(TAG, "This is next Hour" + String.valueOf(next.getHour()));
                    Log.d(TAG, "This is next Minute" + String.valueOf(next.getMin()));
                    newTime.add(Calendar.MINUTE, min);


                    Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                    alarm.putExtra(AlarmClock.EXTRA_HOUR, newTime.get(Calendar.HOUR_OF_DAY));
                    alarm.putExtra(AlarmClock.EXTRA_MINUTES, newTime.get(Calendar.MINUTE));
                    alarm.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                    alarm.putExtra(AlarmClock.EXTRA_VIBRATE, true);

                    startActivityForResult(alarm, app.getCode());
                }



            }

            if(resultCode == RESULT_OK)
            {
                Log.d(TAG, "ALARM ACTIVITY WENT OFF 2");

                if(data != null)
                {
                    Log.d(TAG, "ALARM ACTIVITY WENT OFF 3");

                    int bool = data.getIntExtra("Bool", 0);
                    if(bool == 0)
                    {
                        Event e = new Event(data.getStringExtra("Description"), data.getIntExtra("Minute", 0), data.getIntExtra("Hour", 0));
                        app.addToEvents(e);
                        updateList();
                        Log.d(TAG, "This is position" + String.valueOf(data.getIntExtra("Position", 0)));
                        Log.d(TAG, "This is bool" + String.valueOf(data.getIntExtra("Bool", 0)));
                        Log.d(TAG, "ALARM ACTIVITY WENT OFF 5");


                    }
                    else if (bool == 1)
                    {
                        Log.d(TAG, "ALARM ACTIVITY WENT OFF 6");


                        Log.d(TAG, "Entered");

                        app.updateEvent(data.getIntExtra("Position", 0),data.getStringExtra("Description"), data.getIntExtra("Minute", 0), data.getIntExtra("Hour", 0));
                        updateList();
                    }
                    else if (bool == 2)
                    {
                        Log.d(TAG, "ALARM ACTIVITY WENT OFF 7");

                        updateList();
                    }
                    else
                    {

                    }

                }
            }


        }
    }
}



package edu.fullerton.ecs.metinerary;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static edu.fullerton.ecs.metinerary.R.id.itemDescriptionEditText;
import static edu.fullerton.ecs.metinerary.R.id.itineraryListView;


/**
 * Created by zachm on 11/6/2017.
 */

public class ItineraryItem extends AppCompatActivity implements View.OnClickListener {
    private ItineraryApp app;
    private int bool;
    private Button saveEventButton;
    private Button discardChangesButton;
    final static String TAG = "MyActivity";
    private Button deleteEventButton;
    ListView listview;
    private EditText itemDescriptionEditText;
    int position;
    private TimePicker timeTimePicker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_item);

        app = (ItineraryApp) getApplication();

        saveEventButton = (Button) findViewById(R.id.saveEventButton);
        discardChangesButton = (Button) findViewById(R.id.discardChangesButton);
        deleteEventButton = (Button) findViewById(R.id.deleteEventButton);

        itemDescriptionEditText = (EditText) findViewById(R.id.itemDescriptionEditText);

        timeTimePicker = (TimePicker) findViewById(R.id.timeTimePicker);
        timeTimePicker.setIs24HourView(Boolean.TRUE);
        bool = 0;
        saveEventButton.setOnClickListener(this);
        discardChangesButton.setOnClickListener(this);
        deleteEventButton.setOnClickListener(this);
        listview = (ListView)findViewById(R.id.itineraryListView);

        Intent check = new Intent();
        check = getIntent();

        if(check != null  && check.getBooleanExtra("Boolean", false) != false)
        {
            itemDescriptionEditText.setText(check.getStringExtra("Description"));
            timeTimePicker.setHour(check.getIntExtra("Hour", 0));
            timeTimePicker.setMinute(check.getIntExtra("Min", 0));
            position = check.getIntExtra("position", 0);

            app.updateEvent(position, check.getStringExtra("Description"), (check.getIntExtra("Hour", 0)), check.getIntExtra("Min", 0));
            bool = 1;
        }
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.saveEventButton:
                Toast.makeText(this, "Event has been saved", Toast.LENGTH_SHORT).show();

                intent.putExtra("Description",  itemDescriptionEditText.getText().toString());
                intent.putExtra("Minute",timeTimePicker.getMinute());
                intent.putExtra("Hour", timeTimePicker.getHour());
                intent.putExtra("Bool", bool);
                intent.putExtra("Position", position);
                setResult(RESULT_OK, intent);
                finish();

                break;
            case R.id.discardChangesButton:
                Toast.makeText(this, "Changes have been discarded", Toast.LENGTH_SHORT).show();
                intent.putExtra("Useless", ";jldf");
                setResult(RESULT_CANCELED, intent);
                finish();


                break;
            case R.id.deleteEventButton:
                Toast.makeText(this, "Event was deleted", Toast.LENGTH_SHORT).show();
                bool = 2;
                app.listRemove(position);
                intent.putExtra("Bool", bool);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}



package org.tensorflow.lite.examples.detection.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.model.MainAdapter;
import org.tensorflow.lite.examples.detection.model.MainModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CheckInActivity extends AppCompatActivity {


//    public static String formattedTimeCheckIn="";
//
//    public void setFormattedTimeCheckIn(String formattedTimeCheckIn) {
//        this.formattedTimeCheckIn = formattedTimeCheckIn;
//    }
//
//    public String getFormattedTimeCheckIn() {
//        return formattedTimeCheckIn;
//    }

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        recyclerView=(RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CheckIn"), MainModel.class)
                        .build();
        mainAdapter=new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

//        Calendar currentTime = Calendar.getInstance();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
//        String s = dateFormat.format(currentTime.getTime());
//        setFormattedTimeCheckIn(s);
//        Log.d("Current Time", getFormattedTimeCheckIn());

    }


    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();

    }
}
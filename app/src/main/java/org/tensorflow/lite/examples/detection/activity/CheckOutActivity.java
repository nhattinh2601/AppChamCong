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

public class CheckOutActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    MainAdapter mainAdapter;
//
//
//    CheckInActivity checkin= new CheckInActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        recyclerView=(RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CheckOut"), MainModel.class)
                        .build();
        mainAdapter=new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);
//
//        String s = checkin.getFormattedTimeCheckIn();
//        Calendar currentTime = Calendar.getInstance();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
//        String formattedTime = dateFormat.format(currentTime.getTime());
//
////        Log.d("Current Time", formattedTime);
//        Log.d("CheckInOut",s);
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
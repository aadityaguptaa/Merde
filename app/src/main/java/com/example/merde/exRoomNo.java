package com.example.merde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class exRoomNo extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText editText;
    TextView textView;
    Button button;

    List<String> USN = new ArrayList<String>();
    List<String> RoomNo = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_room_no);

        button = findViewById(R.id.USN_Check_Button);
        editText = findViewById(R.id.USN_Field);
        textView = findViewById(R.id.Room_No_TextView);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("0");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
                    USN.add(item_snapshot.child("USN").getValue().toString());
                    RoomNo.add(item_snapshot.child("Room No").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editText.getText().toString().isEmpty()){
                    button.setEnabled(false);
                }else{
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void onclick(View view){
        String usn = editText.getText().toString();
        usn = usn.toUpperCase();


        for(int i = 0; i < USN.size(); i++){
            if(usn.equals(USN.get(i))){
                textView.setText(RoomNo.get(i));
                return;
            }
        }
        textView.setText("No such USN in our Database, Please check it again ");
    }
}
package com.example.finalprojectlnt.ui.counter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalprojectlnt.R;
import com.example.finalprojectlnt.ui.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CounterFragment extends Fragment {

    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference reference;
    private String userId;
    int count;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        View view = inflater.inflate(R.layout.fragment_counter, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprof = snapshot.getValue(User.class);

                if(userprof != null){
                    String fullname = userprof.name;
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Hello, "+fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SharedPreferences sref = getContext().getSharedPreferences("mref", Context.MODE_PRIVATE);
        count = sref.getInt("cnum", 0);

        TextView countnum = (TextView) view.findViewById(R.id.numcounter);
        Button plusnum = (Button) view.findViewById(R.id.plusbtn);
        Button minusnum = (Button) view.findViewById(R.id.minusbtn);
        Button resetnum = (Button) view.findViewById(R.id.resetbtn);

        countnum.setText(""+ count);

        plusnum.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                count++;
                countnum.setText(""+count);
                SharedPreferences countref = getContext().getSharedPreferences("mref", Context.MODE_PRIVATE);
                SharedPreferences.Editor mycount = countref.edit();
                mycount.putInt("cnum", Integer.parseInt(countnum.getText().toString()));
                mycount.commit();
            }
        });

        minusnum.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                count--;
                if(count<0){
                    count=0;
                }
                countnum.setText(""+count);
                SharedPreferences countref = getContext().getSharedPreferences("mref", Context.MODE_PRIVATE);
                SharedPreferences.Editor mycount = countref.edit();
                mycount.putInt("cnum", Integer.parseInt(countnum.getText().toString()));
                mycount.commit();
            }
        });

        resetnum.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                count=0;
                countnum.setText("0");
                SharedPreferences countref = getContext().getSharedPreferences("mref", Context.MODE_PRIVATE);
                SharedPreferences.Editor mycount = countref.edit();
                mycount.putInt("cnum", Integer.parseInt(countnum.getText().toString()));
                mycount.commit();
            }
        });

        return view;
    }

}

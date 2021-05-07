package com.example.finalprojectlnt.ui.volume;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finalprojectlnt.R;
import com.example.finalprojectlnt.ui.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class VolumeFragment extends Fragment {

    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference reference;
    private String userId;

    float result;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        View view = inflater.inflate(R.layout.fragment_volume, container, false);

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

        Button balok = (Button) view.findViewById(R.id.btnbalok);
        Button piramid = (Button) view.findViewById(R.id.btnpiramid);
        Button tabung = (Button) view.findViewById(R.id.btntabung);
        TextView volume = (TextView) view.findViewById(R.id.labelvolume);
        TextView volres = (TextView) view.findViewById(R.id.volcalc);
        TextView hpb = (TextView) view.findViewById(R.id.hintpanjangbalok);
        TextView hlb = (TextView) view.findViewById(R.id.hintlebarbalok);
        TextView htb = (TextView) view.findViewById(R.id.hinttinggibalok);
        TextView hap = (TextView) view.findViewById(R.id.hintalaspiramid);
        TextView htp = (TextView) view.findViewById(R.id.hinttinggipiramid);
        TextView hjt = (TextView) view.findViewById(R.id.hintjaritabung);
        TextView htt = (TextView) view.findViewById(R.id.hinttinggitabung);
        EditText ipb = (EditText) view.findViewById(R.id.inputpanjangbalok);
        EditText ilb = (EditText) view.findViewById(R.id.inputlebarbalok);
        EditText itb = (EditText) view.findViewById(R.id.inputtinggibalok);
        EditText iap = (EditText) view.findViewById(R.id.inputalaspiramid);
        EditText itp = (EditText) view.findViewById(R.id.inputtinggipiramid);
        EditText ijt = (EditText) view.findViewById(R.id.inputjaritabung);
        EditText itt = (EditText) view.findViewById(R.id.inputtinggitabung);
        Button hitbalok = (Button) view.findViewById(R.id.Hitungbalok);
        Button hitpiramid = (Button) view.findViewById(R.id.Hitungpiramid);
        Button hittabung = (Button) view.findViewById(R.id.Hitungtabung);

        balok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpb.setVisibility(View.VISIBLE);
                hlb.setVisibility(View.VISIBLE);
                htb.setVisibility(View.VISIBLE);
                hap.setVisibility(View.INVISIBLE);
                htp.setVisibility(View.INVISIBLE);
                hjt.setVisibility(View.INVISIBLE);
                htt.setVisibility(View.INVISIBLE);
                ipb.setVisibility(View.VISIBLE);
                ilb.setVisibility(View.VISIBLE);
                itb.setVisibility(View.VISIBLE);
                iap.setVisibility(View.INVISIBLE);
                itp.setVisibility(View.INVISIBLE);
                ijt.setVisibility(View.INVISIBLE);
                itt.setVisibility(View.INVISIBLE);
                hitbalok.setVisibility(View.VISIBLE);
                hitpiramid.setVisibility(View.INVISIBLE);
                hittabung.setVisibility(View.INVISIBLE);
                volres.setText("0");
                volume.setText("Balok");
            }
        });

        piramid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpb.setVisibility(View.INVISIBLE);
                hlb.setVisibility(View.INVISIBLE);
                htb.setVisibility(View.INVISIBLE);
                hap.setVisibility(View.VISIBLE);
                htp.setVisibility(View.VISIBLE);
                hjt.setVisibility(View.INVISIBLE);
                htt.setVisibility(View.INVISIBLE);
                ipb.setVisibility(View.INVISIBLE);
                ilb.setVisibility(View.INVISIBLE);
                itb.setVisibility(View.INVISIBLE);
                iap.setVisibility(View.VISIBLE);
                itp.setVisibility(View.VISIBLE);
                ijt.setVisibility(View.INVISIBLE);
                itt.setVisibility(View.INVISIBLE);
                hitbalok.setVisibility(View.INVISIBLE);
                hitpiramid.setVisibility(View.VISIBLE);
                hittabung.setVisibility(View.INVISIBLE);
                volres.setText("0");
                volume.setText("Piramid");
            }
        });

        tabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpb.setVisibility(View.INVISIBLE);
                hlb.setVisibility(View.INVISIBLE);
                htb.setVisibility(View.INVISIBLE);
                hap.setVisibility(View.INVISIBLE);
                htp.setVisibility(View.INVISIBLE);
                hjt.setVisibility(View.VISIBLE);
                htt.setVisibility(View.VISIBLE);
                ipb.setVisibility(View.INVISIBLE);
                ilb.setVisibility(View.INVISIBLE);
                itb.setVisibility(View.INVISIBLE);
                iap.setVisibility(View.INVISIBLE);
                itp.setVisibility(View.INVISIBLE);
                ijt.setVisibility(View.VISIBLE);
                itt.setVisibility(View.VISIBLE);
                hitbalok.setVisibility(View.INVISIBLE);
                hitpiramid.setVisibility(View.INVISIBLE);
                hittabung.setVisibility(View.VISIBLE);
                volres.setText("0");
                volume.setText("Tabung");
            }
        });

        hitbalok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(ipb.getText().toString())){
                    ipb.setError("Cannot Empty");
                    ipb.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(ilb.getText().toString())){
                    ilb.setError("Cannot Empty");
                    ilb.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(itb.getText().toString())){
                    itb.setError("Cannot Empty");
                    itb.requestFocus();
                    return;
                }
                double pb = Double.parseDouble(ipb.getText().toString());
                double lb = Double.parseDouble(ilb.getText().toString());
                double tb = Double.parseDouble(itb.getText().toString());
                result = (float) (pb * lb * tb);
                volres.setText("" + new DecimalFormat("##.##").format(result)+ " cm³");
            }
        });

        hitpiramid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(iap.getText().toString())){
                    iap.setError("Cannot Empty");
                    iap.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(itp.getText().toString())){
                    itp.setError("Cannot Empty");
                    itp.requestFocus();
                    return;
                }
                double ap = Double.parseDouble(iap.getText().toString());
                double tp = Double.parseDouble(itp.getText().toString());
                result = (float) ((ap * ap *tp) / 3);
                volres.setText("" + new DecimalFormat("##.##").format(result)+ " cm³");
            }
        });

        hittabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(ijt.getText().toString())){
                    ijt.setError("Cannot Empty");
                    ijt.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(itt.getText().toString())){
                    itt.setError("Cannot Empty");
                    itt.requestFocus();
                    return;
                }
                double jt = Double.parseDouble(ijt.getText().toString());
                double tb = Double.parseDouble(itt.getText().toString());
                result = (float) (3.14 * jt * jt * tb);
                volres.setText("" + new DecimalFormat("##.##").format(result)+ " cm³");
            }
        });

        return view;
    }
}
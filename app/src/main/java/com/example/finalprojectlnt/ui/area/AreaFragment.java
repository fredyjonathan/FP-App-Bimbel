package com.example.finalprojectlnt.ui.area;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class AreaFragment extends Fragment {

    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference reference;
    private String userId;

    double result;
    String dia = new String("Diameter");
    String r = new String("Jari-jari");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        View view = inflater.inflate(R.layout.fragment_area, container, false);

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

        Spinner spinner = (Spinner) view.findViewById(R.id.jaridiameter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.jenis, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        TextView area = (TextView) view.findViewById(R.id.labelarea);
        TextView calcres = (TextView) view.findViewById(R.id.areacalc);
        Button persegi = (Button) view.findViewById(R.id.btnpersegi);
        Button segitiga = (Button) view.findViewById(R.id.btnsegitiga);
        Button lingkaran = (Button) view.findViewById(R.id.btnlingkaran);
        TextView hpersegi = (TextView) view.findViewById(R.id.hintsisipersegi);
        TextView halassegitiga = (TextView) view.findViewById(R.id.hintalassegitiga);
        TextView htinggisegitiga = (TextView) view.findViewById(R.id.hinttinggisegitiga);
        TextView hpilihjd = (TextView) view.findViewById(R.id.pilihjd);
        TextView hlingkaran = (TextView) view.findViewById(R.id.hintlingkaran);
        EditText ipersegi = (EditText) view.findViewById(R.id.inputpersegi);
        EditText ialassegitiga = (EditText) view.findViewById(R.id.inputalassegitiga);
        EditText itinggisegitiga = (EditText) view.findViewById(R.id.inputtinggisegitiga);
        EditText ilingkaran = (EditText) view.findViewById(R.id.inputlingkaran);
        Button hitpersegi = (Button) view.findViewById(R.id.Hitungpersegi);
        Button hitsegitiga = (Button) view.findViewById(R.id.Hitungsegitiga);
        Button hitlingkaran = (Button) view.findViewById(R.id.Hitunglingkaran);
        Spinner jd = (Spinner) view.findViewById(R.id.jaridiameter);

        persegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpersegi.setVisibility(View.VISIBLE);
                ipersegi.setVisibility(View.VISIBLE);
                hitpersegi.setVisibility(View.VISIBLE);
                halassegitiga.setVisibility(View.INVISIBLE);
                htinggisegitiga.setVisibility(View.INVISIBLE);
                hpilihjd.setVisibility(View.INVISIBLE);
                hlingkaran.setVisibility(View.INVISIBLE);
                ialassegitiga.setVisibility(View.INVISIBLE);
                itinggisegitiga.setVisibility(View.INVISIBLE);
                ilingkaran.setVisibility(View.INVISIBLE);
                hitsegitiga.setVisibility(View.INVISIBLE);
                hitlingkaran.setVisibility(View.INVISIBLE);
                jd.setVisibility(View.INVISIBLE);
                calcres.setText("0");
                area.setText("Persegi");
            }
        });

        segitiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpersegi.setVisibility(View.INVISIBLE);
                ipersegi.setVisibility(View.INVISIBLE);
                hitpersegi.setVisibility(View.INVISIBLE);
                halassegitiga.setVisibility(View.VISIBLE);
                htinggisegitiga.setVisibility(View.VISIBLE);
                hpilihjd.setVisibility(View.INVISIBLE);
                hlingkaran.setVisibility(View.INVISIBLE);
                ialassegitiga.setVisibility(View.VISIBLE);
                itinggisegitiga.setVisibility(View.VISIBLE);
                ilingkaran.setVisibility(View.INVISIBLE);
                hitsegitiga.setVisibility(View.VISIBLE);
                hitlingkaran.setVisibility(View.INVISIBLE);
                jd.setVisibility(View.INVISIBLE);
                calcres.setText("0");
                area.setText("Segitiga");
            }
        });

        lingkaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpersegi.setVisibility(View.INVISIBLE);
                ipersegi.setVisibility(View.INVISIBLE);
                hitpersegi.setVisibility(View.INVISIBLE);
                halassegitiga.setVisibility(View.INVISIBLE);
                htinggisegitiga.setVisibility(View.INVISIBLE);
                hpilihjd.setVisibility(View.VISIBLE);
                hlingkaran.setVisibility(View.VISIBLE);
                ialassegitiga.setVisibility(View.INVISIBLE);
                itinggisegitiga.setVisibility(View.INVISIBLE);
                ilingkaran.setVisibility(View.VISIBLE);
                hitsegitiga.setVisibility(View.INVISIBLE);
                hitlingkaran.setVisibility(View.VISIBLE);
                jd.setVisibility(View.VISIBLE);
                calcres.setText("0");
                area.setText("Lingkaran");
            }
        });

        hitpersegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(ipersegi.getText().toString())){
                    ipersegi.setError("Cannot Empty");
                    ipersegi.requestFocus();
                    return;
                }
                double persegisisi = Double.parseDouble(ipersegi.getText().toString());
                result = persegisisi * persegisisi;
                calcres.setText("" + new DecimalFormat("##.#").format(result) + " cm²");
            }
        });

        hitsegitiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(ialassegitiga.getText().toString())){
                    ialassegitiga.setError("Cannot Empty");
                    ialassegitiga.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(itinggisegitiga.getText().toString())){
                    itinggisegitiga.setError("Cannot Empty");
                    itinggisegitiga.requestFocus();
                    return;
                }
                double alas = Double.parseDouble(ialassegitiga.getText().toString());
                double tinggi = Double.parseDouble(itinggisegitiga.getText().toString());
                result = (alas * tinggi * 0.5);
                calcres.setText("" + new DecimalFormat("##.#").format(result)+ " cm²");
            }
        });

        hitlingkaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jd.getSelectedItem().toString().equals(dia)){
                    if(TextUtils.isEmpty(ilingkaran.getText().toString())){
                        ilingkaran.setError("Cannot Empty");
                        ilingkaran.requestFocus();
                        return;
                    }
                    double diameter = Double.parseDouble(ilingkaran.getText().toString());
                    result = (0.25 * 3.14 * diameter * diameter);
                    Toast.makeText(getActivity(),"Diameter!",Toast.LENGTH_SHORT).show();
                    calcres.setText("" + new DecimalFormat("##.##").format(result));
                }
                if(jd.getSelectedItem().toString().equals(r)){
                    if(TextUtils.isEmpty(ilingkaran.getText().toString())){
                        ilingkaran.setError("Cannot Empty");
                        ilingkaran.requestFocus();
                        return;
                    }
                    double jari = Double.parseDouble(ilingkaran.getText().toString());
                    Toast.makeText(getActivity(),"Jari-jari",Toast.LENGTH_SHORT).show();
                    result = (3.14 * jari * jari);
                    calcres.setText("" + new DecimalFormat("##.##").format(result)+ " cm²");
                }
            }
        });

        return view;
    }
}


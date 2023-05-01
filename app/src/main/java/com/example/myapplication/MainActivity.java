package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(v -> {
            EditText VWindField = findViewById(R.id.VWind);
            EditText VPlaneField = findViewById(R.id.VPlane);
            EditText WindAzimutField = findViewById(R.id.WindAzimut);
            EditText TargetAzimutField = findViewById(R.id.TargetAzimut);
            EditText TargetDistanceField = findViewById(R.id.TargetDistance);
            int VWind = Integer.parseInt(VWindField.getText().toString());
            int VPlane = Integer.parseInt(VPlaneField.getText().toString());
            int WindAzimut = Integer.parseInt(WindAzimutField.getText().toString());
            int TargetAzimut = Integer.parseInt(TargetAzimutField.getText().toString());
            int TargetDistance = Integer.parseInt(TargetDistanceField.getText().toString());
            String result = NewCoordinates(TargetDistance, VPlane, VWind, WindAzimut, TargetAzimut);
            String[] results = result.split(";");
            TextView ResultTime = findViewById(R.id.ResultTime);
            ResultTime.setText("Час у польоті до цілі: "+results[1]+" секунд");
            TextView ResultDistance = findViewById(R.id.ResultDistance);
            ResultDistance.setText("Відстань до цілі: "+results[0]+" метрів");
            TextView ResultAzimut = findViewById(R.id.ResultAzimut);
            ResultAzimut.setText("Азімут цілі: "+results[2]+" градусів");
        });
    }

    public static String NewCoordinates(int TargetDistance, int VPlane, int VWind, int WindAzimut, int TargetAzimut){
        double alfa = Math.toRadians(TargetAzimut);
        double beta = Math.toRadians(WindAzimut);
        double T = ((double) TargetDistance) / VPlane;
        double TargetX = TargetDistance * Math.cos(alfa);
        double TargetY = TargetDistance * Math.sin(alfa);
        double WindDistance = T * VWind;
        double WindDistanceX = WindDistance * Math.cos(beta);
        double WindDistanceY = WindDistance * Math.sin(beta);
        double CorrectedTargetX = TargetX - WindDistanceX;
        double CorrectedTargetY = TargetY - WindDistanceY;
        //New Distance
        double CorrectedDistance = Math.sqrt(CorrectedTargetX*CorrectedTargetX+CorrectedTargetY*CorrectedTargetY);
        //New Time
        int newT =(int) CorrectedDistance/VPlane;
        double CorrectedTargetAzimut = Math.atan(CorrectedTargetY/CorrectedTargetX);
        //New Azimut
        int CorrectedTargetAzimutInt = (int) Math.toDegrees(CorrectedTargetAzimut);

        if (CorrectedTargetX<0) CorrectedTargetAzimutInt = 180 + CorrectedTargetAzimutInt;
        if (CorrectedTargetX>0 && CorrectedTargetY<0) CorrectedTargetAzimutInt = 360 + CorrectedTargetAzimutInt;

        return ""+(int)CorrectedDistance+";"+newT+";"+CorrectedTargetAzimutInt;
    }

}
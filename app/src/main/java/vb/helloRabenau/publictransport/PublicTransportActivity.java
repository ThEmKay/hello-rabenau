package vb.helloRabenau.publictransport;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vb.helloRabenau.R;

public class PublicTransportActivity extends ActionBarActivity {

    TextView Linie520;
    TextView Linie55;
    TextView LinieMR86;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_transport);

        Linie520 = (TextView) findViewById(R.id.Linie520);
        Linie55 = (TextView) findViewById(R.id.Linie55);
        LinieMR86 = (TextView) findViewById(R.id.LinieMR86);

        Linie520.setOnClickListener (new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Linie520.setText("Hier wird noch gearbeitet");
            }
        });

        Linie55.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Linie55.setText("Hier wird noch gearbeitet");
            }
        });

        LinieMR86.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LinieMR86.setText("Hier wird noch gearbeitet");
            }
        });
    }




}

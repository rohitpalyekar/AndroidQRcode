package demo.androidqrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity {
    EditText name,desgination,biolink;
    Button qrscan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.name);
        desgination=findViewById(R.id.designation);
        biolink=findViewById(R.id.biolink);
        qrscan=findViewById(R.id.scanqr);

        qrscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, BarcodeScanner.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            jasondecoder(extras.getString("result"));
        }
    }

    public void jasondecoder(String rawResult){
        if(!rawResult.isEmpty()) {
            try {
                String Decodedata = URLDecoder.decode(String.valueOf(rawResult), "utf-8");
                System.out.println("Decodedata:" + Decodedata);
                JSONObject obj = new JSONObject(Decodedata);
                JSONObject biols = obj.getJSONObject("Bio Links");

                name.setText(obj.getString("Name"));
                desgination.setText(obj.getString("Destination"));
                biolink.setText(biols.getString("Git"));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

}

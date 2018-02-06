package demo.androidqrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class BarcodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView  zxinscan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!runtime_permission()){
            enablesanner();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(zxinscan != null) {
            zxinscan.stopCameraPreview();
        }
    }

    private Boolean runtime_permission() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.
                permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},100);
            return  true;
        }
        return  false;
    }

    private void enablesanner() {
                if(zxinscan == null) {
                    zxinscan = new ZXingScannerView(BarcodeScanner.this);
                    setContentView(zxinscan);
                }
                zxinscan.setResultHandler(BarcodeScanner.this);
                zxinscan.startCamera();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    enablesanner();
                }else{
                    Toast.makeText(BarcodeScanner.this, "Camera Permission Required",
                            Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult== null) {
            Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(BarcodeScanner.this, MainActivity.class);
            i.putExtra("result", rawResult.toString());
            startActivity(i);
            finish();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zxinscan.stopCamera();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        zxinscan.stopCamera();
    }
}

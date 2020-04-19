package br.com.flavio.geradordeapertos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class Leitura extends AppCompatActivity {
    private SurfaceView sv_camera;
    private CameraSource camera;
    private TextView tv_leitura_cabecalho;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitura);
        
        tv_leitura_cabecalho = findViewById(R.id.tv_leitura_cabecalho);
        sv_camera = findViewById(R.id.sv_camera);
        BarcodeDetector detector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.CODE_128).build();
        camera = new CameraSource.Builder(this, detector).setAutoFocusEnabled(true)
                // É necessario reduzir o número de quadros da camera pra que a imagem não fique escura
                .setRequestedFps(10.0f)
                .build();
        
        sv_camera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ContextCompat.checkSelfPermission(Leitura.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        camera.start(holder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_DENIED);
                }
            }
            
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
            
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camera.stop();
            }
        });
        detector.setProcessor(new Detector.Processor<Barcode>() {
            
            @Override
            public void release() {
            }
            
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> barcodes = detections.getDetectedItems();
                
                Intent intent = new Intent(Leitura.this, Formulario.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                
                if (barcodes.size() > 0) {
                    String npLido = barcodes.valueAt(0).displayValue;
                    tv_leitura_cabecalho.setTextColor(Color.GREEN);
                    tv_leitura_cabecalho.setText(R.string.leitura_cabecalho_ok);
                    
                    intent.putExtra("npLido", npLido);
                    startActivity(intent);
                }
            }
        });
    }
    
    @Override
    protected void onResume() {
        // Seta o texto do cabeçalho para o original
        tv_leitura_cabecalho.setText(R.string.leitura_cabecalho);
        tv_leitura_cabecalho.setTextColor(Color.DKGRAY);
        super.onResume();
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PackageManager.PERMISSION_DENIED) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    camera.start(sv_camera.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
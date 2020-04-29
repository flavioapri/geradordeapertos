package br.com.flavio.geradordeapertos;

import android.Manifest;
import android.app.Activity;
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
import androidx.core.app.ActivityCompat;
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
    private boolean primeiroCodigoDetectado = true;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitura);
        tv_leitura_cabecalho = findViewById(R.id.tv_leitura_cabecalho);
        // Inicializa os recursos para criar o leitor de código de barras
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
                } else
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_DENIED);
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
                
                if (barcodes.size() > 0 && primeiroCodigoDetectado) {
                    String np = barcodes.valueAt(0).displayValue;
                    tv_leitura_cabecalho.setTextColor(Color.GREEN);
                    tv_leitura_cabecalho.setText(R.string.leitura_cabecalho_ok);
                    
                    /* Flag muda para false e evita que se repitam varias vezes esse bloco de
                       código não inicializando varias activitys do formulário */
                    primeiroCodigoDetectado = false;
                    Intent intentFormulario = new Intent().putExtra("np", np);
                    setResult(Activity.RESULT_OK, intentFormulario);
                    finish();
                }
            }
        });
        // Verifica se o app tem permissão de acessar a camera e se não tiver pede a permissão
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        PackageManager.PERMISSION_GRANTED);
            }
        }
    }
    
    @Override
    protected void onResume() {
        // Seta o texto do cabeçalho para o formato original
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
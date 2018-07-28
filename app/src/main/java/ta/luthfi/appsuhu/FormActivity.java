package ta.luthfi.appsuhu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    EditText edtNama;
    Button btnMasuk, btnPasien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edtNama = (EditText) findViewById(R.id.edt_nama);

        btnMasuk = (Button) findViewById(R.id.btn_masuk);
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNama.getText().toString().equals("")){
                    Toast.makeText(FormActivity.this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    String nama = edtNama.getText().toString();
                    Intent intent = new Intent(FormActivity.this, MainActivity.class);
                    intent.putExtra("key_nama", nama);
                    startActivity(intent);
                }

            }
        });

        btnPasien = (Button) findViewById(R.id.btn_pasien);
        btnPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormActivity.this, PasienActivity.class);
                startActivity(intent);

            }
        });
    }
}

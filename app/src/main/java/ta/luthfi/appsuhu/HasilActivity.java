package ta.luthfi.appsuhu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HasilActivity extends AppCompatActivity {

    String nama_pasien, suhu_pasien;

    TextView txtNama, txtSuhu, txtHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        getSupportActionBar().setTitle("Hasil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_pasien = getIntent().getStringExtra("key_nama");
        suhu_pasien = getIntent().getStringExtra("key_suhu");

        txtNama = (TextView) findViewById(R.id.txt_nama);
        txtNama.setText(nama_pasien);

        txtSuhu = (TextView) findViewById(R.id.txt_suhu);
        txtSuhu.setText(suhu_pasien);

        txtHasil = (TextView) findViewById(R.id.txt_hasil);
        float hasil = Float.parseFloat(suhu_pasien);
        if(hasil > 38){
            txtHasil.setText("Harap periksa ke dokter, ada indikasi hipertermia.");
            txtHasil.setTextColor(Color.RED);
        }
        else if(hasil < 35){
            txtHasil.setText("Harap periksa ke dokter, ada indikasi hipotermia.");
            txtHasil.setTextColor(Color.RED);
        }
        else{
            txtHasil.setText("Suhu tubuh berada pada kondisi ideal");
            txtHasil.setTextColor(Color.GREEN);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

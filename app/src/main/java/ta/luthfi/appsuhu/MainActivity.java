package ta.luthfi.appsuhu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    List<ItemSuhu> items;
    ArrayList<Entry> entries;
    AdapterSuhu adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    //private ProgressDialog pDialog;

    TextView txtNama;
    TextView txtSuhu;
    TextView txtSelesai;

    CountDownTimer timer;
    long milliLeft;
    String suhu, nama_pasien;

    LineChart chart;

    private static String url_suhu_rata = Config.HOST+"suhu_ratarata.php";
    private static String url_suhu_history = Config.HOST+"lihat_suhu.php";
    private static String url_pasien_tambah = Config.HOST+"input_data_pasien.php";
    private static String url_pasien_post = Config.HOST+"input_data_detail_suhu.php";
    private static String url_grap = Config.HOST+"untuk_graph.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama_pasien = getIntent().getStringExtra("key_nama");

        txtNama = (TextView) findViewById(R.id.txt_nama);
        txtNama.setText(nama_pasien);

        txtSuhu = (TextView) findViewById(R.id.txt_suhu);
        txtSelesai = (TextView) findViewById(R.id.lbl_selesai);

        //panggil RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //set LayoutManager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();

        tugas();

        //set adapter
        adapter = new AdapterSuhu(getApplicationContext(), items);
        recyclerView.setAdapter(adapter);

        timerStart(60 * 1000); //setelah satu menit

        chart = (LineChart) findViewById(R.id.chart);

        entries = new ArrayList<>();


    }

    public class suhu_rata extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_suhu_rata, dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            suhu = c.getString("suhu");


                        }
                    } else {
                        // no data found
                        psn = ob.getString("message");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.notifyDataSetChanged();
            //pDialog.dismiss();
            txtSuhu.setText(suhu);

        }

    }

    public class ambilData extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn, id_pasien;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_suhu_history, dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id_suhu = c.getString("id_suhu");
                            String suhu = c.getString("suhu");
                            String time = c.getString("time");

                            items.add(new ItemSuhu(id_suhu, suhu, time));

                            /////////////////////////////////

                        }
                    } else {
                        // no data found
                        psn = ob.getString("message");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.notifyDataSetChanged();
            //pDialog.dismiss();
        }

    }

    public class postData extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn, nama, suhu;

        public postData(String nama, String suhu){
            this.nama = nama;
            this.suhu = suhu;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("nama", nama);
                detail.put("suhu", suhu);

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_pasien_tambah, dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        psn = ob.getString("pesan");


                    } else {
                        // no data found
                        psn = ob.getString("pesan");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.notifyDataSetChanged();
            //pDialog.dismiss();
        }

    }


    public class grafik extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn, id_pasien;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_grap, dataToSend);

                    //dapatkan respon
                    Log.e("Respon grap", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        entries.clear();
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id_suhu = c.getString("id_suhu");
                            String suhu = c.getString("suhu");
                            String time = c.getString("time");
                            entries.add(new Entry(i, Float.valueOf(suhu)));
                        }
                    } else {
                        // no data found
                        psn = ob.getString("message");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //...
            LineDataSet dataSet = new LineDataSet(entries, "Perubahan suhu");
            dataSet.setColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
            dataSet.setValueTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));

            //****
            // Controlling X axis
            XAxis xAxis = chart.getXAxis();
            // Set the xAxis position to bottom. Default is top
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            //Customizing x axis value
            final String[] rentang_detik = new String[]{"dtk ke 0", "dtk ke 10", "dtk ke 20", "dtk ke 30", "dtk ke 40", "dtk ke 50", "dtk ke 60"};

            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return rentang_detik[(int) value];
                }
            };
            xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
            xAxis.setValueFormatter(formatter);

            //***
            // Controlling right side of y axis
            YAxis yAxisRight = chart.getAxisRight();
            yAxisRight.setEnabled(false);

            //***
            // Controlling left side of y axis
            YAxis yAxisLeft = chart.getAxisLeft();
            yAxisLeft.setGranularity(1f);

            // Setting Data
            LineData data = new LineData(dataSet);
            chart.setData(data);
        /*chart.animateX(2500);*/
            //refresh
            chart.invalidate();
            ///
        }

    }

    private static String hashMapToUrl(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_menu = item.getItemId();
        if(id_menu == R.id.action_refresh){
            tugas();
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void tugas(){
        items.clear();
        new ambilData().execute();
        new suhu_rata().execute();
        //new grafik
        new grafik().execute();
    }

    private void timerStart(long millisUntilFinished){
        timer = new CountDownTimer(millisUntilFinished, 1000) { // adjust the milli seconds here //90000

            public void onTick(long millisUntilFinished) {
                milliLeft = millisUntilFinished;
                tugas();
                txtSelesai.setText("Hasil akan keluar setelah "+String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))+" detik lagi");
            }

            public void onFinish() {

                new postData(nama_pasien, suhu).execute();

                Intent intent = new Intent(MainActivity.this, HasilActivity.class);
                intent.putExtra("key_nama", nama_pasien);
                intent.putExtra("key_suhu", suhu);
                startActivity(intent);
            }
        };
        timer.start();
    }

}

package sg.edu.rp.c346.p09_gettingmylocations;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ListOfRecords extends AppCompatActivity {

    ListView lvRecords;
    TextView tvRecords;
    Button btnRefresh;
    String folderLocation;
    ArrayList<String> recordList = new ArrayList<String>();
    ArrayAdapter<String> aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_records);

        lvRecords = (ListView)findViewById(R.id.lvRecords);
        tvRecords = (TextView)findViewById(R.id.tvRecords);
        btnRefresh = (Button)findViewById(R.id.btnRefresh);

        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Location";
        retrieveData();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordList.clear();
                retrieveData();
            }
        });
    }

    public void retrieveData() {

        File targetFile = new File(folderLocation, "locationData.txt");

        if(targetFile.exists() == true){
            String data = "";
            try {
                FileReader reader = new FileReader(targetFile);
                BufferedReader br = new BufferedReader(reader);
                String line = br.readLine();
                while (line != null){
                    data += line + "\n";
                    recordList.add(line);
                    line = br.readLine();
                }
                aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,recordList);
                lvRecords.setAdapter(aa);
                tvRecords.setText("Number of records: "+recordList.size());
                br.close();
                reader.close();
            } catch (Exception e){
                Toast.makeText(ListOfRecords.this, "Failed to read!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Toast.makeText(ListOfRecords.this, data, Toast.LENGTH_LONG).show();
        }
    }

}

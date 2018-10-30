package dell.example.com.thi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dell.example.com.thi.adapter.ModelAdapter;
import dell.example.com.thi.database.DatabaseHelper;
import dell.example.com.thi.model.Model;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private List<Model> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ModelAdapter adapter;
    private EditText edtID;
    private EditText edtdate;
    private EditText edtPRICE;
    private Button btnThem, btnSua;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler);
        list = databaseHelper.getPhoneAll();
        adapter = new ModelAdapter(list, databaseHelper, this);
        btnThem = findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = databaseHelper.getPhoneAll();
                String a = null;
                String b = "daco";
                String id = edtID.getText().toString().trim();
                for (int i = 0; i < list.size(); i++) {

                    Model food1 = list.get(i);
                    if (id.equals(food1.getId())) {
                        a = "daco";
                    } else {
                        a = "chuaco";
                    }


                }
                if (edtID.getText().toString().equals("") || edtdate.getText().toString().equals("") ) {
                    if (edtID.getText().toString().equals("")) {
                        edtID.setError("nội dung không được để trống");
                    }
                    if (edtdate.getText().toString().equals("")) {
                        edtdate.setError("ngày tháng không được để trống");
                    }
                } else {
                    if (edtID.getText().toString().length() > 100 ||
                            edtdate.getText().toString().length() > 10 ) {
                        if (edtID.getText().toString().length() > 100) {
                            edtID.setError("nội dung không được nhập quá 100 kí tứ");
                        }
                        if (edtdate.getText().toString().length() > 10) {
                            edtdate.setError("ngày tháng không được nhập quá 10 kí tự");
                        }
                    } else {
                        if (b.equals(a)) {
                            Toast.makeText(getApplicationContext(), "đã có", Toast.LENGTH_SHORT).show();
                        } else {
                            Model phone = new Model();
                            phone.setId(edtID.getText().toString());
                            phone.setName(edtdate.getText().toString());
                            databaseHelper.insertPhone(phone);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            list.add(phone);
                            adapter.notifyDataSetChanged();
                        }
                    }


                }
                adapter.notifyDataSetChanged();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtID.getText().toString().equals("") || edtdate.getText().toString().equals("") ) {
                    if (edtID.getText().toString().equals("")) {
                        edtID.setError("nội dung không được để trống");
                    }
                    if (edtdate.getText().toString().equals("")) {
                        edtdate.setError("ngày tháng không được để trống");
                    }
                } else {
                    if (edtID.getText().toString().length() > 100 ||
                            edtdate.getText().toString().length() > 10
                           ) {
                        if (edtID.getText().toString().length() > 100) {
                            edtID.setError("nội dung không được nhập quá 100 kí tự");
                        }
                        if (edtdate.getText().toString().length() > 10) {
                            edtdate.setError("ngày tháng không được nhập quá 10 kí tự");
                        }
                    } else {
                        Model food = new Model();
                        food.setName(edtdate.getText().toString());
                        databaseHelper.updatePhone(edtID.getText().toString(), food);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                    }


                }
                adapter.notifyDataSetChanged();
            }
        });

        Log.e("TAG", list.size() + "");

        adapter.notifyDataSetChanged();
        final RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }


    private void initView() {
        edtID = findViewById(R.id.edtid);
        edtdate = findViewById(R.id.edtdate);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
    }

    public void chonngay(View view) {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                long time = calendar.getTimeInMillis();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                edtdate.setText(format.format(time));
            }
        }, nam, thang, ngay);
        pickerDialog.show();
    }
}

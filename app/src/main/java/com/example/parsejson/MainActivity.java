package com.example.parsejson;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnParse;
    ListView lv;
    ArrayList<String> myList;
    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        btnParse = findViewById(R.id.btnparse);
        lv = findViewById(R.id.lv);

        // Khởi tạo list và adapter
        myList = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        lv.setAdapter(myAdapter);

        // Bắt sự kiện click
        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJson();
            }
        });
    }

    private void parseJson() {
        String json = null;
        try {
            // Đọc file JSON từ thư mục assets
            InputStream inputStream = getAssets().open("computer.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

            // Parse JSON
            JSONObject reader = new JSONObject(json);
            myList.clear(); // Xóa dữ liệu cũ trước khi load mới

            // Lấy MaDM và TenDM
            myList.add(reader.getString("MaDM"));
            myList.add(reader.getString("TenDM"));

            // Lấy danh sách sản phẩm
            JSONArray myArray = reader.getJSONArray("Sanphams");
            for (int i = 0; i < myArray.length(); i++) {
                JSONObject myObj = myArray.getJSONObject(i);

                String maSP = myObj.getString("MaSP");
                String tenSP = myObj.getString("TenSP");
                int soLuong = myObj.getInt("SoLuong");
                int donGia = myObj.getInt("DonGia");
                int thanhTien = myObj.getInt("ThanhTien");
                String hinh = myObj.getString("Hinh");

                myList.add(maSP + " - " + tenSP);
                myList.add(soLuong + " * " + donGia + " = " + thanhTien);
                myList.add(hinh);
            }

            // Cập nhật lại ListView
            myAdapter.notifyDataSetChanged();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

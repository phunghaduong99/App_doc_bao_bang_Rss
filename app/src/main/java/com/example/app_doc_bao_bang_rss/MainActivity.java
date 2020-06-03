package com.example.app_doc_bao_bang_rss;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.app_doc_bao_bang_rss.fragment.Tab1;
import com.example.app_doc_bao_bang_rss.fragment.Tab2;
import com.example.app_doc_bao_bang_rss.fragment.Tab3;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayTitle;
    ArrayAdapter adapter;

    CusAdapter cusAdapter;
    ArrayList<Docbao> mangDocBao;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Tab1 tab1;
    private Tab2 tab2;
    private Tab3 tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);


        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        tab1 = new Tab1();
        tab2 = new Tab2();
        tab3 = new Tab3();

        tabLayout.setupWithViewPager(viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 0);
        pagerAdapter.addFragment(tab1, "Trang Chủ");
        pagerAdapter.addFragment(tab2, "Thế Giới");
        pagerAdapter.addFragment(tab3, "Thời Sự");
        viewPager.setAdapter(pagerAdapter);

        Log.d("c", " coo ");

//        listView = (ListView) findViewById(R.id.listView);
//        mangDocBao = new ArrayList<Docbao>();
//
//        new ReadRSS().execute("https://vnexpress.net/rss/thoi-su.rss");
//        if(listView != null)
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                intent.putExtra("link", mangDocBao.get(position).link);
//                startActivity(intent);
//            }
//        });

    }


//    }
}

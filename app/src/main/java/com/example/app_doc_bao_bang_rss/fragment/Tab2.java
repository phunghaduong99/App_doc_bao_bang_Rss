package com.example.app_doc_bao_bang_rss.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.app_doc_bao_bang_rss.CusAdapter;
import com.example.app_doc_bao_bang_rss.Docbao;
import com.example.app_doc_bao_bang_rss.Main2Activity;
import com.example.app_doc_bao_bang_rss.R;
import com.example.app_doc_bao_bang_rss.XMLDOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CusAdapter cusAdapter;
    ArrayList<Docbao> mangDocBao;
    ListView listView;
    Context context;

    public Tab2() {
// Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
// TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1,
                container, false);
        listView = rootView.findViewById(R.id.listView);
        mangDocBao = new ArrayList<Docbao>();
        cusAdapter = new CusAdapter(getContext(), android.R.layout.simple_list_item_1
                , mangDocBao);
        new Tab2.ReadRSS().execute("https://vnexpress.net/rss/the-gioi.rss");
        if (listView != null)
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), Main2Activity.class);
                    intent.putExtra("link", mangDocBao.get(position).link);
                    startActivity(intent);
                }
            });
        return rootView;
    }

    private class ReadRSS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader =
                        new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            String hinhanh = "";
            String title = "";
            String link = "";
//            Toast.makeText(getContext(), "kaka" + nodeList.getLength(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                NodeList nodeListDescription = element.getElementsByTagName("description");
                String cData =
                        nodeListDescription.item(0).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cData);
                if (matcher.find()) {
                    hinhanh = matcher.group(1);
                    Log.d("hinhanh: ", hinhanh);
                }
                NodeList Title = element.getElementsByTagName("title");
                title = Title.item(0).getTextContent();
                Log.d("title: ", title);
                NodeList Link = element.getElementsByTagName("link");
                link = Link.item(0).getTextContent();
                Log.d("link: ", link);
                mangDocBao.add(new Docbao(title, link, hinhanh));
            }
            cusAdapter = new CusAdapter(getContext(), android.R.layout.simple_list_item_1
                    , mangDocBao);
            listView.setAdapter(cusAdapter);
            super.onPostExecute(s);
        }
    }
}
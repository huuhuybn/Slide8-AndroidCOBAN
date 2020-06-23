package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    private String link = "https://vnexpress.net/";

    private String html = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<h1>The img element</h1>\n" +
            "\n" +
            "<img src=\"https://www.w3schools.com/tags/img_girl.jpg\" alt=\"Girl in a jacket\" width=\"100%\" height=\"100%\">\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";

    String duLieu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);
        //webView.loadUrl(link);
        webView.loadData(html, "text/html", "utf-8");
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asyncTask = new AsyncTask() {
                    // xu ly trong luong
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        getData();
                        return null;
                    }
                    // ket thuc xu ly

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        if (!duLieu.isEmpty()) {
                            Toast.makeText(MainActivity.this, duLieu, Toast.LENGTH_LONG).show();
                        }

                    }
                };
                asyncTask.execute();
            }
        });

    }

    private void getData() {
        try {
            URL url = new URL(link);
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection)
                        url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                String data = "";
                Scanner scanner
                        = new Scanner(inputStream);
                while (scanner.hasNext()) {
                    data += scanner.nextLine();
                }
                scanner.close();
                inputStream.close();
                httpURLConnection.disconnect();

                duLieu = data;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
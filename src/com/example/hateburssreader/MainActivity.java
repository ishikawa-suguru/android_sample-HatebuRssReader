package com.example.hateburssreader;

import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		HttpURLConnection con = null;
		InputStream in = null;
		String body = "";
		try {
			// URLの作成
			URL url = new URL("http://b.hatena.ne.jp/hotentry.rss");

			// 接続用HttpURLConnectionオブジェクト作成
			con = (HttpURLConnection) url.openConnection();
			// リクエストメソッドの設定
			con.setRequestMethod("GET");
			// 接続
			con.connect();
			// HTMLソースを読み出す
			in = con.getInputStream();
			byte[] line = new byte[1024];
			int size;
			while (true) {
				size = in.read(line);
				if (size <= 0)
					break;
				body += new String(line);
			}
		} catch (Exception e) {

		} finally {
			con.disconnect();
		}

		TextView text = (TextView) findViewById(R.id.textView);
//		text.setText(body);

		String title = "";
		String link = "";
		String body2 = "";
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
	            this, android.R.layout.simple_list_item_1);
		XmlPullParser xmlPullParser = Xml.newPullParser();
		try {
			xmlPullParser.setInput(new StringReader(body));
			int eventType;
			while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG
						&& "title".equals(xmlPullParser.getName())) {
					title = xmlPullParser.nextText();
				}
				if (eventType == XmlPullParser.START_TAG
						&& "link".equals(xmlPullParser.getName())) {
					link = xmlPullParser.nextText();
				}
				if (title != "" && link != "") {
//					body2 += ("title:" + title + ", link:" + link + "¥n");
					adapter.add(title);
					title = "";
					link = "";
				}
				Log.d("body2:", body2);
			}
		} catch (Exception e) {
			Log.d("error:", e.getMessage());
		}
//		text.setText(body2);
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

package com.example.msi.myapplication;
import android.content.Intent;
import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.os.Handler;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.OutputStream;
    import java.net.CookieHandler;
    import java.net.CookieManager;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.net.URLEncoder;
    import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    import android.app.Activity;
    import android.text.TextUtils;
    import android.view.View;
    import android.webkit.CookieSyncManager;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import org.jsoup.Connection;
    import org.jsoup.Jsoup;
    import org.jsoup.nodes.Document;
    import org.jsoup.nodes.Element;
    import org.jsoup.select.Elements;

public class MainActivity extends Activity {
    static List<String> datastring = new ArrayList<>();
    Button downImgBtn = null;
    ImageView showImageView = null;
    Intent intent = null;
    // 澹版槑鎺т欢瀵硅薄
    //comment here
    private EditText id233, pwd233, xdvfb233;
    // 澹版槑鏄剧ず杩斿洖鏁版嵁搴撶殑鎺т欢瀵硅薄
    private TextView tv_result;
    private Handler handler = new Handler();
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 璁剧疆鏄剧ず鐨勮鍥�
        setContentView(R.layout.activity_main);
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        System.out.println(cookieManager);
        // 閫氳繃 findViewById(id)鏂规硶鑾峰彇鐢ㄦ埛鍚嶇殑鎺т欢瀵硅薄
        id233 = (EditText) findViewById(R.id.et_name);
        // 閫氳繃 findViewById(id)鏂规硶鑾峰彇鐢ㄦ埛瀵嗙爜鐨勬帶浠跺璞�
        pwd233 = (EditText) findViewById(R.id.et_pass);
        xdvfb233 = (EditText) findViewById(R.id.et_yzm);
        // 閫氳繃 findViewById(id)鏂规硶鑾峰彇鏄剧ず杩斿洖鏁版嵁鐨勬帶浠跺璞�
        tv_result = (TextView) findViewById(R.id.tv_result);
        final ImageView imageView = (ImageView)this.findViewById(R.id.imagview_show);
        new Thread() {
            public void run() {
                String urlbitmap = "http://210.42.121.241/servlet/GenImg";
                //寰楀埌鍙敤鐨勫浘鐗�
                Bitmap bitmap = getHttpBitmap(urlbitmap);

                //鏄剧ず
                imageView.setImageBitmap(bitmap);
                 // 璋冪敤loginByPost鏂规硶
            };
        }.start();

        initUI();
        downImgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showImageView.setVisibility(View.VISIBLE);
                String imgUrl = "http://210.42.121.241/servlet/GenImg";
                new DownImgAsyncTask().execute(imgUrl);
            }
        });
    }
    /**
     * 鑾峰彇缃戣惤鍥剧墖璧勬簮
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //鑾峰緱杩炴帴
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //璁剧疆瓒呮椂鏃堕棿涓�6000姣锛宑onn.setConnectionTiem(0);琛ㄧず娌℃湁鏃堕棿闄愬埗
            conn.setConnectTimeout(6000);
            //杩炴帴璁剧疆鑾峰緱鏁版嵁娴�
            conn.setDoInput(true);
            //涓嶄娇鐢ㄧ紦瀛�
            conn.setUseCaches(false);
            //杩欏彞鍙湁鍙棤锛屾病鏈夊奖鍝�
            conn.connect();
            //寰楀埌鏁版嵁娴�
            InputStream is = conn.getInputStream();
            //瑙ｆ瀽寰楀埌鍥剧墖
            bitmap = BitmapFactory.decodeStream(is);
            //鍏抽棴鏁版嵁娴�
            is.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public void initUI() {
        showImageView = (ImageView)findViewById(R.id.imagview_show);
        downImgBtn = (Button)findViewById(R.id.btn_download_img);
    }

    /**
     * 浠庢寚瀹歎RL鑾峰彇鍥剧墖
     * @param url
     * @return
     */
    private Bitmap getImageBitmap(String url){
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            showImageView.setImageBitmap(null);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Bitmap b = getImageBitmap(params[0]);
            return b;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if(result!=null){
                showImageView.setImageBitmap(result);
            }
        }
    }


     /**
      * POST璇锋眰鎿嶄綔
      *
      * @param id
      * @param pwd
      * @param xdvfb
      */
     public void loginByPost(String id, String pwd, String xdvfb) {

         try {
             // 璇锋眰鐨勫湴鍧�
             String spec = "http://210.42.121.241/servlet/Login";
             // 鏍规嵁鍦板潃鍒涘缓URL瀵硅薄
             URL url = new URL(spec);
             // 鏍规嵁URL瀵硅薄鎵撳紑閾炬帴
             HttpURLConnection urlConnection = (HttpURLConnection) url
                     .openConnection();

             // 璁剧疆璇锋眰鐨勬柟寮�
             urlConnection.setRequestMethod("POST");
             // 璁剧疆璇锋眰鐨勮秴鏃舵椂闂�
             urlConnection.setReadTimeout(5000);
             urlConnection.setConnectTimeout(5000);
             // 浼犻�掔殑鏁版嵁
             String data = "id=" + URLEncoder.encode(id,"GB2312")
                     + "&pwd=" + URLEncoder.encode(pwd,"GB2312")
                     + "&xdvfb=" + URLEncoder.encode(xdvfb,"GB2312");
             // 璁剧疆璇锋眰鐨勫ご
             urlConnection.setRequestProperty("Connection", "keep-alive");
             // 璁剧疆璇锋眰鐨勫ご
             urlConnection.setRequestProperty("Content-Type",
                     "application/x-www-form-urlencoded");
             // 璁剧疆璇锋眰鐨勫ご
             urlConnection.setRequestProperty("Content-Length",
                     String.valueOf(data.getBytes().length));
             // 璁剧疆璇锋眰鐨勫ご
             urlConnection
                     .setRequestProperty("User-Agent",
                             "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/42.0");
             urlConnection.setDoOutput(true); // 鍙戦�丳OST璇锋眰蹇呴』璁剧疆鍏佽杈撳嚭
             urlConnection.setDoInput(true); // 鍙戦�丳OST璇锋眰蹇呴』璁剧疆鍏佽杈撳叆
             OutputStream os = urlConnection.getOutputStream();
             os.write(data.getBytes());

             final StringBuffer sb = new StringBuffer();//鎶婅幏鍙栫殑鏁版嵁涓嶆柇瀛樻斁鍒癝tringBuffer涓紱
             BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "GB2312"));//浣跨敤reader鍚戣緭鍏ユ祦涓鍙栨暟鎹紝骞朵笉鏂瓨鏀惧埌StringBuffer涓紱
             String line;
             while ((line = reader.readLine()) != null) {//鍙杩樻病鏈夎鍙栧畬锛屽氨涓嶆柇璇诲彇锛�
                 sb.append(line);//鍦⊿tringBuffer涓坊鍔狅紱
             }
             handler.post(new Runnable() {//浣跨敤Handler鏇存柊UI锛涘綋鐒惰繖閲屼篃鍙互浣跨敤sendMessage();handMessage()鏉ヨ繘琛屾搷浣滐紱
                    @Override
                    public void run() {
                    tv_result.setText(sb.toString());//StringBuffer杞寲涓篠tring杈撳嚭锛�
                    }
             });
             os.flush();



         }
         catch (MalformedURLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();}
         catch (Exception e) {
                e.printStackTrace();
         }
         try {

             // 璇锋眰鐨勫湴鍧�
             String spec = "http://210.42.121.241/servlet/Svlt_QueryStuLsn?action=queryStuLsn";
             // 鏍规嵁鍦板潃鍒涘缓URL瀵硅薄
             URL url = new URL(spec);
             // 鏍规嵁URL瀵硅薄鎵撳紑閾炬帴
             HttpURLConnection urlConnection2 = (HttpURLConnection) url
                     .openConnection();
             // 璁剧疆璇锋眰鐨勬柟寮�
             urlConnection2.setRequestMethod("GET");

             // 璁剧疆璇锋眰鐨勮秴鏃舵椂闂�
             urlConnection2.setReadTimeout(5000);
             urlConnection2.setConnectTimeout(5000);
             urlConnection2
                     .setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/42.0");
             urlConnection2.setDoOutput(true); // 鍙戦�丳OST璇锋眰蹇呴』璁剧疆鍏佽杈撳嚭
             urlConnection2.setDoInput(true); // 鍙戦�丳OST璇锋眰蹇呴』璁剧疆鍏佽杈撳叆
             OutputStream os = urlConnection2.getOutputStream();

             final StringBuffer sb = new StringBuffer();//鎶婅幏鍙栫殑鏁版嵁涓嶆柇瀛樻斁鍒癝tringBuffer涓紱
             BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection2.getInputStream(), "GB2312"));//浣跨敤reader鍚戣緭鍏ユ祦涓鍙栨暟鎹紝骞朵笉鏂瓨鏀惧埌StringBuffer涓紱
             String line;
             String lessons = null;
             int i = 0;
             while ((line = reader.readLine()) != null) {//鍙杩樻病鏈夎鍙栧畬锛屽氨涓嶆柇璇诲彇锛�
                 sb.append(line);//鍦⊿tringBuffer涓坊鍔狅紱
                 Pattern p = Pattern.compile(".*?;//璇剧▼鍚�$");
                 Matcher m = p.matcher(line);
                 List<String> result=new ArrayList<>();

                 while(m.find()){
                     result.add(m.group());
                     lessons = line;
                     lessons = lessons.replaceAll("\\s+","");
                     lessons = lessons.replaceAll("varlessonName=\"","");
                     lessons = lessons.replaceAll("\";//璇剧▼鍚�","");
                     System.out.println(lessons);
                     datastring.add(lessons);
                     HashSet h = new HashSet(datastring);
                     datastring.clear();
                     datastring.addAll(h);

                 }
                 for(String s1:result){
                     System.out.println(s1);
                 }

             }
             handler.post(new Runnable() {//浣跨敤Handler鏇存柊UI锛涘綋鐒惰繖閲屼篃鍙互浣跨敤sendMessage();handMessage()鏉ヨ繘琛屾搷浣滐紱
                 @Override
                 public void run() {
                     tv_result.setText(sb.toString());//StringBuffer杞寲涓篠tring杈撳嚭锛�
                 }
             });
             os.flush();

         }
         catch (MalformedURLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();}
         catch (Exception e) {
             e.printStackTrace();
         }

     }
    static int t = datastring.size();
    public static List<String> generateData(int size)
    {
        List<String> datas = new ArrayList<>();
        datas.addAll(datastring);
        return datas;
    }
     /**
     * 閫氳繃android:onClick="login"鎸囧畾鐨勬柟娉� 锛� 瑕佹眰杩欎釜鏂规硶涓帴鍙椾綘鐐瑰嚮鎺т欢瀵硅薄鐨勫弬鏁皏
     *
     * @param v
     */
     public void login(View v) {
         // 鑾峰彇鐐瑰嚮鎺т欢鐨刬d
         int id1 = v.getId();
         // 鏍规嵁id杩涜鍒ゆ柇杩涜鎬庝箞鏍风殑澶勭悊
         switch (id1) {
             // 鐧婚檰浜嬩欢鐨勫鐞�
             case R.id.btn_login:
                 // 鑾峰彇鐢ㄦ埛鍚�
                 final String id = id233.getText().toString();
                 // 鑾峰彇鐢ㄦ埛瀵嗙爜
                 final String pwd = pwd233.getText().toString();
                 final String xdvfb = xdvfb233.getText().toString();
                 if (TextUtils.isEmpty(id) || TextUtils.isEmpty(pwd)) {
                     Toast.makeText(this, "鐢ㄦ埛鍚嶆垨鑰呭瘑鐮佷笉鑳戒负绌�", Toast.LENGTH_LONG).show();
                 } else {
                     // 寮�鍚瓙绾跨▼
                     new Thread() {
                         public void run() {
                             loginByPost(id, pwd, xdvfb); // 璋冪敤loginByPost鏂规硶
                             intent = new Intent(MainActivity.this, MainActivity2.class);
                             startActivity(intent);
                         };
                     }.start();
                 }
                 break;
         }

     }

}

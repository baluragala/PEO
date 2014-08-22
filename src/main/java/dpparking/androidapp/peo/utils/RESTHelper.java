package dpparking.androidapp.peo.utils;

/**
 * Created by RBK on 2/8/14.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;
import android.util.Log;

public class RESTHelper {
    private static final String TAG = RESTHelper.class.getSimpleName();

    private int timeout = 30000;
    private HttpClient httpClient;
    private HttpPost httpPost;
    private HttpGet httpGet;
    private HttpResponse httpResponse;
    private String userName;
    private String password;
    private String base64Creds;
    private String plainCreds;

    private String error;
    private String responseString = null;
    private String headerBookingId;

    private ArrayList data;

    public RESTHelper(){
        this.httpClient = new DefaultHttpClient();
        this.data = new ArrayList();
    }

    public RESTHelper(String userName, String password){
        this.httpClient = new DefaultHttpClient();
        this.data = new ArrayList();
        this.plainCreds = userName+":"+password;
        String base64Creds = Base64.encodeToString(this.plainCreds.getBytes(), Base64.DEFAULT);
        Log.d(TAG,plainCreds);
        Log.d(TAG,base64Creds);
    }

    public void setTimeout(int timeout){
        this.timeout = timeout;
    }

    public void post(String url){
        this.post(url, null);
    }

    public void post(String url, ArrayList<NameValuePair> data){
        this.httpPost = new HttpPost(url);
        this.httpClient.getParams().setParameter("http.socket.timeout", this.timeout);
        try {
            this.data = data;
            if(data != null)
            {
                Log.i("dpparking.androidapp.mobileparking.utils RESTHelper : data elements size",data.size()+"");
                this.httpPost.setEntity(new UrlEncodedFormEntity(this.data));
                HttpEntity entity = this.httpPost.getEntity();
                InputStream stream = entity.getContent();
                InputStreamReader rdr = new InputStreamReader(stream);
                BufferedReader brdr = new BufferedReader(rdr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = brdr.readLine()) != null) {
                    sb.append(line);
                }
                Log.i("dpparking.androidapp.mobileparking.utils RESTHelper : form encoded data",sb.toString()+"");


            }
            this.httpResponse = this.httpClient.execute(this.httpPost);
        } catch (UnsupportedEncodingException e) {
            this.error = e.getMessage();
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            this.error = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            this.error = e.getMessage();
            e.printStackTrace();
        }
    }

    public void postAndGetId(String url, ArrayList<NameValuePair> data,String headerName){
        this.httpPost = new HttpPost(url);
        this.httpClient.getParams().setParameter("http.socket.timeout", this.timeout);
        try {
            this.data = data;
            if(data != null)
            {
                Log.i("dpparking.androidapp.mobileparking.utils RESTHelper : data elements size",data.size()+"");
                this.httpPost.setEntity(new UrlEncodedFormEntity(this.data));
                HttpEntity entity = this.httpPost.getEntity();
                InputStream stream = entity.getContent();
                InputStreamReader rdr = new InputStreamReader(stream);
                BufferedReader brdr = new BufferedReader(rdr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = brdr.readLine()) != null) {
                    sb.append(line);
                }
                Log.i("dpparking.androidapp.mobileparking.utils RESTHelper : form encoded data",sb.toString()+"");


            }
            this.httpResponse = this.httpClient.execute(this.httpPost);
            Log.i("dpparking.androidapp.mobileparking.utils RESTHelper : data elements size",headerName);
            Header idHeader = this.httpResponse.getFirstHeader(headerName);
            if(idHeader != null)
                headerBookingId =  idHeader.getValue();

        } catch (UnsupportedEncodingException e) {
            this.error = e.getMessage();
            e.printStackTrace();

        } catch (ClientProtocolException e) {
            this.error = e.getMessage();
            e.printStackTrace();

        } catch (IOException e) {
            this.error = e.getMessage();
            e.printStackTrace();

        }
    }

    public void get(String url){
        this.httpGet = new HttpGet(url);
        this.httpGet.addHeader("Authorization",this.base64Creds);
        this.httpClient.getParams().setParameter("http.socket.timeout", this.timeout);
        try {
            this.httpResponse = this.httpClient.execute(this.httpGet);
        } catch (UnsupportedEncodingException e) {
            this.error = e.getMessage();
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            this.error = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            this.error = e.getMessage();
            e.printStackTrace();
        }
    }

    public String getError() {
        return this.error;
    }

    public String getResponseString(){
        if(this.httpResponse!=null){
            try {
                HttpEntity httpEntity = this.httpResponse.getEntity();
                InputStream is = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "utf-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                this.responseString = sb.toString();
                return this.responseString;
            } catch (Exception e) {
                this.error = e.getMessage();
                Log.e("RESTHelper", "Response string buffer error. " + e.getMessage());
            }
        }
        return null;
    }

    public JSONObject getResponseJSONObject(){
        String JSONString = this.getResponseString();
        if(JSONString != null)
            Log.i("RESTHelper", JSONString);
        else Log.i("RESTHelper", "JSONString is null");
        if(JSONString != null) {
            JSONObject jObj;
            try {
                jObj = new JSONObject(JSONString);
                return jObj;
            } catch (JSONException e) {
                this.error = e.getMessage();
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getResponseText(){
        return this.responseString;
    }

    public String getHeaderBookingId()
    {
        return headerBookingId;
    }
}
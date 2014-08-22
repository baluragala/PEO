package dpparking.androidapp.peo.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.apache.http.NameValuePair;;
import org.json.JSONObject;
import java.util.ArrayList;
import dpparking.androidapp.peo.utils.RESTHelper;

/**
 * Created by RBK on 2/9/14.
 */
public class RESTPostAsyncTask extends AsyncTask<String, Void, Void> {
    private ProgressDialog progressDialog;
    private Context applicationContext;
    private String activityName;
    private ArrayList<NameValuePair> params;
    private JSONObject postResponse;
    private String bookingId, bookingFromTimeGC, bookingToTimeGC, bookingVehicleGC, bookingLocationGC,bookingVehicle, bookingLocation;
    private String message,title;

    public RESTPostAsyncTask(Context applicationContext, String activityName, ArrayList<NameValuePair> params,String message,String title) {
        this.applicationContext = applicationContext;
        this.activityName = activityName;
        this.params = params;
        this.message = message;
        this.title = title;
    }

    @Override
    protected Void doInBackground(String... strings) {
        RESTHelper restHelper = new RESTHelper();
        restHelper.post(strings[0], params);
        postResponse = restHelper.getResponseJSONObject();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(applicationContext);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void noResult) {
        super.onPostExecute(noResult);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }


    }
}
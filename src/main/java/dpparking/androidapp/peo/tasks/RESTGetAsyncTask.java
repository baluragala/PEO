package dpparking.androidapp.peo.tasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

import dpparking.androidapp.peo.Constants;
import dpparking.androidapp.peo.SearchActivity;
import dpparking.androidapp.peo.pojo.Ticket;
import dpparking.androidapp.peo.utils.RESTHelper;

/**
 * Created by RBK on 2/8/14.
 */
public class RESTGetAsyncTask extends AsyncTask<String, Void, JSONObject> {
    ProgressDialog progressDialog;
    protected Context applicationContext;
    protected String actionName;
    protected String dialogTitle;
    protected String dialogMessage;
    protected String userName;
    protected String password;
    private SharedPreferences sharedPreferences;

    //Log TAG
    public static final String TAG = RESTGetAsyncTask.class.getSimpleName();

    public RESTGetAsyncTask(Context applicationContext, String actionName, String dialogTitle, String dialogMessage) {
        this.applicationContext = applicationContext;
        this.actionName = actionName;
        this.dialogTitle = dialogTitle;
        this.dialogMessage = dialogMessage;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        this.userName = sharedPreferences.getString("USERNAME", "");
        this.password = sharedPreferences.getString("PASSWORD", "");
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        RESTHelper restHelper = new RESTHelper();
        Log.i( TAG, "doInBackground : " + strings[0]);
        restHelper.get(strings[0]);
        return restHelper.getResponseJSONObject();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Log.i( TAG, "onPreExecute");
        progressDialog = new ProgressDialog(applicationContext);
        progressDialog.setTitle(dialogTitle);
        progressDialog.setMessage(dialogMessage);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(JSONObject resultJSON) {
        super.onPostExecute(resultJSON);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (resultJSON == null) {
            progressDialog = new ProgressDialog(applicationContext);
            progressDialog.setTitle("Error");
            progressDialog.setMessage("Please check your internet connection");
            progressDialog.setCancelable(true);
            progressDialog.show();
        } else {
            Log.i("RESTGetAsyncTask", resultJSON.toString());
        }

        if(actionName.equals(Constants.GET_TICKETS_FOR_PEO))
        {
            Log.i(TAG,"onPostExecute");
            Ticket ticket = new Ticket();
            ticket.prepareTickets(resultJSON);
            Log.i(TAG,"Total tickets prepared :" + ticket.get_tickets().size()+"");
            SearchActivity activity = (SearchActivity) applicationContext;
            activity.moveToListActivity(ticket.get_tickets());

        }

    }

}

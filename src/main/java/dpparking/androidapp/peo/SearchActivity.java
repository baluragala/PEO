package dpparking.androidapp.peo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import dpparking.androidapp.peo.pojo.Ticket;
import dpparking.androidapp.peo.tasks.RESTGetAsyncTask;
import dpparking.androidapp.peo.utils.Helper;


public class SearchActivity extends ActionBarActivity {

    //Log Tag
    public static final String TAG = SearchActivity.class.getSimpleName();


    //UI Control variables
    private Button buttonSearch;
    private EditText editTextSearchValue;
    private Spinner spinnerSearchOptions;
    private RadioGroup radioGroupBookingType;
    private RadioGroup radioGroupElapsedBy;
    private RadioButton radioButtonBookingType;
    private RadioButton radioButtonElapsedBy;


    //locals
    private Boolean isInternetAvailable;
    private String url;
    private String searchParamString;
    private String searchElapsedBy;
    private String searchBookingType;
    private String searchType;
    private String searchLiteral;
    private int selectedBookingTypeId;
    private int selectedElapsedId;
    private ArrayList<Ticket> mTickets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_acitivty);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        editTextSearchValue = (EditText) findViewById(R.id.editTextSearchString);
        spinnerSearchOptions = (Spinner) findViewById(R.id.spinnerSearchBy);
        radioGroupBookingType = (RadioGroup) findViewById(R.id.radioGroupBookingType);
        radioGroupElapsedBy = (RadioGroup) findViewById(R.id.radioGroupElapsedBy);

        //register button click
        buttonSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the search options issued
                //and fetch the ticket details
                //from server
                getTickets();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_acitivty, menu);
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

    private void getTickets() {
        isInternetAvailable = Helper.isConnectingToInternet(getApplicationContext());
        if (isInternetAvailable) {
            searchType = spinnerSearchOptions.getSelectedItem().toString();
            searchLiteral = editTextSearchValue.getText().toString();

            selectedBookingTypeId = radioGroupBookingType.getCheckedRadioButtonId();
            radioButtonBookingType = (RadioButton) findViewById(selectedBookingTypeId);

            selectedElapsedId = radioGroupElapsedBy.getCheckedRadioButtonId();
            radioButtonElapsedBy = (RadioButton) findViewById(selectedElapsedId);

            searchBookingType = radioButtonBookingType.getText().toString();
            searchElapsedBy = radioButtonElapsedBy.getText().toString();

            searchParamString = Uri.encode(searchType,"utf-8") +"/" + Uri.encode(searchLiteral,"utf-8")+ "/"+ searchBookingType + "/" + searchElapsedBy+".json";
            url = Constants.DP_REST_URL + Constants.DP_REST_CONTROLLER_SEARCH_TICKETS + searchParamString;

            Log.i(TAG,"url : " + url);
            new RESTGetAsyncTask(SearchActivity.this, Constants.GET_TICKETS_FOR_PEO, getString(R.string.title_searching), getString(R.string.title_please_wait)).execute(url);
        }else
        {
            Toast.makeText(getApplicationContext(),
                    R.string.title_no_connection, Toast.LENGTH_LONG).show();
        }
    }

    public void moveToListActivity(ArrayList<Ticket> tickets){
        if(tickets.size()==0){
            Toast.makeText(getApplicationContext(),
                    R.string.title_no_tickets, Toast.LENGTH_LONG).show();
        }else {
            Intent intent = new Intent(SearchActivity.this, TicketListActivity.class);
            intent.putParcelableArrayListExtra(Constants.SEARCH_TICKET_RESULTS, tickets);
            startActivity(intent);
        }
    }



}

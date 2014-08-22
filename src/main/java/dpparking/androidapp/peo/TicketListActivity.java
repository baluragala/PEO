package dpparking.androidapp.peo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import dpparking.androidapp.peo.pojo.Ticket;


public class TicketListActivity extends ListActivity {

    public static final String TAG = TicketListActivity.class.getSimpleName();
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private ArrayList<Ticket> mTickets;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ticket_list);

        listView = getListView();

        //get the tickets from called intent
        Intent intent = getIntent();
        mTickets = intent.getParcelableArrayListExtra(Constants.SEARCH_TICKET_RESULTS);
        Log.i(TAG,"Tickets Count :" + mTickets.size()+"");
        for(Ticket t: mTickets){
            Log.i(TAG,t.get_id()+"");
        }

        TicketAdapter adapter = new TicketAdapter(this,R.layout.activity_ticket_list,mTickets);

        listView.setAdapter(adapter);
    }



    public ArrayList<Ticket> getTickets(){
        return mTickets;
    }

}

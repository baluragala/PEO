package dpparking.androidapp.peo;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import dpparking.androidapp.peo.pojo.Ticket;


/**
 * Created by baluteju on 5/11/2014.
 */
public class TicketAdapter extends ArrayAdapter<Ticket> {

    //Log TAG
    public static final String TAG = TicketAdapter.class.getSimpleName();


    //locals
    private ArrayList<Ticket> objects;
    private TextView textViewBookingId, textViewVehicle, textViewLocation, textViewRemainingTime, textViewStart, textViewEnd;
    private DateFormat androidDateFormat, androidTimeFormat, simpleDateFormat;
    private Context context;
    private String booking_from_time_string, booking_to_time_string, booking_from_time_timer;


    public TicketAdapter(Context context, int resource, ArrayList<Ticket> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
        Log.i(TAG, "Total received for adapter constructor" + objects.size() + "");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_ticket_list, parent, false);

        Ticket ticket = objects.get(position);
        Log.i(TAG, "Preparing for position : " + position + "");

        androidDateFormat = android.text.format.DateFormat.getLongDateFormat(context);
        androidTimeFormat = android.text.format.DateFormat.getTimeFormat(context);
        try {
            if (rowView != null) {
                textViewBookingId = (TextView) rowView.findViewById(R.id.textViewBookingId);

                textViewVehicle = (TextView) rowView.findViewById(R.id.textViewVehicle);
                textViewLocation = (TextView) rowView.findViewById(R.id.textViewParkingAreaCode);
                textViewRemainingTime = (TextView) rowView.findViewById(R.id.textViewRemainingTime);
                textViewStart = (TextView) rowView.findViewById(R.id.textViewStart);
                textViewEnd = (TextView) rowView.findViewById(R.id.textViewEnd);

                textViewBookingId.setText(ticket.get_id() + "");
                textViewLocation.setText(ticket.get_parkingAreaCode());
                textViewVehicle.setText(ticket.get_vehicleNo());
            }

            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar now = Calendar.getInstance();
            booking_from_time_timer = simpleDateFormat.format(now.getTime());
            Log.i(TAG, "Parsing Dates");


            Log.i(TAG, "ticket.get_bookedFromTime() : " + ticket.get_bookedFromTime());
            booking_from_time_string = androidDateFormat.format(simpleDateFormat.parse(ticket.get_bookedFromTime()));
            booking_from_time_string += " " + androidTimeFormat.format(simpleDateFormat.parse(ticket.get_bookedFromTime()));
            Log.i(TAG, " booking_from_time_string : " + booking_from_time_string);
            textViewStart.setText(booking_from_time_string);

            booking_to_time_string = androidDateFormat.format(simpleDateFormat.parse(ticket.get_bookedToTime()));
            booking_to_time_string += " " + androidTimeFormat.format(simpleDateFormat.parse(ticket.get_bookedToTime()));
            Log.i(TAG, "booking_to_time_string : " + booking_to_time_string);
            textViewEnd.setText(booking_to_time_string);

            Date from_date = simpleDateFormat.parse(booking_from_time_timer);
            Date to_date = simpleDateFormat.parse(ticket.get_bookedToTime());
            long diffMilliseconds = to_date.getTime() - from_date.getTime();

            Log.i(TAG, "diffMilliseconds : " + diffMilliseconds + "");

            if (diffMilliseconds > 0) {
                textViewRemainingTime.setText("     ");
                textViewRemainingTime.setBackgroundColor(Color.GREEN);
            } else {
                textViewRemainingTime.setText("     ");
                textViewRemainingTime.setBackgroundColor(Color.RED);
            }

        } catch (ParseException pex) {
            Log.e(TAG, pex.getMessage());
        } catch (NullPointerException npe) {
            Log.e(TAG, npe.getMessage());
            Toast.makeText(context, R.string.title_app_crash, Toast.LENGTH_LONG).show();
        }

        Log.i(TAG, "Prepaing ticket id " + ticket.get_id() + "");

        // the view must be returned to our activity
        return rowView;
    }
}

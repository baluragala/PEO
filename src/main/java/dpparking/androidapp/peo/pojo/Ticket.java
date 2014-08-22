package dpparking.androidapp.peo.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by baluteju on 5/10/2014.
 */
public class Ticket implements Parcelable {

    //Log TAG
    public static final String TAG = Ticket.class.getSimpleName();

    private long _id;
    private String _vehicleNo;
    private String _bookedFromTime;
    private String _bookedToTime;
    private String _parkingAreaCode;
    private String _mobileNumber;
    private String _bookingType;
    private ArrayList<Ticket> _tickets;


    public Ticket()
    {

    }

    public Ticket(Parcel in){
        this._id = in.readLong();
        this._vehicleNo = in.readString();
        this._bookedFromTime = in.readString();
        this._bookedToTime = in.readString();
        this._parkingAreaCode = in.readString();
        this._mobileNumber = in.readString();
        this._bookingType = in.readString();
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(_id);
        parcel.writeString(_vehicleNo);
        parcel.writeString(_bookedFromTime);
        parcel.writeString(_bookedToTime);
        parcel.writeString(_parkingAreaCode);
        parcel.writeString(_mobileNumber);
        parcel.writeString(_bookingType);
        //parcel.writeTypedList(_tickets);
    }


    public void prepareTickets(JSONObject jsonObject){
        Log.i(TAG,"prepareTickets");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
        _tickets = new ArrayList<Ticket>();
        try {
            JSONArray dockets = jsonObject.getJSONArray("dockets");
            Log.i(TAG,dockets.toString());
            for(int counter=0;counter<dockets.length();counter++){
                JSONObject docket = dockets.getJSONObject(counter).getJSONObject("Docket");
                Log.i(TAG,docket.toString());
                Ticket ticket = new Ticket();
                ticket._id = docket.getLong("id");
                ticket._vehicleNo = docket.getString("vehicle_no");
                ticket._bookedFromTime = docket.getString("booked_from_time");
                ticket._bookedToTime = docket.getString("booked_to_time");
                ticket._parkingAreaCode = docket.getString("parking_area_code");
                ticket._mobileNumber = docket.getString("mobile_number");
                ticket._bookingType = docket.getString("booking_type");
                Log.i(TAG,"adding ticket to list with id :" + ticket._id+"");
                _tickets.add(ticket);
            }
        }catch (JSONException e){
            Log.i(TAG,e.getMessage());
        }

    }

    public long get_id() {
        return _id;
    }

    public String get_vehicleNo() {
        return _vehicleNo;
    }

    public String get_bookedFromTime() {
        return _bookedFromTime;
    }

    public String get_bookedToTime() {
        return _bookedToTime;
    }

    public String get_parkingAreaCode() {
        return _parkingAreaCode;
    }

    public String get_mobileNumber() {
        return _mobileNumber;
    }

    public String get_bookingType() {
        return _bookingType;
    }

    public ArrayList<Ticket> get_tickets() {
        return _tickets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Ticket> CREATOR
            = new Parcelable.Creator<Ticket>() {

        public  Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        public  Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

}

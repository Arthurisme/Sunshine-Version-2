package com.example.android.sunshine.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sunshine.app.data.WeatherContract;

/**
 * {@link com.example.android.sunshine.app.ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
    }

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    @Override
    public int getItemViewType(int position) {
        return position==0? VIEW_TYPE_TODAY:VIEW_TYPE_FUTURE_DAY  ;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    /*
            Remember that these views are reused as needed.
         */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        int viewtype=getItemViewType(cursor.getPosition());
int layoutId=-1;
        switch (viewtype){
            case VIEW_TYPE_TODAY:{
                layoutId=R.layout.list_item_forecast_today;
                break;
            }
            case VIEW_TYPE_FUTURE_DAY:{
                layoutId=R.layout.list_item_forecast;
                break;
            }


        }


         //View view = LayoutInflater.from(context).inflate(R.layout.list_item_forecast, parent, false);



//        //try curse:
//         cursor.moveToFirst();
//        for(int i = 0; i < cursor.getCount()-1; i++) {
//            String y="";
//            for(int i2 = 0; i2 < 9; i2++) {
//
//                y  = y+cursor.getString(i2)+", ";
//            }
//            Log.d("2384 d", "2384,cur to string: " + y+"\n");
//            //Log.d("2384 a", "2384,cur to string: " + y);
//            cursor.moveToNext();
//        }
//        cursor.moveToFirst();//if not move to first , the app will have a date+13



        Log.d("2384 d", "2384,cur to string: " + 1);

        return LayoutInflater.from(context).inflate(layoutId, parent, false);
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.

        // Read weather icon ID from cursor
        int weatherId = cursor.getInt(ForecastFragment.COL_WEATHER_ID);
        // Use placeholder image for now
        ImageView iconView = (ImageView) view.findViewById(R.id.list_item_icon);
        iconView.setImageResource(R.drawable.ic_launcher);

        // Read date from cursor
        long dateInMillis = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
        // Find TextView and set formatted date on it
        TextView dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
        dateView.setText(Utility.getFriendlyDayString(context, dateInMillis));

        // Read weather forecast from cursor
        String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
        // Find TextView and set weather forecast on it
        TextView descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
        descriptionView.setText(description);

        // Read user preference for metric or imperial temperature units
        boolean isMetric = Utility.isMetric(context);

        // Read high temperature from cursor
        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        TextView highView = (TextView) view.findViewById(R.id.list_item_high_textview);
        highView.setText(Utility.formatTemperature(high, isMetric));

        // Read low temperature from cursor
        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        TextView lowView = (TextView) view.findViewById(R.id.list_item_low_textview);
        lowView.setText(Utility.formatTemperature(low, isMetric));

        //
        //tv.setText("same");

//        //try curse:
//
//        cursor.moveToFirst();
//        for(int i = 0; i < cursor.getCount()-1; i++) {
//            String y="";
//            for(int i2 = 0; i2 < 9; i2++) {
//
//                y  = y+cursor.getString(i2)+", ";
//            }
//            Log.d("2384 e", "2384,cur to string: " + y+"\n");
//            //Log.d("2384 a", "2384,cur to string: " + y);
//            cursor.moveToNext();
//        }
//        cursor.moveToFirst();//if not move to first , the app will have a date+13


        Log.d("2384 e", "2384,cur to string: " + 1);

    }




    //    /*
//        This is ported from FetchWeatherTask --- but now we go straight from the cursor to the
//        string.
//     */
//    private String convertCursorRowToUXFormat(Cursor cursor) {
//        // get row indices for our cursor
////        int idx_max_temp = cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP);
////        int idx_min_temp = cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP);
////        int idx_date = cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE);
////        int idx_short_desc = cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC);
//
//        String highAndLow = formatHighLows(
//                cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP),
//                cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP));
//
//
//
////        //try curse:
////         cursor.moveToFirst();
////        for(int i = 0; i < cursor.getCount()-1; i++) {
////            String y="";
////            for(int i2 = 0; i2 < 9; i2++) {
////
////                y  = y+cursor.getString(i2)+", ";
////            }
////            Log.d("2384 c", "2384,cur to string: " + y+"\n");
////            //Log.d("2384 a", "2384,cur to string: " + y);
////            cursor.moveToNext();
////        }
////        cursor.moveToFirst();//if not move to first , the app will have a date+13
//
//
//        Log.d("2384 c", "2384,cur to string: " + 1);
//
//        return Utility.formatDate(cursor.getLong(ForecastFragment.COL_WEATHER_DATE)) +
//                " - " + cursor.getString(ForecastFragment.COL_WEATHER_DESC) +
//                " - " + highAndLow;
//
//
//
//
//
//
//
//    }

    /**
     * Prepare the weather high/lows for presentation.
     */
//    private String formatHighLows(double high, double low) {
//        boolean isMetric = Utility.isMetric(mContext);
//        String highLowStr = Utility.formatTemperature(high, isMetric) + "/" + Utility.formatTemperature(low, isMetric);
//        return highLowStr;
//    }

}
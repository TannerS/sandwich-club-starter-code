package com.udacity.sandwichclub.utils;

import android.content.Context;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    /**
     * @param   json     json of sandwich data
     * @return          Sandwich object containing sandwich data
     */
    public static Sandwich parseSandwichJson(Context context, String json) {
        // check for null or empty string
        if(json != null && !json.isEmpty()) {
            try {
                // get root json object
                JSONObject mJsonRoot = new JSONObject(json);
                // get children object below
                JSONObject mJsonRootName = mJsonRoot.getJSONObject(context.getResources().getString(R.string.sandwich_json_name_root));
                String mSandwichName = (String) mJsonRootName.get(context.getResources().getString(R.string.sandwich_json_name_main));
                List<String> mSandwichNameAlternatives = getJsonArrayAsArray((JSONArray) mJsonRootName.get(context.getResources().getString(R.string.sandwich_json_name_alternatives)));
                String mSandwichPlaceOfOrigin = (String) mJsonRoot.get(context.getResources().getString(R.string.sandwich_json_place_of_origin));
                String mSandwichDesc = (String) mJsonRoot.get(context.getResources().getString(R.string.sandwich_json_description));
                String mSandwichImage = (String) mJsonRoot.get(context.getResources().getString(R.string.sandwich_json_image));
                List<String> mSandwichIngredients = getJsonArrayAsArray((JSONArray) mJsonRoot.get(context.getResources().getString(R.string.sandwich_json_ingredients)));
                // return new sandwich object
                return new Sandwich(mSandwichName, mSandwichNameAlternatives, mSandwichPlaceOfOrigin, mSandwichDesc, mSandwichImage, mSandwichIngredients);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // something happened!
        return null;
    }

    /**
     * @param   mJsonArray      JSONArray object to extract data from
     * @return  List<String>    List of string values (only string values in this case) from the JSONArray object
     */
    private static List<String> getJsonArrayAsArray(JSONArray mJsonArray) {
        // declare array to hold the values in the json array
        ArrayList<String> mArray = null;
        if (mJsonArray != null) {
            mArray = new ArrayList<>();
            // loop to get the values
            for (int cursor = 0; cursor < mJsonArray.length(); cursor++) {
                try {
                    // add value to array
                    mArray.add(mJsonArray.getString(cursor));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return mArray;
    }
}

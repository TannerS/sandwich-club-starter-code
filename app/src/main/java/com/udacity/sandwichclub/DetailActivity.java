package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();

        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        this.sandwich = JsonUtils.parseSandwichJson(this, json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     *      UI element populating
     */
    private void populateUI() {
        // variables to hold references to view items
        TextView mName = (TextView) findViewById(R.id.name_tv);
        TextView mNameAlternative = findViewById(R.id.also_known_tv);
        TextView mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        TextView mOrigin = (TextView) findViewById(R.id.origin_tv);
        TextView mDescription = (TextView) findViewById(R.id.description_tv);
        // list item variables
        List<String> mNameAlternativeList = sandwich.getAlsoKnownAs();
        List<String> mIngredientsList = sandwich.getIngredients();

        // check for alternative names, it none, dont display
        if (mNameAlternativeList.size() != 0) {
            mNameAlternative.setText(listToString(mNameAlternativeList));
        } else {
            mNameAlternative.setVisibility(View.GONE);
        }

        // check for ingredients, it none, dont display
        if (mIngredientsList.size() != 0) {
            mIngredients.setText(listToString(mIngredientsList));
        } else {
            (findViewById(R.id.ingredients_container)).setVisibility(View.GONE);
        }

        // check for place of origin, it none, dont display
        if (sandwich.getPlaceOfOrigin() != null && sandwich.getPlaceOfOrigin().length() > 0) {
            mOrigin.setText(sandwich.getPlaceOfOrigin());
        } else {
            (findViewById(R.id.origin_container)).setVisibility(View.GONE);
        }

        // check for description, it none, dont display
        if (sandwich.getDescription() != null && sandwich.getDescription().length() > 0) {
            mDescription.setText(sandwich.getDescription());
        } else {
            (findViewById(R.id.desc_container)).setVisibility(View.GONE);
        }
        // set name, assumed never to be empty
        mName.setText(sandwich.getMainName());
    }

    /**
     * Turn list of strings into comma separated string
     * @param   items     list of strings
     * @return          comma separated string
     */
    private String listToString(List<String> items)
    {
        StringBuilder mBuilder = new StringBuilder();

        for(int i = 0; i < items.size(); i++)
        {
            if(i == items.size() - 1) {
                mBuilder.append(items.get(i));
            }
            else
            {
                mBuilder.append(items.get(i));
                mBuilder.append(", ");
            }
        }

        return mBuilder.toString();
    }
}

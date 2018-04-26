package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
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

    private void populateUI() {

//        private String mainName;
//        private List<String> alsoKnownAs = null;
//        private String placeOfOrigin;
//        private String description;
//        private String image;
//        private List<String> ingredients = null;

//
//        TextView mName = (TextView) findViewById(R.id.name_tv);
//        TextView mNameAlt = (TextView) findViewById(R.id.also_known_tv);
//        TextView mIngredients = (TextView) findViewById(R.id.ingredients_tv);
//        TextView mOrigin= (TextView) findViewById(R.id.origin_tv);
//        TextView mDescription = (TextView) findViewById(R.id.description_tv);


        ((TextView) findViewById(R.id.name_tv)).setText(sandwich.getMainName());
        ((TextView) findViewById(R.id.also_known_tv)).setText(listToString(sandwich.getAlsoKnownAs()));
        ((TextView) findViewById(R.id.ingredients_tv)).setText(listToString(sandwich.getIngredients()));
        ((TextView) findViewById(R.id.origin_tv)).setText(sandwich.getPlaceOfOrigin());
        ((TextView) findViewById(R.id.description_tv)).setText(sandwich.getDescription());

    }

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

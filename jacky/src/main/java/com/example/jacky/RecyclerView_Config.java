package com.example.jacky;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.jacky.Model.Foods;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private FoodsAdapter mFoodAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Foods> foods, List<String> keys){
        mContext = context;
        mFoodAdapter = new FoodsAdapter(foods, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mFoodAdapter);
    }

    class FoodItemView extends RecyclerView.ViewHolder{
        private TextView mName;
        //private TextView mScore;
        private TextView mDescription;
        private RatingBar mScore;

        private String key;

        public FoodItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.food_list_item, parent, false));

            mName = (TextView) itemView.findViewById(R.id.name_txtView);
            //mScore = (TextView) itemView.findViewById(R.id.price_txtView);
            mDescription = (TextView) itemView.findViewById(R.id.description_txtView);
            mScore = (RatingBar) itemView.findViewById(R.id.Score_ratingBar);
        }
        public void bind(Foods food, String key){
            mName.setText(food.getCustomer_Name());
            //mScore.setText(food.getScore());
            mDescription.setText(food.getDescription());
            mScore.setRating(Float.valueOf(food.getScore()));
            this.key = key;
        }
    }
    class FoodsAdapter extends RecyclerView.Adapter<FoodItemView> {
        private List<Foods> foodList;
        private List<String> mKeys;

        public FoodsAdapter(List<Foods> foodList, List<String> mKeys) {
            this.foodList = foodList;
            this.mKeys = mKeys;
        }

        @Override
        public FoodItemView onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new FoodItemView(viewGroup);
        }

        @Override
        public void onBindViewHolder(FoodItemView foodItemView, int i) {
            foodItemView.bind(foodList.get(i), mKeys.get(i));
        }

        @Override
        public int getItemCount() {
            return foodList.size();
        }
    }
}

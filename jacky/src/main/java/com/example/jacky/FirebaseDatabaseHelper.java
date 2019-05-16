package com.example.jacky;

import android.support.annotation.NonNull;

import com.example.jacky.Model.Foods;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceFood;
    private List<Foods> foods = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Foods> foods, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceFood = mDatabase.getReference("Comment");
    }

    public void readMenu(final DataStatus dataStatus){
        mReferenceFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foods.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Foods food = keyNode.getValue(Foods.class);
                    foods.add(food);
                }
                dataStatus.DataIsLoaded(foods, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addComment(Foods food, final DataStatus dataStatus){
        String key = mReferenceFood.push().getKey();
        mReferenceFood.child(key).setValue(food).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }
}

package sample.firebaseexpense.com;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExpensesView extends Activity {
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> dataList;
    DatabaseReference mdataRef;
    CustomAdapter mAdapter;

   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_expenseview);
        mdataRef = FirebaseDatabase.getInstance().getReference("ExpenseManager");
       Toolbar toolbar=findViewById(R.id.toolbar);
       toolbar.setTitle("Expense Manager");
       dataList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

   }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        mdataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               dataList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    DataModel dataModel = postSnapshot.getValue(DataModel.class);
                    Log.e("aa", dataModel.getId());
                    //adding artist to the lista
                    dataList.add(dataModel);

                    mAdapter = new CustomAdapter(dataList, getApplicationContext());
                }
                //creating adapter
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error", databaseError.toString());

            }
        });


    }



}
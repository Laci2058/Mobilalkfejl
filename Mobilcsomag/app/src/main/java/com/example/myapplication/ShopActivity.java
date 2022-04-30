package com.example.myapplication;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {
    private FirebaseUser user;
    String[] nevek;
    String[] leiras;
    String[] ar;
    TypedArray imgresource;
    private ArrayList<Item> itemList;
    private RecyclerView recyclerView;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    private FrameLayout redCircle;
    private TextView contentText;
    private int cartItems = 0;
    private ItemAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nevek = getResources().getStringArray(R.array.item_names);
        leiras = getResources().getStringArray(R.array.item_description);
        ar = getResources().getStringArray(R.array.item_prices);
        imgresource = getResources().obtainTypedArray(R.array.shopping_item_images);
        setContentView(R.layout.activity_shop);

        itemList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            finish();
        }

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");

        queryData();

    }

    private void queryData() {
        itemList.clear();

        mItems.orderBy("nev").limit(3).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Item elem = document.toObject(Item.class);
                itemList.add(elem);
            }
            if (itemList.size() == 0) {
                setUserInfo();
                queryData();
            }
            setAdapter();
        });
    }

    private void setAdapter() {
        ItemAdapter adapter = new ItemAdapter(this, itemList);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setUserInfo() {
        for (int i = 0; i < nevek.length; i++) {
            mItems.add(new Item(nevek[i], leiras[i], ar[i], imgresource.getResourceId(i, 0)));
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.kosar:
                Toast.makeText(this, "kosár", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.settings:
                Toast.makeText(this, "beállítások", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.kosar);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        redCircle = (FrameLayout) rootView.findViewById(R.id.view_alert_red_circle);
        contentText = (TextView) rootView.findViewById(R.id.view_alert_count_textview);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(alertMenuItem);
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    public void updateAlertIcon() {
        cartItems += 1;
        if (cartItems > 0) {
            contentText.setText(String.valueOf(cartItems));
        } else {
            contentText.setText("");
        }
        redCircle.setVisibility(cartItems > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
}
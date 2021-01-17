package com.bce.passwordsaver;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    public static final int ADD_ITEM_REQUEST = 1;
    public static final int EDIT_ITEM_REQUEST = 2;

    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ItemAdapter adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                itemViewModel.delete(adapter.getItemAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                Intent intent = new Intent(MainActivity.this, AddEditItemActivity.class);
                intent.putExtra(AddEditItemActivity.EXTRA_ID, item.getId());
                intent.putExtra(AddEditItemActivity.EXTRA_TITLE, item.getTitle());
                intent.putExtra(AddEditItemActivity.EXTRA_USERID, item.getUserid());
                intent.putExtra(AddEditItemActivity.EXTRA_PASSWORD, item.getPassword());
                intent.putExtra(AddEditItemActivity.EXTRA_DESCRIPTION, item.getDescription());
                startActivityForResult(intent, EDIT_ITEM_REQUEST);

            }
        });

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                adapter.setItems(items);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditItemActivity.class);
                startActivityForResult(intent, ADD_ITEM_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            itemViewModel.deleteAllItems();
            Toast.makeText(this, "All Information Deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditItemActivity.EXTRA_TITLE);
            String userid = data.getStringExtra(AddEditItemActivity.EXTRA_USERID);
            String password = data.getStringExtra(AddEditItemActivity.EXTRA_PASSWORD);
            String description = data.getStringExtra(AddEditItemActivity.EXTRA_DESCRIPTION);

            Item item = new Item(title, userid, password, description);
            itemViewModel.insert(item);

            Toast.makeText(this, "Information Saved Succesfully", Toast.LENGTH_LONG).show();
        } else if (requestCode == EDIT_ITEM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditItemActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Information can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditItemActivity.EXTRA_TITLE);
            String userid = data.getStringExtra(AddEditItemActivity.EXTRA_USERID);
            String password = data.getStringExtra(AddEditItemActivity.EXTRA_PASSWORD);
            String description = data.getStringExtra(AddEditItemActivity.EXTRA_DESCRIPTION);

            Item item = new Item(title, userid, password, description);
            item.setId(id);
            itemViewModel.update(item);
            Toast.makeText(this, "Information updated successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Information Not Saved", Toast.LENGTH_LONG).show();
        }
    }
}
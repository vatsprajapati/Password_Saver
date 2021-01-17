package com.bce.passwordsaver;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("ALL")
@Database(entities = {Item.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {

    private static ItemDatabase instance;

    public abstract ItemDao itemDao();

    public static synchronized ItemDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ItemDatabase.class, "item_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private ItemDao itemDao;

        private PopulateDbAsyncTask(ItemDatabase database){
            itemDao = database.itemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.insert(new Item("Google","vatslprajpati2604@gmail.com","Vatsal@2604", "I always Forget this."));
            itemDao.insert(new Item("Yahoo", "vatsalprajapati2604@yahoo.com","Vatsasl@2604", "I never remember this."));
            itemDao.insert(new Item("Title","User Id","Password","I don't have to remeber this Description"));
            return null;
        }
    }

}

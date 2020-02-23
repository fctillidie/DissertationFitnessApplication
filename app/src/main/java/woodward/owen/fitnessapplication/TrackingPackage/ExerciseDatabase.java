package woodward.owen.fitnessapplication.TrackingPackage;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import woodward.owen.fitnessapplication.ExercisePackage.Exercise;

@Database(entities = Exercise.class, version = 1)
@TypeConverters({Converters.class})
public abstract class ExerciseDatabase extends RoomDatabase {

    private static ExerciseDatabase instance;

    public abstract ExerciseDao exerciseDao();

    public static synchronized ExerciseDatabase getInstance(Context context) { //synchronized means only one thread at a time can access the method
        if(instance == null) {
            synchronized (ExerciseDatabase.class){ //This was altered here with an extra synchronized
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), ExerciseDatabase.class, "exericse_database")
                            .fallbackToDestructiveMigration().addCallback(roomCallBack).build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExerciseDao dao;

        private PopulateDbAsyncTask(ExerciseDatabase db){
            dao = db.exerciseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.Insert(new Exercise("Leg Curl", 10, 50, 4, "1"));
            dao.Insert(new Exercise("Flat Barbell Bench Press", 15, 70, 9, "2"));
            dao.Insert(new Exercise("Bicep Curl", 15, 60, 6, "3"));

            return null;
        }
    }
}
package woodward.owen.fitnessapplication.weight_tracking_package;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.Objects;

import woodward.owen.fitnessapplication.exercise_package.Exercise;

public class AddExerciseViewModel extends AndroidViewModel {
    private ExerciseRepository repository;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String WEIGHT = "Weight";
    private static final String REPS = "Reps";
    private static final String RPE = "Rpe";
    private SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

    public AddExerciseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
    }

    //Wrapper methods for the repository from the ViewModel
    public void Insert(Exercise exercise) {
        repository.Insert(exercise);
    }

    public void Update(Exercise exercise) {
        repository.Update(exercise);
    }

    public void saveSharedPrefData (String weight, String reps, String rpe) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WEIGHT, weight);
        editor.putString(REPS, reps);
        editor.putString(RPE, rpe);
        editor.apply();
    }

    public String loadWeightSharedPreference () {
        return sharedPreferences.getString(WEIGHT, "");
    }

    public String loadRepsSharedPreference () {
        return sharedPreferences.getString(REPS, "");
    }

    public String loadRPESharedPreference () {
        return sharedPreferences.getString(RPE, "");
    }

    public void cleanSharedPreferences () {
        sharedPreferences.edit().clear().apply();
    }
}
package woodward.owen.fitnessapplication.weight_tracking_package;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import woodward.owen.fitnessapplication.exercise_package.Exercise;

public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseRepository repository;
    private static MutableLiveData<Exercise> cachedExercise;
    private MutableLiveData<String> currentDate = new MutableLiveData<>();
    private LiveData<List<Exercise>> allExercises = Transformations.switchMap(currentDate, (date) -> repository.getAllExercises(date));

    public ExerciseViewModel(@NonNull Application application) {
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

    public void Delete(Exercise exercise) {
        repository.Delete(exercise);
    }

    public void DeleteAllExercises(String date) {
        repository.DeleteAllExercises(date);
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public void setDate(String date) {
        currentDate.setValue(date);
    }

    public MutableLiveData<String> getCurrentDate() {
        return currentDate;
    }

    public MutableLiveData<Exercise> getCurrentCachedExercise() {
        return cachedExercise;
    }

    public void setCachedExercise(Exercise e) {
        if (cachedExercise == null) {
            cachedExercise = new MutableLiveData<>();
        }
        cachedExercise.setValue(e);
    }

    //Conversion of date to stop data conversion conflicts
    public String ConvertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + (input);
        }
    }
}
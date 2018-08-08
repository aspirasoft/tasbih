package pk.aspirasoft.tasbih.data;

import android.app.Activity;

import java.util.ArrayList;

public class CounterManager extends ArrayList<Counter> {

    private static CounterManager ourInstance;
    private final Database database;

    private boolean isInitialized;

    private CounterManager(Activity activity) {
        database = Database.getInstance(activity);
        isInitialized = database.isInitialized();
        diskIn();
    }

    public static CounterManager getInstance(Activity activity) throws NullPointerException {
        if (ourInstance == null) {
            ourInstance = new CounterManager(activity);
        }

        return ourInstance;
    }

    public void addDefaultCounters(ArrayList<Counter> counters) {
        this.addAll(counters);
        diskOut();
        database.initialize();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void diskOut() {
        database.put(toString());
    }

    private void diskIn() {
        String counters = database.get();

        if (counters != null) {
            fromString(counters);
        }
    }

    public void fromString(String counters) {
        clear();

        for (String s : counters.split("\n")) {
            if (!s.replace("\t", "").replace(" ", "").equals("")) {
                try {
                    Counter counter = new Counter(s);
                    add(counter);
                } catch (ArrayIndexOutOfBoundsException ignored) {

                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            string.append(get(i).toString()).append("\n");
        }
        string.append("");
        return string.toString();
    }

    @Override
    public boolean add(Counter object) {
        for (Counter c : this) {
            if (c.getName().equals(object.getName()))
                return false;
        }

        return super.add(object);
    }
}

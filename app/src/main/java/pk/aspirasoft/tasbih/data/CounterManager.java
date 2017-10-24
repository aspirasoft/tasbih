package pk.aspirasoft.tasbih.data;

import android.app.Activity;

import java.util.ArrayList;

public class CounterManager extends ArrayList<Counter> {

    private static CounterManager ourInstance;
    private final Database database;

    private CounterManager(Activity activity) {
        database = Database.getInstance(activity);
        diskIn();
    }

    public static CounterManager getInstance(Activity activity) throws NullPointerException {
        if (ourInstance == null) {
            ourInstance = new CounterManager(activity);
        }

        return ourInstance;
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
                Counter counter = new Counter(s);
                add(counter);
            }
        }
    }

    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < size(); i++) {
            string += get(i).toString() + "\n";
        }
        string += "";
        return string;
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

package pk.aspirasoft.tasbih.data;

public class Counter {

    private static final String sep = "\t";

    private final String name;
    private final String description;
    private int value;
    private int max;

    public Counter(String rawData) throws IndexOutOfBoundsException {
        String[] data = rawData.split(sep);
        this.name = data[0];
        this.description = data[1];
        this.value = Integer.parseInt(data[2]);

        try {
            this.max = Integer.parseInt(data[3]);
        } catch (IndexOutOfBoundsException ex) {
            this.max = 100;
        }
    }

    public Counter(String name, String description) {
        this.name = name;
        this.description = description;
        this.value = 0;
        this.max = 100;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) throws ArithmeticException {
        if (value < 0)
            throw new ArithmeticException();

        this.value = value;
    }

    public void increment() {
        this.value++;
    }

    public void decrement() {
        if (this.value > 0) this.value--;
    }

    public void reset() {
        this.value = 0;
    }

    @Override
    public String toString() {
        String string = "";
        string += name + sep;
        string += description + sep;
        string += String.valueOf(value) + sep;
        string += String.valueOf(max);
        return string;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
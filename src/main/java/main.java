import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {

        Fighter fi = new Fighter("OPN", "KISS");

        Map<String, String> s = new HashMap<>();
        s.put("ti", "to");
        s.put("tx", "tea");


        for (Map.Entry<String, String> z: s.entrySet()) {
            System.out.println("Task: " + z.getKey() + " | Status: " + z.getValue());
        }



    }
}

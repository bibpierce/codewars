import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class Employee {
    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    int id;
    String name;
}


class Records {
    public int getId() {
        return id;
    }

    public Records(int id, int employeeId, String role) {
        this.id = id;
        this.employeeId = employeeId;
        this.role = role;
    }

    int id;
    int employeeId;
    String role;
}

class test{
    public static Map<String, List<Records>> test(List<Employee> employees, List<Records> records){

        Map<String, List<Records>> result = new HashMap<>();
        for (int i = 0; i < employees.size(); i++) {
            List<Records> recordsList = new ArrayList<>();
            int finalInt = i;
            recordsList = records.stream().filter(x -> x.employeeId == employees.get(finalInt).id).toList();
            result.put(employees.get(i).getName(),recordsList);
        }
        return result;
    }

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "tite"));
        employees.add(new Employee(2, "jabol"));
        employees.add(new Employee(3, "krack"));
        employees.add(new Employee(4, "knickknowcks"));
        employees.add(new Employee(5, "lily"));

        List<Records> records = new ArrayList<>();
        records.add(new Records(1,1, "negg"));
        records.add(new Records(2,1, "pulubi"));
        records.add(new Records(3,3, "admin"));
        records.add(new Records(4,4, "chekwa"));
        records.add(new Records(5,5, "yummy"));
        records.add(new Records(6,5, "user"));
        records.add(new Records(7,2, "bantay"));

        System.out.println(test(employees, records));

    }
}
import java.util.List;

/**
 * Main class that calls all example methods
 *
 * @author Gary A. Stafford
 */
public class MainProgramm {

    private static final EmployeeImplementation examples = new EmployeeImplementation();
    private static final ProcessTimer timer = new ProcessTimer();

    /**
     * @param args the command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println();
        System.out.println("SQL SERVER STATEMENT EXAMPLES");
        System.out.println("======================================");

        // Statement example, no parameters, returns Integer
        timer.setStartTime(System.nanoTime());
        List<Employee> employeeList = examples.getEmployeeById("2");
        System.out.println("Method: GetProductsByColorAndSizeCS");
        System.out.println("Description: CallableStatement, (2) input parameter, returns ResultSet");
        System.out.printf("Duration (ms): %d%n", timer.getDuration());
        if (employeeList.size() > 0) {
            System.out.printf("Results: Employee found (ID: '%s'): %d%n",1, employeeList.size());
            System.out.printf("         First Employee: %s (%s)%n", employeeList.get(0).getID(), employeeList.get(0).getName());
        } else {
            System.out.printf("No Employee found with Id '%s' '%n", 1);
        }
        System.out.println("---");

        examples.closeConnection();
    }
}
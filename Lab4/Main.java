import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfUpdates = sc.nextInt();
        Roster roster = new Roster("Default");
        while (numOfUpdates-- > 0) {
            String student = sc.next();
            String course = sc.next();
            String assessment = sc.next();
            String grade = sc.next();
            roster = roster.add(student, course, assessment, grade);
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
        }

        String output = "";
        while (true) {
            String student = sc.next();
            String course = sc.next();
            String assessment = sc.next();
            output += roster.getGrade(student, course, assessment);
            if (sc.hasNextLine() && sc.hasNext()) {
                sc.nextLine();
                output += "\n";
                continue;
            }
            break;
        }

        System.out.println(output);
        sc.close();
    }
}

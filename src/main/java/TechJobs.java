import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by (type 'x' to quit):", actionChoices);

            if (actionChoice == null) {
                break;
            } else if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            if (in.hasNextInt()) {
                choiceIdx = in.nextInt();
                in.nextLine();
            } else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");
                if (shouldQuit) {
                    return null;
                }
            }

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

//for each job in the arraylist, iterate through the hashmap at that index and print the jobs
        if (someJobs.size() > 0) {

            for (int index = 0; index < someJobs.size(); index++) {
                System.out.println("*****");
                for (Map.Entry<String, String> jobs : someJobs.get(index).entrySet()) {

                    System.out.println(jobs.getKey() + ": " + jobs.getValue());

                }
                System.out.println("*****\n");
            }
        } else {
            System.out.println("No Results");
        }

        //Tip
        //To do this, you’ll need to iterate over an ArrayList of jobs. Each job is itself a HashMap. While you can get
        //each of the items out of the HashMap using the known keys (employer, location, etc.), think instead about
        // creating a nested loop to loop over each HashMap. If a new field is added to the job records, this approach
        // will print out the new field without any updates to printJobs.
    }
}

//Write the code for the task, verifying manually that it works by running the TechJobs.main method.
//When you think you’ve completed a task, run the individual test that corresponds to the task.
//If the test fails, review the test output and go back to your code to try to fix it.
//Once the single test passes, run all of the tests to make sure you didn’t break any tests that previously passed.
//Repeat this process until all tests pass.

//For the autograding script to correctly grade your code, you’ll need to match this format exactly. In particular,
// note the number of asterisks surrounding each listing, and the blank line between listings.
//
//If there are no results, it should print No Results. Again, you should use this exact message.
//
//*****
//        position type: Web - Back End
//        name: Ruby specialist
//        employer: LaunchCode
//        location: Saint Louis
//        core competency: Javascript
//        *****
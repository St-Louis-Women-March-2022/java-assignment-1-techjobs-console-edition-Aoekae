import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        // Bonus mission: sort the results
        Collections.sort(values);

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        // Bonus mission; normal version returns allJobs
        return new ArrayList<>(allJobs);
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);

            if (aValue.contains(value)) {
                jobs.add(row);
            }
        }

        return jobs;
    }

    /**
     * Search all columns for the given term
     *
     * @param value The search term to look for
     * @return      List of all jobs with at least one field containing the value
     */
    public static ArrayList<HashMap<String, String>> findByValue(String value) {

        // load data, if not already loaded
        loadData();

        // TODO - implement this method

        ArrayList<HashMap<String, String>> selectedJobs = new ArrayList<>();

        for (HashMap<String, String> job : allJobs) { //for each job in all jobs
            for (Map.Entry<String, String> jobs : allJobs.get(allJobs.indexOf(job)).entrySet()) { //loop through the job hashmap
                if (jobs.getKey().contains(value)) { //if the hashmap keys contain the value
                    if (!selectedJobs.contains(job)) { //check if the selectedJobs arrayList contains the search term
                        selectedJobs.add(allJobs.get(allJobs.indexOf(job))); //if it doesn't, add the job to that arraylist

                    }

                } else if (jobs.getValue().contains(value)) { //if the hashmap values contain the value
                    if (!selectedJobs.contains(job)) { //check if the selectedJobs arrayList contains the search term
                        selectedJobs.add(allJobs.get(allJobs.indexOf(job))); //if it doesn't, add the job to that arraylist
                    }
                    }
            }
        }

//The code that you write should not contain duplicate jobs.

//You’ll need to call findByValue from somewhere in main. We’ll leave it up to you to find where. You might have
// noticed that when you try to search all columns using the app, a message is printed, so that is a good clue to help
// you find where to place this new method call. Once you find where to call your new method, you can Run the program
// again to test your code.

        //TODO
        //loop through each job and see if it contains the search term

        //TODO:
        //to make sure there are no duplicates, check if the second arrayList contains the job before putting the job
        //into the new arrayList.

        //TODO:
        //read findByColumnAndValue, which will look similar to my finished code in some ways


        return selectedJobs;
    }

    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}

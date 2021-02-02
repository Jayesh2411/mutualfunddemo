import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class MutualFundProcessor {

    static String urlString = "https://api.mfapi.in/mf/102885";

    public MutualFundProcessor() {
        // TODO Auto-generated constructor stub
    }

    private static String getFormattedDate(LocalDate previousDate) {
        return previousDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void process(int periodOfInvestment, int horizon) {

        LocalDate previousDate = LocalDate.now().minusDays(1l);
        String previousDateString = getFormattedDate(previousDate);

        try {
            URL url = new ConnectionImpl().getURLConnection(urlString);

            String allData = "";
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                allData += scanner.nextLine();
            }

            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            jsonObject = (JSONObject) jsonParser.parse(allData);
            jsonArray = (JSONArray) jsonObject.get("data");

            HashMap<String, String> dateAndNav = new HashMap<String, String>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                dateAndNav.put(obj.get("date").toString(), obj.get("nav").toString());
            }
            previousDate = previousDate.minusYears(horizon);
            for(int i=0;i<horizon*12;i++) {
            	LocalDate endDate = previousDate.plusMonths(i);
                String endDateString = getFormattedDate(endDate).trim();
                
                while (true) {
                	if(dateAndNav.containsKey(endDateString)) {
                		break;
                	}
                    endDate = endDate.minusDays(1l);
                    endDateString = getFormattedDate(endDate);
                }
                LocalDate startDate = endDate.minusYears(periodOfInvestment);
                String startDateString = getFormattedDate(startDate);
                while (true) {
                	if(dateAndNav.containsKey(startDateString)) {
                		break;
                	}
                    startDate = startDate.minusDays(1l);
                    startDateString = getFormattedDate(startDate);
                }
                double startNav = Double.parseDouble(dateAndNav.get(startDateString));
                double endNav = Double.parseDouble(dateAndNav.get(endDateString));
                double returns = Math.pow((endNav / startNav), 1 / periodOfInvestment)-1;
                System.out.println("Return :"+returns*100);
                System.out.println("Start NAV :" +startNav);
                System.out.println("End NAV :" +endNav);
                System.out.println("Start Date:" +startDateString);
                System.out.println("End Date:" +endDateString);

            }
            
            scanner.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

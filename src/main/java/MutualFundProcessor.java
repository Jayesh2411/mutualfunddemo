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
                System.out.println(obj.get("date").toString());
                dateAndNav.put(obj.get("date").toString(), obj.get("nav").toString());
            }

            LocalDate startDate = previousDate.minusYears(periodOfInvestment);
            while (!dateAndNav.containsKey(startDate.toString())) {
                System.out.println(dateAndNav.entrySet());
                System.out.println(dateAndNav.keySet());
                startDate = startDate.minusDays(1l);
            }

            LocalDate endDate = previousDate;
            double startNav = Double.parseDouble(dateAndNav.get(getFormattedDate(startDate)));
            double endNav = Double.parseDouble(dateAndNav.get(getFormattedDate(endDate)).toString());
            double returns = Math.pow((endNav / startNav), 1 / periodOfInvestment);
            System.out.println(returns);

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

import com.google.gson.*;
import java.net.*;
import java.io.*;
public class SelenaChatbotMain{
    private static HttpURLConnection connection; //define http connection
    public static void main(String[] args) throws Exception  {
            //Start the bot up.
        SelenaChatbot bot = new SelenaChatbot();
        
        // Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        bot.connect("irc.us.libera.chat");
        
        // Join the #pircbot channel on free node
        bot.joinChannel("#SWLinBot");
        // the bot will send the message to the user first
        bot.sendMessage("#SWLinBot", "here are my commands! Type weather help or covid help!");
    
    }
// making http request for weather API , includes api key
public static String weatherAPI(String city, String country) {
    BufferedReader reader;
    String line;
    StringBuffer responseContent = new StringBuffer();
    try {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city
                + ","+ country + "&units=imperial&APPID="+"08bd99fde35d8ecb2687ac5739360d56");
        //Request setup
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
      //sets connecting timeout after 5 seconds, if the connection isn't successful, it times out
        connection.setConnectTimeout(5000);
        //sets reading timeout after 5 seconds, if the connection isn't successful, it times out
        connection.setReadTimeout(5000);
        int status = connection.getResponseCode();
        //get response call from server, to see if connection is successful
        // read response, build respond content
        if (status > 299) {// if connection has not in the range of connection successfully, user gets error message
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while((line = reader.readLine()) != null) { // we still have things to read
                responseContent.append(line);
            }
            reader.close(); //after the reading is complete, close the reader
        }
        else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
        
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }catch (IOException e) {
        e.printStackTrace();
    }
    finally {
        connection.disconnect();
    }
    return responseContent.toString(); //returns JSON data
}
//parsing JSON for weather
public static String weatherParse(String URL){
    double temperature, highTemp, lowTemp;
    // parsing json object, then narrows down to key values
     JsonObject jsonObject = new JsonParser().parse(URL).getAsJsonObject();
     //json object main
     JsonObject main = jsonObject.getAsJsonObject("main");
     // temp key value inside main
     temperature = main.get("temp").getAsDouble();
     // low_min key value inside main
     lowTemp = main.get("temp_min").getAsDouble();
     //temp_max key value inside main
     highTemp = main.get("temp_max").getAsDouble();
   
     //returning doubles into string
     String result = "The weather is going to be " + temperature+ " \u00B0F"+
     " with a high of "+ highTemp+ " \u00B0F and a low of " + lowTemp+" \u00B0F";
   
    return result;
}    
//making http request for covid19 API
public static String covidAPI(String countryName) {
    BufferedReader reader;
    String line;
    StringBuffer responseContent = new StringBuffer();
    
    try {
        // covid 19 api doesn't require api key
        URL url2 = new URL("https://coronavirus-19-api.herokuapp.com/countries/" + countryName);
        // similar to our we get http request for weather api, except we did not include api key in the url
        connection = (HttpURLConnection) url2.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
 
        int status = connection.getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        connection.disconnect();
    }
    return responseContent.toString();
   
}
// parsing JSON for covid status
public static String covid19Parse(String URL2){
 
    String countryNamePart;
    int cases, todayCase;
    int deaths,todayDeaths;
    int recovered, active, critical;
    int casesPerOneMillion, deathsPerOneMillion;
    int totalTests, testsPerOneMillion;
    // parsing json object, then narrows down to key values
     JsonObject jsonObject = new JsonParser().parse(URL2).getAsJsonObject();
     JsonObject nameless = jsonObject.getAsJsonObject();
     // get country name
     countryNamePart = nameless.get("country").getAsString();
     // get number of cases
      cases = nameless.get("cases").getAsInt();
     // get today's cases
     todayCase = nameless.get("todayCases").getAsInt();
     // get number of deaths
      deaths = nameless.get("deaths").getAsInt();
     //get today's deaths
      todayDeaths = nameless.get("todayDeaths").getAsInt();
     // get recovery
      recovered = nameless.get("recovered").getAsInt();
     // get active cases
     active = nameless.get("active").getAsInt();
     // get critical cases
     critical = nameless.get("critical").getAsInt();
     // get cases per million
     casesPerOneMillion = nameless.get("casesPerOneMillion").getAsInt();
      //get deaths per million
     deathsPerOneMillion = nameless.get("deathsPerOneMillion").getAsInt();
     //get total tests
     totalTests = nameless.get("totalTests").getAsInt();
     //get tests per one million
     testsPerOneMillion = nameless.get("testsPerOneMillion").getAsInt();
     //returning ints into string
     String result2 = "country name : " + countryNamePart + " number of cases: " + cases  
             + " today's cases: "+ todayCase + " number of deaths: " + deaths  + " today's death: " +
             todayDeaths + " recovery: " + recovered + " active:" + active + " critical:"+ critical +
              " casesPerOneMillion:"+ casesPerOneMillion  + " deathsPerOneMillion:"+deathsPerOneMillion+
             " totalTests:"+totalTests+ " testsPerOneMillion:"+testsPerOneMillion;
     return result2;
    
}    
 
 }// end of chatbot main class


import org.jibble.pircbot.*; //http://www.jibble.org/pircbot.php
// https://web.libera.chat/
public class SelenaChatbot extends PircBot { // child class of Pirbot

    public SelenaChatbot() {
           this.setName("SelenaWLinBot");// chatbot name
       }
       
   //read a message from the channel
    public void onMessage(String channel, String sender, String login, String hostname, String message)
 {
    // this function reads the message that comes in
    if (message.equalsIgnoreCase("weather help")) {
       
         sendMessage(channel,"When getting the weather information, please type in the word 'weatherinfo' " +
                 "followed by a space, then the city name and country name with " +
                 "commas in between. For example, weatherinfo tokyo,japan");
            
        
    }
   if(message.toLowerCase().contains("weatherinfo")) {
       String city, country,weather;    
       weather=message.substring(0, message.indexOf(' '));
       message = message.substring(weather.length()+1);
       city=message.substring(0, message.indexOf(',')); // adds a comma after city input
       message = message.substring(city.length()+2);
       country=message;
       // the user needs to type in weatherinfo city,country to get the weather information
       // makes weather api call
       sendMessage(channel, SelenaChatbotMain.weatherParse(SelenaChatbotMain.weatherAPI(city, country)));

   }
   if (message.equalsIgnoreCase("covid help")) {
       
       sendMessage(channel,"When getting covid information, please type in the word 'covidinfo' " +
               "followed by a space, then type the country name with " +
               "commas in between. For example: covidinfo,china  OR covidinfo china");
  }
   if(message.toLowerCase().contains("covidinfo")) {
       String country=message.substring(10); // ignores first 10 characters including space or commas
      // user input has to be covidinfo countryName
       // makes covid api call, it'll take a 4 seconds
       sendMessage(channel, SelenaChatbotMain.covid19Parse(SelenaChatbotMain.covidAPI(country)));

   }
   //greetings
    if (message.equalsIgnoreCase("Hello")) {
     
    sendMessage(channel, "Hey " + sender + "!" );
    sendMessage(channel, "Please type the following:"+
                "weather help or covid help");

    }  
    //greetings
    if (message.equalsIgnoreCase("How are you")) {
       
        sendMessage(channel, "I am doing well. Thanks for asking" );
        sendMessage(channel, "Please type the following:"+
                "weather help or covid help");
       }

}
   
}

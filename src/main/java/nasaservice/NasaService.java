package nasaservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

public class NasaService {

    String nasaUrl = "https://api.nasa.gov/planetary/apod?api_key=";

    public NasaService() {
        nasaUrl = "https://api.nasa.gov/planetary/apod?api_key="+NasaAPIAuth.NASA_API_TOKEN  ;
    }


    public String getNasaUrl(String optionalDate) throws IOException {
        NasaAnswerDTO nasaDto = getNasaObject(optionalDate);
        return nasaDto.url;
    }


    public NasaAnswerDTO getNasaObject(String optionalDate) throws IOException {

        String effectiveUrl = nasaUrl + optionalDate;

        ObjectMapper mapper = new ObjectMapper();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(effectiveUrl);
        NasaAnswerDTO answer;
        try (CloseableHttpResponse response = client.execute(request)) {

            answer = mapper.readValue(response.getEntity()
                            .getContent(),
                    NasaAnswerDTO.class);
            String pictureUrl = answer.url;
            return answer;
        } catch (IOException e){
            System.out.println("NASA connection error!");
        }
        return null;

    }

}

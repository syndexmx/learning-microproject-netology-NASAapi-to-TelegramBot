package nasaservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NasaAnswerDTO {

      String date;

      String explanation;
      String hdurl;
      String media_type;
      String service_version;
      String  title;

      String url;
      String copyright;
      String code;
      String msg;

      public NasaAnswerDTO(@JsonProperty("date") String date,
                           @JsonProperty("explanation") String explanation,
                              @JsonProperty("hdurl") String hdurl,
                              @JsonProperty("media_type") String media_type,
                              @JsonProperty("service_version") String service_version,
                              @JsonProperty("title") String title,
                              @JsonProperty("copyright") String copyright,
                              @JsonProperty("code") String code,
                              @JsonProperty("msg") String msg,
                              @JsonProperty("url") String url) {
            this.date = date;
            this.explanation = explanation;
            this.hdurl = hdurl;
            this.media_type = media_type;
            this.service_version = service_version;
            this.title = title;
            this.copyright = copyright;
            this.code = code;
            this.msg = msg;
            this.url = url;
      }

      public String getExplanation() {
            return explanation;
      }

      public String getUrl() {
            return url;
      }

      public String getTitle() {
            return title;
      }
}

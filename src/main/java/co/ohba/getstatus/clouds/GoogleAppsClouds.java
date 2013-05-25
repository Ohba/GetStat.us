package co.ohba.getstatus.clouds;

import co.ohba.getstatus.enums.ResponseType;
import co.ohba.getstatus.enums.Status;
import com.mashape.unirest.http.HttpResponse;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Slf4j
public class GoogleAppsClouds {

    public static abstract class AbstractGoogleAppsCloud implements Cloud<String> {

        private static ObjectMapper om = new ObjectMapper();
        private static String urlStr = "http://www.google.com/appsstatus/json/en",
            JSONP1 = "dashboard.jsonp(", JSONP2 = ");";
        private static URL url = null;

        private static Response buildJson(String body) throws IOException {
            log.debug("response body is {}", body);

            // strip of json-padding
            String json = body.substring(JSONP1.length(), body.length() - JSONP2.length());
            log.debug("response json is {}", json);

            return om.readValue(json, Response.class);
        }

        @Getter Status status;
        @Getter DateTime timestamp;
        @Getter ResponseType responseType = ResponseType.TEXT;

        @Override
        public URL getUrl() throws MalformedURLException {
            if (url == null) {
                url = new URL(urlStr);
            }
            return url;
        }

        @Override
        public void parse(HttpResponse<String> response, DateTime timestamp) {
            this.timestamp = timestamp;
            status = Status.OK;

            Response res = null;
            try {
                res = buildJson(response.getBody());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                status = Status.ERROR;
            }



            Integer serviceId=null;
            for(Response.Service s : res.getServices()){
                if(getServiceName().equals(s.getName())){
                    serviceId = s.getId();
                    break;
                }
            }
            if(serviceId ==null) {
                status=Status.WARNING;
            } else {
                for(Response.Message m : res.getMessages()){
                    if(serviceId.equals(m.getService()) && m.getResolved()!=Boolean.TRUE){
                        status=Status.ERROR;
                    }
                }
            }
        }

        abstract String getServiceName();
    }

    public static class GoogleAppsGmailCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Gmail";
        @Getter String serviceName = "Gmail";
    }

    public static class GoogleAppsCalendarCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Calendar";
        @Getter String serviceName = "Google Calendar";
    }

    public static class GoogleAppstalkCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Talk";
        @Getter String serviceName = "Google Talk";
    }

    public static class GoogleAppsDriveCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Drive";
        @Getter String serviceName = "Google Drive";
    }

    public static class GoogleAppsDocumentsCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Documents";
        @Getter String serviceName = "Google Documents";
    }

    public static class GoogleAppsSpreadsheetsCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Spreadsheets";
        @Getter String serviceName = "Google Spreadsheets";
    }

    public static class GoogleAppsPresentationsCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Presentations";
        @Getter String serviceName = "Google Presentations";
    }

    public static class GoogleAppsDrawingsCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Drawings";
        @Getter String serviceName = "Google Drawings";
    }

    public static class GoogleAppsSitesCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Sites";
        @Getter String serviceName = "Google Sites";
    }

    public static class GoogleAppsGroupsCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Groups";
        @Getter String serviceName = "Google Groups";
    }

    public static class GoogleAppsAdminControlApiCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-AdminControlAPI";
        @Getter String serviceName = "Admin control panel / API";
    }

    public static class GoogleAppsPostiniCloud extends AbstractGoogleAppsCloud {
        @Getter String name = "Google-Apps-Postini";
        @Getter String serviceName = "Postini Services";
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        List<Service> services;
        List<Message> messages;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Service {
            Integer id, sort;
            String name;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Message {
            Integer service, type;
            String pst, message;
            Boolean resolved, premier;
        }

    }
}

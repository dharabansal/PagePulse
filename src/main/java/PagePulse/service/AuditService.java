package PagePulse.service;


import PagePulse.dto.AuditResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import org.jsoup.HttpStatusException;

@Service
public class AuditService {

    public AuditResponse analyzeWebsite(String url) {

        long startTime = System.currentTimeMillis();

        AuditResponse response = new AuditResponse();

        try {
            Connection.Response pageResponse =
                    Jsoup.connect(url).ignoreHttpErrors(true).userAgent("Mozilla/5.0").timeout(5000)
                            .execute();
            String contentType = pageResponse.contentType();
            if(contentType == null || !contentType.contains("text/html")){

                response.setUrl(url);
                response.setStatusCode(415);
                response.setMessage("Non HTML content");
                long endTime = System.currentTimeMillis();

                response.setResponseTime(endTime - startTime);

                return response;
            }
            Document document = pageResponse.parse();



            response.setUrl(url);
            response.setTitle(document.title());
            response.setStatusCode(pageResponse.statusCode());
            response.setWordCount(document.text().split("\\s+").length);
            response.setH1Count(document.select("h1").size());
            // SEO checks

            boolean hasTitle = !document.title().isBlank();

            boolean hasMetaDescription =
                    document.select("meta[name=description]").size() > 0;
            String metaDescription = "";

            if(hasMetaDescription){
                metaDescription = document
                        .select("meta[name=description]")
                        .attr("content");
            }
            else{
                metaDescription = "Not available";
            }

            int imagesMissingAlt =
                    document.select("img:not([alt]), img[alt='']").size();


            int seoScore = 0;

            if (hasTitle) {
                seoScore += 20;
            }

            if (hasMetaDescription) {
                seoScore += 20;
            }

            if (response.getH1Count() > 0) {
                seoScore += 20;
            }

            if (imagesMissingAlt==0) {
                seoScore += 20;
            }

            if (response.getWordCount() > 300) {
                seoScore += 20;
            }


            response.setSeoScore(seoScore);
            response.setMetaDescriptionPresent(hasMetaDescription);
            response.setMetaDescription(metaDescription);
            response.setImagesMissingAlt(imagesMissingAlt);
            response.setMessage("Audit completed successfully");

            long endTime = System.currentTimeMillis();

            response.setResponseTime(endTime - startTime);

        }catch(SocketTimeoutException e){
            response.setUrl(url);
            response.setStatusCode(408);
            response.setMessage("Request timeout");
            long endTime = System.currentTimeMillis();

            response.setResponseTime(endTime - startTime);


        }catch
        (Exception e) {

            response.setUrl(url);
            response.setStatusCode(404);
            response.setMessage("Website not reachable: " + e.getClass().getSimpleName());

            long endTime = System.currentTimeMillis();

            response.setResponseTime(endTime - startTime);
        }

        return response;
    }
}
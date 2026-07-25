package PagePulse.dto;


import lombok.Data;

@Data
public class AuditResponse {

    private String url;

    private String title;

    private int statusCode;

    private int wordCount;

    private int h1Count;

    private int seoScore;

    private boolean metaDescriptionPresent;

    private int imagesMissingAlt;

    private String message;

    private long responseTime;

    private String metaDescription;
}

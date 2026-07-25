package PagePulse;

import PagePulse.dto.AuditResponse;
import PagePulse.service.AuditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuditServiceTest {

    @Autowired
    private AuditService auditService;


    @Test
    void validWebsiteTest(){

        AuditResponse response =
                auditService.analyzeWebsite("https://example.com");

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getTitle());
    }


    @Test
    void websiteNotReachableTest(){

        AuditResponse response =
                auditService.analyzeWebsite("https://abcxyzrandom123.com");

        assertEquals(404, response.getStatusCode());
    }
    @Test
    void nonHtmlResponseTest(){

        AuditResponse response =
                auditService.analyzeWebsite("https://www.google.com/robots.txt");

        assertEquals(415, response.getStatusCode());
    }
    @Test
    void timeoutTest(){

        AuditResponse response =
                auditService.analyzeWebsite("https://10.255.255.1");

        assertEquals(408, response.getStatusCode());

    }

}
package integration.services;


import client.MckingBurguerClient;
import integration.configuration.MckingBurguerTestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = MckingBurguerTestConfiguration.class)
@ActiveProfiles({"${env:local}", "${user.name}", "test", "${env:local}-test", "${user.name}-test"})
public abstract class TestCommon {

    @Autowired
    protected MckingBurguerClient mckingBurguerClient;

}
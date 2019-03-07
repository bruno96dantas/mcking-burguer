import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@Slf4j
public class MckingBurguerApplication {

    public static void main(String[] args) throws UnknownHostException {

        String username = System.getProperty("user.name");
        String hostname = InetAddress.getLocalHost().getHostName();
        String env = System.getProperty("bsft.env", "local");

        new SpringApplicationBuilder(MckingBurguerApplication.class)
                .profiles(env, username, hostname)
                .run();

        log.info("\n" +
                "\n" +
                "      .--..--..--..--..--..--.\n" +
                "    .' \\  (`._   (_)     _   \\\n" +
                "  .'    |  '._)         (_)  |\n" +
                "  \\ _.')\\      .----..---.   /\n" +
                "  |(_.'  |    /    .-\\-.  \\  |\n" +
                "  \\     0|    |   ( O| O) | o|\n" +
                "   |  _  |  .--.____.'._.-.  |\n" +
                "   \\ (_) | o         -` .-`  |\n" +
                "    |    \\   |`-._ _ _ _ _\\ /\n" +
                "    \\    |   |  `. |_||_|   |\n" +
                "    | o  |    \\_      \\     |     -.   .-.\n" +
                "    |.-.  \\     `--..-'   O |     `.`-' .'\n" +
                "  _.'  .' |     `-.-'      /-.__   ' .-'\n" +
                ".' `-.` '.|='=.='=.='=.='=|._/_ `-'.'\n" +
                "`-._  `.  |________/\\_____|    `-.'\n" +
                "   .'   ).| '=' '='\\/ '=' |\n" +
                "   `._.`  '---------------'\n" +
                "           //___\\   //___\\\n" +
                "             ||       ||\n" +
                "             ||_.-.   ||_.-.\n" +
                "            (_.--__) (_.--__)\n" +
                "MCKING Burguer STARTED!\n" +
                "====================================================================================================");
    }
}

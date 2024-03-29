package tonicforhealth.tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tonicforhealth.components.HelpForm;
import tonicforhealth.helpers.DriverUtils;
import tonicforhealth.pages.Contact;
import tonicforhealth.pages.Product;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class TonicForHealthTest extends BaseTest {

    final static String TONIC_FOR_HEALTH_URL = "https://tonicforhealth.com/";
    Faker faker = new Faker();

    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            userEmail = faker.internet().emailAddress(),
            userNumber = faker.phoneNumber().subscriberNumber(10),
            organization = faker.medical().hospitalName(),
            howCanWeHelp = "I would like product information",
            tellUsComment = faker.medical().symptoms();

    @Test
    @Description("Fill in help form")
    @Disabled
    void verifyFillingInHelpForm() {
        step("Open url 'https://tonicforhealth.com/'", () ->
                open(TONIC_FOR_HEALTH_URL)
        );

        step("Press \"Request More Info\" button", () -> {
            Product product = new Product();
            product.pressRequestMoreInfo();
        });

        step("Fill in Help Form", () -> {
            String expectedText = "Thanks for submitting the form.";
            HelpForm helpForm = new HelpForm();
            Contact contact = new Contact();
            contact.switchToHelpFormIframe();
            helpForm.setFirstName(firstName);
            helpForm.setLastName(lastName);
            helpForm.setEmail(userEmail);
            helpForm.setPhoneNumber(userNumber);
            helpForm.setOrganization(organization);
            helpForm.selectHowCanWeHelp(howCanWeHelp);
            helpForm.setComment(tellUsComment);
            helpForm.submit();
            contact.switchToThankYouSubmitFrame();
            contact.checkThankYouSubmittingText(expectedText);
        });
    }

    @Test
    @Description("Check if the \"Tonic Joined R1 RCM to Reimagine the Patient Experience\" title is exist and correct")
    @DisplayName("Verify title tonichealthcare")
    void verifyHeaderTitleTest() {
        step("Open url 'https://tonicforhealth.com/'", () ->
                open(TONIC_FOR_HEALTH_URL)
        );

        step("check label \"Tonic Joined R1 RCM to Reimagine the Patient Experience\"", () -> {
            String expectedText = "Tonic Joined R1 RCM to Reimagine the Patient Experience";
            Product product = new Product();
            product.checkProductTitle(expectedText);
        });
    }

    @Test
    @Description("Check if page title \"Tonic Health: World’s Best Patient Data Collection and Payments Platform.\" is exist and correct ")
    @DisplayName("Page title should have header text")
    void verifyPageTitleTest() {
        step("Open url 'https://tonicforhealth.com/'", () ->
                open(TONIC_FOR_HEALTH_URL)
        );

        step("Page title should have text 'Tonic Health: World’s Best Patient Data Collection and Payments Platform.'", () -> {
            String expectedTitle = "Tonic Health: World’s Best Patient Data Collection and Payments Platform.";
            String actualTitle = title();
            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Test
    @Description("Console has to be without errors")
    @DisplayName("Page console log should not have errors")
    void consoleShouldNotHaveErrorsTest() {
        step("Open url 'https://tonicforhealth.com/'", () ->
                open(TONIC_FOR_HEALTH_URL)
        );

        step("Console logs should not contain text 'SEVERE'", () -> {
            String consoleLogs = DriverUtils.getConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }
}

package ru.netology.sql.page;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import ru.netology.sql.data.DataHelper;

import static com.codeborne.selenide.Selenide.page;

public class LoginPage {

    @FindBy(css = "[data-test-id=login] input")
    private SelenideElement loginInput;
    @FindBy(css = "[data-test-id=password] input")
    private SelenideElement passwordInput;
    @FindBy(css = "[data-test-id=action-login]")
    private SelenideElement actionButton;
    @FindBy(css = "[data-test-id=error-notification]")
    private SelenideElement errorWindow;

    public void verifyLoginPageVisibility() {
        loginInput.shouldBe(Condition.visible);
    }

    public VerificationPage validVerify(DataHelper.AuthInfo info) {
        loginInput.sendKeys(Keys.CONTROL + "A");
        loginInput.sendKeys(Keys.BACK_SPACE);

        loginInput.setValue(info.getLogin());

        passwordInput.sendKeys(Keys.CONTROL + "A");
        passwordInput.sendKeys(Keys.BACK_SPACE);

        passwordInput.setValue(info.getPassword());
        actionButton.click();
        return page(VerificationPage.class);
    }

    public void validUserAndInvalidPasswordLogin(DataHelper.AuthInfo info) {
        loginInput.sendKeys(Keys.CONTROL + "A");
        loginInput.sendKeys(Keys.BACK_SPACE);

        loginInput.sendKeys(info.getLogin());

        passwordInput.sendKeys(Keys.CONTROL + "A");
        passwordInput.sendKeys(Keys.BACK_SPACE);

        passwordInput.sendKeys(DataHelper.generateRandomUser().getPassword());
        actionButton.click();
    }

    public void shouldErrorMessage(String errorMsg) {
        errorWindow.shouldBe(Condition.visible,Condition.text(errorMsg));
    }

}

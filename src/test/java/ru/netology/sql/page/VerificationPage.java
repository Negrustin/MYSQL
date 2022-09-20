package ru.netology.sql.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import ru.netology.sql.data.DataHelper;

import static com.codeborne.selenide.Selenide.page;

public class VerificationPage {
    @FindBy(css = "[data-test-id=code] input")
    private SelenideElement verificationCodeInput;
    @FindBy(css = "[data-test-id=action-verify]" )
    private SelenideElement actionVerifyButton;
    @FindBy(css = "[data-test-id=error-notification]")
    private SelenideElement popUpErrorWindow;

    public void shouldErrorMessage(String errorMsg) {
        popUpErrorWindow.shouldHave(Condition.text(errorMsg));
    }

    public void verifyVerificationPageVisibility(){
        verificationCodeInput.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(String verificationCode){
        verification(verificationCode);
        return page(DashboardPage.class);
    }
    public void verification(String verificationCode){
        verificationCodeInput.doubleClick();
        verificationCodeInput.sendKeys(Keys.BACK_SPACE);

        verificationCodeInput.setValue(verificationCode);
        actionVerifyButton.click();

    }
}

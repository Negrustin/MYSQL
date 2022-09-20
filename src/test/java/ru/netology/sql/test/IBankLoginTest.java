package ru.netology.sql.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.DataHelper;
import ru.netology.sql.data.SQLHelper;
import ru.netology.sql.page.DashboardPage;
import ru.netology.sql.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class IBankLoginTest {
    @BeforeAll
    static void addTestUser(){
        SQLHelper.addTestUserToDB();
    }
    @AfterAll
    static void tearDown() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("logging in to the account by a registered user")
    void shouldSuccessfulLogin() {

        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validVerify(authInfo);

        verificationPage.verifyVerificationPageVisibility();

        var verificationCode = SQLHelper.getVerificationCode();

        verificationPage.validVerify(verificationCode.getCode());
        var dashboardPage = new DashboardPage();

        dashboardPage.shouldDashboardPageTitle();
    }

    @Test
    @DisplayName("login to the account by an unregistered user.")
    void shouldErrorMassageWhileUserUnregistered() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var ValidAuthInfo = DataHelper.generateRandomUser();
        loginPage.validVerify(ValidAuthInfo);
        loginPage.shouldErrorMessage("Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("logging in with a valid login and invalid password")
    void shouldErrorMassageWhileUserIsValidAndPasswordIsInvalid() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();

        loginPage.validUserAndInvalidPasswordLogin(authInfo);

        loginPage.shouldErrorMessage("Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("show error massage, when typing incorrect verification code")
    void shouldErrorMsgWhereVerificationCodeInvalid() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validVerify(authInfo);

        var invalidVerificationCode = DataHelper.generateRandomVerificationCode().getCode();


        verificationPage.validVerify(invalidVerificationCode);

        verificationPage.shouldErrorMessage("Неверно указан код!");

    }

    @Test
    @DisplayName("show the user lockout message when an incorrect password is entered 3 times.")
    void shouldUserIsBlockedWhenIncorrectPasswordEnteredThreeTimes() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();

        var counter = 0;

        while (counter != 3) {
            loginPage.validUserAndInvalidPasswordLogin(authInfo);
            loginPage.shouldErrorMessage("Неверно указан логин или пароль");
            counter++;
        }
        loginPage.validVerify(authInfo);

        loginPage.shouldErrorMessage("Вход в аккаунт временно заблокирован, попробуйте через нескольео минут");

    }

    @Test
    @DisplayName("show the user lockout message when an incorrect verification code is entered 3 times.")
    void shouldUserUserIsBlockedWhenIncorrectVerificationCodeEnteredThreeTimes() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getTestUser();
        var verificationPage = loginPage.validVerify(authInfo);

        verificationPage.verifyVerificationPageVisibility();



        var counter = 0;

        while (counter != 3) {
            var invalidVerificationCode = DataHelper.generateRandomVerificationCode();
            verificationPage.validVerify(invalidVerificationCode.getCode());
            counter++;
        }
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());

        loginPage.shouldErrorMessage("Превышено количество попыток ввода кода");
    }


}

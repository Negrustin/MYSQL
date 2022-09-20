package ru.netology.sql.page;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {
    SelenideElement dashboard = $("[data-test-id=dashboard]");

//    private DashboardPage() {
//        dashboard.shouldBe(Condition.visible);
//    }

public void shouldDashboardPageTitle(){
    dashboard.shouldBe(Condition.visible);
}

}

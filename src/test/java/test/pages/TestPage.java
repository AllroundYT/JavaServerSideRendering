package test.pages;

import de.allround.ssr.page.WebPage;
import de.allround.ssr.page.htmx.components.custom.tab.Tab;
import de.allround.ssr.page.htmx.components.custom.tab.TabContainer;
import de.allround.ssr.page.htmx.components.input.Button;
import de.allround.ssr.page.htmx.components.text.Text;

public class TestPage extends WebPage {
    @Override
    public void init() {
        add(
                TabContainer.create(
                        Button.create("Tab 1").clazz("tab-active").trigger("click").tabTarget("tab-1"),
                        Button.create("Tab 2").trigger("click").tabTarget("tab-2"),
                        Tab.create(
                                Text.create("Tab 1")
                        ).id("tab-1"),
                        Tab.create(
                                Text.create("Tab 2")
                        ).id("tab-2")
                )
        );
    }
}

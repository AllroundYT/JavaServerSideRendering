package test.pages;

import de.allround.ssr.annotations.Route;
import de.allround.ssr.page.WebPage;
import de.allround.ssr.page.css.Selector;
import de.allround.ssr.page.css.Style;
import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.low.container.Div;
import de.allround.ssr.page.htmx.low.form.Button;
import de.allround.ssr.page.htmx.low.form.TextInput;
import de.allround.ssr.page.htmx.low.list.UnorderedList;
import de.allround.ssr.page.htmx.low.text.Headline;

import java.awt.*;

@Route("/console")
public class ConsolePage extends WebPage {
    @Override
    public void init() {
        title("Remote Console");

        add(
                Div.create(
                                Headline.create("Remote console")
                                        .sseTrigger("new_line")
                                        .get("/api/console/lines/all")
                                        .target(".console-line-list")
                                        .swap("outerHTML focus-scroll:true"),
                                UnorderedList.create().clazz("console-line-list").scroll(Component.ScrollDestination.BOTTOM),
                                TextInput.create("Input command...").name("password").id("console-input"),
                                Button.create().trigger("click")
                                        .post("/api/console/lines/add")
                                        .value("Send")
                                        .target(".console-line-list")
                                        .params("#console-input")
                                        .swap("none")
                        )
                        .sseConnect("/api/console/update")
        );

        style(
                new Style().selector(Selector.byClass("console-line-list"))
                        .listStyleType(Style.ListStyleType.NONE)
                        .backgroundColor(new Color(0xFF101010, true))
                        .height(new Style.CSSSize(50, Style.SizeType.PERCENT))
                        .color(Color.WHITE)
                        .overflow(Style.Overflow.SCROLL),
                new Style().selector(Selector.byClass("console-line-list-item"))
                        .margin(new Style.CSSSize(0.1, Style.SizeType.EM))
                        .textAlign(Style.TextAlign.LEFT)
        );
    }
}

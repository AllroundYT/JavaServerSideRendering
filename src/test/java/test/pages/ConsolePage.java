package test.pages;

import de.allround.ssr.annotations.Route;
import de.allround.ssr.page.WebPage;
import de.allround.ssr.page.css.Selector;
import de.allround.ssr.page.css.Style;
import de.allround.ssr.page.htmx.Component;
import de.allround.ssr.page.htmx.low.container.Div;
import de.allround.ssr.page.htmx.low.container.UnorderedList;
import de.allround.ssr.page.htmx.low.input.ServerButton;
import de.allround.ssr.page.htmx.low.input.TextInput;
import de.allround.ssr.page.htmx.low.text.Headline;
import de.allround.ssr.page.htmx.util.HtmxMethod;

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
                                new UnorderedList().clazz("console-line-list").scroll(Component.ScrollDestination.BOTTOM),
                                TextInput.create("Input command...", "input").id("console-input"),
                                ServerButton.create("Send", "/api/console/lines/add", ".console-line-list")
                                        .params("#console-input")
                                        .httpMethod(HtmxMethod.POST)
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

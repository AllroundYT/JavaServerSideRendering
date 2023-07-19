package de.allround.ssr.page.htmx.components.container;

import de.allround.ssr.page.htmx.Container;

import java.net.URL;


public class Audio extends Container<Audio> {
    protected Audio() {
        super("audio");
    }

    public Audio autoplay() {
        addAttribute("autoplay", "");
        return this;
    }

    public Audio controls() {
        addAttribute("controls", "");
        return this;
    }

    public Audio loop() {
        addAttribute("loop", "");
        return this;
    }

    public Audio muted() {
        addAttribute("muted", "");
        return this;
    }

    public Audio preload(String preload) {
        addAttribute("preload", preload);
        return this;
    }

    public Audio src(URL src) {
        addAttribute("src", src);
        return this;
    }
}

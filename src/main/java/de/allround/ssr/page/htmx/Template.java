package de.allround.ssr.page.htmx;

public abstract class Template extends Container<Template> {
    protected Template() {
        super("div");
    }

    public abstract void init();

    @Override
    public StyleRenderFunction styles() {
        return data -> {
            init();
            return super.styles().renderStyles(data);
        };
    }

    @Override
    public RenderFunction preRender() {
        return data -> {
            init();
            return super.preRender().render(data);
        };
    }
}

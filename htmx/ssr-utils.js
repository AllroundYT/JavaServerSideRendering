htmx.defineExtension('ssr-utils', {
    onEvent: function (name, evt) {
        const target = evt.target;
        if (name === 'htmx:afterSwap') {
            const scrollDirection = target.getAttribute('ssr-scroll');
            if (scrollDirection) {
                scrollElement(target, scrollDirection);
            }
        }
        if (name === 'htmx:trigger') {
            tabFunctionality(target)
        }
    }
});

function tabFunctionality(target) {
    if (target.getAttribute("ssr-tab-target")) {
        const targetId = target.getAttribute("ssr-tab-target")
        const element = document.getElementById(targetId)
        const parent = element.parentElement
        if (parent.hasAttribute("ssr-tab-container")) {
            for (let i = 0; i < parent.children.length; i++) {
                const child = parent.children.item(i);
                if (child.hasAttribute("ssr-tab")) {
                    child.classList.add("ssr-tab-inactive")
                }
            }
        }
        if (element.hasAttribute("ssr-tab")) {
            element.classList.remove("ssr-tab-inactive")
        }

        for (let i = 0; i < document.getElementsByTagName("button").length; i++) {
            document.getElementsByTagName("button")[i].classList.remove("tab-active")
        }
        target.classList.add("tab-active")
    }
}

function scrollElement(element, direction) {
    const container = element.closest('[ssr-scroll]');

    if (container) {
        if (direction === 'top') {
            container.scrollTop = 0;
        } else if (direction === 'bottom') {
            container.scrollTop = container.scrollHeight;
        }
    }
}

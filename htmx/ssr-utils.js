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
            toggleClasses(target)
        }

        if (name === 'htmx:beforeRequest') {
            if (target.getAttribute("ssr-payload")) {
                const xhr = evt.detail.xhr;
                const xhrOptions = evt.detail.xhrOptions;
                addCustomJson(xhr, xhrOptions, JSON.parse(target.getAttribute("ssr-payload")))
            }
        }
    }
});

function maybeClearInput(target) {
    if (target.hasAttribute("ssr-clear-input")) {
        const selector = target.getAttribute("ssr-clear-input")
        console.info(selector)
        document.querySelectorAll(selector).forEach(value => {
            console.info("test " + value)
            if (value instanceof HTMLInputElement) {
                console.info("test2")
                value.value = ""
            }
        })
    }
}

function toggleClasses(target) {
    if (target.getAttribute("ssr-toggle-class")) {
        const classes = target.getAttribute("ssr-toggle-class").split(",")
        classes.forEach(clazz => {
            if (target.classList.contains(clazz)) {
                target.classList.remove(clazz)
            } else {
                target.classList.add(clazz)
            }
        })
    }
}

function addCustomJson(xhr, xhrOptions, customData) {

    // Convert the custom data to a JSON string
    const jsonPayload = JSON.stringify(customData);

    // Set the Content-Type header to indicate JSON data
    xhr.setRequestHeader('Content-Type', 'application/json');

    // Set the JSON payload as the request body
    xhr.send(jsonPayload);
}

function addJsonToRequestBody(xhr, args) {
    if (args.jsonData) {
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(JSON.stringify(args.jsonData));
    }
}

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

(function () {
    function scrollElementTo(element, position) {
        if (position === 'bottom') {
            element.scrollTop = element.scrollHeight;
        } else if (position === 'top') {
            element.scrollTop = 0;
        }
    }

    function handleSSRScroll(event) {
        const position = event.target.getAttribute('ssr-scroll');
        if (position === 'bottom' || position === 'top') {
            scrollElementTo(event.target, position);
        }
    }

    htmx.defineExtension('ssr-utils', {
        onEvent: function (name, evt) {
            const target = evt.target;
            const scrollAttribute = target.getAttribute('ssr-scroll');
            if (name === 'htmx:afterSettle') {
                if (scrollAttribute) {
                    scrollElementTo(target, scrollAttribute);
                }
            }
            if (name === 'htmx:configRequest') {
                if (scrollAttribute) {
                    evt.parameters.ssrScroll = scrollAttribute;
                }
            }
            if (name === 'htmx:afterProcessNode') {
                if (scrollAttribute) {
                    scrollElementTo(target, scrollAttribute);
                }
            }
            if (name === 'htmx:beforeProcessNode') {
                if (scrollAttribute) {
                    target.removeAttribute('ssr-scroll');
                }
            }
            if (name === 'htmx:afterSwap') {
                handleSSRScroll(evt);
            }
        },
    });
})();

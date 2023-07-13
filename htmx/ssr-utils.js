(function () {
    function scrollElementTo(element, position) {
        if (position === 'bottom') {
            element.scrollTop = element.scrollHeight;
        } else if (position === 'top') {
            element.scrollTop = 0;
        }
    }

    function handleSSRScroll(event) {
        var position = event.target.getAttribute('ssr-scroll');
        if (position === 'bottom' || position === 'top') {
            scrollElementTo(event.target, position);
        }
    }

    htmx.defineExtension('ssr-utils', {
        onEvent: function (name, evt) {
            if (name === 'htmx:afterSettle') {
                var target = evt.target;
                var scrollAttribute = target.getAttribute('ssr-scroll');
                if (scrollAttribute) {
                    scrollElementTo(target, scrollAttribute);
                }
            }
            if (name === 'htmx:configRequest') {
                var target = evt.target;
                var scrollAttribute = target.getAttribute('ssr-scroll');
                if (scrollAttribute) {
                    evt.parameters.ssrScroll = scrollAttribute;
                }
            }
            if (name === 'htmx:afterProcessNode') {
                var target = evt.elt;
                var scrollAttribute = target.getAttribute('ssr-scroll');
                if (scrollAttribute) {
                    scrollElementTo(target, scrollAttribute);
                }
            }
            if (name === 'htmx:beforeProcessNode') {
                var target = evt.elt;
                var scrollAttribute = target.getAttribute('ssr-scroll');
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

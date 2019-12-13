var editor = window.pell.init({
    element: document.getElementById('editor'),
    defaultParagraphSeparator: 'p',
    onChange: function (html) {
        document.getElementById('html-output').textContent = html
    }
})
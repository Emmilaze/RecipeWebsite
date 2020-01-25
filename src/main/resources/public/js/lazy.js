if (!('IntersectionObserver' in window) ||
    !('IntersectionObserverEntry' in window) ||
    !('intersectionRatio' in window.IntersectionObserverEntry.prototype)) {
    for (i = 0; i < document.getElementsByTagName('img').length; i++) {
        document.body.style.background = "url('../../img/wallpaper.png') no-repeat ";
        document.body.style.backgroundSize = 'cover'
        document.getElementsByTagName('img')[i].src = document.getElementsByTagName('img')[i].attributes['data-src'].nodeValue;
    }

} else {

    let images = document.querySelectorAll('img');

    const options = {
        root: null,
        rootMargin: '0px',
        threshold: 0.1
    }

    function handleImg(myImg, observer) {
        myImg.forEach(function (myImgSingle) {
            console.log(myImgSingle.intersectionRatio);
            if (myImgSingle.intersectionRatio > 0) {
                loadImage(myImgSingle.target);
            }
        })
    }

    function loadImage(image) {
        image.src = image.getAttribute('data-src');
    }

    const observer = new IntersectionObserver(handleImg, options);

    images.forEach(function (img) {
        observer.observe(img);
    })
}



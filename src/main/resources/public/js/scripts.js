$(document).ready(function () {
    $(window).scroll(function () {
        if ($(this).scrollTop() > 10) {
            $("#goTop").fadeIn(300);
        } else {
            $("#goTop").fadeOut(300);
        }
    });

    $("#goTop").on("click", function () {
        $('html, body').animate({scrollTop: 0}, 350)
    });

    if (document.getElementById('qrcode')) {
        var qrcode = new QRCode(document.getElementById("qrcode"), {
            width: 100,
            height: 100
        });
        qrcode.makeCode(window.location.href);
    }

    $(function () {
        if (!($(".recipes:hidden").length > 0)) {
            $("#loadMore").fadeOut();
        }
    });
});

$(function () {
    $(".recipes").slice(0, 9).show();

    $("#loadMore").on('click', function (e) {
        e.preventDefault();
        $(".recipes:hidden").slice(0, 9).slideDown();
        if (!($(".recipes:hidden").length > 0)) {
            $("#loadMore").fadeOut();
        }
    });
});

$("#loginShow").on("click", function () {
    $("#login").toggleClass("display");
    $("#loginShow").toggleClass("showed");
    $("#navigation").removeClass("display");
    $("#menu").removeClass("showed");
    $("#searchMenu").removeClass("display");
    $("#searchBtn").removeClass("showed");
});

$("#show").on("click", function () {
    $("#login").removeClass("display");
    $("#loginShow").removeClass("showed");
    $("#navigation").toggleClass("display");
    $("#menu").toggleClass("showed");
    $("#searchMenu").removeClass("display");
    $("#searchBtn").removeClass("showed");
});


$("#searchBtn").on("click", function () {
    $("#searchMenu").toggleClass("display");
    $("#searchBtn").toggleClass("showed");
    $("#navigation").removeClass("display");
    $("#menu").removeClass("showed");
    $("#login").removeClass("display");
    $("#loginShow").removeClass("showed");
});

$("#showAdvance").on("click", function () {
    $("#colapsed").toggleClass("expand");
});

$("#showFullText").on("click", function () {
    $("#fullTextBlock").toggleClass("expand");
});

$("#expandUsers").on("click", function () {
    $("#usersTable").toggleClass("expandFull");
});

$("#expandCategories").on("click", function () {
    $("#cat").toggleClass("expandFull");
});

$("#expandDownloads").on("click", function () {
    $("#down").toggleClass("expandFull");
});

$("#expandRecipes").on("click", function () {
    $("#rec").toggleClass("expandFull");
});

function filterFunction() {
    var input, filter, ul, li, span, i, check;
    input = document.getElementById("myFilter");
    filter = input.value.toUpperCase();
    div = document.getElementById("filter");
    span = div.getElementsByTagName("label");
    for (i = 0; i < span.length; i++) {
        txtValue = span[i].textContent || span[i].innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            span[i].style.display = "";
        } else {
            span[i].style.display = "none";
        }
    }
}


function previewFile() {
    var preview = document.querySelector('#rec-img');
    var file = document.querySelector('input[type=file]').files[0];
    var reader = new FileReader();
    var theData = document.getElementById("rec-img");
    theData.data = document.getElementById("rec-img").src

    reader.addEventListener("load", function () {
        console.log('before preview');
        preview.src = reader.result;
    }, false);

    if (file) {
        console.log('inside if');
        reader.readAsDataURL(file);
        theData.attr("data", document.getElementById("rec-img").src);
    } else {
        console.log('inside else');
    }
}

function addIngr() {
    var len = $("#ingr-cont .ingr").length;
    console.log(len);
    // if(len < 20)
    // {
    $('#ingr-cont').append("<li class='ingr'> <input type='text' class='ingr-add' name='add-ingr' required><i class='far fa-plus-square' onclick='addIngr()'></i><i class='far fa-minus-square ' required></i> </li>");
    // }
    // else{
    //   alert("You cannot create more fields for ingredients");
    // }
};


$("body").on("click", ".fa-minus-square", function (e) {
    var val = $("#ingr-cont .ingr").length;
    console.log(val);
    if (val > 1) {
        $(this).parents('.ingr').remove();
    }
});

if ($('.time-input').value) {
    Date.now = function now() {
        return new Date().getTime();
    };
}

$("#DP").on("click", function (e) {
    window.print();
    window.onafterprint = function (event) {
        document.getElementById("print").submit();
    };
});

var mediaQueryList = window.matchMedia('print');
mediaQueryList.addListener(function (mql) {
    if (mql.matches) {
        if (document.getElementById("print")) {
            window.onafterprint = function (event) {
                document.getElementById("print").submit();
            };
        }
    }
});

var QRCode;
!function () {
    function a(a) {
        this.mode = c.MODE_8BIT_BYTE, this.data = a, this.parsedData = [];
        for (var b = [], d = 0, e = this.data.length; e > d; d++) {
            var f = this.data.charCodeAt(d);
            f > 65536 ? (b[0] = 240 | (1835008 & f) >>> 18, b[1] = 128 | (258048 & f) >>> 12, b[2] = 128 | (4032 & f) >>> 6, b[3] = 128 | 63 & f) : f > 2048 ? (b[0] = 224 | (61440 & f) >>> 12, b[1] = 128 | (4032 & f) >>> 6, b[2] = 128 | 63 & f) : f > 128 ? (b[0] = 192 | (1984 & f) >>> 6, b[1] = 128 | 63 & f) : b[0] = f, this.parsedData = this.parsedData.concat(b)
        }
        this.parsedData.length != this.data.length && (this.parsedData.unshift(191), this.parsedData.unshift(187), this.parsedData.unshift(239))
    }

    function b(a, b) {
        this.typeNumber = a, this.errorCorrectLevel = b, this.modules = null, this.moduleCount = 0, this.dataCache = null, this.dataList = []
    }

    function i(a, b) {
        if (void 0 == a.length) throw new Error(a.length + "/" + b);
        for (var c = 0; c < a.length && 0 == a[c];) c++;
        this.num = new Array(a.length - c + b);
        for (var d = 0; d < a.length - c; d++) this.num[d] = a[d + c]
    }

    function j(a, b) {
        this.totalCount = a, this.dataCount = b
    }

    function k() {
        this.buffer = [], this.length = 0
    }

    function m() {
        return "undefined" != typeof CanvasRenderingContext2D
    }

    function n() {
        var a = !1, b = navigator.userAgent;
        return /android/i.test(b) && (a = !0, aMat = b.toString().match(/android ([0-9]\.[0-9])/i), aMat && aMat[1] && (a = parseFloat(aMat[1]))), a
    }

    function r(a, b) {
        for (var c = 1, e = s(a), f = 0, g = l.length; g >= f; f++) {
            var h = 0;
            switch (b) {
                case d.L:
                    h = l[f][0];
                    break;
                case d.M:
                    h = l[f][1];
                    break;
                case d.Q:
                    h = l[f][2];
                    break;
                case d.H:
                    h = l[f][3]
            }
            if (h >= e) break;
            c++
        }
        if (c > l.length) throw new Error("Too long data");
        return c
    }

    function s(a) {
        var b = encodeURI(a).toString().replace(/\%[0-9a-fA-F]{2}/g, "a");
        return b.length + (b.length != a ? 3 : 0)
    }

    a.prototype = {
        getLength: function () {
            return this.parsedData.length
        }, write: function (a) {
            for (var b = 0, c = this.parsedData.length; c > b; b++) a.put(this.parsedData[b], 8)
        }
    }, b.prototype = {
        addData: function (b) {
            var c = new a(b);
            this.dataList.push(c), this.dataCache = null
        }, isDark: function (a, b) {
            if (0 > a || this.moduleCount <= a || 0 > b || this.moduleCount <= b) throw new Error(a + "," + b);
            return this.modules[a][b]
        }, getModuleCount: function () {
            return this.moduleCount
        }, make: function () {
            this.makeImpl(!1, this.getBestMaskPattern())
        }, makeImpl: function (a, c) {
            this.moduleCount = 4 * this.typeNumber + 17, this.modules = new Array(this.moduleCount);
            for (var d = 0; d < this.moduleCount; d++) {
                this.modules[d] = new Array(this.moduleCount);
                for (var e = 0; e < this.moduleCount; e++) this.modules[d][e] = null
            }
            this.setupPositionProbePattern(0, 0), this.setupPositionProbePattern(this.moduleCount - 7, 0), this.setupPositionProbePattern(0, this.moduleCount - 7), this.setupPositionAdjustPattern(), this.setupTimingPattern(), this.setupTypeInfo(a, c), this.typeNumber >= 7 && this.setupTypeNumber(a), null == this.dataCache && (this.dataCache = b.createData(this.typeNumber, this.errorCorrectLevel, this.dataList)), this.mapData(this.dataCache, c)
        }, setupPositionProbePattern: function (a, b) {
            for (var c = -1; 7 >= c; c++) if (!(-1 >= a + c || this.moduleCount <= a + c)) for (var d = -1; 7 >= d; d++) -1 >= b + d || this.moduleCount <= b + d || (this.modules[a + c][b + d] = c >= 0 && 6 >= c && (0 == d || 6 == d) || d >= 0 && 6 >= d && (0 == c || 6 == c) || c >= 2 && 4 >= c && d >= 2 && 4 >= d ? !0 : !1)
        }, getBestMaskPattern: function () {
            for (var a = 0, b = 0, c = 0; 8 > c; c++) {
                this.makeImpl(!0, c);
                var d = f.getLostPoint(this);
                (0 == c || a > d) && (a = d, b = c)
            }
            return b
        }, createMovieClip: function (a, b, c) {
            var d = a.createEmptyMovieClip(b, c), e = 1;
            this.make();
            for (var f = 0; f < this.modules.length; f++) for (var g = f * e, h = 0; h < this.modules[f].length; h++) {
                var i = h * e, j = this.modules[f][h];
                j && (d.beginFill(0, 100), d.moveTo(i, g), d.lineTo(i + e, g), d.lineTo(i + e, g + e), d.lineTo(i, g + e), d.endFill())
            }
            return d
        }, setupTimingPattern: function () {
            for (var a = 8; a < this.moduleCount - 8; a++) null == this.modules[a][6] && (this.modules[a][6] = 0 == a % 2);
            for (var b = 8; b < this.moduleCount - 8; b++) null == this.modules[6][b] && (this.modules[6][b] = 0 == b % 2)
        }, setupPositionAdjustPattern: function () {
            for (var a = f.getPatternPosition(this.typeNumber), b = 0; b < a.length; b++) for (var c = 0; c < a.length; c++) {
                var d = a[b], e = a[c];
                if (null == this.modules[d][e]) for (var g = -2; 2 >= g; g++) for (var h = -2; 2 >= h; h++) this.modules[d + g][e + h] = -2 == g || 2 == g || -2 == h || 2 == h || 0 == g && 0 == h ? !0 : !1
            }
        }, setupTypeNumber: function (a) {
            for (var b = f.getBCHTypeNumber(this.typeNumber), c = 0; 18 > c; c++) {
                var d = !a && 1 == (1 & b >> c);
                this.modules[Math.floor(c / 3)][c % 3 + this.moduleCount - 8 - 3] = d
            }
            for (var c = 0; 18 > c; c++) {
                var d = !a && 1 == (1 & b >> c);
                this.modules[c % 3 + this.moduleCount - 8 - 3][Math.floor(c / 3)] = d
            }
        }, setupTypeInfo: function (a, b) {
            for (var c = this.errorCorrectLevel << 3 | b, d = f.getBCHTypeInfo(c), e = 0; 15 > e; e++) {
                var g = !a && 1 == (1 & d >> e);
                6 > e ? this.modules[e][8] = g : 8 > e ? this.modules[e + 1][8] = g : this.modules[this.moduleCount - 15 + e][8] = g
            }
            for (var e = 0; 15 > e; e++) {
                var g = !a && 1 == (1 & d >> e);
                8 > e ? this.modules[8][this.moduleCount - e - 1] = g : 9 > e ? this.modules[8][15 - e - 1 + 1] = g : this.modules[8][15 - e - 1] = g
            }
            this.modules[this.moduleCount - 8][8] = !a
        }, mapData: function (a, b) {
            for (var c = -1, d = this.moduleCount - 1, e = 7, g = 0, h = this.moduleCount - 1; h > 0; h -= 2) for (6 == h && h--; ;) {
                for (var i = 0; 2 > i; i++) if (null == this.modules[d][h - i]) {
                    var j = !1;
                    g < a.length && (j = 1 == (1 & a[g] >>> e));
                    var k = f.getMask(b, d, h - i);
                    k && (j = !j), this.modules[d][h - i] = j, e--, -1 == e && (g++, e = 7)
                }
                if (d += c, 0 > d || this.moduleCount <= d) {
                    d -= c, c = -c;
                    break
                }
            }
        }
    }, b.PAD0 = 236, b.PAD1 = 17, b.createData = function (a, c, d) {
        for (var e = j.getRSBlocks(a, c), g = new k, h = 0; h < d.length; h++) {
            var i = d[h];
            g.put(i.mode, 4), g.put(i.getLength(), f.getLengthInBits(i.mode, a)), i.write(g)
        }
        for (var l = 0, h = 0; h < e.length; h++) l += e[h].dataCount;
        if (g.getLengthInBits() > 8 * l) throw new Error("code length overflow. (" + g.getLengthInBits() + ">" + 8 * l + ")");
        for (g.getLengthInBits() + 4 <= 8 * l && g.put(0, 4); 0 != g.getLengthInBits() % 8;) g.putBit(!1);
        for (; ;) {
            if (g.getLengthInBits() >= 8 * l) break;
            if (g.put(b.PAD0, 8), g.getLengthInBits() >= 8 * l) break;
            g.put(b.PAD1, 8)
        }
        return b.createBytes(g, e)
    }, b.createBytes = function (a, b) {
        for (var c = 0, d = 0, e = 0, g = new Array(b.length), h = new Array(b.length), j = 0; j < b.length; j++) {
            var k = b[j].dataCount, l = b[j].totalCount - k;
            d = Math.max(d, k), e = Math.max(e, l), g[j] = new Array(k);
            for (var m = 0; m < g[j].length; m++) g[j][m] = 255 & a.buffer[m + c];
            c += k;
            var n = f.getErrorCorrectPolynomial(l), o = new i(g[j], n.getLength() - 1), p = o.mod(n);
            h[j] = new Array(n.getLength() - 1);
            for (var m = 0; m < h[j].length; m++) {
                var q = m + p.getLength() - h[j].length;
                h[j][m] = q >= 0 ? p.get(q) : 0
            }
        }
        for (var r = 0, m = 0; m < b.length; m++) r += b[m].totalCount;
        for (var s = new Array(r), t = 0, m = 0; d > m; m++) for (var j = 0; j < b.length; j++) m < g[j].length && (s[t++] = g[j][m]);
        for (var m = 0; e > m; m++) for (var j = 0; j < b.length; j++) m < h[j].length && (s[t++] = h[j][m]);
        return s
    };
    for (var c = {MODE_NUMBER: 1, MODE_ALPHA_NUM: 2, MODE_8BIT_BYTE: 4, MODE_KANJI: 8}, d = {
        L: 1,
        M: 0,
        Q: 3,
        H: 2
    }, e = {
        PATTERN000: 0,
        PATTERN001: 1,
        PATTERN010: 2,
        PATTERN011: 3,
        PATTERN100: 4,
        PATTERN101: 5,
        PATTERN110: 6,
        PATTERN111: 7
    }, f = {
        PATTERN_POSITION_TABLE: [[], [6, 18], [6, 22], [6, 26], [6, 30], [6, 34], [6, 22, 38], [6, 24, 42], [6, 26, 46], [6, 28, 50], [6, 30, 54], [6, 32, 58], [6, 34, 62], [6, 26, 46, 66], [6, 26, 48, 70], [6, 26, 50, 74], [6, 30, 54, 78], [6, 30, 56, 82], [6, 30, 58, 86], [6, 34, 62, 90], [6, 28, 50, 72, 94], [6, 26, 50, 74, 98], [6, 30, 54, 78, 102], [6, 28, 54, 80, 106], [6, 32, 58, 84, 110], [6, 30, 58, 86, 114], [6, 34, 62, 90, 118], [6, 26, 50, 74, 98, 122], [6, 30, 54, 78, 102, 126], [6, 26, 52, 78, 104, 130], [6, 30, 56, 82, 108, 134], [6, 34, 60, 86, 112, 138], [6, 30, 58, 86, 114, 142], [6, 34, 62, 90, 118, 146], [6, 30, 54, 78, 102, 126, 150], [6, 24, 50, 76, 102, 128, 154], [6, 28, 54, 80, 106, 132, 158], [6, 32, 58, 84, 110, 136, 162], [6, 26, 54, 82, 110, 138, 166], [6, 30, 58, 86, 114, 142, 170]],
        G15: 1335,
        G18: 7973,
        G15_MASK: 21522,
        getBCHTypeInfo: function (a) {
            for (var b = a << 10; f.getBCHDigit(b) - f.getBCHDigit(f.G15) >= 0;) b ^= f.G15 << f.getBCHDigit(b) - f.getBCHDigit(f.G15);
            return (a << 10 | b) ^ f.G15_MASK
        },
        getBCHTypeNumber: function (a) {
            for (var b = a << 12; f.getBCHDigit(b) - f.getBCHDigit(f.G18) >= 0;) b ^= f.G18 << f.getBCHDigit(b) - f.getBCHDigit(f.G18);
            return a << 12 | b
        },
        getBCHDigit: function (a) {
            for (var b = 0; 0 != a;) b++, a >>>= 1;
            return b
        },
        getPatternPosition: function (a) {
            return f.PATTERN_POSITION_TABLE[a - 1]
        },
        getMask: function (a, b, c) {
            switch (a) {
                case e.PATTERN000:
                    return 0 == (b + c) % 2;
                case e.PATTERN001:
                    return 0 == b % 2;
                case e.PATTERN010:
                    return 0 == c % 3;
                case e.PATTERN011:
                    return 0 == (b + c) % 3;
                case e.PATTERN100:
                    return 0 == (Math.floor(b / 2) + Math.floor(c / 3)) % 2;
                case e.PATTERN101:
                    return 0 == b * c % 2 + b * c % 3;
                case e.PATTERN110:
                    return 0 == (b * c % 2 + b * c % 3) % 2;
                case e.PATTERN111:
                    return 0 == (b * c % 3 + (b + c) % 2) % 2;
                default:
                    throw new Error("bad maskPattern:" + a)
            }
        },
        getErrorCorrectPolynomial: function (a) {
            for (var b = new i([1], 0), c = 0; a > c; c++) b = b.multiply(new i([1, g.gexp(c)], 0));
            return b
        },
        getLengthInBits: function (a, b) {
            if (b >= 1 && 10 > b) switch (a) {
                case c.MODE_NUMBER:
                    return 10;
                case c.MODE_ALPHA_NUM:
                    return 9;
                case c.MODE_8BIT_BYTE:
                    return 8;
                case c.MODE_KANJI:
                    return 8;
                default:
                    throw new Error("mode:" + a)
            } else if (27 > b) switch (a) {
                case c.MODE_NUMBER:
                    return 12;
                case c.MODE_ALPHA_NUM:
                    return 11;
                case c.MODE_8BIT_BYTE:
                    return 16;
                case c.MODE_KANJI:
                    return 10;
                default:
                    throw new Error("mode:" + a)
            } else {
                if (!(41 > b)) throw new Error("type:" + b);
                switch (a) {
                    case c.MODE_NUMBER:
                        return 14;
                    case c.MODE_ALPHA_NUM:
                        return 13;
                    case c.MODE_8BIT_BYTE:
                        return 16;
                    case c.MODE_KANJI:
                        return 12;
                    default:
                        throw new Error("mode:" + a)
                }
            }
        },
        getLostPoint: function (a) {
            for (var b = a.getModuleCount(), c = 0, d = 0; b > d; d++) for (var e = 0; b > e; e++) {
                for (var f = 0, g = a.isDark(d, e), h = -1; 1 >= h; h++) if (!(0 > d + h || d + h >= b)) for (var i = -1; 1 >= i; i++) 0 > e + i || e + i >= b || (0 != h || 0 != i) && g == a.isDark(d + h, e + i) && f++;
                f > 5 && (c += 3 + f - 5)
            }
            for (var d = 0; b - 1 > d; d++) for (var e = 0; b - 1 > e; e++) {
                var j = 0;
                a.isDark(d, e) && j++, a.isDark(d + 1, e) && j++, a.isDark(d, e + 1) && j++, a.isDark(d + 1, e + 1) && j++, (0 == j || 4 == j) && (c += 3)
            }
            for (var d = 0; b > d; d++) for (var e = 0; b - 6 > e; e++) a.isDark(d, e) && !a.isDark(d, e + 1) && a.isDark(d, e + 2) && a.isDark(d, e + 3) && a.isDark(d, e + 4) && !a.isDark(d, e + 5) && a.isDark(d, e + 6) && (c += 40);
            for (var e = 0; b > e; e++) for (var d = 0; b - 6 > d; d++) a.isDark(d, e) && !a.isDark(d + 1, e) && a.isDark(d + 2, e) && a.isDark(d + 3, e) && a.isDark(d + 4, e) && !a.isDark(d + 5, e) && a.isDark(d + 6, e) && (c += 40);
            for (var k = 0, e = 0; b > e; e++) for (var d = 0; b > d; d++) a.isDark(d, e) && k++;
            var l = Math.abs(100 * k / b / b - 50) / 5;
            return c += 10 * l
        }
    }, g = {
        glog: function (a) {
            if (1 > a) throw new Error("glog(" + a + ")");
            return g.LOG_TABLE[a]
        }, gexp: function (a) {
            for (; 0 > a;) a += 255;
            for (; a >= 256;) a -= 255;
            return g.EXP_TABLE[a]
        }, EXP_TABLE: new Array(256), LOG_TABLE: new Array(256)
    }, h = 0; 8 > h; h++) g.EXP_TABLE[h] = 1 << h;
    for (var h = 8; 256 > h; h++) g.EXP_TABLE[h] = g.EXP_TABLE[h - 4] ^ g.EXP_TABLE[h - 5] ^ g.EXP_TABLE[h - 6] ^ g.EXP_TABLE[h - 8];
    for (var h = 0; 255 > h; h++) g.LOG_TABLE[g.EXP_TABLE[h]] = h;
    i.prototype = {
        get: function (a) {
            return this.num[a]
        }, getLength: function () {
            return this.num.length
        }, multiply: function (a) {
            for (var b = new Array(this.getLength() + a.getLength() - 1), c = 0; c < this.getLength(); c++) for (var d = 0; d < a.getLength(); d++) b[c + d] ^= g.gexp(g.glog(this.get(c)) + g.glog(a.get(d)));
            return new i(b, 0)
        }, mod: function (a) {
            if (this.getLength() - a.getLength() < 0) return this;
            for (var b = g.glog(this.get(0)) - g.glog(a.get(0)), c = new Array(this.getLength()), d = 0; d < this.getLength(); d++) c[d] = this.get(d);
            for (var d = 0; d < a.getLength(); d++) c[d] ^= g.gexp(g.glog(a.get(d)) + b);
            return new i(c, 0).mod(a)
        }
    }, j.RS_BLOCK_TABLE = [[1, 26, 19], [1, 26, 16], [1, 26, 13], [1, 26, 9], [1, 44, 34], [1, 44, 28], [1, 44, 22], [1, 44, 16], [1, 70, 55], [1, 70, 44], [2, 35, 17], [2, 35, 13], [1, 100, 80], [2, 50, 32], [2, 50, 24], [4, 25, 9], [1, 134, 108], [2, 67, 43], [2, 33, 15, 2, 34, 16], [2, 33, 11, 2, 34, 12], [2, 86, 68], [4, 43, 27], [4, 43, 19], [4, 43, 15], [2, 98, 78], [4, 49, 31], [2, 32, 14, 4, 33, 15], [4, 39, 13, 1, 40, 14], [2, 121, 97], [2, 60, 38, 2, 61, 39], [4, 40, 18, 2, 41, 19], [4, 40, 14, 2, 41, 15], [2, 146, 116], [3, 58, 36, 2, 59, 37], [4, 36, 16, 4, 37, 17], [4, 36, 12, 4, 37, 13], [2, 86, 68, 2, 87, 69], [4, 69, 43, 1, 70, 44], [6, 43, 19, 2, 44, 20], [6, 43, 15, 2, 44, 16], [4, 101, 81], [1, 80, 50, 4, 81, 51], [4, 50, 22, 4, 51, 23], [3, 36, 12, 8, 37, 13], [2, 116, 92, 2, 117, 93], [6, 58, 36, 2, 59, 37], [4, 46, 20, 6, 47, 21], [7, 42, 14, 4, 43, 15], [4, 133, 107], [8, 59, 37, 1, 60, 38], [8, 44, 20, 4, 45, 21], [12, 33, 11, 4, 34, 12], [3, 145, 115, 1, 146, 116], [4, 64, 40, 5, 65, 41], [11, 36, 16, 5, 37, 17], [11, 36, 12, 5, 37, 13], [5, 109, 87, 1, 110, 88], [5, 65, 41, 5, 66, 42], [5, 54, 24, 7, 55, 25], [11, 36, 12], [5, 122, 98, 1, 123, 99], [7, 73, 45, 3, 74, 46], [15, 43, 19, 2, 44, 20], [3, 45, 15, 13, 46, 16], [1, 135, 107, 5, 136, 108], [10, 74, 46, 1, 75, 47], [1, 50, 22, 15, 51, 23], [2, 42, 14, 17, 43, 15], [5, 150, 120, 1, 151, 121], [9, 69, 43, 4, 70, 44], [17, 50, 22, 1, 51, 23], [2, 42, 14, 19, 43, 15], [3, 141, 113, 4, 142, 114], [3, 70, 44, 11, 71, 45], [17, 47, 21, 4, 48, 22], [9, 39, 13, 16, 40, 14], [3, 135, 107, 5, 136, 108], [3, 67, 41, 13, 68, 42], [15, 54, 24, 5, 55, 25], [15, 43, 15, 10, 44, 16], [4, 144, 116, 4, 145, 117], [17, 68, 42], [17, 50, 22, 6, 51, 23], [19, 46, 16, 6, 47, 17], [2, 139, 111, 7, 140, 112], [17, 74, 46], [7, 54, 24, 16, 55, 25], [34, 37, 13], [4, 151, 121, 5, 152, 122], [4, 75, 47, 14, 76, 48], [11, 54, 24, 14, 55, 25], [16, 45, 15, 14, 46, 16], [6, 147, 117, 4, 148, 118], [6, 73, 45, 14, 74, 46], [11, 54, 24, 16, 55, 25], [30, 46, 16, 2, 47, 17], [8, 132, 106, 4, 133, 107], [8, 75, 47, 13, 76, 48], [7, 54, 24, 22, 55, 25], [22, 45, 15, 13, 46, 16], [10, 142, 114, 2, 143, 115], [19, 74, 46, 4, 75, 47], [28, 50, 22, 6, 51, 23], [33, 46, 16, 4, 47, 17], [8, 152, 122, 4, 153, 123], [22, 73, 45, 3, 74, 46], [8, 53, 23, 26, 54, 24], [12, 45, 15, 28, 46, 16], [3, 147, 117, 10, 148, 118], [3, 73, 45, 23, 74, 46], [4, 54, 24, 31, 55, 25], [11, 45, 15, 31, 46, 16], [7, 146, 116, 7, 147, 117], [21, 73, 45, 7, 74, 46], [1, 53, 23, 37, 54, 24], [19, 45, 15, 26, 46, 16], [5, 145, 115, 10, 146, 116], [19, 75, 47, 10, 76, 48], [15, 54, 24, 25, 55, 25], [23, 45, 15, 25, 46, 16], [13, 145, 115, 3, 146, 116], [2, 74, 46, 29, 75, 47], [42, 54, 24, 1, 55, 25], [23, 45, 15, 28, 46, 16], [17, 145, 115], [10, 74, 46, 23, 75, 47], [10, 54, 24, 35, 55, 25], [19, 45, 15, 35, 46, 16], [17, 145, 115, 1, 146, 116], [14, 74, 46, 21, 75, 47], [29, 54, 24, 19, 55, 25], [11, 45, 15, 46, 46, 16], [13, 145, 115, 6, 146, 116], [14, 74, 46, 23, 75, 47], [44, 54, 24, 7, 55, 25], [59, 46, 16, 1, 47, 17], [12, 151, 121, 7, 152, 122], [12, 75, 47, 26, 76, 48], [39, 54, 24, 14, 55, 25], [22, 45, 15, 41, 46, 16], [6, 151, 121, 14, 152, 122], [6, 75, 47, 34, 76, 48], [46, 54, 24, 10, 55, 25], [2, 45, 15, 64, 46, 16], [17, 152, 122, 4, 153, 123], [29, 74, 46, 14, 75, 47], [49, 54, 24, 10, 55, 25], [24, 45, 15, 46, 46, 16], [4, 152, 122, 18, 153, 123], [13, 74, 46, 32, 75, 47], [48, 54, 24, 14, 55, 25], [42, 45, 15, 32, 46, 16], [20, 147, 117, 4, 148, 118], [40, 75, 47, 7, 76, 48], [43, 54, 24, 22, 55, 25], [10, 45, 15, 67, 46, 16], [19, 148, 118, 6, 149, 119], [18, 75, 47, 31, 76, 48], [34, 54, 24, 34, 55, 25], [20, 45, 15, 61, 46, 16]], j.getRSBlocks = function (a, b) {
        var c = j.getRsBlockTable(a, b);
        if (void 0 == c) throw new Error("bad rs block @ typeNumber:" + a + "/errorCorrectLevel:" + b);
        for (var d = c.length / 3, e = [], f = 0; d > f; f++) for (var g = c[3 * f + 0], h = c[3 * f + 1], i = c[3 * f + 2], k = 0; g > k; k++) e.push(new j(h, i));
        return e
    }, j.getRsBlockTable = function (a, b) {
        switch (b) {
            case d.L:
                return j.RS_BLOCK_TABLE[4 * (a - 1) + 0];
            case d.M:
                return j.RS_BLOCK_TABLE[4 * (a - 1) + 1];
            case d.Q:
                return j.RS_BLOCK_TABLE[4 * (a - 1) + 2];
            case d.H:
                return j.RS_BLOCK_TABLE[4 * (a - 1) + 3];
            default:
                return void 0
        }
    }, k.prototype = {
        get: function (a) {
            var b = Math.floor(a / 8);
            return 1 == (1 & this.buffer[b] >>> 7 - a % 8)
        }, put: function (a, b) {
            for (var c = 0; b > c; c++) this.putBit(1 == (1 & a >>> b - c - 1))
        }, getLengthInBits: function () {
            return this.length
        }, putBit: function (a) {
            var b = Math.floor(this.length / 8);
            this.buffer.length <= b && this.buffer.push(0), a && (this.buffer[b] |= 128 >>> this.length % 8), this.length++
        }
    };

    var l = [[17, 14, 11, 7], [32, 26, 20, 14], [53, 42, 32, 24], [78, 62, 46, 34], [106, 84, 60, 44], [134, 106, 74, 58], [154, 122, 86, 64], [192, 152, 108, 84], [230, 180, 130, 98], [271, 213, 151, 119], [321, 251, 177, 137], [367, 287, 203, 155], [425, 331, 241, 177], [458, 362, 258, 194], [520, 412, 292, 220], [586, 450, 322, 250], [644, 504, 364, 280], [718, 560, 394, 310], [792, 624, 442, 338], [858, 666, 482, 382], [929, 711, 509, 403], [1003, 779, 565, 439], [1091, 857, 611, 461], [1171, 911, 661, 511], [1273, 997, 715, 535], [1367, 1059, 751, 593], [1465, 1125, 805, 625], [1528, 1190, 868, 658], [1628, 1264, 908, 698], [1732, 1370, 982, 742], [1840, 1452, 1030, 790], [1952, 1538, 1112, 842], [2068, 1628, 1168, 898], [2188, 1722, 1228, 958], [2303, 1809, 1283, 983], [2431, 1911, 1351, 1051], [2563, 1989, 1423, 1093], [2699, 2099, 1499, 1139], [2809, 2213, 1579, 1219], [2953, 2331, 1663, 1273]],
        o = function () {
            var a = function (a, b) {
                this._el = a, this._htOption = b
            };
            return a.prototype.draw = function (a) {
                function g(a, b) {
                    var c = document.createElementNS("http://www.w3.org/2000/svg", a);
                    for (var d in b) b.hasOwnProperty(d) && c.setAttribute(d, b[d]);
                    return c
                }

                var b = this._htOption, c = this._el, d = a.getModuleCount();
                Math.floor(b.width / d), Math.floor(b.height / d), this.clear();
                var h = g("svg", {
                    viewBox: "0 0 " + String(d) + " " + String(d),
                    width: "100%",
                    height: "100%",
                    fill: b.colorLight
                });
                h.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xlink", "http://www.w3.org/1999/xlink"), c.appendChild(h), h.appendChild(g("rect", {
                    fill: b.colorDark,
                    width: "1",
                    height: "1",
                    id: "template"
                }));
                for (var i = 0; d > i; i++) for (var j = 0; d > j; j++) if (a.isDark(i, j)) {
                    var k = g("use", {x: String(i), y: String(j)});
                    k.setAttributeNS("http://www.w3.org/1999/xlink", "href", "#template"), h.appendChild(k)
                }
            }, a.prototype.clear = function () {
                for (; this._el.hasChildNodes();) this._el.removeChild(this._el.lastChild)
            }, a
        }(), p = "svg" === document.documentElement.tagName.toLowerCase(), q = p ? o : m() ? function () {
            function a() {
                this._elImage.src = this._elCanvas.toDataURL("image/png"), this._elImage.style.display = "block", this._elCanvas.style.display = "none"
            }

            function d(a, b) {
                var c = this;
                if (c._fFail = b, c._fSuccess = a, null === c._bSupportDataURI) {
                    var d = document.createElement("img"), e = function () {
                        c._bSupportDataURI = !1, c._fFail && _fFail.call(c)
                    }, f = function () {
                        c._bSupportDataURI = !0, c._fSuccess && c._fSuccess.call(c)
                    };
                    return d.onabort = e, d.onerror = e, d.onload = f, d.src = "data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==", void 0
                }
                c._bSupportDataURI === !0 && c._fSuccess ? c._fSuccess.call(c) : c._bSupportDataURI === !1 && c._fFail && c._fFail.call(c)
            }

            if (this._android && this._android <= 2.1) {
                var b = 1 / window.devicePixelRatio, c = CanvasRenderingContext2D.prototype.drawImage;
                CanvasRenderingContext2D.prototype.drawImage = function (a, d, e, f, g, h, i, j) {
                    if ("nodeName" in a && /img/i.test(a.nodeName)) for (var l = arguments.length - 1; l >= 1; l--) arguments[l] = arguments[l] * b; else "undefined" == typeof j && (arguments[1] *= b, arguments[2] *= b, arguments[3] *= b, arguments[4] *= b);
                    c.apply(this, arguments)
                }
            }
            var e = function (a, b) {
                this._bIsPainted = !1, this._android = n(), this._htOption = b, this._elCanvas = document.createElement("canvas"), this._elCanvas.width = b.width, this._elCanvas.height = b.height, a.appendChild(this._elCanvas), this._el = a, this._oContext = this._elCanvas.getContext("2d"), this._bIsPainted = !1, this._elImage = document.createElement("img"), this._elImage.style.display = "none", this._el.appendChild(this._elImage), this._bSupportDataURI = null
            };
            return e.prototype.draw = function (a) {
                var b = this._elImage, c = this._oContext, d = this._htOption, e = a.getModuleCount(), f = d.width / e,
                    g = d.height / e, h = Math.round(f), i = Math.round(g);
                b.style.display = "none", this.clear();
                for (var j = 0; e > j; j++) for (var k = 0; e > k; k++) {
                    var l = a.isDark(j, k), m = k * f, n = j * g;
                    c.strokeStyle = l ? d.colorDark : d.colorLight, c.lineWidth = 1, c.fillStyle = l ? d.colorDark : d.colorLight, c.fillRect(m, n, f, g), c.strokeRect(Math.floor(m) + .5, Math.floor(n) + .5, h, i), c.strokeRect(Math.ceil(m) - .5, Math.ceil(n) - .5, h, i)
                }
                this._bIsPainted = !0
            }, e.prototype.makeImage = function () {
                this._bIsPainted && d.call(this, a)
            }, e.prototype.isPainted = function () {
                return this._bIsPainted
            }, e.prototype.clear = function () {
                this._oContext.clearRect(0, 0, this._elCanvas.width, this._elCanvas.height), this._bIsPainted = !1
            }, e.prototype.round = function (a) {
                return a ? Math.floor(1e3 * a) / 1e3 : a
            }, e
        }() : function () {
            var a = function (a, b) {
                this._el = a, this._htOption = b
            };
            return a.prototype.draw = function (a) {
                for (var b = this._htOption, c = this._el, d = a.getModuleCount(), e = Math.floor(b.width / d), f = Math.floor(b.height / d), g = ['<table style="border:0;border-collapse:collapse;">'], h = 0; d > h; h++) {
                    g.push("<tr>");
                    for (var i = 0; d > i; i++) g.push('<td style="border:0;border-collapse:collapse;padding:0;margin:0;width:' + e + "px;height:" + f + "px;background-color:" + (a.isDark(h, i) ? b.colorDark : b.colorLight) + ';"></td>');
                    g.push("</tr>")
                }
                g.push("</table>"), c.innerHTML = g.join("");
                var j = c.childNodes[0], k = (b.width - j.offsetWidth) / 2, l = (b.height - j.offsetHeight) / 2;
                k > 0 && l > 0 && (j.style.margin = l + "px " + k + "px")
            }, a.prototype.clear = function () {
                this._el.innerHTML = ""
            }, a
        }();
    QRCode = function (a, b) {
        if (this._htOption = {
            width: 256,
            height: 256,
            typeNumber: 4,
            colorDark: "#000000",
            colorLight: "#ffffff",
            correctLevel: d.H
        }, "string" == typeof b && (b = {text: b}), b) for (var c in b) this._htOption[c] = b[c];
        "string" == typeof a && (a = document.getElementById(a)), this._android = n(), this._el = a, this._oQRCode = null, this._oDrawing = new q(this._el, this._htOption), this._htOption.text && this.makeCode(this._htOption.text)
    }, QRCode.prototype.makeCode = function (a) {
        this._oQRCode = new b(r(a, this._htOption.correctLevel), this._htOption.correctLevel), this._oQRCode.addData(a), this._oQRCode.make(), this._el.title = a, this._oDrawing.draw(this._oQRCode), this.makeImage()
    }, QRCode.prototype.makeImage = function () {
        "function" == typeof this._oDrawing.makeImage && (!this._android || this._android >= 3) && this._oDrawing.makeImage()
    }, QRCode.prototype.clear = function () {
        this._oDrawing.clear()
    }, QRCode.CorrectLevel = d
}();

// DataPicker
!function (a) {
    "function" == typeof define && define.amd ? define(["jquery"], a) : a("object" == typeof exports ? require("jquery") : jQuery)
}(function (a, b) {
    function c() {
        return new Date(Date.UTC.apply(Date, arguments))
    }

    function d() {
        var a = new Date;
        return c(a.getFullYear(), a.getMonth(), a.getDate())
    }

    function e(a, b) {
        return a.getUTCFullYear() === b.getUTCFullYear() && a.getUTCMonth() === b.getUTCMonth() && a.getUTCDate() === b.getUTCDate()
    }

    function f(c, d) {
        return function () {
            return d !== b && a.fn.datepicker.deprecated(d), this[c].apply(this, arguments)
        }
    }

    function g(a) {
        return a && !isNaN(a.getTime())
    }

    function h(b, c) {
        function d(a, b) {
            return b.toLowerCase()
        }

        var e, f = a(b).data(), g = {}, h = new RegExp("^" + c.toLowerCase() + "([A-Z])");
        c = new RegExp("^" + c.toLowerCase());
        for (var i in f) c.test(i) && (e = i.replace(h, d), g[e] = f[i]);
        return g
    }

    function i(b) {
        var c = {};
        if (q[b] || (b = b.split("-")[0], q[b])) {
            var d = q[b];
            return a.each(p, function (a, b) {
                b in d && (c[b] = d[b])
            }), c
        }
    }

    var j = function () {
        var b = {
            get: function (a) {
                return this.slice(a)[0]
            }, contains: function (a) {
                for (var b = a && a.valueOf(), c = 0, d = this.length; c < d; c++) if (0 <= this[c].valueOf() - b && this[c].valueOf() - b < 864e5) return c;
                return -1
            }, remove: function (a) {
                this.splice(a, 1)
            }, replace: function (b) {
                b && (a.isArray(b) || (b = [b]), this.clear(), this.push.apply(this, b))
            }, clear: function () {
                this.length = 0
            }, copy: function () {
                var a = new j;
                return a.replace(this), a
            }
        };
        return function () {
            var c = [];
            return c.push.apply(c, arguments), a.extend(c, b), c
        }
    }(), k = function (b, c) {
        a.data(b, "datepicker", this), this._process_options(c), this.dates = new j, this.viewDate = this.o.defaultViewDate, this.focusDate = null, this.element = a(b), this.isInput = this.element.is("input"), this.inputField = this.isInput ? this.element : this.element.find("input"), this.component = !!this.element.hasClass("date") && this.element.find(".add-on, .input-group-addon, .btn"), this.component && 0 === this.component.length && (this.component = !1), this.isInline = !this.component && this.element.is("div"), this.picker = a(r.template), this._check_template(this.o.templates.leftArrow) && this.picker.find(".prev").html(this.o.templates.leftArrow), this._check_template(this.o.templates.rightArrow) && this.picker.find(".next").html(this.o.templates.rightArrow), this._buildEvents(), this._attachEvents(), this.isInline ? this.picker.addClass("datepicker-inline").appendTo(this.element) : this.picker.addClass("datepicker-dropdown dropdown-menu"), this.o.rtl && this.picker.addClass("datepicker-rtl"), this.o.calendarWeeks && this.picker.find(".datepicker-days .datepicker-switch, thead .datepicker-title, tfoot .today, tfoot .clear").attr("colspan", function (a, b) {
            return Number(b) + 1
        }), this._process_options({
            startDate: this._o.startDate,
            endDate: this._o.endDate,
            daysOfWeekDisabled: this.o.daysOfWeekDisabled,
            daysOfWeekHighlighted: this.o.daysOfWeekHighlighted,
            datesDisabled: this.o.datesDisabled
        }), this._allow_update = !1, this.setViewMode(this.o.startView), this._allow_update = !0, this.fillDow(), this.fillMonths(), this.update(), this.isInline && this.show()
    };
    k.prototype = {
        constructor: k,
        _resolveViewName: function (b) {
            return a.each(r.viewModes, function (c, d) {
                if (b === c || a.inArray(b, d.names) !== -1) return b = c, !1
            }), b
        },
        _resolveDaysOfWeek: function (b) {
            return a.isArray(b) || (b = b.split(/[,\s]*/)), a.map(b, Number)
        },
        _check_template: function (c) {
            try {
                if (c === b || "" === c) return !1;
                if ((c.match(/[<>]/g) || []).length <= 0) return !0;
                var d = a(c);
                return d.length > 0
            } catch (a) {
                return !1
            }
        },
        _process_options: function (b) {
            this._o = a.extend({}, this._o, b);
            var e = this.o = a.extend({}, this._o), f = e.language;
            q[f] || (f = f.split("-")[0], q[f] || (f = o.language)), e.language = f, e.startView = this._resolveViewName(e.startView), e.minViewMode = this._resolveViewName(e.minViewMode), e.maxViewMode = this._resolveViewName(e.maxViewMode), e.startView = Math.max(this.o.minViewMode, Math.min(this.o.maxViewMode, e.startView)), e.multidate !== !0 && (e.multidate = Number(e.multidate) || !1, e.multidate !== !1 && (e.multidate = Math.max(0, e.multidate))), e.multidateSeparator = String(e.multidateSeparator), e.weekStart %= 7, e.weekEnd = (e.weekStart + 6) % 7;
            var g = r.parseFormat(e.format);
            e.startDate !== -(1 / 0) && (e.startDate ? e.startDate instanceof Date ? e.startDate = this._local_to_utc(this._zero_time(e.startDate)) : e.startDate = r.parseDate(e.startDate, g, e.language, e.assumeNearbyYear) : e.startDate = -(1 / 0)), e.endDate !== 1 / 0 && (e.endDate ? e.endDate instanceof Date ? e.endDate = this._local_to_utc(this._zero_time(e.endDate)) : e.endDate = r.parseDate(e.endDate, g, e.language, e.assumeNearbyYear) : e.endDate = 1 / 0), e.daysOfWeekDisabled = this._resolveDaysOfWeek(e.daysOfWeekDisabled || []), e.daysOfWeekHighlighted = this._resolveDaysOfWeek(e.daysOfWeekHighlighted || []), e.datesDisabled = e.datesDisabled || [], a.isArray(e.datesDisabled) || (e.datesDisabled = e.datesDisabled.split(",")), e.datesDisabled = a.map(e.datesDisabled, function (a) {
                return r.parseDate(a, g, e.language, e.assumeNearbyYear)
            });
            var h = String(e.orientation).toLowerCase().split(/\s+/g), i = e.orientation.toLowerCase();
            if (h = a.grep(h, function (a) {
                return /^auto|left|right|top|bottom$/.test(a)
            }), e.orientation = {x: "auto", y: "auto"}, i && "auto" !== i) if (1 === h.length) switch (h[0]) {
                case"top":
                case"bottom":
                    e.orientation.y = h[0];
                    break;
                case"left":
                case"right":
                    e.orientation.x = h[0]
            } else i = a.grep(h, function (a) {
                return /^left|right$/.test(a)
            }), e.orientation.x = i[0] || "auto", i = a.grep(h, function (a) {
                return /^top|bottom$/.test(a)
            }), e.orientation.y = i[0] || "auto"; else ;
            if (e.defaultViewDate instanceof Date || "string" == typeof e.defaultViewDate) e.defaultViewDate = r.parseDate(e.defaultViewDate, g, e.language, e.assumeNearbyYear); else if (e.defaultViewDate) {
                var j = e.defaultViewDate.year || (new Date).getFullYear(), k = e.defaultViewDate.month || 0,
                    l = e.defaultViewDate.day || 1;
                e.defaultViewDate = c(j, k, l)
            } else e.defaultViewDate = d()
        },
        _events: [],
        _secondaryEvents: [],
        _applyEvents: function (a) {
            for (var c, d, e, f = 0; f < a.length; f++) c = a[f][0], 2 === a[f].length ? (d = b, e = a[f][1]) : 3 === a[f].length && (d = a[f][1], e = a[f][2]), c.on(e, d)
        },
        _unapplyEvents: function (a) {
            for (var c, d, e, f = 0; f < a.length; f++) c = a[f][0], 2 === a[f].length ? (e = b, d = a[f][1]) : 3 === a[f].length && (e = a[f][1], d = a[f][2]), c.off(d, e)
        },
        _buildEvents: function () {
            var b = {
                keyup: a.proxy(function (b) {
                    a.inArray(b.keyCode, [27, 37, 39, 38, 40, 32, 13, 9]) === -1 && this.update()
                }, this), keydown: a.proxy(this.keydown, this), paste: a.proxy(this.paste, this)
            };
            this.o.showOnFocus === !0 && (b.focus = a.proxy(this.show, this)), this.isInput ? this._events = [[this.element, b]] : this.component && this.inputField.length ? this._events = [[this.inputField, b], [this.component, {click: a.proxy(this.show, this)}]] : this._events = [[this.element, {
                click: a.proxy(this.show, this),
                keydown: a.proxy(this.keydown, this)
            }]], this._events.push([this.element, "*", {
                blur: a.proxy(function (a) {
                    this._focused_from = a.target
                }, this)
            }], [this.element, {
                blur: a.proxy(function (a) {
                    this._focused_from = a.target
                }, this)
            }]), this.o.immediateUpdates && this._events.push([this.element, {
                "changeYear changeMonth": a.proxy(function (a) {
                    this.update(a.date)
                }, this)
            }]), this._secondaryEvents = [[this.picker, {click: a.proxy(this.click, this)}], [this.picker, ".prev, .next", {click: a.proxy(this.navArrowsClick, this)}], [this.picker, ".day:not(.disabled)", {click: a.proxy(this.dayCellClick, this)}], [a(window), {resize: a.proxy(this.place, this)}], [a(document), {
                "mousedown touchstart": a.proxy(function (a) {
                    this.element.is(a.target) || this.element.find(a.target).length || this.picker.is(a.target) || this.picker.find(a.target).length || this.isInline || this.hide()
                }, this)
            }]]
        },
        _attachEvents: function () {
            this._detachEvents(), this._applyEvents(this._events)
        },
        _detachEvents: function () {
            this._unapplyEvents(this._events)
        },
        _attachSecondaryEvents: function () {
            this._detachSecondaryEvents(), this._applyEvents(this._secondaryEvents)
        },
        _detachSecondaryEvents: function () {
            this._unapplyEvents(this._secondaryEvents)
        },
        _trigger: function (b, c) {
            var d = c || this.dates.get(-1), e = this._utc_to_local(d);
            this.element.trigger({
                type: b,
                date: e,
                viewMode: this.viewMode,
                dates: a.map(this.dates, this._utc_to_local),
                format: a.proxy(function (a, b) {
                    0 === arguments.length ? (a = this.dates.length - 1, b = this.o.format) : "string" == typeof a && (b = a, a = this.dates.length - 1), b = b || this.o.format;
                    var c = this.dates.get(a);
                    return r.formatDate(c, b, this.o.language)
                }, this)
            })
        },
        show: function () {
            if (!(this.inputField.prop("disabled") || this.inputField.prop("readonly") && this.o.enableOnReadonly === !1)) return this.isInline || this.picker.appendTo(this.o.container), this.place(), this.picker.show(), this._attachSecondaryEvents(), this._trigger("show"), (window.navigator.msMaxTouchPoints || "ontouchstart" in document) && this.o.disableTouchKeyboard && a(this.element).blur(), this
        },
        hide: function () {
            return this.isInline || !this.picker.is(":visible") ? this : (this.focusDate = null, this.picker.hide().detach(), this._detachSecondaryEvents(), this.setViewMode(this.o.startView), this.o.forceParse && this.inputField.val() && this.setValue(), this._trigger("hide"), this)
        },
        destroy: function () {
            return this.hide(), this._detachEvents(), this._detachSecondaryEvents(), this.picker.remove(), delete this.element.data().datepicker, this.isInput || delete this.element.data().date, this
        },
        paste: function (b) {
            var c;
            if (b.originalEvent.clipboardData && b.originalEvent.clipboardData.types && a.inArray("text/plain", b.originalEvent.clipboardData.types) !== -1) c = b.originalEvent.clipboardData.getData("text/plain"); else {
                if (!window.clipboardData) return;
                c = window.clipboardData.getData("Text")
            }
            this.setDate(c), this.update(), b.preventDefault()
        },
        _utc_to_local: function (a) {
            if (!a) return a;
            var b = new Date(a.getTime() + 6e4 * a.getTimezoneOffset());
            return b.getTimezoneOffset() !== a.getTimezoneOffset() && (b = new Date(a.getTime() + 6e4 * b.getTimezoneOffset())), b
        },
        _local_to_utc: function (a) {
            return a && new Date(a.getTime() - 6e4 * a.getTimezoneOffset())
        },
        _zero_time: function (a) {
            return a && new Date(a.getFullYear(), a.getMonth(), a.getDate())
        },
        _zero_utc_time: function (a) {
            return a && c(a.getUTCFullYear(), a.getUTCMonth(), a.getUTCDate())
        },
        getDates: function () {
            return a.map(this.dates, this._utc_to_local)
        },
        getUTCDates: function () {
            return a.map(this.dates, function (a) {
                return new Date(a)
            })
        },
        getDate: function () {
            return this._utc_to_local(this.getUTCDate())
        },
        getUTCDate: function () {
            var a = this.dates.get(-1);
            return a !== b ? new Date(a) : null
        },
        clearDates: function () {
            this.inputField.val(""), this.update(), this._trigger("changeDate"), this.o.autoclose && this.hide()
        },
        setDates: function () {
            var b = a.isArray(arguments[0]) ? arguments[0] : arguments;
            return this.update.apply(this, b), this._trigger("changeDate"), this.setValue(), this
        },
        setUTCDates: function () {
            var b = a.isArray(arguments[0]) ? arguments[0] : arguments;
            return this.setDates.apply(this, a.map(b, this._utc_to_local)), this
        },
        setDate: f("setDates"),
        setUTCDate: f("setUTCDates"),
        remove: f("destroy", "Method `remove` is deprecated and will be removed in version 2.0. Use `destroy` instead"),
        setValue: function () {
            var a = this.getFormattedDate();
            return this.inputField.val(a), this
        },
        getFormattedDate: function (c) {
            c === b && (c = this.o.format);
            var d = this.o.language;
            return a.map(this.dates, function (a) {
                return r.formatDate(a, c, d)
            }).join(this.o.multidateSeparator)
        },
        getStartDate: function () {
            return this.o.startDate
        },
        setStartDate: function (a) {
            return this._process_options({startDate: a}), this.update(), this.updateNavArrows(), this
        },
        getEndDate: function () {
            return this.o.endDate
        },
        setEndDate: function (a) {
            return this._process_options({endDate: a}), this.update(), this.updateNavArrows(), this
        },
        setDaysOfWeekDisabled: function (a) {
            return this._process_options({daysOfWeekDisabled: a}), this.update(), this
        },
        setDaysOfWeekHighlighted: function (a) {
            return this._process_options({daysOfWeekHighlighted: a}), this.update(), this
        },
        setDatesDisabled: function (a) {
            return this._process_options({datesDisabled: a}), this.update(), this
        },
        place: function () {
            if (this.isInline) return this;
            var b = this.picker.outerWidth(), c = this.picker.outerHeight(), d = 10, e = a(this.o.container),
                f = e.width(), g = "body" === this.o.container ? a(document).scrollTop() : e.scrollTop(),
                h = e.offset(), i = [0];
            this.element.parents().each(function () {
                var b = a(this).css("z-index");
                "auto" !== b && 0 !== Number(b) && i.push(Number(b))
            });
            var j = Math.max.apply(Math, i) + this.o.zIndexOffset,
                k = this.component ? this.component.parent().offset() : this.element.offset(),
                l = this.component ? this.component.outerHeight(!0) : this.element.outerHeight(!1),
                m = this.component ? this.component.outerWidth(!0) : this.element.outerWidth(!1), n = k.left - h.left,
                o = k.top - h.top;
            "body" !== this.o.container && (o += g), this.picker.removeClass("datepicker-orient-top datepicker-orient-bottom datepicker-orient-right datepicker-orient-left"), "auto" !== this.o.orientation.x ? (this.picker.addClass("datepicker-orient-" + this.o.orientation.x), "right" === this.o.orientation.x && (n -= b - m)) : k.left < 0 ? (this.picker.addClass("datepicker-orient-left"), n -= k.left - d) : n + b > f ? (this.picker.addClass("datepicker-orient-right"), n += m - b) : this.o.rtl ? this.picker.addClass("datepicker-orient-right") : this.picker.addClass("datepicker-orient-left");
            var p, q = this.o.orientation.y;
            if ("auto" === q && (p = -g + o - c, q = p < 0 ? "bottom" : "top"), this.picker.addClass("datepicker-orient-" + q), "top" === q ? o -= c + parseInt(this.picker.css("padding-top")) : o += l, this.o.rtl) {
                var r = f - (n + m);
                this.picker.css({top: o, right: r, zIndex: j})
            } else this.picker.css({top: o, left: n, zIndex: j});
            return this
        },
        _allow_update: !0,
        update: function () {
            if (!this._allow_update) return this;
            var b = this.dates.copy(), c = [], d = !1;
            return arguments.length ? (a.each(arguments, a.proxy(function (a, b) {
                b instanceof Date && (b = this._local_to_utc(b)), c.push(b)
            }, this)), d = !0) : (c = this.isInput ? this.element.val() : this.element.data("date") || this.inputField.val(), c = c && this.o.multidate ? c.split(this.o.multidateSeparator) : [c], delete this.element.data().date), c = a.map(c, a.proxy(function (a) {
                return r.parseDate(a, this.o.format, this.o.language, this.o.assumeNearbyYear)
            }, this)), c = a.grep(c, a.proxy(function (a) {
                return !this.dateWithinRange(a) || !a
            }, this), !0), this.dates.replace(c), this.o.updateViewDate && (this.dates.length ? this.viewDate = new Date(this.dates.get(-1)) : this.viewDate < this.o.startDate ? this.viewDate = new Date(this.o.startDate) : this.viewDate > this.o.endDate ? this.viewDate = new Date(this.o.endDate) : this.viewDate = this.o.defaultViewDate), d ? (this.setValue(), this.element.change()) : this.dates.length && String(b) !== String(this.dates) && d && (this._trigger("changeDate"), this.element.change()), !this.dates.length && b.length && (this._trigger("clearDate"), this.element.change()), this.fill(), this
        },
        fillDow: function () {
            if (this.o.showWeekDays) {
                var b = this.o.weekStart, c = "<tr>";
                for (this.o.calendarWeeks && (c += '<th class="cw">&#160;</th>'); b < this.o.weekStart + 7;) c += '<th class="dow', a.inArray(b, this.o.daysOfWeekDisabled) !== -1 && (c += " disabled"), c += '">' + q[this.o.language].daysMin[b++ % 7] + "</th>";
                c += "</tr>", this.picker.find(".datepicker-days thead").append(c)
            }
        },
        fillMonths: function () {
            for (var a, b = this._utc_to_local(this.viewDate), c = "", d = 0; d < 12; d++) a = b && b.getMonth() === d ? " focused" : "", c += '<span class="month' + a + '">' + q[this.o.language].monthsShort[d] + "</span>";
            this.picker.find(".datepicker-months td").html(c)
        },
        setRange: function (b) {
            b && b.length ? this.range = a.map(b, function (a) {
                return a.valueOf()
            }) : delete this.range, this.fill()
        },
        getClassNames: function (b) {
            var c = [], f = this.viewDate.getUTCFullYear(), g = this.viewDate.getUTCMonth(), h = d();
            return b.getUTCFullYear() < f || b.getUTCFullYear() === f && b.getUTCMonth() < g ? c.push("old") : (b.getUTCFullYear() > f || b.getUTCFullYear() === f && b.getUTCMonth() > g) && c.push("new"), this.focusDate && b.valueOf() === this.focusDate.valueOf() && c.push("focused"), this.o.todayHighlight && e(b, h) && c.push("today"), this.dates.contains(b) !== -1 && c.push("active"), this.dateWithinRange(b) || c.push("disabled"), this.dateIsDisabled(b) && c.push("disabled", "disabled-date"), a.inArray(b.getUTCDay(), this.o.daysOfWeekHighlighted) !== -1 && c.push("highlighted"), this.range && (b > this.range[0] && b < this.range[this.range.length - 1] && c.push("range"), a.inArray(b.valueOf(), this.range) !== -1 && c.push("selected"), b.valueOf() === this.range[0] && c.push("range-start"), b.valueOf() === this.range[this.range.length - 1] && c.push("range-end")), c
        },
        _fill_yearsView: function (c, d, e, f, g, h, i) {
            for (var j, k, l, m = "", n = e / 10, o = this.picker.find(c), p = Math.floor(f / e) * e, q = p + 9 * n, r = Math.floor(this.viewDate.getFullYear() / n) * n, s = a.map(this.dates, function (a) {
                return Math.floor(a.getUTCFullYear() / n) * n
            }), t = p - n; t <= q + n; t += n) j = [d], k = null, t === p - n ? j.push("old") : t === q + n && j.push("new"), a.inArray(t, s) !== -1 && j.push("active"), (t < g || t > h) && j.push("disabled"), t === r && j.push("focused"), i !== a.noop && (l = i(new Date(t, 0, 1)), l === b ? l = {} : "boolean" == typeof l ? l = {enabled: l} : "string" == typeof l && (l = {classes: l}), l.enabled === !1 && j.push("disabled"), l.classes && (j = j.concat(l.classes.split(/\s+/))), l.tooltip && (k = l.tooltip)), m += '<span class="' + j.join(" ") + '"' + (k ? ' title="' + k + '"' : "") + ">" + t + "</span>";
            o.find(".datepicker-switch").text(p + "-" + q), o.find("td").html(m)
        },
        fill: function () {
            var d, e, f = new Date(this.viewDate), g = f.getUTCFullYear(), h = f.getUTCMonth(),
                i = this.o.startDate !== -(1 / 0) ? this.o.startDate.getUTCFullYear() : -(1 / 0),
                j = this.o.startDate !== -(1 / 0) ? this.o.startDate.getUTCMonth() : -(1 / 0),
                k = this.o.endDate !== 1 / 0 ? this.o.endDate.getUTCFullYear() : 1 / 0,
                l = this.o.endDate !== 1 / 0 ? this.o.endDate.getUTCMonth() : 1 / 0,
                m = q[this.o.language].today || q.en.today || "", n = q[this.o.language].clear || q.en.clear || "",
                o = q[this.o.language].titleFormat || q.en.titleFormat;
            if (!isNaN(g) && !isNaN(h)) {
                this.picker.find(".datepicker-days .datepicker-switch").text(r.formatDate(f, o, this.o.language)), this.picker.find("tfoot .today").text(m).css("display", this.o.todayBtn === !0 || "linked" === this.o.todayBtn ? "table-cell" : "none"), this.picker.find("tfoot .clear").text(n).css("display", this.o.clearBtn === !0 ? "table-cell" : "none"), this.picker.find("thead .datepicker-title").text(this.o.title).css("display", "string" == typeof this.o.title && "" !== this.o.title ? "table-cell" : "none"), this.updateNavArrows(), this.fillMonths();
                var p = c(g, h, 0), s = p.getUTCDate();
                p.setUTCDate(s - (p.getUTCDay() - this.o.weekStart + 7) % 7);
                var t = new Date(p);
                p.getUTCFullYear() < 100 && t.setUTCFullYear(p.getUTCFullYear()), t.setUTCDate(t.getUTCDate() + 42), t = t.valueOf();
                for (var u, v, w = []; p.valueOf() < t;) {
                    if (u = p.getUTCDay(), u === this.o.weekStart && (w.push("<tr>"), this.o.calendarWeeks)) {
                        var x = new Date(+p + (this.o.weekStart - u - 7) % 7 * 864e5),
                            y = new Date(Number(x) + (11 - x.getUTCDay()) % 7 * 864e5),
                            z = new Date(Number(z = c(y.getUTCFullYear(), 0, 1)) + (11 - z.getUTCDay()) % 7 * 864e5),
                            A = (y - z) / 864e5 / 7 + 1;
                        w.push('<td class="cw">' + A + "</td>")
                    }
                    v = this.getClassNames(p), v.push("day");
                    var B = p.getUTCDate();
                    this.o.beforeShowDay !== a.noop && (e = this.o.beforeShowDay(this._utc_to_local(p)), e === b ? e = {} : "boolean" == typeof e ? e = {enabled: e} : "string" == typeof e && (e = {classes: e}), e.enabled === !1 && v.push("disabled"), e.classes && (v = v.concat(e.classes.split(/\s+/))), e.tooltip && (d = e.tooltip), e.content && (B = e.content)), v = a.isFunction(a.uniqueSort) ? a.uniqueSort(v) : a.unique(v), w.push('<td class="' + v.join(" ") + '"' + (d ? ' title="' + d + '"' : "") + ' data-date="' + p.getTime().toString() + '">' + B + "</td>"), d = null, u === this.o.weekEnd && w.push("</tr>"), p.setUTCDate(p.getUTCDate() + 1)
                }
                this.picker.find(".datepicker-days tbody").html(w.join(""));
                var C = q[this.o.language].monthsTitle || q.en.monthsTitle || "Months",
                    D = this.picker.find(".datepicker-months").find(".datepicker-switch").text(this.o.maxViewMode < 2 ? C : g).end().find("tbody span").removeClass("active");
                if (a.each(this.dates, function (a, b) {
                    b.getUTCFullYear() === g && D.eq(b.getUTCMonth()).addClass("active")
                }), (g < i || g > k) && D.addClass("disabled"), g === i && D.slice(0, j).addClass("disabled"), g === k && D.slice(l + 1).addClass("disabled"), this.o.beforeShowMonth !== a.noop) {
                    var E = this;
                    a.each(D, function (c, d) {
                        var e = new Date(g, c, 1), f = E.o.beforeShowMonth(e);
                        f === b ? f = {} : "boolean" == typeof f ? f = {enabled: f} : "string" == typeof f && (f = {classes: f}), f.enabled !== !1 || a(d).hasClass("disabled") || a(d).addClass("disabled"), f.classes && a(d).addClass(f.classes), f.tooltip && a(d).prop("title", f.tooltip)
                    })
                }
                this._fill_yearsView(".datepicker-years", "year", 10, g, i, k, this.o.beforeShowYear), this._fill_yearsView(".datepicker-decades", "decade", 100, g, i, k, this.o.beforeShowDecade), this._fill_yearsView(".datepicker-centuries", "century", 1e3, g, i, k, this.o.beforeShowCentury)
            }
        },
        updateNavArrows: function () {
            if (this._allow_update) {
                var a, b, c = new Date(this.viewDate), d = c.getUTCFullYear(), e = c.getUTCMonth(),
                    f = this.o.startDate !== -(1 / 0) ? this.o.startDate.getUTCFullYear() : -(1 / 0),
                    g = this.o.startDate !== -(1 / 0) ? this.o.startDate.getUTCMonth() : -(1 / 0),
                    h = this.o.endDate !== 1 / 0 ? this.o.endDate.getUTCFullYear() : 1 / 0,
                    i = this.o.endDate !== 1 / 0 ? this.o.endDate.getUTCMonth() : 1 / 0, j = 1;
                switch (this.viewMode) {
                    case 0:
                        a = d <= f && e <= g, b = d >= h && e >= i;
                        break;
                    case 4:
                        j *= 10;
                    case 3:
                        j *= 10;
                    case 2:
                        j *= 10;
                    case 1:
                        a = Math.floor(d / j) * j <= f, b = Math.floor(d / j) * j + j >= h
                }
                this.picker.find(".prev").toggleClass("disabled", a), this.picker.find(".next").toggleClass("disabled", b)
            }
        },
        click: function (b) {
            b.preventDefault(), b.stopPropagation();
            var e, f, g, h;
            e = a(b.target), e.hasClass("datepicker-switch") && this.viewMode !== this.o.maxViewMode && this.setViewMode(this.viewMode + 1), e.hasClass("today") && !e.hasClass("day") && (this.setViewMode(0), this._setDate(d(), "linked" === this.o.todayBtn ? null : "view")), e.hasClass("clear") && this.clearDates(), e.hasClass("disabled") || (e.hasClass("month") || e.hasClass("year") || e.hasClass("decade") || e.hasClass("century")) && (this.viewDate.setUTCDate(1), f = 1, 1 === this.viewMode ? (h = e.parent().find("span").index(e), g = this.viewDate.getUTCFullYear(), this.viewDate.setUTCMonth(h)) : (h = 0, g = Number(e.text()), this.viewDate.setUTCFullYear(g)), this._trigger(r.viewModes[this.viewMode - 1].e, this.viewDate), this.viewMode === this.o.minViewMode ? this._setDate(c(g, h, f)) : (this.setViewMode(this.viewMode - 1), this.fill())), this.picker.is(":visible") && this._focused_from && this._focused_from.focus(), delete this._focused_from
        },
        dayCellClick: function (b) {
            var c = a(b.currentTarget), d = c.data("date"), e = new Date(d);
            this.o.updateViewDate && (e.getUTCFullYear() !== this.viewDate.getUTCFullYear() && this._trigger("changeYear", this.viewDate), e.getUTCMonth() !== this.viewDate.getUTCMonth() && this._trigger("changeMonth", this.viewDate)), this._setDate(e)
        },
        navArrowsClick: function (b) {
            var c = a(b.currentTarget), d = c.hasClass("prev") ? -1 : 1;
            0 !== this.viewMode && (d *= 12 * r.viewModes[this.viewMode].navStep), this.viewDate = this.moveMonth(this.viewDate, d), this._trigger(r.viewModes[this.viewMode].e, this.viewDate), this.fill()
        },
        _toggle_multidate: function (a) {
            var b = this.dates.contains(a);
            if (a || this.dates.clear(), b !== -1 ? (this.o.multidate === !0 || this.o.multidate > 1 || this.o.toggleActive) && this.dates.remove(b) : this.o.multidate === !1 ? (this.dates.clear(), this.dates.push(a)) : this.dates.push(a), "number" == typeof this.o.multidate) for (; this.dates.length > this.o.multidate;) this.dates.remove(0)
        },
        _setDate: function (a, b) {
            b && "date" !== b || this._toggle_multidate(a && new Date(a)), (!b && this.o.updateViewDate || "view" === b) && (this.viewDate = a && new Date(a)), this.fill(), this.setValue(), b && "view" === b || this._trigger("changeDate"), this.inputField.trigger("change"), !this.o.autoclose || b && "date" !== b || this.hide()
        },
        moveDay: function (a, b) {
            var c = new Date(a);
            return c.setUTCDate(a.getUTCDate() + b), c
        },
        moveWeek: function (a, b) {
            return this.moveDay(a, 7 * b)
        },
        moveMonth: function (a, b) {
            if (!g(a)) return this.o.defaultViewDate;
            if (!b) return a;
            var c, d, e = new Date(a.valueOf()), f = e.getUTCDate(), h = e.getUTCMonth(), i = Math.abs(b);
            if (b = b > 0 ? 1 : -1, 1 === i) d = b === -1 ? function () {
                return e.getUTCMonth() === h
            } : function () {
                return e.getUTCMonth() !== c
            }, c = h + b, e.setUTCMonth(c), c = (c + 12) % 12; else {
                for (var j = 0; j < i; j++) e = this.moveMonth(e, b);
                c = e.getUTCMonth(), e.setUTCDate(f), d = function () {
                    return c !== e.getUTCMonth()
                }
            }
            for (; d();) e.setUTCDate(--f), e.setUTCMonth(c);
            return e
        },
        moveYear: function (a, b) {
            return this.moveMonth(a, 12 * b)
        },
        moveAvailableDate: function (a, b, c) {
            do {
                if (a = this[c](a, b), !this.dateWithinRange(a)) return !1;
                c = "moveDay"
            } while (this.dateIsDisabled(a));
            return a
        },
        weekOfDateIsDisabled: function (b) {
            return a.inArray(b.getUTCDay(), this.o.daysOfWeekDisabled) !== -1
        },
        dateIsDisabled: function (b) {
            return this.weekOfDateIsDisabled(b) || a.grep(this.o.datesDisabled, function (a) {
                return e(b, a)
            }).length > 0
        },
        dateWithinRange: function (a) {
            return a >= this.o.startDate && a <= this.o.endDate
        },
        keydown: function (a) {
            if (!this.picker.is(":visible")) return void (40 !== a.keyCode && 27 !== a.keyCode || (this.show(), a.stopPropagation()));
            var b, c, d = !1, e = this.focusDate || this.viewDate;
            switch (a.keyCode) {
                case 27:
                    this.focusDate ? (this.focusDate = null, this.viewDate = this.dates.get(-1) || this.viewDate, this.fill()) : this.hide(), a.preventDefault(), a.stopPropagation();
                    break;
                case 37:
                case 38:
                case 39:
                case 40:
                    if (!this.o.keyboardNavigation || 7 === this.o.daysOfWeekDisabled.length) break;
                    b = 37 === a.keyCode || 38 === a.keyCode ? -1 : 1, 0 === this.viewMode ? a.ctrlKey ? (c = this.moveAvailableDate(e, b, "moveYear"), c && this._trigger("changeYear", this.viewDate)) : a.shiftKey ? (c = this.moveAvailableDate(e, b, "moveMonth"), c && this._trigger("changeMonth", this.viewDate)) : 37 === a.keyCode || 39 === a.keyCode ? c = this.moveAvailableDate(e, b, "moveDay") : this.weekOfDateIsDisabled(e) || (c = this.moveAvailableDate(e, b, "moveWeek")) : 1 === this.viewMode ? (38 !== a.keyCode && 40 !== a.keyCode || (b *= 4), c = this.moveAvailableDate(e, b, "moveMonth")) : 2 === this.viewMode && (38 !== a.keyCode && 40 !== a.keyCode || (b *= 4), c = this.moveAvailableDate(e, b, "moveYear")), c && (this.focusDate = this.viewDate = c, this.setValue(), this.fill(), a.preventDefault());
                    break;
                case 13:
                    if (!this.o.forceParse) break;
                    e = this.focusDate || this.dates.get(-1) || this.viewDate, this.o.keyboardNavigation && (this._toggle_multidate(e), d = !0), this.focusDate = null, this.viewDate = this.dates.get(-1) || this.viewDate, this.setValue(), this.fill(), this.picker.is(":visible") && (a.preventDefault(), a.stopPropagation(), this.o.autoclose && this.hide());
                    break;
                case 9:
                    this.focusDate = null, this.viewDate = this.dates.get(-1) || this.viewDate, this.fill(), this.hide()
            }
            d && (this.dates.length ? this._trigger("changeDate") : this._trigger("clearDate"), this.inputField.trigger("change"))
        },
        setViewMode: function (a) {
            this.viewMode = a, this.picker.children("div").hide().filter(".datepicker-" + r.viewModes[this.viewMode].clsName).show(), this.updateNavArrows(), this._trigger("changeViewMode", new Date(this.viewDate))
        }
    };
    var l = function (b, c) {
        a.data(b, "datepicker", this), this.element = a(b), this.inputs = a.map(c.inputs, function (a) {
            return a.jquery ? a[0] : a
        }), delete c.inputs, this.keepEmptyValues = c.keepEmptyValues, delete c.keepEmptyValues, n.call(a(this.inputs), c).on("changeDate", a.proxy(this.dateUpdated, this)), this.pickers = a.map(this.inputs, function (b) {
            return a.data(b, "datepicker")
        }), this.updateDates()
    };
    l.prototype = {
        updateDates: function () {
            this.dates = a.map(this.pickers, function (a) {
                return a.getUTCDate()
            }), this.updateRanges()
        },
        updateRanges: function () {
            var b = a.map(this.dates, function (a) {
                return a.valueOf()
            });
            a.each(this.pickers, function (a, c) {
                c.setRange(b)
            })
        },
        dateUpdated: function (c) {
            if (!this.updating) {
                this.updating = !0;
                var d = a.data(c.target, "datepicker");
                if (d !== b) {
                    var e = d.getUTCDate(), f = this.keepEmptyValues, g = a.inArray(c.target, this.inputs), h = g - 1,
                        i = g + 1, j = this.inputs.length;
                    if (g !== -1) {
                        if (a.each(this.pickers, function (a, b) {
                            b.getUTCDate() || b !== d && f || b.setUTCDate(e)
                        }), e < this.dates[h]) for (; h >= 0 && e < this.dates[h];) this.pickers[h--].setUTCDate(e); else if (e > this.dates[i]) for (; i < j && e > this.dates[i];) this.pickers[i++].setUTCDate(e);
                        this.updateDates(), delete this.updating
                    }
                }
            }
        },
        destroy: function () {
            a.map(this.pickers, function (a) {
                a.destroy()
            }), a(this.inputs).off("changeDate", this.dateUpdated), delete this.element.data().datepicker
        },
        remove: f("destroy", "Method `remove` is deprecated and will be removed in version 2.0. Use `destroy` instead")
    };
    var m = a.fn.datepicker, n = function (c) {
        var d = Array.apply(null, arguments);
        d.shift();
        var e;
        if (this.each(function () {
            var b = a(this), f = b.data("datepicker"), g = "object" == typeof c && c;
            if (!f) {
                var j = h(this, "date"), m = a.extend({}, o, j, g), n = i(m.language), p = a.extend({}, o, n, j, g);
                b.hasClass("input-daterange") || p.inputs ? (a.extend(p, {inputs: p.inputs || b.find("input").toArray()}), f = new l(this, p)) : f = new k(this, p), b.data("datepicker", f)
            }
            "string" == typeof c && "function" == typeof f[c] && (e = f[c].apply(f, d))
        }), e === b || e instanceof k || e instanceof l) return this;
        if (this.length > 1) throw new Error("Using only allowed for the collection of a single element (" + c + " function)");
        return e
    };
    a.fn.datepicker = n;
    var o = a.fn.datepicker.defaults = {
        assumeNearbyYear: !1,
        autoclose: !1,
        beforeShowDay: a.noop,
        beforeShowMonth: a.noop,
        beforeShowYear: a.noop,
        beforeShowDecade: a.noop,
        beforeShowCentury: a.noop,
        calendarWeeks: !1,
        clearBtn: !1,
        toggleActive: !1,
        daysOfWeekDisabled: [],
        daysOfWeekHighlighted: [],
        datesDisabled: [],
        endDate: 1 / 0,
        forceParse: !0,
        format: "mm/dd/yyyy",
        keepEmptyValues: !1,
        keyboardNavigation: !0,
        language: "en",
        minViewMode: 0,
        maxViewMode: 4,
        multidate: !1,
        multidateSeparator: ",",
        orientation: "auto",
        rtl: !1,
        startDate: -(1 / 0),
        startView: 0,
        todayBtn: !1,
        todayHighlight: !1,
        updateViewDate: !0,
        weekStart: 0,
        disableTouchKeyboard: !1,
        enableOnReadonly: !0,
        showOnFocus: !0,
        zIndexOffset: 10,
        container: "body",
        immediateUpdates: !1,
        title: "",
        templates: {leftArrow: "&#x00AB;", rightArrow: "&#x00BB;"},
        showWeekDays: !0
    }, p = a.fn.datepicker.locale_opts = ["format", "rtl", "weekStart"];
    a.fn.datepicker.Constructor = k;
    var q = a.fn.datepicker.dates = {
        en: {
            days: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
            daysShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
            daysMin: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"],
            months: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
            monthsShort: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            today: "Today",
            clear: "Clear",
            titleFormat: "MM yyyy"
        }
    }, r = {
        viewModes: [{names: ["days", "month"], clsName: "days", e: "changeMonth"}, {
            names: ["months", "year"],
            clsName: "months",
            e: "changeYear",
            navStep: 1
        }, {
            names: ["years", "decade"],
            clsName: "years",
            e: "changeDecade",
            navStep: 10
        }, {
            names: ["decades", "century"],
            clsName: "decades",
            e: "changeCentury",
            navStep: 100
        }, {names: ["centuries", "millennium"], clsName: "centuries", e: "changeMillennium", navStep: 1e3}],
        validParts: /dd?|DD?|mm?|MM?|yy(?:yy)?/g,
        nonpunctuation: /[^ -\/:-@\u5e74\u6708\u65e5\[-`{-~\t\n\r]+/g,
        parseFormat: function (a) {
            if ("function" == typeof a.toValue && "function" == typeof a.toDisplay) return a;
            var b = a.replace(this.validParts, "\0").split("\0"), c = a.match(this.validParts);
            if (!b || !b.length || !c || 0 === c.length) throw new Error("Invalid date format.");
            return {separators: b, parts: c}
        },
        parseDate: function (c, e, f, g) {
            function h(a, b) {
                return b === !0 && (b = 10), a < 100 && (a += 2e3, a > (new Date).getFullYear() + b && (a -= 100)), a
            }

            function i() {
                var a = this.slice(0, j[n].length), b = j[n].slice(0, a.length);
                return a.toLowerCase() === b.toLowerCase()
            }

            if (!c) return b;
            if (c instanceof Date) return c;
            if ("string" == typeof e && (e = r.parseFormat(e)), e.toValue) return e.toValue(c, e, f);
            var j, l, m, n, o, p = {d: "moveDay", m: "moveMonth", w: "moveWeek", y: "moveYear"},
                s = {yesterday: "-1d", today: "+0d", tomorrow: "+1d"};
            if (c in s && (c = s[c]), /^[\-+]\d+[dmwy]([\s,]+[\-+]\d+[dmwy])*$/i.test(c)) {
                for (j = c.match(/([\-+]\d+)([dmwy])/gi), c = new Date, n = 0; n < j.length; n++) l = j[n].match(/([\-+]\d+)([dmwy])/i), m = Number(l[1]), o = p[l[2].toLowerCase()], c = k.prototype[o](c, m);
                return k.prototype._zero_utc_time(c)
            }
            j = c && c.match(this.nonpunctuation) || [];
            var t, u, v = {}, w = ["yyyy", "yy", "M", "MM", "m", "mm", "d", "dd"], x = {
                yyyy: function (a, b) {
                    return a.setUTCFullYear(g ? h(b, g) : b)
                }, m: function (a, b) {
                    if (isNaN(a)) return a;
                    for (b -= 1; b < 0;) b += 12;
                    for (b %= 12, a.setUTCMonth(b); a.getUTCMonth() !== b;) a.setUTCDate(a.getUTCDate() - 1);
                    return a
                }, d: function (a, b) {
                    return a.setUTCDate(b)
                }
            };
            x.yy = x.yyyy, x.M = x.MM = x.mm = x.m, x.dd = x.d, c = d();
            var y = e.parts.slice();
            if (j.length !== y.length && (y = a(y).filter(function (b, c) {
                return a.inArray(c, w) !== -1
            }).toArray()), j.length === y.length) {
                var z;
                for (n = 0, z = y.length; n < z; n++) {
                    if (t = parseInt(j[n], 10), l = y[n], isNaN(t)) switch (l) {
                        case"MM":
                            u = a(q[f].months).filter(i), t = a.inArray(u[0], q[f].months) + 1;
                            break;
                        case"M":
                            u = a(q[f].monthsShort).filter(i), t = a.inArray(u[0], q[f].monthsShort) + 1
                    }
                    v[l] = t
                }
                var A, B;
                for (n = 0; n < w.length; n++) B = w[n], B in v && !isNaN(v[B]) && (A = new Date(c), x[B](A, v[B]), isNaN(A) || (c = A))
            }
            return c
        },
        formatDate: function (b, c, d) {
            if (!b) return "";
            if ("string" == typeof c && (c = r.parseFormat(c)), c.toDisplay) return c.toDisplay(b, c, d);
            var e = {
                d: b.getUTCDate(),
                D: q[d].daysShort[b.getUTCDay()],
                DD: q[d].days[b.getUTCDay()],
                m: b.getUTCMonth() + 1,
                M: q[d].monthsShort[b.getUTCMonth()],
                MM: q[d].months[b.getUTCMonth()],
                yy: b.getUTCFullYear().toString().substring(2),
                yyyy: b.getUTCFullYear()
            };
            e.dd = (e.d < 10 ? "0" : "") + e.d, e.mm = (e.m < 10 ? "0" : "") + e.m, b = [];
            for (var f = a.extend([], c.separators), g = 0, h = c.parts.length; g <= h; g++) f.length && b.push(f.shift()), b.push(e[c.parts[g]]);
            return b.join("")
        },
        headTemplate: '<thead><tr><th colspan="7" class="datepicker-title"></th></tr><tr><th class="prev">' + o.templates.leftArrow + '</th><th colspan="5" class="datepicker-switch"></th><th class="next">' + o.templates.rightArrow + "</th></tr></thead>",
        contTemplate: '<tbody><tr><td colspan="7"></td></tr></tbody>',
        footTemplate: '<tfoot><tr><th colspan="7" class="today"></th></tr><tr><th colspan="7" class="clear"></th></tr></tfoot>'
    };
    r.template = '<div class="datepicker"><div class="datepicker-days"><table class="table-condensed">' + r.headTemplate + "<tbody></tbody>" + r.footTemplate + '</table></div><div class="datepicker-months"><table class="table-condensed">' + r.headTemplate + r.contTemplate + r.footTemplate + '</table></div><div class="datepicker-years"><table class="table-condensed">' + r.headTemplate + r.contTemplate + r.footTemplate + '</table></div><div class="datepicker-decades"><table class="table-condensed">' + r.headTemplate + r.contTemplate + r.footTemplate + '</table></div><div class="datepicker-centuries"><table class="table-condensed">' + r.headTemplate + r.contTemplate + r.footTemplate + "</table></div></div>", a.fn.datepicker.DPGlobal = r, a.fn.datepicker.noConflict = function () {
        return a.fn.datepicker = m, this
    }, a.fn.datepicker.version = "1.7.1", a.fn.datepicker.deprecated = function (a) {
        var b = window.console;
        b && b.warn && b.warn("DEPRECATED: " + a)
    }, a(document).on("focus.datepicker.data-api click.datepicker.data-api", '[data-provide="datepicker"]', function (b) {
        var c = a(this);
        c.data("datepicker") || (b.preventDefault(), n.call(c, "show"))
    }), a(function () {
        n.call(a('[data-provide="datepicker-inline"]'))
    })
});

if (document.getElementById('datepicker')) {
    var date = new Date();
    date.setDate(date.getDate());
    $('#datepicker').datepicker({
        startDate: date,
        weekStart: 0,
        daysOfWeekHighlighted: "6,0",
        autoclose: true,
        todayHighlight: true,
    });
    $('#datepicker').datepicker("setDate", new Date());

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blah')
                    .attr('src', e.target.result)
                    .width(150)
                    .height(200);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    function previewFile() {
        var preview = document.querySelector('#rec-img');
        var file = document.querySelector('input[type=file]').files[0];
        var reader = new FileReader();

        reader.addEventListener("load", function () {
            console.log('before preview');
            preview.src = reader.result;
            console.log('after preview');
        }, false);

        if (file) {
            console.log('inside if');
            reader.readAsDataURL(file);
        } else {
            console.log('inside else');
        }

    }
}

window.onload = function (e) {
    //perform a for loop to add the event handler
    Array.from(document.getElementsByClassName("time-input")).forEach(
        function (element, index, array) {
            //Add the event handler to the time input
            element.addEventListener("blur", inputTimeBlurEvent);
        }
    );
}

inputTimeBlurEvent = function (e) {
    var newTime = "";
    var timeValue = e.target.value;
    var numbers = [];
    var splitTime = [];

    if (timeValue.trim() == "") {
        e.target.value = "00:00";
        return;
    }

    var regex = /^[0-9.:]+$/;
    if (!regex.test(timeValue)) {
        e.target.value = "00:00";
        return;
    }

    e.target.value = e.target.value.replace(".", ":").replace(/\./g, "");
    timeValue = e.target.value;

    if (timeValue.indexOf(".") == -1 && timeValue.indexOf(":") == -1) {
        if (timeValue.trim().length > 4) {
            timeValue = timeValue.substring(0, 4);
        }
        var inputTimeLength = timeValue.trim().length;
        numbers = timeValue.split('');
        switch (inputTimeLength) {
            case 2:
                if (parseInt(timeValue) <= 0) {
                    e.target.value = "00:00";
                } else if (parseInt(timeValue) >= 24) {
                    e.target.value = "00:00";
                } else {
                    e.target.value = timeValue + ":00";
                }
                break;
            case 3:
                newTime = "0" + numbers[0] + ":";
                if (parseInt(numbers[1] + numbers[2]) > 59) {
                    newTime += "00";
                } else {
                    newTime += numbers[1] + numbers[2];
                }
                e.target.value = newTime;
                break;
            case 4:
                if (parseInt(numbers[0] + numbers[1]) >= 24) {
                    newTime = "23:";
                } else {
                    newTime = numbers[0] + numbers[1] + ":";
                }
                if (parseInt(numbers[2] + numbers[3]) > 59) {
                    newTime += "59";
                } else {
                    newTime += numbers[2] + numbers[3];
                }
                e.target.value = newTime;
                break;
        }
        return;
    }

    //5th condition: if double dot found
    var doubleDotIndex = timeValue.indexOf(":");
    //if user doesnt enter the first part of hours example => :35
    if (doubleDotIndex == 0) {
        newTime = "00:";
        splitTime = timeValue.split(':');
        numbers = splitTime[1].split('');
        if (parseInt(numbers[0] + numbers[1]) > 59) {
            newTime += "00";
        } else {
            newTime += numbers[0] + numbers[1];
        }
        e.target.value = newTime;
        return;
    } else {
        //if user enter not full time example=> 9:3
        splitTime = timeValue.split(':');
        var partTime1 = splitTime[0].split('');
        if (partTime1.length == 1) {
            newTime = "0" + partTime1[0] + ":";
        } else {
            if (parseInt(partTime1[0] + partTime1[1]) > 23) {
                newTime = "00:";
            } else {
                newTime = partTime1[0] + partTime1[1] + ":";
            }
        }

        var partTime2 = splitTime[1].split('');
        if (partTime2.length == 1) {
            newTime += "0" + partTime2[0];
        } else {
            if (parseInt(partTime2[0] + partTime2[1]) > 59) {
                newTime += "00";
            } else {
                newTime += partTime2[0] + partTime2[1];
            }
        }
        e.target.value = newTime;
        return;
    }
}


function getCheckedBoxes() {
    if ($(".filter").hasClass("showing")) {
        $(".filter").toggleClass("showing");
    }
    var checkboxes = document.getElementsByName("ingr-item");
    var field = document.getElementById("Search");
    var div = document.getElementById("filter");
    var span = div.getElementsByTagName("span");
    var items = document.getElementsByName("ingr");
    var checkboxesChecked = [];
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            checkboxesChecked.push(span[i].innerText);
        }
    }
    console.log(checkboxesChecked);
    console.log(field.value);
    console.log(span.value);
    console.log(div.value);
    // if ( (checkboxesChecked.length == 0) || (!(field.innerText))){
    //   alert("No data was inserted");
    // }
}

var nums = $("#loop .items").length;
var limitPerPage = 9;

var pages = Math.round(nums / limitPerPage);

$("#loop .items:gt(" + (limitPerPage - 1) + ")").hide();

$('.pagination').append("<li class='page-item' id = 'Prev' ><button class='like-link page-link'  href='javascript:void(0)'>" + ' <i class="fas fa-arrow-left"></i>' + "</button></li>");
$('.pagination').append("<li class ='active-num current-page page-item'><button class='like-link page-link' href='javascript:void(0)'>1</button></li>");
for (var i = 2; i < pages; i++) {
    if (i > 5) {
        $('.pagination').append("<li class ='current-page page-item non'><button class='like-link page-link' name='" + i + "' href='javascript:void(0)'>" + i + "</button></li>");
    } else {
        $('.pagination').append("<li class ='current-page page-item '><button class='like-link page-link' name='" + i + "' href='javascript:void(0)'>" + i + "</button></li>");
    }
}

$('.pagination').append("<li class='page-item' id='Next'><button class='like-link page-link' href='javascript:void(0)'>" + '<i class="fas fa-arrow-right"></i>' + "</button></li>");

$('.pagination li.current-page').on("click", function () {
    if ($(this).hasClass("active-num")) {
        return false;
    } else {
        var currentPage = $(this).index();
        $(".pagination li").removeClass("active-num");
        $(this).addClass("active-num");

        $("#loop .items").hide();
        $("html, body").animate({scrollTop: 0}, "slow");
        prevActive = currentPage;
        var grandTotal = limitPerPage * currentPage;
        if (currentPage != pages && currentPage > 3 && currentPage < pages - 2) {
            for (var i = 0; i < (currentPage + 4); i++) {
                if ($(".pagination li.current-page:eq(" + (i) + ")").hasClass("non")) {
                    if ((i > (currentPage - 4) && i < (currentPage + 2)) || (i == (currentPage))) {
                        $(".pagination li.current-page:eq(" + (i) + ")").removeClass("non");
                    } else {
                        $(".pagination li.current-page:eq(" + (i) + ")").addClass("non");
                    }
                } else {
                    if ((i > (currentPage - 4) && i < (currentPage + 2)) || (i == (currentPage))) {
                        $(".pagination li.current-page:eq(" + (i) + ")").removeClass("non");
                    } else {
                        $(".pagination li.current-page:eq(" + (i) + ")").addClass("non");
                    }
                }
            }
        }
        for (var i = grandTotal - limitPerPage; i < grandTotal; i++) {
            $("#loop .items:eq(" + i + ")").show();
        }
    }
});

$("#Next").on("click", function () {
    var currentPage = $(".pagination li.active-num").index();
    if (currentPage === pages) {
        return false;
    } else {
        currentPage++;
        $(".pagination li").removeClass("active-num");
        $("#loop .items").hide();
        var grandTotal = limitPerPage * currentPage;
        $("html, body").animate({scrollTop: 0}, "slow");
        for (var i = grandTotal - limitPerPage; i < grandTotal; i++) {
            $("#loop .items:eq(" + i + ")").show();
        }
        $(".pagination li.current-page:eq(" + (currentPage - 1) + ")").addClass("active-num");
        if (currentPage != pages && currentPage > 3 && currentPage < pages - 2) {
            $(".pagination li.current-page:eq(" + (currentPage - 4) + ")").addClass("non");
            $(".pagination li.current-page:eq(" + (currentPage - 5) + ")").addClass("non");
            $(".pagination li.current-page:eq(" + (currentPage - 6) + ")").addClass("non");
            $(".pagination li.current-page:eq(" + (currentPage - 1) + ")").removeClass("non");
            $(".pagination li.current-page:eq(" + (currentPage) + ")").removeClass("non");
            $(".pagination li.current-page:eq(" + (currentPage + 1) + ")").removeClass("non");
        }
    }
});

$("#Prev").on("click", function () {
    var currentPage = $(".pagination li.active-num").index();
    if (currentPage === 1) {
        return false;
    } else {
        currentPage--;
        $(".pagination li").removeClass("active-num");
        $("#loop .items").hide();
        $("html, body").animate({scrollTop: 0}, "slow");
        var grandTotal = limitPerPage * currentPage;
        for (var i = grandTotal - limitPerPage; i < grandTotal; i++) {
            $("#loop .items:eq(" + i + ")").show();
        }
        $(".pagination li.current-page:eq(" + (currentPage - 1) + ")").addClass("active-num");
        if (currentPage != 1 && currentPage === pages - 1) {
            $(".pagination li.current-page:eq(" + (currentPage - 1) + ")").removeClass("non");
        }
        if (currentPage != pages && currentPage > 3 && currentPage < pages - 2) {
            $(".pagination li.current-page:eq(" + (currentPage + 2) + ")").addClass("non");
            $(".pagination li.current-page:eq(" + (currentPage - 2) + ")").removeClass("non");
            $(".pagination li.current-page:eq(" + (currentPage - 3) + ")").removeClass("non");
            $(".pagination li.current-page:eq(" + (currentPage) + ")").removeClass("non");
            $(".pagination li.current-page:eq(" + (currentPage - 1) + ")").removeClass("non");
            $(".pagination li.current-page:eq(" + (currentPage + 1) + ")").removeClass("non");
        }
    }
});
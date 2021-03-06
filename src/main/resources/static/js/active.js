(function ($) {
    'use strict';

    var $window = $(window);

    // :: Nav Active Code
    if ($.fn.classyNav) {
        $('#essenceNav').classyNav();
    }

    // :: Sliders Active Code
    if ($.fn.owlCarousel) {
        $('.popular-products-slides').owlCarousel({
            items: 4,
            margin: 30,
            loop: true,
            nav: false,
            dots: false,
            autoplay: true,
            autoplayTimeout: 5000,
            smartSpeed: 1000,
            responsive: {
                0: {
                    items: 1
                },
                576: {
                    items: 2
                },
                768: {
                    items: 3
                },
                992: {
                    items: 4
                }
            }
        });
        $('.product_thumbnail_slides').owlCarousel({
            items: 1,
            margin: 0,
            loop: true,
            nav: true,
            navText: ["<img src='img/core-img/long-arrow-left.svg' alt=''>", "<img src='img/core-img/long-arrow-right.svg' alt=''>"],
            dots: false,
            autoplay: true,
            autoplayTimeout: 5000,
            smartSpeed: 1000
        });
    }

    // :: Header Cart Active Code
    var cartbtn1 = $('#essenceCartBtn');
    var cartOverlay = $(".cart-bg-overlay");
    var cartWrapper = $(".right-side-cart-area");
    var cartbtn2 = $("#rightSideCart");
    var cartOverlayOn = "cart-bg-overlay-on";
    var cartOn = "cart-on";

    cartbtn1.on('click', function () {
        $('.cart-list').empty();
        var valor_carrinho = 0;
        if (typeof carrinho !== 'undefined') {
            for (var i = 0; i < carrinho["produtos"].length; i++) {
                valor_carrinho += parseInt(carrinho["produtos"][i].preco.replace("R$", "")*carrinho["produtos"][i].quantidade);
                $('.cart-list').append('<div class="single-cart-item">' +
                    '<a href="#" class="product-image"><span style="display: none" class="id-produto-cart">' + carrinho["produtos"][i].id + '</span>' +
                    '<img src="' + carrinho["produtos"][i].imagem + '" class="cart-thumb" alt="">' +
                    '<div class="cart-item-desc">' +
                    '<span class="product-remove removeprodutobtn"><i class="fa fa-close deletacarrinho" aria-hidden="true"></i></span>' +
                    '<span class="badge">' + carrinho["produtos"][i].marca + '</span> <!-- marca do produto -->' +
                    '<h6 class="nome-prod-carrinho">' + carrinho["produtos"][i].nome + '</h6> <!-- nome do produto -->' +
                    '<p class="size">Quantidade: ' + carrinho["produtos"][i].quantidade + '</p> <!-- quantidade que ele quer comprar -->' +
                    '<p class="price">R$' + carrinho["produtos"][i].preco.replace("R$", "") * carrinho["produtos"][i].quantidade + '</p> <!-- valor por unidade -->' +
                    '</div> ' +
                    '</a>' +
                    '</div>');
            }
        }

        $('#totalcomfrete').html("R$"+valor_carrinho);
        $('#totalsemfrete').html("R$"+valor_carrinho);
        carrinho.valor = valor_carrinho;
        localStorage.setItem('carrinho_pi4', JSON.stringify(carrinho));
        cartOverlay.toggleClass(cartOverlayOn);
        cartWrapper.toggleClass(cartOn);
    });
    cartOverlay.on('click', function () {
        $(this).removeClass(cartOverlayOn);
        cartWrapper.removeClass(cartOn);
    });
    cartbtn2.on('click', function () {
        cartOverlay.removeClass(cartOverlayOn);
        cartWrapper.removeClass(cartOn);
    });

    // :: ScrollUp Active Code
    if ($.fn.scrollUp) {
        $.scrollUp({
            scrollSpeed: 1000,
            easingType: 'easeInOutQuart',
            scrollText: '<i class="fa fa-angle-up" aria-hidden="true"></i>'
        });
    }

    // :: Sticky Active Code
    $window.on('scroll', function () {
        if ($window.scrollTop() > 0) {
            $('.header_area').addClass('sticky');
        } else {
            $('.header_area').removeClass('sticky');
        }
    });

    // :: Nice Select Active Code
    /*if ($.fn.niceSelect) {
        $('select').niceSelect();
    }*/

    // :: Slider Range Price Active Code
    $('.slider-range-price').each(function () {
        var min = jQuery(this).data('min');
        var max = jQuery(this).data('max');
        var unit = jQuery(this).data('unit');
        var value_min = jQuery(this).data('value-min');
        var value_max = jQuery(this).data('value-max');
        var label_result = jQuery(this).data('label-result');
        var encontrou = 0;
        var t = $(this);
        $(this).slider({
            range: true,
            min: min,
            max: max,
            values: [value_min, value_max],
            slide: function (event, ui) {
                var result = label_result + " " + unit + ui.values[0] + ' - ' + unit + ui.values[1];
                console.log(t);
                t.closest('.slider-range').find('.range-price').html(result);
                console.log("Min: " + ui.values[0] + " max: " + ui.values[1]);

                encontrou = 0;
                $('#lista-produtos').children('div').each(function () {
                    if($(this).find(".preco-prod").html().replace(unit, "") >= ui.values[0] && $(this).find(".preco-prod").html().replace(unit, "") <= ui.values[1])
                    {
                        $(this).show();
                        encontrou++;
                    } else {
                        $(this).hide();
                    }


                });
                $("#produtos-encontrados").html(encontrou);

            }
        });
    });

    // :: Favorite Button Active Code
    var favme = $(".favme");

    favme.on('click', function () {
        $(this).toggleClass('active');
    });

    favme.on('click touchstart', function () {
        $(this).toggleClass('is_animating');
    });

    favme.on('animationend', function () {
        $(this).toggleClass('is_animating');
    });

    // :: Nicescroll Active Code
    if ($.fn.niceScroll) {
        $(".cart-list, .cart-content").niceScroll();
    }

    // :: wow Active Code
    if ($window.width() > 767) {
        new WOW().init();
    }

    // :: Tooltip Active Code
    if ($.fn.tooltip) {
        $('[data-toggle="tooltip"]').tooltip();
    }

    // :: PreventDefault a Click
    $("a[href='#']").on('click', function ($) {
        $.preventDefault();
    });

})(jQuery);
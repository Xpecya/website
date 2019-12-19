// 带参数的一般方法
function generalAjax(url, method, data, imageCheck, consumer) {
    console.log(data);
    $.ajax({
        url: url,
        type: method,
        contentType: "application/json",
        data: data,
        success: abstractConsumer(consumer)
    });
    return false;
}

function abstractConsumer(consumer) {
    return function(data) {
        data = JSON.parse(data);
        var status = data.status;
        if (!status) {
            alert(data.errorMessage);
        } else {
            consumer(data.data);
        }
    }
}

// 带参数的GET查询
function getWithParam(url, data, consumer) {
    $.ajax({
        url, data, success: abstractConsumer(consumer)
    });
}

// 不带参数的GET查询
function getWithoutParam(url, consumer) {
    return getWithParam(url, null, consumer);
}

// 加载html
function loadHtml(url, selector, flag) {
    $.ajax({
        url: url,
        type: "GET",
        success: function(data) {
            $(selector).html(data);
            var show = $(".body .show");
            show.hide();
            show.removeClass("show");
            $(selector).addClass("show");
            show = $(".body .show");
            show.show();
            if (flag != null) flag = true;
        }
    });
}

// 根据json对象生成html
function json2html(jsonObject) {
    const tagName = jsonObject.tagName;
    const result = document.createElement(tagName);
    result.id = jsonObject.id;
    result.className = jsonObject.className;
}

var sessionId = null;

// 如果发现用户尚未登录，跳转到login页面
function loginCheck() {
    if (sessionId === null) {
        $(".content").add("disappear");
        $(".login").removeClass("disappear");
    }
}

// 点击登录按钮
function onLoginClick() {
    var username = $(".username").val();
    var password = $(".password").val();
    getWithParam("/login", {
        username, password
    }, function(data) {
        sessionId = data.id;
        $(".login").addClass("disappear");
        $(".content").removeClass("disappear");
        $("a.username").text(" " + username);
    });
}

// 添加一个简介小标题
var columnCounter = 0;
function addNewColumn() {
    var parent = $(".form")[0];
    var button = $("button.right")[0];
    parent.removeChild(button);

    var div = document.createElement("div");
    div.className = "columns";

    var titleLabel = document.createElement("label");
    titleLabel.innerText = "小标题 ";
    var innerTitleSpan = document.createElement("span");
    innerTitleSpan.style = "color: red";
    innerTitleSpan.innerText = "*";
    titleLabel.appendChild(innerTitleSpan);
    div.appendChild(titleLabel);

    var input = document.createElement("input");
    input.type = "text";
    input.className = "field size1 name_" + columnCounter;
    div.appendChild(input);

    var contentLabel = document.createElement("label");
    contentLabel.innerText = "小标题内容 ";
    var innerContentSpan = document.createElement("span");
    innerContentSpan.style = "color: red";
    innerContentSpan.innerText = "*";
    contentLabel.appendChild(innerContentSpan);
    div.appendChild(contentLabel);

    var content = document.createElement("textarea");
    content.className = "field size1 content_" + columnCounter;
    content.rows = 10;
    content.cols = 30;
    div.appendChild(content);

    parent.appendChild(div);
    columnCounter += 1;
    parent.appendChild(button);
}

// 点击按钮上传新的简介内容和(如果存在的话)小标题及其内容
function createDetailRequest() {
    var params = [];
    var detailName = $(".field[name=name]").val();
    if (name === null) {
        alert("简介标题缺失!");
    } else {
        for (var i = 0; i < columnCounter; i++) {
            var name = $(".name_" + i).val();
            var content = $(".content_" + i).val();
            params.push({name, content});
        }
        generalAjax("/introduction/detail", "PUT",
            JSON.stringify({sessionId, name: detailName, params}), false, function(data) {
            $(".columns").remove();
            $(".detailName").val("");
            loadDetailNames();
        });
    }
    return false;
}

// 点击提交按钮后的操作
function generalSubmit(url, method, callback) {
    var formData = new FormData($(".updateForm")[0]);
    if (imageCheck) return function(event) {generalAjax(url, method, formData, imageCheck, callback);};
    else return function(event) {};
}

// 点击返回按钮的通用处理
function generalBack(createMethod, data) {
    generalUpdate(createMethod, null, null, null, null);
    $(".return").remove();
}

// 生成一个新的中央table
// @param thNames 除了修改操作之外的header名称列表
function newTable(thNames) {
    var container = $(".table")[0];
    $(".mainTable").remove();
    var table = document.createElement("table");
    table.width = "100%";
    table.border = 0;
    table.cellSpacing = 0;
    table.className = "mainTable";

    var tr = document.createElement("tr");

    var length = thNames.length;
    for (var i = 0; i < length; i++) {
        var th = document.createElement("th");
        th.innerText = thNames[i];
        tr.appendChild(th);
    }
    var update = document.createElement("th");
    update.width = 110;
    update.className = "ac";
    tr.appendChild(update);
    table.appendChild(tr);

    container.appendChild(table);
    return table;
}

// 添加分页导航栏
// @return 分页栏右侧div
function pagingObject(pageNumber) {
    var div = document.createElement("div");
    var left = document.createElement("div");
    left.className = "left";
    div.appendChild(left);

    for (var i = 0; i < pageNumber; i++) {
        var pageNav = document.createElement("span");
        pageNav.className = "pageNav";
        pageNav.innerText = i;
        left.appendChild(pageNav);
    }

    var right = document.createElement("div");
    right.className = "right";
    div.appendChild("right");

    $(".table")[0].appendChild(div);
    return right;
}

// 通用删除方法
function generalDelete(url, id, initMethod) {
    generalAjax(url, "DELETE", JSON.stringify({sessionId, id}), false, function(data) {
        alert("删除成功！");
        initMethod();
    });
}

// 当前显示在页面中的数据
var centralData = [];

// 通用中央显示方法
function generalShow(columnNames, readArray, updateObject, deleteObject) {
    var table = newTable(columnNames);
    $(".navContainer").remove();

    var length = readArray.length;
    for (var i = 0; i < length; i++) {
        var currentData = centralData[i];
        var id = currentData.id;
        var readObject = readArray[i];
        var readClassName = readObject.className + "_" + id;

        var tr = document.createElement("tr");
        var td = document.createElement("td");
        var h3 = document.createElement("h3");
        var a = document.createElement("a");
        a.href = "#";
        a.className = readClassName;
        a.innerText = readObject.innerText;
        a.onclick = readObject.clickMethod;
        h3.appendChild(a);
        td.appendChild(h3);
        tr.appendChild(td);

        var tools = document.createElement("td");

        var deleteTag = document.createElement("a");
        deleteTag.href = "#";
        deleteTag.className = "ico del " + deleteObject.className + "_" + id;
        deleteTag.innerText = "删除";
        deleteTag.onclick = deleteObject.clickMethod;
        tools.appendChild(deleteTag);

        var updateTag = document.createElement("a");
        updateTag.href = "#";
        updateTag.className = "ico edit " + updateObject.className + "_" + id;
        updateTag.innerText = "修改";
        updateTag.onclick = updateObject.clickMethod;
        tools.appendChild(updateTag);

        tr.appendChild(tools);
        table.appendChild(tr);
    }
}

// 通用导航栏生成方法
function navigator(innerText, showMethod) {
    var parent = $(".small-nav")[0];

    var navContainer = document.createElement("span");
    navContainer.className = "navContainer";
    parent.appendChild(navContainer);

    var navSpan = document.createElement("span");
    navSpan.innerText = " > ";
    navContainer.appendChild(navSpan);

    var a = document.createElement("a");
    a.innerText = innerText;
    a.href = "#";
    navContainer.appendChild(a);

    $(".introduction-nav").on("click", function() {
        showMethod();
    });
}

// 用于修改的数据详情
var dataForUpdate;
var imageCheck = false;

// 通用修改方法
function generalUpdate(updateMethod, returnMethod, url, id, getDataMethod) {
    var updateWithData = function(dataObject) {
        $(".form").remove();
        var parent = $(".updateForm")[0];
        parent.onsubmit = function(event) {event.preventDefault();}
        var form = document.createElement("div");
        form.className = "form";

        var id = dataObject.id;
        var idInput = document.createElement("input");
        idInput.type = "text";
        idInput.style = "display:none;";
        idInput.value = id;
        idInput.name = "id";
        form.appendChild(idInput);

        var length = dataObject.array.length;
        for (var i = 0; i < length; i++) {
            var p = document.createElement("p");
            form.appendChild(p);

            var data = dataObject.array[i];
            var type = data.type;
            var title = data.title;
            var name = data.name;
            var nullable = data.nullable;
            var content = data.content;

            var label = document.createElement("label");
            label.innerText = title + " ";
            if (!nullable) {
                var star = document.createElement("span");
                star.color = "red";
                star.innerText = "*";
            }
            p.appendChild(label);

            var input;
            switch(type) {
                case "text": {
                    input = document.createElement("input");
                    input.type = "text";
                    break;
                }
                case "textarea": {
                    input = document.createElement("textarea");
                    break;
                }
                case "image": {
                    input = document.createElement("input");
                    input.type = "image";
                    imageCheck = true;
                    break;
                }
                default: {
                    alert("type is wrong!");
                    return;
                }
            }
            input.name = name;
            input.className = "field size1";
            if (type !== "image" && content !== undefined) input.value = content;
            p.appendChild(input);
        }
        $(".buttons").remove();
        var buttons = document.createElement("div");
        buttons.className = "buttons";
        var submit = document.createElement("button");
        submit.className = "button";
        submit.innerText = "提交";
        submit.addEventListener("click", updateMethod);
        buttons.appendChild(submit);
        var cancel = document.createElement("button");
        cancel.className = "button return";
        cancel.innerText = "返回";
        cancel.addEventListener("click", returnMethod);
        buttons.appendChild(cancel);

        parent.appendChild(form);
        parent.appendChild(buttons);
    };
    if (dataForUpdate !== undefined) updateWithData(dataForUpdate);
    else getWithParam(url, {id}, function(data) {
        dataForUpdate = getDataMethod(data);
        updateWithData(dataForUpdate);
    });
}

// 加载所有简介标题
var columns;
function loadDetailNames() {
    getWithoutParam("/introduction/detail/names", function (data) {
        centralData = data;
        var columnNames = ["标题名称"];
        var length = centralData.length;
        var readArray = [];
        for (var i = 0; i < length; i++) {
            var key;
            var clickMethod = function(event) {
                if (event !== undefined) key = event.target.className.split("_")[1];
                getWithParam("/introduction/detail", {id: key}, function (detailData) {
                    console.log(detailData);
                    navigator("简介详情", loadDetailNames);
                    centralData = detailData;
                    columnNames = ["副标题名称"];
                    length = centralData.length;
                    readArray = [];
                    var updateMethod = function(event) {
                        var id = event.target.className.split("_")[1];
                        var data;
                        for (var j = 0; j < length; j++) {
                            console.log(centralData[j]);
                            if (centralData[j].id == id) {
                                data = centralData[j];
                                break;
                            }
                        }
                        console.log(data);
                        dataForUpdate = {
                            array: [{
                                type: "text",
                                name: "name",
                                title: "简介标题",
                                nullable: false,
                                content: data.title
                            }, {
                                type: "textarea",
                                name: "content",
                                title: "简介内容",
                                nullable: false,
                                content: data.content
                            }]
                        };
                        generalUpdate();
                    };
                    for (var j = 0; j < length; j++) {
                        readArray.push({
                            className: "updateColumn",
                            innerText: centralData[j].title,
                            clickMethod: updateMethod
                        });
                    }
                    var updateObject = {
                        className: "updateColumn",
                        clickMethod: updateMethod
                    };
                    var deleteObject = {
                        className: "deleteColumn",
                        clickMethod: function (event) {
                            var key = event.target.className.split("_")[1];
                            generalDelete("/introduction/detail/column", key, clickMethod);
                        }
                    };
                    generalShow(columnNames, readArray, updateObject, deleteObject);
                });
            };
            readArray.push({
                className: "showColumn",
                innerText: centralData[i].name,
                clickMethod: clickMethod
            });
        }
        var updateObject = {
            className: "updateIntroductionDetail",
            clickMethod: function (event) {
            }
        };
        var deleteObject = {
            className: "deleteIntroductionDetail",
            clickMethod: function (event) {
                var key = event.target.className.split("_")[1];
                generalDelete("/introduction/detail", key, loadDetailNames);
            }
        };
        generalShow(columnNames, readArray, updateObject, deleteObject);

        dataForUpdate = {
            array: [{
                type: "text",
                name: "name",
                title: "简介标题",
                nullable: false
            }]
        };

        generalUpdate(createDetailRequest, null, null, null, null);
        $(".return").remove();
        var newButton = document.createElement("button");
        newButton.type = "button";
        newButton.className = "right button";
        newButton.onclick = addNewColumn;
        newButton.innerText = "添加一个小标题";
        $(".form")[0].appendChild(newButton);
    });
}

// 初始化方法；
function init() {
    loadDetailNames();
}

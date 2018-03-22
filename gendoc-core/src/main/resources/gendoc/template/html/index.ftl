<#-- @ftlvariable name="jsonData" type="java.lang.String" -->
<#-- @ftlvariable name="classList" type="java.util.List<com.easycodingnow.template.vo.DocApiApiClass>" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <script>
        <#include "jquery.js">
    </script>
    <style type="text/css">
        .sidebar-menu {
            list-style: none;
            padding: 0;
            margin: 30px 0 0;
            z-index: 100;
            color: #303133;
            font-size: 14px;
            background-color: #fafafa;
            border-right: 1px solid #e6e6e6;
        }

        .sidebar-menu > li {
            position: relative;
            margin: 0;
            padding: 0;
            border-color .3s,background-color .3s,color .3s
        }

        .sidebar-menu > li > a {
            padding: 12px 5px 12px 15px;
            display: block;
        }

        .sidebar-menu > li > a > .fa {
            width: 20px;
        }



        .sidebar-menu > li > .treeview-menu {
            margin: 0 1px;
        }

        .sidebar-menu > li .label,
        .sidebar-menu > li .badge {
            margin-top: 3px;
            margin-right: 5px;
        }

        .sidebar li.header {
            font-size: 12px;
            width: 300px;
            position: fixed;
            z-index: 100;
            display: block;
            text-decoration: none;
        }


        .sidebar-menu li.active > .treeview-menu {
            display: block;
        }

        .sidebar-menu a {
            text-decoration: none;
            color: #303133;
            margin: 0;
            height: 50px;
            line-height: 50px;
            min-width: 200px;
        }

        .sidebar-menu .treeview-menu {
            display: none;
            list-style: none;
            margin: 0;
            padding-left: 20px;

        }


        .sidebar-menu .treeview-menu > li {
            margin-left:20px;
        }

        .main-sidebar {
            position: absolute;
            top: 0;
            left: 0;
            height: 100%;
            min-height: 100%;
            width: 300px;
            z-index: 101;
        }

        .content-box{
            margin-left: 300px;
        }

        .doc-title-box{
            border-bottom: 1px solid #ebebeb;
        }

        .doc-content-box{
            width: 62%;
            margin: 0 auto;
        }
        .doc-content-box code{
            border: 1px solid #ddd;
            background: #f6f6f6;
            padding: 3px;
            border-radius: 3px;
            font-size: 14px;
        }
        table{
            border-collapse: collapse;
            border-spacing: 0;
            display: block;
            width: 100%;
            overflow: auto;
        }
         table td,  table th{
            padding: 6px 13px;
            border: 1px solid #ddd;
        }

        li.active >a{
            color: #409eff;
        }

        a.active{
            color: #409eff;
        }

        #search-api{
            -webkit-appearance: none;
            background-color: #fff;
            border-radius: 4px;
            border: 1px solid #dcdfe6;
            box-sizing: border-box;
            color: #606266;
            display: inline-block;
            font-size: inherit;
            height: 40px;
            line-height: 1;
            outline: 0;
            padding: 0 15px;
            transition: border-color .2s cubic-bezier(.645,.045,.355,1);
            width: 100%;
        }

        .msg-layer-bg{
            width: 100%;
            z-index: 999;
            position: fixed;
            background: #000;
            opacity: 0.4;
            top: 0;
            height: 100%;
            filter: alpha(opacity=50);
            display: none;
        }

        .modal-box{
            position: fixed;
            max-height: 400px;
            overflow-y: scroll;
            top: 100px;
            width: 720px;
            margin:0 auto;
            z-index: 1000;
            background-color: white;
            left: 27%;
        }
    </style>
</head>
<body>

<aside class="main-sidebar">
    <section class="sidebar" style="height: calc(100% - 20px);overflow-y: scroll">
        <li class="header"><input autocomplete="off" placeholder="输入关键字后按回车以搜索" type="text"  id="search-api"></li>
        <ul class="sidebar-menu">
            <#list classList as class>
            <li class="treeview">
                <a onclick="menuClick(this)"   data-index="${class_index}" href="#" class="apiClass">
                    <span>${class.apiName}</span>
                </a>
                <ul class="treeview-menu">
                    <#list class.methods as method>
                        <li><a onclick="apiMethodClick(this)" data-index="${class_index},${method_index}" href="javascript:void" class="apiMethod">${method.apiName}</a></li>
                    </#list>
                </ul>
            </li>
            </#list>
        </ul>
    </section>
</aside>
<div class="content-box">
    <section class="content">
        <div class="doc-content-box">
            <div class="doc-title-box">
                <h2 id="method-name"></h2>
            </div>
            <p><strong>简要描述：</strong></p>
            <ul>
                <li id="method-desc"></li>
            </ul>
            <p><strong>请求URL：</strong></p>
            <ul>
                <li><code id="method-url" style="color: rgb(221, 17, 68);"></code></li>
            </ul>
            <p><strong>请求方式：</strong></p>
            <ul>
                <li id="method-way"></li>
            </ul>
            <p><strong>参数：</strong></p>
            <div style="width: 100%;overflow-x: auto;">
                <table id="method-params">
                    <thead>
                    <tr style="background-color: rgb(64, 158, 255); color: rgb(255, 255, 255);">
                        <th style="text-align: left; width: 180px;">参数名</th>
                        <th style="text-align: left; width: 180px;">必选</th>
                        <th style="text-align: left; width: 180px;">类型</th>
                        <th style="text-align: left; width: 180px;">默认值</th>
                        <th style="width: 180px;">说明</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div id="returnBox">
                <p><strong>返回参数:<span id="return-desc"></span></strong></p>
                <div id="returnContent">

                </div>
            </div>
        </div>
    </section>


</div>
<section class="modal-box">
    <div class="param-tables">

    </div>

</section>
<div class="msg-layer-bg" onclick="hideRequestModal()"></div>


<script>
    var filterStr = "";

    var menuTmep = '<li class="treeview">' +
            '<a onclick="menuClick(this)" data-index="{{class_index}}" href="#" class="apiClass">' +
            '<span>{{apiName}}</span>' +
            '</a>' +
            '<ul '+(filterStr?'style="display: block"':"")+' class="treeview-menu">' +
            '{{subMenuList}}</ul></li>';


    var subMenuTemp =
            '<li><a onclick="apiMethodClick(this)" data-index="{{method_index}}" href="javascript:void" class="apiMethod">{{apiName}}</a></li>';


    var requestTableTemp = '<ul>'+
            '<li>{{requestType}}</li>'+
            '</ul>'+
            '<div style="width: 100%;overflow-x: auto;">'+
            '<table>'+
            '<thead>'+
            '<tr style="background-color: rgb(64, 158, 255); color: rgb(255, 255, 255);">'+
            '<th style="text-align: left; width: 180px;">参数名</th>'+
            '<th style="text-align: left; width: 180px;">必选</th>'+
            '<th style="text-align: left; width: 180px;">类型</th>'+
            '<th style="text-align: left; width: 180px;">默认值</th>'+
            '<th style="width: 180px;">说明</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody>{{tr}}'+
            '</tbody>'+
            '</table>'+
            '</div>';

    var requestParamTrTemp = '<tr style="background-color: rgb(255, 255, 255);">'
            + '<td style="text-align:left;">{{name}}</td>'
            + '<td style="text-align:left;">{{required}}</td>'
            + '<td style="text-align:left;">{{type}}</td>'
            + '<td style="text-align:left;">{{defaultValue}}</td>'
            + '<td style="text-align:left;">{{desc}}</td></tr>';

    var returnTableTemp = '<ul>'+
            '<li>{{returnType}}</li>'+
            '</ul>'+
            '<div style="width: 100%;overflow-x: auto;">'+
            '<table>'+
            '<thead>'+
            '<tr style="background-color: rgb(64, 158, 255); color: rgb(255, 255, 255);">'+
            '<th style="text-align: left; width: 180px;">参数名</th>'+
            '<th style="text-align: left; width: 180px;">类型</th>'+
            '<th style="width: 180px;">说明</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody>{{tr}}'+
            '</tbody>'+
            '</table>'+
            '</div>';


    var returnTrTemp = '<tr style="background-color: rgb(255, 255, 255);">'
            + '<td style="text-align:left;">{{name}}</td>'
            + '<td style="text-align:left;">{{type}}</td>'
            + '<td style="text-align:left;">{{desc}}</td></tr>';


    function hideRequestModal() {
        $(".modal-box").hide();
        $(".msg-layer-bg").hide();
    }

    function showRequestModal() {
        $(".modal-box").show();
        $(".msg-layer-bg").show();
    }


    //请求参数的模态框
    function requestModal(index, paramIndex) {
        var indexArr = index.split(",");
        var method = apiJson[indexArr[0]]['methods'][indexArr[1]];
        var param = method['requestParams'][paramIndex];

        var tableHtml = "";
        for(var i=0; i<param['typeDoc'].length; i++){


            var tableTrHtml = "";

            for(var j=0; j<param['typeDoc'][i]['fields'].length; j++){
                var requestParam = param['typeDoc'][i]['fields'][j];

                tableTrHtml += requestParamTrTemp.replace("{{name}}", requestParam['name'] ? requestParam['name'] : "")
                        .replace("{{required}}", "")
                        .replace("{{type}}", requestParam['type'] ? requestParam['type'] : "")
                        .replace("{{defaultValue}}", requestParam["defaultValue"]?requestParam["defaultValue"]:"")
                        .replace("{{desc}}", requestParam['description'] ? requestParam['description'] : "");
            }
            tableHtml += requestTableTemp
                    .replace("{{requestType}}", param['typeDoc'][i]['type'])
                    .replace("{{tr}}", tableTrHtml);
        }

        $(".param-tables").empty().html(tableHtml);
        showRequestModal();
    }



    function initMenu() {

        filterStr = filterStr.trim();

        var html = "";

        for (var i = 0; i < apiJson.length; i++) {
            var filterMethods = [];
            var methods = apiJson[i]['methods'];
            for (var j = 0; j < methods.length; j++) {
                var methodName = methods[j]['apiName'];
                methodName = methodName ? methodName : "";
                var requestPath = methods[j]['requestPath'];
                requestPath = requestPath ? requestPath : "";
                if (!filterStr || methodName.search(filterStr) !== -1
                        || requestPath.search(filterStr) !== -1) {
                    methods[j]['originIndex'] = j;
                    filterMethods.push(methods[j]);
                }
            }

            if (filterMethods.length > 0) {
                var menuHtml = "";
                for (var z = 0; z < filterMethods.length; z++) {
                    menuHtml += subMenuTemp.replace("{{method_index}}", i + "," + filterMethods[z]['originIndex'])
                            .replace("{{apiName}}", filterMethods[z]['apiName']);

                }
                html += menuTmep.replace("{{class_index}}", i)
                        .replace("{{apiName}}", apiJson[i]['apiName'])
                        .replace("{{subMenuList}}", menuHtml);
            }
        }
        $(".sidebar-menu").empty().html(html);
        showRequestModal();
    }


    $('#search-api').bind('keypress', function (event) {
        if (event.keyCode === 13) {
            filterStr = $("#search-api").val();
            initMenu();
        }
    });

    function apiMethodClick(that) {
        $("a").removeClass("active");
        $(that).addClass("active");

        var indexStr = $(that).data("index");
        var index = indexStr.split(",");
        var cls = apiJson[index[0]];
        var method = cls['methods'][index[1]];

        $("#method-name").text(method['apiName']);
        $("#method-desc").html(method['apiDescription']?method['apiDescription']:method['apiName']);

        var methodPath = method['requestPath'];
        if(methodPath && methodPath[0] !== '/'){
            methodPath = "/" + methodPath;
        }

        $("#method-url").text(cls['requestPath'] + methodPath);
        $("#method-way").text(cls['requestMethod']);

        $("#method-params tbody").empty();


        if (method['requestParams']) {
            var paramsHtml = "";
            for (var i = 0; i < method['requestParams'].length; i++) {
                var requestParam = method['requestParams'][i];

                var typeHtml = requestParam['type'] ? requestParam['type'] : "";
                if(requestParam['typeDoc']){
                    typeHtml = "<a style=\"cursor: pointer;color: #409eff\" onclick=\"requestModal('"+indexStr+"',"+i+")\">"
                    +typeHtml+"</a>"
                }
                paramsHtml +=requestParamTrTemp.replace("{{name}}", requestParam['name'] ? requestParam['name'] : "")
                        .replace("{{required}}", requestParam['required'] ? "是" : "否")
                        .replace("{{type}}", typeHtml)
                        .replace("{{defaultValue}}", requestParam["defaultValue"]?requestParam["defaultValue"]:"")
                        .replace("{{desc}}", requestParam['description'] ? requestParam['description'] : "");
            }

            $("#method-params tbody").html(paramsHtml);
        }



        var returnTableHtml = "";
        if(method['returnTypes']){
            for(var z=0; z<method['returnTypes'].length; z++){
                var returnType = method['returnTypes'][z];
                var fields =  returnType['fields'];
                var returnHtml = "";
                for (var i = 0; i < fields.length; i++) {
                    var fd = fields[i];

                    returnHtml +=returnTrTemp.replace("{{name}}", fd['name'] ? fd['name'] : "")
                            .replace("{{type}}", fd['type'] ? fd['type'] : "")
                            .replace("{{desc}}", fd['desc'] ? fd['desc'] : "");
                }
                var returnTypeStr =     returnType['type'] + (returnType['desc']?("("+returnType['desc']+")"):"");
                returnTableHtml += returnTableTemp
                        .replace("{{returnType}}",returnTypeStr)
                        .replace("{{tr}}", returnHtml);

            }

            $("#returnContent").html(returnTableHtml);
        }else{
            $("#returnContent").empty();
        }
        $("#return-desc").html(method['returnDesc']);
    }

    function menuClick(that) {
        var menu = $(that).parent().children(".treeview-menu");

        if (menu.hasClass("menu-open")) {
            menu.slideUp();
            menu.removeClass("menu-open");
        } else {
            menu.slideDown();
            menu.addClass("menu-open");
        }
    }
    



</script>
<script>
    var apiJson = ${jsonData};
</script>
</body>
</html>
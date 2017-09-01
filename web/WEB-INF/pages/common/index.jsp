<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>账号密码管理</title>
    <!-- 导入jquery核心类库 -->
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
    <!-- 导入easyui类库 -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/css/default.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
    <script src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
            type="text/javascript"></script>
    <%--文件上传类库--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ocupload-1.1.2.js"></script>
    <%--文件上传类库--%>
    <%--aes 类库--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/aes/aes.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/aes/mode-ecb.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/md5.js"></script>
    <%--aes 类库--%>
    <script type="text/javascript">
        var editIndex;

        function doAdd() {
            if (encryptKey == "") {
                doOpenWindow();
                return;
            }
            if (editIndex != undefined) {
                $("#grid").datagrid('endEdit', editIndex);
            }
            if (editIndex == undefined) {
                //alert("快速添加电子单...");
                $("#grid").datagrid('insertRow', {
                    index: 0,
                    row: {}
                });
                $("#grid").datagrid('beginEdit', 0);
                editIndex = 0;
            }
        }

        function doSave() {
            $("#grid").datagrid('endEdit', editIndex);
//            $("#grid").datagrid("load", {});
        }


        function doCancel() {
            if (editIndex != undefined) {
                $("#grid").datagrid('cancelEdit', editIndex);
                if ($('#grid').datagrid('getRows')[editIndex].id == undefined) {
                    $("#grid").datagrid('deleteRow', editIndex);
                }
                editIndex = undefined;
            }
        }

        function doOpenWindow() {
            $("#inputKey").window("open");
        }

        //工具栏
        var toolbar = [{
            id: 'button-add',
            text: '增加',
            iconCls: 'icon-add',
            handler: doAdd
        }, {
            id: 'button-cancel',
            text: '取消',
            iconCls: 'icon-cancel',
            handler: doCancel
        }, {
            id: 'button-save',
            text: '保存',
            iconCls: 'icon-save',
            handler: doSave
        }, {
            id: 'button-inputKey',
            text: '输入解码key',
            iconCls: 'icon-edit',
            handler: doOpenWindow
        }, {
            id: 'button-import',
            text: '导入',
            iconCls: 'icon-redo',
            handler: doOpenWindow
        }];
        // 定义列
        var columns = [[{
            field: 'id',
            title: 'id',
            width: 120,
            align: 'center',
            hidden: 'true'
        }, {
            field: 'accountType',
            title: '账号类型',
            width: 120,
            align: 'center',
            editor: {
                type: 'validatebox',
                options: {
                    required: true
                }
            }
        }, {
            field: 'username',
            title: '用户名',
            width: 160,
            align: 'center',
            editor: {
                type: 'validatebox',
                options: {
                    required: true
                }
            }
        }, {
            field: 'password',
            title: '密码',
            width: 160,
            align: 'center',
            editor: {
                type: 'validatebox',
                options: {
                    required: true
                }
            }
        }, {
            field: 'unlockme',
            title: '密保',
            width: 325,
            align: 'center',
            editor: {
                type: 'validatebox',
                options: {
                    required: false
                }
            }
        }]];


        var encryptKey = "";

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility: "visible"});

            // 收派标准数据表格
            $('#grid').datagrid({
                iconCls: 'icon-forward',
                fit: true,
                border: true,
                rownumbers: true,
                striped: true,
                pageList: [30, 50, 100],
                pagination: true,
                toolbar: toolbar,
                url: "${pageContext.request.contextPath}/APMAction_pageQuery.action",
                idField: 'id',
                columns: columns,
                onDblClickRow: doDblClickRow,
                onAfterEdit: function (rowIndex, rowData, changes) {
                    console.info(rowData);

                    rowData.accountType = encrypt(rowData.accountType);
                    rowData.username = encrypt(rowData.username);
                    rowData.password = encrypt(rowData.password);
                    rowData.unlockme = encrypt(rowData.unlockme);

                    var url = "${pageContext.request.contextPath}/APMAction_add.action";
                    $.post(url, rowData, function (data) {
                        if (data == "0") {
                            editIndex = undefined;
                            //录入成功
                            $.messager.alert("提示信息", "录入成功！", "info");
                        } else {
                            //录入失败
                            $.messager.alert("提示信息", "录入失败！", "warning");
                            doCancel();
                        }
                    }).error(function () {
                        //录入失败
                        $.messager.alert("提示信息", "录入失败！", "warning");
                        doCancel();
                    });
                },
                loadFilter: function (data) {
                    console.log(data);
                    if (data.rows.length > 0) {
                        for (var i = 0; i < data.rows.length; i++) {
                            data.rows[i].accountType = decrypt(data.rows[i].accountType);
                            data.rows[i].username = decrypt(data.rows[i].username);
                            data.rows[i].password = decrypt(data.rows[i].password);
                            data.rows[i].unlockme = decrypt(data.rows[i].unlockme);
                        }
                    }

                    console.log(data);

                    return data;
                }
            });

            $("#inputKey").window("open");

        });


        function encrypt(word) {
            var key = CryptoJS.enc.Utf8.parse(encryptKey);
            var srcs = CryptoJS.enc.Utf8.parse(word);
            var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
            return encrypted.toString();
        }

        function decrypt(word) {
            var key = CryptoJS.enc.Utf8.parse(encryptKey);
            var decrypt = CryptoJS.AES.decrypt(word, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
            return CryptoJS.enc.Utf8.stringify(decrypt).toString();
        }

        function doDblClickRow(rowIndex, rowData) {
            alert("双击表格数据...");
            console.info(rowIndex);
            $('#grid').datagrid('beginEdit', rowIndex);
            editIndex = rowIndex;
        }

        function getEncryptKey() {
            encryptKey = MD5($("#getEncryptKey").val()).substring(0, 16);
            var url = '${pageContext.request.contextPath}/APMAction_setKey.action';
            $.post(url, {encryptKey: encryptKey}, function (data) {
                $("#button-import").upload({
                    action: '${pageContext.request.contextPath}/APMAction_importXls.action',
                    name: 'file',
                    onComplete: function (data) {
                        if (data == '1') {
                            $.messager.alert("提示信息", "数据导入成功", "info");
                        } else {
                            $.messager.alert("提示信息", "数据导入失败", "warning");
                        }
                    }
                })
            });
            $("#grid").datagrid("load", {});
            $("#inputKey").window("close");
            return encryptKey;
        }

    </script>
    <%--<script type="text/javascript">--%>
    <%--$(function () {--%>

    <%--$("#button-import").upload({--%>
    <%--action: '${pageContext.request.contextPath}/APMAction_importXls.action',--%>
    <%--name: 'file',--%>
    <%--onComplete: function (data) {--%>
    <%--if (data == '1') {--%>
    <%--$.messager.alert("提示信息", "数据导入成功", "info");--%>
    <%--} else {--%>
    <%--$.messager.alert("提示信息", "数据导入失败", "warning");--%>
    <%--}--%>
    <%--}--%>
    <%--})--%>
    <%--})--%>
    <%--</script>--%>

    <style>
        body {
            text-align: center;
            background: #d9d9d9 url("${pageContext.request.contextPath}/images/bg-12.png") no-repeat center;
        }

        .bg-l {
            background: rgba(0, 0, 0, 0.7);
            height: 100%;
            position: relative;
            text-align: center;
        }

        .div {
            margin: 0 auto;
            width: 800px;
            height: 80%;
            position: absolute;
            top: 10%;
            left: 0;
            bottom: 0;
            right: 0;
        }
    </style>

</head>
<body style="visibility:hidden;">
<div class="bg-l">
    <div class="div">
        <table id="grid"></table>
    </div>
</div>

<%--设置解码Key--%>
<div class="easyui-window" title="请输入加密Key" id="inputKey" collapsible="false" minimizable="false"
     maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
        <div class="datagrid-toolbar" style="padding: 10px;color: red;">
            此Key请自己妥善保管，系统不做保存。若遗忘将无法解码！切记！切记！
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <table class="table-edit" width="80%" align="center">
            <tr>
                <td>解码Key</td>
                <td>
                    <input id="getEncryptKey" type="text" name="standard" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
        </table>
        <div style="text-align: center;padding: 10px">
            <a class="easyui-linkbutton" onclick="getEncryptKey()">解&nbsp;&nbsp;&nbsp;&nbsp;码</a>
        </div>
    </div>
</div>

</body>
</html>
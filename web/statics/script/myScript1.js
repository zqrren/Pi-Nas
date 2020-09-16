$(function (){
    refresh = function (name){
        let SEPARATOR = getSEPARATOR();
        let path = $("#nowPath").text();
        if (name === ".."){
            const regex = /.*(?=\\)/gm;
            path = regex.exec(path)[0];
        }
        if (name && name !== ".."){
            path += SEPARATOR + name;
        }
        if ((name+"").indexOf("\\")!==-1){
            path = name;
        }
        $.post({
            url:"/file",
            data:{"path":path},
            dataType:"json",
            success:function (data){
                // 导航
                // let paths = path.split(/(?:\\+|\/)/);
                let paths = path.split(SEPARATOR);
                $("#crumb").empty();
                $.each(paths,function (i,p){
                    let repPath = "";
                    for (let j = 0;j<=i;j++){
                        repPath+=paths[j];
                        if (j!=i){
                            repPath+=SEPARATOR.replace("\\","\\\\")
                        }
                    }
                    let li = "";
                    if (i===paths.length-1){
                        li += "<li class=\"breadcrumb-item active\" aria-current=\"page\">"+p+"</li>";
                        li += "<span hidden id='nowPath'>"+path+"</span>";
                    }
                    else {
                        li += "<li class=\"breadcrumb-item\">";
                        li += "<a href='#' onclick='refresh(\""+repPath+"\")'>"+p+"</a>";
                        li += "</li>";
                    }
                    $("#crumb").append(li);
                })

                // 清空表格内容
                $("#tBody").empty();
                $.each(data, function (i,row){
                    let tr = "";
                    tr += "<td><div class=\"form-check\"><input class=\"form-check-input\" type=\"checkbox\" value=\"\" id=\"defaultCheck1\" path='"+row.path+"'></div></td>"
                    if (!row.dir){
                        tr+="<td>"+row.name+"</td>";
                    }
                    else {
                        let a = "<a href='#' onclick='refresh(\""+row.name+"\")'>";
                        tr+="<td>"+a+row.name+"</a>"+"</td>";
                    }
                    if (row.dir){
                        row.size = '-';
                        row.date = '-';
                    }
                    else {
                        row.date = dateFormat(row.date);
                        row.size = sizeFormat(row.size);
                    }
                    tr+="<td>"+row.size+"</td>";
                    tr+="<td>"+row.date+"</td>";
                    $("#tBody").append("<tr>"+tr+"</tr>");
                })
            }
        })
    }
    function dateFormat(timestamp){
        let date = new Date(timestamp);
        let Y = date.getFullYear() + '-';
        let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        let D = (date.getDate()<10?'0'+date.getDate():date.getDate()) + ' ';
        let h = (date.getHours()<10?'0'+date.getHours():date.getHours())  + ':';
        let m = (date.getMinutes()<10?'0'+date.getMinutes():date.getMinutes())  + ':';
        let s = (date.getSeconds()<10?'0'+date.getSeconds():date.getSeconds())
        return Y+M+D+h+m+s;
    }
    function sizeFormat(bytes){
        let fileSize = bytes;
        let result = '';
        if (fileSize >= 1073741824) {
            result = fileSize % 1073741824 === 0 ? fileSize / 1073741824 + 'G' : Math.trunc(fileSize / 1073741824) + 'G'
        } else if (fileSize >= 1048576) {
            result = fileSize % 1048576 === 0 ? fileSize / 1048576 + 'MB' :  Math.trunc(fileSize / 1048576) + 'MB'
        } else if (fileSize >= 1024) {
            result = fileSize % 1024 === 0 ? fileSize / 1024 + 'KB' :  Math.trunc(fileSize / 1024) + 'KB'
        } else {
            result = fileSize + 'B'
        }
        return result;
    }
    function getSEPARATOR(){
        let SEPARATOR = "";
        $.post({
            url: "/getSEPARATOR",
            async: false,
            success: function (data){
                SEPARATOR = data;
            }
        })
        return SEPARATOR;
    }
    download = function (){
        let downloadList = [];
        $("#tBody input[type=checkbox]:checked").each(function (){
            downloadList.push($(this).attr('path'));
        })
        if (downloadList.length===0){
            return;
        }
        let url = encodeURI("/download?path="+JSON.stringify(downloadList));
        $(".container").append("<a id='temp' href='"+url+"'>");
        $("#temp")[0].click();
        $("#temp").remove();
    }
    upload = function (){
        $("#file").click();
    }
    uploadFileChanged = function (){
        let path = $("#nowPath").text();
        let param = $("#pathParam");
        if (param.children().length===0){
            param.append("<input name='path' value='"+path+"'/>");
        }
        let data = new FormData($("#fileForm")[0]);
        $.post({
            url:"/upload",
            data: data,
            contentType: false,
            processData: false,
            success:function (){
                refresh(path)
            }
        })
    }
})
$(document).ready(function init(){
    refresh()
});

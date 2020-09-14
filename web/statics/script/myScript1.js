$(function (){
    refresh = function (path){
        $.post({
            url:"/file",
            data:{"path":path},
            dataType:"json",
            success:function (data){
                // 导航
                let paths = path.split(/(?:\\+|\/)/);
                $("#crumb").empty();
                $.each(paths,function (i,p){
                    let repPath = "";
                    for (let j = 0;j<=i;j++){
                        repPath+=paths[j]+"\\\\";
                    }
                    let li = "";
                    if (i===paths.length-1){
                        li += "<li class=\"breadcrumb-item active\" aria-current=\"page\">"+p+"</li>";
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
                    let repPath = row.path.replaceAll("\\","\\\\");
                    let tr = "";
                    tr += "<td><div class=\"form-check\"><input class=\"form-check-input\" type=\"checkbox\" value=\"\" id=\"defaultCheck1\" path='"+repPath+"'></div></td>"
                    if (!row.dir){
                        // a = "<a href='/download?path="+encodeURI(row.path)+"'>";
                        tr+="<td>"+row.name+"</td>";
                    }
                    else {
                        let a = "<a href='#' onclick='refresh(\""+repPath+"\")'>";
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
        let h = date.getHours() + ':';
        let m = date.getMinutes() + ':';
        let s = date.getSeconds();
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
    findChecked = function (){
        let downloadList = [];
        $("#tBody input[type=checkbox]:checked").each(function (){
            downloadList.push($(this).attr('path'));
        })
        console.log(downloadList)
        let url = encodeURI("/download?path="+JSON.stringify(downloadList));
        $(".container").append("<a id='temp' href='"+url+"'>");
        $("#temp")[0].click();
        $("#temp").remove();
    }
})
$(document).ready(function init(){
    refresh("Z:\\My_Files")
});
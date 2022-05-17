$(function () {

    /**
     * 页面加载
     */

    // 如果自定义上传封面被选中则展示封面文件上传区域
    $(".file_up_cover_type").click(function () {
        let hide = $("#file_up_hide");
        if (this.id.includes(2)) {
            hide.show();
        } else {
            hide.hide();
        }
    })

    /**
     * 编辑器内容同步（内容发生改变就同步）
     */
    $("#editor").change(function () {
        sycnEditor();
    })

    /**
     * 临时用 日志面板权限控制
     */
    if ($.cookie("userId") !== "2") {
        $(".option").hide();
        $(".temp_my_diary").html("<b>他的日记</b>");
    }
})

/**
 * 编辑器同步
 */
function sycnEditor() {
    $("#editorValue").val($("#editor").html());
}

/**
 * 日记分类管理基础方法
 * @param obj 触发的按钮
 * @param opera 增删改
 */
function clsOpera(obj, opera) {
    var v = $(obj).text();
    var cid = $(obj).parents("tr").attr("id");
    if (cid) cid = getId(cid); // 如果cid不为空才执行（否则会空指针）
    var clsName = $(obj).parents("tr").find("input").val();
    // 日记操作前处理
    if (!diaryPreHandler(cid,obj,opera,clsName)) {
        return;
    }
    // 接口 请求日记分类
    console.log("cid=" + cid + "\tuid=" + $.cookie("userId") + "\tclazz=" + clsName + "\topera=" + opera);
    $.ajax({
        url: "/classes",
        type: opera,
        data: "cid=" + cid + "&uid=" + $.cookie("userId") + "&clazz=" + clsName,
        dataType: "JSON",
        success: function (data) {
            if (data.state) {
                popup("操作成功","pass");
                // switch (opera){
                //     case "POST":
                //         clsAddAfterHandler(obj,data.data.cid); // 新增成功后将按钮改为保存并将所属tr的newCls属性去除
                //         break;
                //     case "DELETE":
                //         showDiaryClasses(); // 重新请求分类列表
                //         break;
                //     case "PUT":
                //
                //         clsAddAfterHandler(obj,data.data.cid);
                //         break;
                //     case "GET":
                //         break;
                // }
                showDiaryClasses(); // 重新请求分类列表
            } else {
                popup("网络异常，提交失败","error");
            }
        },
        error: function (xhr) {
            popup("网络异常，请稍后再试","error");
        }
    })
}

/**
 * 接口 查 获取该用户所有文章分类
 */
function showDiaryClasses() {
    // 清空原有数据
    var area = $("#diaryClasses");
    $.ajax({
        url:"/classes",
        type:"GET",
        data:"uid=" + $.cookie("userId"),
        dataType:"JSON",
        success:function (data) {
            if (data.state) {
                area.empty();
                var classes = data.data;
                for (let i = 0; i < classes.length; i++) {
                    var tr = "<tr id='diaryCls-#{cid}'>\n" +
                        "<th>" + (i+1) + "</th>\n" +
                        "<td class=\"cls_name\">\n" +
                        "    <input type=\"text\" name=\"clsName\" class=\"no_border text-center\" value=\"#{clazz}\" autocomplete='off' \n"+
                        "           maxlength=\"10\" onclick=\"$(this).parents('tr').find('.mybtn_green').show()\">\n" +
                        "</td>\n" +
                        "<td>#{未实现}</td>\n" +
                        "<td class=\"row_control\">\n" +
                        "    <span class=\"clsRecord hide\">#{clazz}</span>\n" +
                        "    <span class=\"mybtn_green hide\" onclick=\"clsOpera(this,'PUT')\">保存</span>\n" +
                        "    <span class=\"mybtn_red\" onclick=\"clsOpera(this,'DELETE')\">删除</span>\n" +
                        "</td>\n" +
                        "</tr>"
                    tr = tr.replace(/#{cid}/g,classes[i].cid);
                    tr = tr.replace(/#{clazz}/g,classes[i].clazz)
                    area.append(tr);
                }
            } else {
                popup("拉取文章分类失败" + data.msg,"warning");
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}

/**
 * 生成标签的id
 * @param pre 前缀格式
 * @param id id
 * @returns {string} 标签的格式化id
 */
function makeId(pre,id) {
    return pre + "-" + id;
}

/**
 * 日记前处理 校验判断
 * @param opera
 * @param clsName
 */
function diaryPreHandler(cid,obj,opera,clsName) {
    // 新增和更新 若是新增或更新请求则判断内容是否为空
    if ((opera === "POST" || opera === "PUT") && isEmpty(clsName)) {
        popup("没有内容可以提交哦","warning");
        return false;
    }
    // 更新 判断是否变更
    var oldV = $(obj).parent().find(".clsRecord").html();
    console.log("旧：" + oldV + "\t新：" + clsName)
    if (opera === "PUT" && clsName === oldV) {
        popup("您并未对【" + clsName + "】作出修改","warning");
        return false;
    }
    // 删除 判断要删除的分类是否为刚点击的新增
    var isLast = $(obj).parents("tr");
    if (opera === "DELETE" && isLast.hasClass("newCls")) { // 若为新增的分类（还没进数据库）
        isLast.remove(); // 直接移除last
        return false;
    }
    // 用户删除确定
    if (opera === "DELETE"){
        return confirm("确定" + $(obj).text() + " 【" + clsName + "】吗？")
    }
    return true;
}

// 新增分类
function addCls() {
    var last = $(".modal_function_area").find("tr").last();
    if (last.hasClass("newCls")) {
        popup("请勿重复点击","warning");
        return;
    }
    var newId = parseInt($(last).first().text()) + 1;
    last.after("<tr class='newCls'>\n" +
                "<th>" + newId + "</th>\n" +
                "<td class=\"cls_name\">\n" +
                "    <input type=\"text\" name=\"clsName\" class=\"no_border text-center\"\nplaceholder='请输入新分类名称' title='分类名称不能超过10个字符'" +
                "          maxlength='10' onclick=\"$(this).parents('tr').find('.mybtn_green').show()\">\n" +
                "</td>\n" +
                "<td></td>\n" +
                "<td class=\"row_control\">\n" +
                "    <span class='clsRecord hide'></span>\n" +
                "    <span class=\"mybtn_green\" onclick=\"clsOpera(this,'POST')\">新增</span>\n" +
                "    <span class=\"mybtn_red\" onclick=\"clsOpera(this,'DELETE')\">删除</span>\n" +
                "</td>\n" +
                "</tr>")
}

// //新增分类后处理
// function clsAddAfterHandler(obj,cid) {
//     $(obj).parents("tr").attr("id",makeCid(cid));
//     $(obj).html("保存");
//     $(obj).attr("style","display:none");
//     $(obj).attr("onclick", "clsOpera(this,'PUT')");
//     $(obj).parents("tr").removeClass();
//     console.log($(obj).parents("tr").attr("class"));
// }

// 日记列表 是否置顶
function isTop(e) {
    var hook = e.find("span");
    var radio = e.find("input");
    if (radio.attr("checked") === "checked") {
        hook.html("✘");
        radio.removeAttr("checked");
    } else {
        hook.html("✔");
        radio.attr("checked","checked");
    }
}

/**
 * 获取对应创作来源 替换样式
 * @param e 创作来源
 */
function getFrom(e) {
    switch (e){
        case 1:
            return ["square_green","原创"]
        case 2:
            return ["square_yellow","转载"]
        case 3:
            return ["square_red","摘录"]
        case 4:
            return ["square_blue","翻译"]
        case 5:
            return ["square_black","其他"]
    }
}


/**
 * 接口 拉取日记列表
 * @param isPersonal
 */
function pullDiaryList(isPersonal) {
    var data;
    var cover;
    var control;
    if (isPersonal) {
        data = $("#diaryListControlForm").serialize();
        data = "uid=" + $.cookie("userId") + "&" + data;
    }
    $.ajax({
        url:"/diaries",
        type:"GET",
        data:data,
        dataType:"JSON",
        success:function (data) {
            if (data.state) {
                $("#diary_list").empty();
                var diaries = data.data;
                var clazz = diaries.clazz;
                for (let i = 0; i < diaries.length; i++) {
                    if (isPersonal) {
                        control =
                            "        <div class=\"menu_box\">\n" +
                            "            <i class=\"bi bi-three-dots menu_control\"></i>\n" +
                            "            <div class=\"menu_body\">\n" +
                            "                <a href=\"write.html?did=#{id}\">编辑</a>\n" +
                            "                <a href=\"\" onClick=\"deleteDiary(#{id})\">删除</a>\n" +
                            "           </div>\n" +
                            "        </div>"
                        cover = "";
                    } else {
                        control = ""; // 看不是自己日记列表的日记则不显示日记的编辑功能
                        var num = Math.ceil(Math.random() * 4) // 临时用 就是玩儿
                        cover =
                        "<div class=\"all_center position-relative\">\n" +
                        "    <div class=\"diary_class\">#{clazz}</div>\n" +
                        "    <img src=\"../../images/test/cat"+num+".jpeg\" class=\"diary_cover corner\" alt=\"\">\n" +
                        "</div>\n"
                    }
                    // 暂时不加hot图标
                    // "    <div class=\"title\"><img src=\"../../images/icons/hot.png\" class=\"icon_hot\" alt=\"\">\n" +
                    var per = "<div id='diary-#{id}' class=\"diary_per border_bottom\" onclick='toDiary(#{id})'>\n" +
                        "<div class=\"body float-left \">\n" +
                        "    <div class=\"title\">\n" +
                                 control +
                        "        #{title}<span class=\"#{corresp}\">#{from}</span></div>\n" +
                        "    <div class=\"content\">\n" +
                        "        #{content}\n" +
                        "    </div>\n" +
                        "    <ul class=\"interact\">\n" +
                        "        <li title=\"发表时间\">\n" +
                        "            <i class=\"bi bi-clock\" style=\"line-height: 23px\"></i>" +
                        "            &nbsp;#{createdTime}" +
                        "        </li>\n" +
                        "        <li title=\"作者\">\n" +
                        "            <i class=\"bi bi-person\" style=\"line-height: 25px\"></i>" +
                        "            &nbsp;#{username}\n" +
                        "        </li>\n" +
                        "        <li title=\"有多少人看过呢？\">\n" +
                        "            <i class=\"bi bi-eye\" style=\"line-height: 23px\"></i>" +
                        "            &nbsp;6.3k\n" +
                        "        </li>\n" +
                        "        <li title=\"喜欢就点个赞吧！\">\n" +
                        "            <i class=\"bi bi-hand-thumbs-up\" style=\"line-height: 23px\"></i>" +
                        "            &nbsp;1.5k\n" +
                        "        </li>\n" +
                        "        <li title=\"转发让更多人看到吧！\">\n" +
                        "            <i class=\"bi bi-box-arrow-in-up-right\" style=\"line-height: 25px\"></i>" +
                        "            &nbsp;800\n" +
                        "        </li>\n" +
                        "        <li title=\"有多少人在参与讨论？\">\n" +
                        "            <i class=\"bi bi-chat-square-text\" style=\"line-height: 25px\"></i>" +
                        "            &nbsp;2.3k\n" +
                        "        </li>\n" +
                        "    </ul>\n" +
                        "</div>\n"
                        + cover +
                        "</div>"
                    per = per.replace(/#{id}/g,diaries[i].id)
                    per = per.replace(/#{username}/g,diaries[i].creator)
                    per = per.replace(/#{title}/g,diaries[i].title)
                    per = per.replace(/#{content}/g,diaries[i].content)
                    per = per.replace(/#{corresp}/g,getFrom(diaries[i].from)[0])
                    per = per.replace(/#{from}/g,getFrom(diaries[i].from)[1])
                    per = per.replace(/#{createdTime}/g,dateFormat(diaries[i].createdTime,"YYYY-MM-DD"))
                    per = per.replace(/#{clazz}/g,diaries[i].clazz.clazz)
                    $("#diary_list").append(per);
                }
            } else {
                popup("日记拉取失败","error");
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}


function toDiary(did) {
    console.log("查看日记：" + did);
    location.href = "diary_per.html?did=" + did
}

function getParaFromUrl(url,pName) {
    var params = url.split("?")[1];
    if (!params) return null;
    return params.split(pName + "=")[1];
}

/**
 * 接口 查看日记详细内容
 */
function showDiary() {
    console.log(getParaFromUrl(location.href, "did"));
    $.ajax({
        url:"/diaries/" + getParaFromUrl(location.href,"did"),
        type:"GET",
        success:function (data) {
            if (data.state) {
                var diary = data.data;
                // 日记信息
                var info = "<div id='diary-#{did}' class=\"title\">" +
                            "   <div class=\"font_less\">" +
                            "       <a href=\"write.html?did=#{did}\">编辑</a>|<a href=\"#\" onclick=\"deleteDiary(#{did})\">删除</a>" +
                            "   </div>" +
                            "   #{title}" +
                            "</div>\n" +
                            "<div class=\"diary_info\">\n" +
                            "    <div>\n" +
                            "        <span class='#{corresp}'>#{from}</span>\n" +
                            "        <span>#{creator}</span>\n" +
                            "        <span>发表于 #{createdTime}</span>\n" +
                            "        <span style=\"position: absolute;right:5px\">\n" +
                            "    <span>浏览量 686</span>\n" +
                            "    <span>点赞 203</span>\n" +
                            "    <span>转发 352</span>\n" +
                            "    <span>收藏 156</span>\n" +
                            "</span>\n" +
                            "    </div>\n" +
                            "    <div>文章分类：<span id='diaryCls-#{cid}' class=\"square_blue\" >#{clazz}</span></div>\n" +
                            "    <div>标签：<span>经验总结</span></div>\n" +
                            "</div>"
                info = info.replace(/#{did}/g,diary.id);
                info = info.replace(/#{title}/g,diary.title);
                info = info.replace(/#{from}/g,getFrom(diary.from)[1]);
                info = info.replace(/#{creator}/g,diary.creator);
                info = info.replace(/#{corresp}/g,getFrom(diary.from)[2]);
                info = info.replace(/#{createdTime}/g,dateFormat(diary.createdTime,null));
                info = info.replace(/#{clazz}/g,diary.clazz.clazz);
                // 日记内容
                var $readText = $("#read_text");
                $readText.empty(); // 清空
                // 更新信息
                $readText.parent().prevAll().remove();
                $readText.parent().before(info);
                // 更新日记内容
                $readText.html(diary.content);
            } else {
                popup("网络开小差啦，请稍后再试","error");
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }

    })
}

/**
 * 接口 提交新日记
 */
function newDiarySubmit() {
    var type = "POST";
    var data = $("#newDiaryForm").serialize() + "&uid=" + $.cookie("userId");
    if (data.includes("=&") || !data.includes("&cid=")) {
        popup("存在空内容，请检查后重新提交","warning");
    } else {
        popup("正在提交新日记，请稍后...","info");
        var did = getParaFromUrl(location.href,"did");
        if (did) { // 若url中存在did说明是编辑更新
            data = (data + "&id=" + did); // 更新日记需要id
            type = "PUT";
            console.log("更新日记：" + did);
        }
        console.log("确认提交：\n" + data + "\n请求：" + type);
        $.ajax({
            url: "/diaries",
            type: type,
            data: data,
            dataType: "JSON",
            success: function (data) {
                if (data.state) {
                    popup("日记已提交，正在前往查看","pass");
                    toDiary(data.data);
                } else {
                    popup("日记提交失败，请稍后重试" + data.msg,"error");
                }
            },
            error: function (xhr) {
                popup("当前网络异常，请检查后重新登录","error");
            }
        })
    }
}

/**
 * 将表单序列化
 * @param data
 * @returns {string}
 */
function formToJson(data) {
    data=data.replace(/&/g,"\",\"");
    data=data.replace(/=/g,"\":\"");
    data="{\""+data+"\"}";
    data = decodeURIComponent(data,true); // 防止中文乱码
    return data;
}

/**
 * 接口 删除日记
 * @param did 日记的id
 */
function deleteDiary(did) {
    if (confirm("确定要删除这篇日记吗？")) {
        $.ajax({
            url:"/diaries/" + did,
            type:"DELETE",
            success:function (data) {
                if (data.state) {
                    popup("删除成功","pass");
                    if (!location.href.includes("diary_per.html")) {
                        pullDiaryList(true); // 重新拉取列表
                    }
                    setTimeout("location.href = \"/web/zone/all.html\"",500);
                } else {
                    popup("网络开小差啦，请稍后再试","error");
                }
            },
            error:function (xhr) {
                popup("当前网络异常，请检查后重新登录","error");
            }
        })
    }
}

/**
 * 加载可选分类
 */
function getDiaryClasses() {
    $.ajax({
        url:"/classes",
        type:"GET",
        data:"uid=" + $.cookie("userId"),
        dataType:"JSON",
        success:function (data) {
            if (data.state) {
                $("#clazzSelect").empty()
                var classes = data.data;
                for (let i = 0; i < classes.length; i++) {
                    var clazz = "<label for=\"clazz-#{id}\" class=\"border\" onclick='selectClazz($(this))'>#{clazz}</label>\n" +
                        "<input id=\"clazz-#{id}\" style='display: none' type=\"radio\" name=\"cid\" value=\"#{id}\">"
                    clazz = clazz.replace(/#{id}/g,classes[i].cid);
                    clazz = clazz.replace(/#{clazz}/g,classes[i].clazz);
                    $("#clazzSelect").append(clazz);
                }
            } else {
                popup("拉取文章分类失败" + data.msg,"warning");
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}

/**
 * 添加选择的分类样式
 * @param e 触发的对象
 */
function selectClazz(e) {
    e.parents(".clazz_select_box").find(".add_btn").prev().remove();
    var span = "<span>#{clazz}</span>"
    span = span.replace(/#{clazz}/g,e.html());
    e.parents(".clazz_select_box").find(".add_btn").before(span);
}
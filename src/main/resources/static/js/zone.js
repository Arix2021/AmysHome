
$(function () {


    // 初始化空间个人信息
    zoneInit();
    initDiaryClazzAndNum()
    initDiaryTag();
    initDiaryRecommend();

    // 互动 胶囊搜索框
    let sc =  $("#search_capsule");
    let sci = $("#search_capsule_input")
    function scFade() {
        let empty = isEmpty(sci);
        if (!isFocus(sci)) { // 非聚焦才能淡出
            if (empty) { // 非聚焦还要判断内容是否为空 空则淡出
                sc.attr("style", "opacity:.7");
            }
        }
    }
    sc.mouseenter(function () {
        sc.attr("style","opacity:1");
    })
    sc.mouseleave(function () {
        scFade();
    })
    sci.blur(function () {
        scFade();
    })

    // 判断输入框 输入框有输入则不缩回，无有效字符则缩回
    let $dynamicText = $("#dynamicText");
    var textCount = 0;
    $dynamicText.focus(function () {
        this.rows = 4;
        $(".idea_quick_menu").css("display","block");
        $dynamicText.prev().css("display","block");
        $dynamicText.keyup(function () {
            if ((textCount = $dynamicText.val().length) > 500 && textCount < 550) {
                $dynamicText.prev().css("color","lightcoral");
                $dynamicText.prev().html("您已输入<span>0</span>字，最多支持500字符");
            } else {
                $dynamicText.prev().html("您已输入<span>0</span>字");
                $dynamicText.prev().css("color","lightslategray");
            }
            var ct= $(this).val();
            ct=ct.replace(/^(\r\n|\n|\r|\t| )+/gm, "");
            $dynamicText.val(ct);
            $dynamicText.prev().find("span").html(textCount);
        })
    });
    $dynamicText.blur(function () {
        let v = $dynamicText.val();
        v.replaceAll("/\/n"," ");
        if ($dynamicText.val().replaceAll(" ","") === "") {
            this.rows = 2;
        }
    });

    // 头部滚动侦测
    // var waypointHeader = new Waypoint({
    //     element: document.getElementById('headerPoint'),
    //     handler: function (direction) {
    //         var $header = $("#head_slider");
    //         if (direction === "down") {
    //             $header.css({
    //                 "position": "fixed",
    //                 "top": "0",
    //             })
    //         } else {
    //             if ($header.css("display") !== "none"){
    //                 $header.attr("style","");
    //                 $header.css("display","block");
    //             }
    //         }
    //         console.log(direction)
    //     }
    // })

    // 留言板留言输入框控制
    $("#guestingControl").click(function () {
        var $guesting = $(".guesting");
        if ($guesting.css("display") === "none") {
            $guesting.show();
            $(this).html("隐藏输入框");
        } else {
            $guesting.hide();
            $(this).html("我也说两句");
        }
    })
})

/**
 * 初始化空间个人信息
 * @type {number}
 */
function zoneInit() {
    var $personal = $(".personal");
    $.ajax({
        url:"/zones",
        type:"GET",
        data:"uid=" + $.cookie("userId"),
        dataType:"JSON",
        success:function (data) {
            if (data.state) {
                var zone = data.data;
                popup(welcomeByHour() + $.cookie("username"), "pass"); // 登录欢迎语
                // $personal.find(".avatar").attr("src","");
                $personal.find(".username").html($.cookie("username"));
                // $personal.find(".follow").html("关注&nbsp;" + zone.follow);
                // $personal.find(".fans").html("粉丝&nbsp;" + zone.fans);
                $personal.find(".signature").html(zone.autograph);
                $personal.find(".home_code").html("社区ID：" + zone.uniqueCode);
                var date = dateDiff(new Date(), zone.createdTime);
                var age = (date[0] ? date[0] + "年" : "") + (date[1] ? date[1] + "月" : "") + (date[2] && !date[0] ? date[2] + "天" : "")
                $personal.find(".home_age").html("社龄：" + age + "&nbsp;加入时间：" + dateFormat(zone.createdTime, "YYYY-MM-DD"));
                // $personal.find(".email").html()
            } else {
                popup("小窝信息拉取失败请稍后重试" + data.msg,"error");
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}

/**
 * 初始化小窝主页标签样式
 */
function initDiaryTag() {
    var tags = $(".diary_tag .card_body span");
    for (let i = 0; i < tags.length; i++) {
        console.log("标签内容：" + tags.eq(i).html());
        var fontSize = randomFontSize(12,3);
        var color = randomColor();
        tags.eq(i).css({
            "font-size": fontSize,
            "color": color,
            "font-weight": "bold",
            "border": color + "1px solid",
            "border-radius": 3,
            "display": "inline-block",
            "padding-inline": 7,
            "padding-block": 3,
            "margin": 5,
            "white-space": "nowrap",
            "cursor": "pointer",
        })
    }
}

/**
 * 接口 小窝主页 日记推荐
 */
function initDiaryRecommend() {
    $.ajax({
        url:"/diaries/views/" +  $.cookie("userId"),
        type:"GET",
        success:function (data) {
            var $recommend = $("#diaryRecommend");
            $recommend.empty();
            if (data.state) {
                var diaries = data.data;
                for (let i = 0; i < diaries.length; i++) {
                    var div =
                        "<div>\n" +
                        "    <a href=\"diary_per.html?did=#{did}\">#{num} #{title}</a>\n" +
                        "    <i class=\"show_hide bi bi-forward hide float-right\"></i>\n" +
                        "</div>"
                    div = div.replace(/#{did}/g,diaries[i].id);
                    div = div.replace(/#{num}/g,(i + 1));
                    div = div.replace(/#{title}/g,diaries[i].title);
                    $recommend.append(div);
                }
            } else {
                $recommend.html("<a href='#' onclick='initDiaryRecommend()'>加载失败 重新加载</a>")
                popup("日记推荐卡片加载失败，请稍后再试","error");
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}

/**
 * 接口 小窝主页 日记分类
 */
function initDiaryClazzAndNum() {
    $.ajax({
        url:"/classes/" + $.cookie("userId"),
        type:"GET",
        success:function (data) {
            var $showClazz = $(".diary_clazz .card_body");
            $showClazz.empty();
            if (data.state) {
                var classes = data.data;
                for (let i = 0; i < classes.length; i++) {
                    var div = "<div><a href=\"diary.html?cid=#{cid}\">#{clazz} #{num}" +
                        "<i class=\"show_hide bi bi-forward hide float-right\"></i></a></div>"
                    div = div.replace(/#{cid}/g,classes[i].cid);
                    div = div.replace(/#{clazz}/g,classes[i].clazz);
                    div = div.replace(/#{num}/g,classes[i].num);
                    $showClazz.append(div);
                }
            } else {
                $showClazz.append("<div><a href=\"#\" onclick='initDiaryClazzAndNum()'>加载失败 重新加载" +
                    "<i class=\"show_hide bi bi-forward hide float-right\"></i></a></div>")

            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}

/**
 * 接口 拉取动态列表
 * @param isPersonal 是否是查看个人的
 */
function pullDynamicList(isPersonal) {
    var data;
    if (isPersonal) {
        data += "&uid=" + $.cookie("userId");
    }
    $.ajax({
        url:"/dynamics",
        type:"GET",
        data:data,
        dataType:"JSON",
        success:function (data) {
            if (data.state) {
                var dynamics = data.data;
                var area = $("#dynamic > div");
                area.nextAll().remove();
                for (let i = 0; i < dynamics.length; i++) { // 添加每条动态
                    var opera = "<a href=\"\">举报</a>";
                    if (dynamics[i].uid === parseInt($.cookie("userId"))) {
                         opera += "<a href=\"#\" onclick='deleteDynamic(#{id})'>删除</a>";
                    }
                    var comments = ""; // 评论
                    var res = getComment(1,dynamics[i].dyid); // 请求评论接口
                    if (res != null) {
                        for (let j = 0; j < res.length; j++) { // 添加评论
                            // 每条评论
                            var menu = "";
                            if (res[j].uid === parseInt($.cookie("userId"))) { // 如果这条评论是自己的（当前用户），则显示删除按钮
                                menu =
                                    "            <span onClick=\"deleteComment(#{cid})\" class=\"comment_menu pointer\">删除</span>";
                            } // 否则只显示举报
                            menu +=
                                "            <span class=\"comment_menu pointer\">举报</span>";
                            var cPer =
                                "        <div id='comment-#{cid}' class=\"comment_per \">\n" +
                                "            <img src=\"../../images/test/avatar%20(3).jpeg\" " +
                                "                class=\"avatar_mini circle border_1 ava_left pointer\" alt=\"\">\n" +
                                "            <div>" +
                                                menu +
                                "            </div>" +
                                "            <div class=\"username font_less\"><a href='#'>#{commentor}</a>：</div>\n" +
                                "            <div class=\"time font_mini\">#{commentTime}</div>\n" +
                                "            <div class=\"comment font_less\">#{commentContent}</div>\n" +
                                "        </div>\n"
                            cPer = cPer.replace(/#{cid}/g,res[j].id);
                            cPer = cPer.replace(/#{commentor}/g,res[j].creator);
                            var cTime = dateFormat(res[j].createdTime,"YYYY年M月D日 HH:mm");
                            cPer = cPer.replace(/#{commentTime}/g,cTime);
                            cPer = cPer.replace(/#{commentContent}/g,res[j].content);
                            comments += cPer; // 将每条评论加到评论区块中
                            menu = ""; // 清空
                        }
                    }
                    var per =
                        "<div id='dynamic-#{id}' class=\"dynamic_per\">\n" +
                        "    <img src=\"../../images/test/avatar%20(1).jpeg\" class=\"avatar border_1 circle ava_left\" alt=\"\">\n" +
                        "    <div class=\"info\">\n" +
                        "        <div class=\"username\">" +
                        "           <div class=\"menu_box gap_col_2\">\n" +
                        "               <div class=\"menu_control padding_col\"><i class=\"bi bi-caret-down opacity_50\"></i></div>\n" +
                        "               <div class=\"menu_body\">\n" +
                                            opera +
                        "               </div>\n" +
                        "           </div>" +
                        "           #{creator}" +
                        "       </div>\n" +
                        "        <div class=\"time\">#{createdTime}</div>\n" +
                        "    </div>\n" +
                        "    <p class=\"content text-justify\">\n"
                             + "#{content}" +
                        "    </p>\n" +
                        "    <div>\n" +
                        "        <div class=\"interact\">\n" +
                        "            <span>\n" +
                        "                <a href=\"#\">\n" +
                        "                    <i class=\"bi bi-eye\"></i>\n" +
                        "                    #{views}\n" +
                        "                </a>\n" +
                        "            </span>\n" +
                        "            <span>\n" +
                        "                <a href=\"#\">\n" +
                        "                    <i class=\"bi bi-heart\"></i>\n" +
                        "                    #{likes}\n" +
                        "                </a>\n" +
                        "            </span>\n" +
                        "            <span>\n" +
                        "                <a href=\"#\">\n" +
                        "                    <i class=\"bi bi-box-arrow-in-up-right\"></i>\n" +
                        "                    #{reprints}\n" +
                        "                </a>\n" +
                        "            </span>\n" +
                        "        </div>\n" +
                                 comments +
                        "    </div>\n" +
                        "    <form id=\"commentForm-#{id}\" action=\"\">\n" +
                        "        <input class=\"comment_input form-control\" type=\"text\"\n" +
                        "               onfocus=\"showMenu($(this).next())\"\n" +
                        "               name=\"content\" " +
                        "               maxlength=\"120\" placeholder=\"说点儿什么吧~\" autocomplete=\"off\">\n" +
                        "        <div class=\"font_less gap gap_row_1\">\n" +
                        "            <span class=\"gap_col_1\">☺</span>\n" +
                        "            <span class=\"gap_col\">@</span>\n" +
                        "            <span class=\"float-right\">\n" +
                        "                <label for=\"commentSubmit-#{id}\">\n" +
                        "                    <input id=\"commentSubmit-#{id}\" type=\"checkbox\" name=\"restrict\"> 悄悄评论\n" +
                        "                </label>\n" +
                        "                <span class=\"form_submit pointer\"  onclick=\"commentSubmit(1,#{id})\">\n" +
                        "                    提交评论\n" +
                        "                </span>\n" +
                        "            </span>\n" +
                        "        </div>" +
                        "    </form>" +
                        "</div>"
                    per = per.replace(/#{id}/g,dynamics[i].dyid);
                    per = per.replace(/#{creator}/g,dynamics[i].creator);
                    var time = dateFormat(dynamics[i].createdTime,"YYYY年M月D日 HH:mm");
                    per = per.replace(/#{createdTime}/g,time);
                    per = per.replace(/#{content}/g,dynamics[i].content);
                    per = per.replace(/#{views}/g,dynamics[i].views);
                    per = per.replace(/#{likes}/g,dynamics[i].likes);
                    per = per.replace(/#{reprints}/g,dynamics[i].reprints);
                    area.after(per);
                }
            } else {
                popup("动态拉取失败","error");
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}

/**
 * 接口 获取评论
 * @param module 哪个模块
 * @param topicId 哪个文章
 * @returns {*} 评论
 */
function getComment(module,topicId) {
    var comments;
    $.ajax({
        url:"/comments/" + module + "/" + topicId,
        type:"GET",
        async:false,
        success:function (data) {
            if (data.state) {
                comments = data.data;
            } else {
                popup("拉取评论列表失败", "error");
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
    return comments;
}

/**
 * 接口 提交评论
 * @param module 属于哪个模块
 * @param topicId 属于哪个文章
 */
function commentSubmit(module,topicId) {
    console.log("#commentForm-" + topicId)
    var data = $("#commentForm-" + topicId).serialize() +
        "&module=" + module + "&topicId=" + topicId +
        "&uid=" + $.cookie("userId");
    console.log(data);
    $.ajax({
        url:"/comments",
        type:"POST",
        data:data,
        dataType:"JSON",
        success:function (data) {
            if (data.state) {
                popup("提交成功！","pass");
                pullDynamicList(false); // 重新拉取动态列表
            } else {
                popup("评论失败，稍后再试", "error");
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}

/**
 * 接口 删除评论
 * @param cid 要删除的评论的id
 */
function deleteComment(cid) {
    if (confirm("确定删除这条评论吗？")) {
        console.log("id=" + cid + "&uid=" + $.cookie("userId"))
        $.ajax({
            url:"/comments",
            type:"DELETE",
            data:"id=" + cid + "&uid=" + $.cookie("userId"),
            dataType:"JSON",
            success:function (data) {
                if (data.state) {
                    popup("删除成功！","pass");
                    $("#comment-" + cid).remove();
                } else {
                    popup("删除评论失败，稍后再试", "error");
                }
            },
            error:function (xhr) {
                popup("当前网络异常，请检查后重新登录","error");
            }
        })
    }
}

/**
 * 删除动态
 * @param dyid 动态id
 */
function deleteDynamic(dyid) {
    if (confirm("确定要删除这条动态吗？")) {
        $.ajax({
            url:"/dynamics/" + dyid,
            type:"DELETE",
            success:function (data) {
                if (data.state) {
                    popup("删除成功","pass");
                    $("#dynamic-" + dyid).remove();
                } else {
                    popup("删除失败，请稍后再试","error");
                }
            },
            error:function (xhr) {
                popup("当前网络异常，请检查后重新登录","error");
            }
        })
    }
}

/**
 * 接口 提交新动态
 * @param state true 发表 false 草稿箱
 */
function newDynamicSubmit(state) {
    $.ajax({
        url:"/dynamics",
        type:"POST",
        data:$("#dynamicForm").serialize() + "&uid=" + $.cookie("userId"),
        dataType:"JSON",
        success:function (data) {
            if (data.state) {
                popup("新动态提交成功！","pass");
                $("#dynamicForm textarea").val("");
                pullDynamicList(false);
            }  else {
                popup("新动态提交失败，请稍后重试" + data.msg,"error");
            }
        },
        error: function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}

/**
 * 控制菜单显示
 * @param e 显示的目标
 */
function showMenu(e) {
    if (e.css("display") === "none") {
        e.show();
    }
}

/**
 * 接口 初始化留言板
 */
function initGuestbook() {
    $.ajax({
        url:"/guestbooks/" + $.cookie("userId"),
        type:"GET",
        success:function (data) {
            if (data.state) {
                $(".guestbook").empty();
                var res = data.data;
                var guestbook =
                    "<div class=\"title font_normal\">留言板</div>\n" +
                    "    <div class=\"tips\">\n" +
                    "        [ ! ] 当前留言默认模式为非好友审核，陌生人的留言都需要经过博主审核后才能发布！\n" +
                    "        <!--\n" +
                    "            mode1 默认模式 所有留言需要经过审核\n" +
                    "            mode2 好友模式 陌生人留言需要经过审核\n" +
                    "            mode3 自由模式 所有人留言不需要经过审核\n" +
                    "            mode4 权限模式 禁止留言\n" +
                    "            mode5 隐私模式 禁止留言并隐藏所有已存在留言\n" +
                    "         -->\n" +
                    "    </div>\n" +
                    "    <div class=\"greet font_normal\">\n" +
                    "        <div class=\"border_bottom\">\n" +
                    "            <span class=\"font_less\">欢迎你的到来</span>\n" +
                    "            <span class=\"setting\">\n" +
                    "                <a href=\"\" class=\"font_mini\">留言板管理</a>\n" +
                    "            </span>\n" +
                    "        </div>\n" +
                    "        <div class=\"content font_less\">\n" +
                    "            #{greet}\n" +
                    "        </div>\n" +
                    "        <div class=\"guesting font_less hide\">\n" +
                    "            <div class=\"font-weight-bold gap\">\n" +
                    "                请编辑您的留言\n" +
                    "                <span class=\"tip hide\"></span>\n" +
                    "            </div>\n" +
                    "            <form id=\"guestbookForm-#{id}\">\n" +
                    "                <textarea name=\"content\" maxlength=\"550\" title=\"最多可输入500字\" rows=\"5\" required\n" +
                    "                           class=\"indent_less\" autocomplete=\"off\" placeholder=\"想对ta说什么\"></textarea>\n" +
                    "                <div class=\"control\">\n" +
                    "                    <div class=\"form_submit all_center font_less\" onclick=\"guestSubmit(#{id})\">提 交</div>\n" +
                    "                </div>\n" +
                    "            </form>\n" +
                    "        </div>\n" +
                    "        <div class=\"control font_mini\">\n" +
                    "            <b>留言(0)</b>\n" +
                    "            <span id=\"guestingControl\" class=\"pointer\">我也说两句</span>\n" +
                    "            <span style=\"position: absolute;right: 40px\"> <a href=\"\">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"\">下一页</a></span>\n" +
                    "        </div>\n" +
                    "        <hr>\n" +
                    "    </div>"
                guestbook = guestbook.replace(/#{id}/g,res.id);
                var greet = res.greet === null ? "和大家打个招呼吧~" : res.greet;
                guestbook = guestbook.replace(/#{greet}/g,greet);
                $(".guestbook").attr("id","guestbook-" + res.id);
                $(".guestbook").append(guestbook);
                guestTotal = res.count;
                $(".greet .control b").html("留言(" + guestTotal + ")")
                pullGuestList(1);
            } else {
                popup("留言板信息拉取失败" + data.msg,"error");
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}
var guestTotal;
/**
 * 拉取留言列表
 */
function pullGuestList(pageNo,pageSize) {
    if (!pageSize) {
        pageSize = 12;
    }
    var data = "id=" + $.cookie("userId") + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
    $.ajax({
        url:"/guests",
        type:"GET",
        data: data,
        dataType: "JSON",
        success:function (data) {
            if (!data.state) {
                popup("留言列表拉取失败" + data.msg,"error");
            }
            // if (pageNo === 1) {
                $(".guest").empty();
            // }
            var guests = data.data;
            if (guests.length === 0) { // 若无数据说明还没有留言
                $(".guest").append("<div class='text-center'>暂时还没有留言</div>")
            } else {
                for (let i = 0; i < guests.length; i++) {
                    var menu =
                        "            <span class=\"pointer\">举报</span>\n";
                    if (guests[i].uid === parseInt($.cookie("userId"))) {
                        menu +=
                            "            <span onclick=\"deleteGuest(#{id})\" class=\"pointer\">删除</span>\n";
                    }
                    var per =
                        "<div id=\"guest-#{id}\" class=\"guest_per\">\n" +
                        "    <img src=\"../../images/test/avatar%20(2).jpeg\" class=\"float-left avatar border_1\" alt=\"\">\n" +
                        "    <div class=\"right\">\n" +
                        "        <div class=\"float-right font_mini\">\n"  +
                                      menu +
                        "        </div>" +
                        "        <span class=\"username\">#{creator}</span>\n" +
                        "        <span class=\"info font_mini\">\n" +
                        "        <span>#{floor} 楼</span>\n" +
                        "        <span>#{createdTime}</span>\n" +
                        "        <span>[赞] 56</span>\n" +
                        "        </span>\n" +
                        "        <div class=\"content font_less\">\n" +
                        "            #{content}\n" +
                        "        </div>\n" +
                        "        <hr>\n" +
                        "    </div>\n" +
                        "</div>"
                    per = per.replace(/#{id}/g,guests[i].id);
                    per = per.replace(/#{avatar}/g,guests[i].avatar);
                    per = per.replace(/#{creator}/g,guests[i].creator);
                    var time = dateFormat(guests[i].createdTime,"YYYY-M-D H:mm")
                    per = per.replace(/#{createdTime}/g,time);
                    per = per.replace(/#{floor}/g,guestTotal--);
                    per = per.replace(/#{content}/g,guests[i].content);
                    $(".guest").append(per);
                }
            }
        },
        error:function (xhr) {
            popup("当前网络异常，请检查后重新登录","error");
        }
    })
}

/**
 * 接口 删除留言
 * @param id 要删除留言的id
 */
function deleteGuest(id) {
    if (confirm("确定删除这条评论吗？")) {
        $.ajax({
            url:"/guests/" + id,
            type:"DELETE",
            success:function (data) {
                if (data.state) {
                    popup("删除成功！","pass");
                    pullGuestList(0);
                } else {
                    popup("删除失败，请稍后重试" + data.msg,"error");
                }
            },
            error:function (xhr) {
                popup("当前网络异常，请检查后重新登录","error");
            }
        })
    }
}

/**
 * 接口 提交留言
 * @param gid 提交到哪个留言板
 */
function guestSubmit(id) {
    var data = $("#guestbookForm-" + id).serialize() + "&gid=" + id + "&uid=" + $.cookie("userId");
    if (!isEmpty($(".guesting textarea"))) {
        $(".guesting .tip").html("正在提交...");
        $(".guesting .tip").show();
        $.ajax({
            url:"/guests",
            type:"POST",
            data:data,
            dataType:"JSON",
            success:function (data) {
                if (data.state) {
                    setTimeout("$('.guesting .tip').html('提交成功！')",300);
                    $(".guesting textarea").val("");
                    pullGuestList(0);
                } else {
                    $(".guesting .tip").html("操作失败，请稍后尝试");
                    $(".guesting .tip").show();
                }
            },
            error:function (xhr) {
                popup("当前网络异常，请检查后重新登录","error");
            }
        })
    } else {
        $(".guesting .tip").html("*请输入留言内容后再进行提交");
        $(".guesting .tip").show();
    }
}
// 留言编辑输入框的控制
$(function () {
    $(".guesting textarea").focus(function () {
        $(".guesting .tip").hide();
    })
})
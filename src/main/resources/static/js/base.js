/**
 * 页面加载
 */
$(function () {

    // 用户信息
    var username = $.cookie("username");
    head_init(username);
    // $(".username").html(username);


})

/**
 * 判断元素是否聚焦
 * @param e 要判断的元素
 * @returns {boolean} yes / no
 */
function isFocus(e) {
    return document.activeElement.id === e.attr("id");
}

/**
 * 判断input输入是否为空 判断空格
 * @param e 要判断的元素（字符）
 * @returns {boolean} yes / no
 */
function isEmpty(e) {
    if (e === null) return false;
    var v = e;
    if (typeof e !== "string") { // 若目标非字符则为input对象
        v = e.val(); // 获取input对象的值
    }
    // 当值为null或去掉所有空格后为空则返回true
    return (v == null || v.replaceAll(" ", "") === "");
}

/**
 * 判断input输入是否为空 不考虑空格
 * @param e 要判断的元素（字符）
 * @returns {boolean} yes / no
 */
function isEmptyIg(e) {
    if (e === null) return false;
    var v = e;
    if (typeof e !== "string") { // 若目标非字符则为input对象
        v = e.val(); // 获取input对象的值
    }
    // 当值为null或去掉所有空格后为空则返回true
    return (v == null || v === "");
}


// 刷新页面
$("#flush").click(function () {
    location.reload();
})

// 回到顶部
// window.scrollTo 原生跳跃
// $(window).scrollTo 平滑滚动
function toTop() {
    window.scrollTo(0, 0);
}


/**
 * 跟随鼠标
 * @param exec 执行元素
 * @param event 从事件中获取坐标
 * @param thresholdX X轴偏差，左负右正
 * @param thresholdY Y轴偏差，上负下正
 * 该方法不管理元素可见性
 */
function follow(exec, event, thresholdX, thresholdY) {
    // 鼠标实际坐标
    let x = event.pageX;
    let y = event.pageY;
    exec.css({
        'let': (x + thresholdX) + 'px',
        'top': (y + thresholdY) + 'px',
    })
}

/**
 * 获取欢迎语 根据当前-时
 */
function welcomeByHour() {
    var now = new Date().getHours();
    var welcome;
    if (5 <= now && now < 11) {
        welcome = "早上好，";
    } else if (11 <= now && now < 14) {
        welcome = "中午好，";
    } else if (14 <= now && now < 19) {
        welcome = "下午好，";
    } else if (19 <= now && now < 23) {
        welcome = "晚上好，";
    } else {
        welcome = "夜深了，";
    }
    return welcome;
}

//初始化头部
function head_init(username) {
    // 不等于空说明已登录
    if (username !== undefined) {
        console.log("page head reloading...");
        var span =  $(".navigation span:first");
        // a.attr("href","#");
        span.html("");
        span.append(welcomeByHour() + "<a href=\"account.html\" style='color:#ff4067'>" + username + "</a>")
    }
}

//显示则隐藏，隐藏则显示
function hideOrShow(e) {
    if (e.css("display") === "none") {
        e.show();
    } else {
        e.hide();
    }
}

/**
 * 消息弹窗 轻量
 * @param val 要提示的内容
 * @param type 提示的类型
 *              1 通过；2 错误；3 警告；4 疑问；5 信息；
 */
var timeExecutor; // 定时执行器
function popup(val,type) {
    var content = $("#popup_tip .popup_content"); // 弹窗内容 元素
    var img = $("#popup_tip img"); // 弹窗图标
    var url = img.attr("src"); // 弹窗图标路径
    // 替换url
    img.attr("src",url.substring(0, url.lastIndexOf("/") + 1) + type + ".png");
    // 替换内容
    content.html(val);
    // 展示并持续1.8s
    $("#popup_tip").fadeIn();
    clearTimeout(timeExecutor); // 清除定时执行器缓存
    timeExecutor = setTimeout("$(\"#popup_tip\").fadeOut()",1800);
}


/**
 * 计算日期差值（不用格式化日期）
 * @param recent 最近的日期
 * @param previous 以前的日期
 * @return 年 月 日
 */
function dateDiff(recent,previous) {
    var day = moment(recent).diff(previous,"second") / 60 / 60 / 24;
    var month,year;
    year = Math.floor(day / 365);
    day = day - year * 365;
    month = Math.floor(day / 30);
    day = Math.floor(day - month * 30) + 1;
    return [year,month,day];
}

/**
 * 日期格式化（有默认格式）
 * @param date
 * @returns {*}
 */
function dateFormat(date,fmt ) {
    var format;
    if (fmt) {
        format = fmt;
    } else {
        format = "YYYY-MM-DD HH:mm:ss";
    }
    return moment(date).format(format);
}

/**
 * 获取随机颜色（rgba）
 */
function randomColor() {
    var rgba = [];
    for (let i = 0; i < 3; i++) {
        rgba.push(Math.ceil(Math.random() * 150 + 50));
    }
    return "rgba(" + rgba[0] + "," + rgba[1] + "," + rgba[2] + "," + ".9)";
}

/**
 * 获取随机字体尺寸
 * @param min
 * @param offset
 */
function randomFontSize(min,offset) {
    return Math.floor(Math.random()* offset)  + min + "px";
}

/**
 * 获取格式化标签id中的id
 * @param idStr 格式化标签id的字串
 * @returns {*}
 */
function getId(idStr) {
    return idStr.split("-")[1];
}

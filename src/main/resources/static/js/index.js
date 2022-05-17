$(function () {
    // 提交 搜索
    const scommit = $("#search_commit");
    scommit.click(function () {
        $("#index_search_form").submit();
    })

    // 互动 搜索框黑猫
    const search = $("#index_search");
    var blink_count = 0;
    const cat = $("#index_log_cat");
    const tip = $("#index_search_cat_tip");
    cat.click(function () {
        if (blink_count % 2 === 0) { // 闭眼
            cat.attr("src", "/images/cat_black_closed.png");
            //闭眼清除input内容
            search.val(null);
            if (blink_count === 0) {
                // console.log(111);
                tip.show();
            }
        } else { // 睁眼
            cat.attr("src", "/images/cat_black.png");
            tip.hide();
        }
        blink_count++;
    })

    // 互动 推送表格
    const tFiled = $(".index-table-row-filed");
    let color;
    tFiled.mouseenter(function () {
        color = this.firstChild.style.color;
        if (this.tagName === "TH".toUpperCase()) {
            this.firstChild.style.color = "red";
        } else {
            this.firstChild.style.color = "whitesmoke";
        }
    })

    tFiled.mouseleave(function () {
        this.firstChild.style.color = color;
    })

})
/**
 * 加载zone模块所有的公共插件
 */

// 加载qrcode插件
var qrcode = new QRCode(document.getElementById("qrCode"), {
    text: "https://www.baidu.com/",
    width: 100,
    height: 100,
    colorDark: "#000000",
    colorLight: "#ffffff",
    correctLevel: QRCode.CorrectLevel.H
});

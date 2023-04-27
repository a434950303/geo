$(function () {
    $("#save").click(function () {
        save();
    });
});

// save()
// 用于提取页面的参数; 无参数，有返回值。返回值格式推荐为 json格式 方便GeoGlobe Server存储 以及 后续服务启动需要使用解析该json
function save() {

    var esIp = $("#esIp").val();
    var esPort = $("#esPort").val();
    var dgraphIp = $("#dgraphIp").val();
    var dgraphPort = $("#dgraphPort").val();
    var stdbmsprotable = $("#stdbmsprotable").val();
    var index = $("#index").val();

    //var DatabaseType = $("#DatabaseType").val();
    var GeoEntity = new Object();

    GeoEntity.esIp = esIp;
    GeoEntity.esPort = esPort;
    GeoEntity.dgraphIp = dgraphIp;
    GeoEntity.dgraphPort = dgraphPort;
    GeoEntity.stdbmsprotable = stdbmsprotable;
    GeoEntity.index = index;
    //GeoEntity.databaseType = DatabaseType;

    var requestVO = JSON.stringify(GeoEntity);

    return requestVO;
}

// edit(data):用于将data 填充到 交互页面。有参数，无返回值。
// 该参数 与 save方法 返回值一致。
function eidt(data){
    $("#fuserDataSetName").val(data);
}

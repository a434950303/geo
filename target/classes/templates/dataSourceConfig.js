/**
 * author:ht
 * date:2021/09/18 10:45
 * 地理实体数据服务在 geoglobe 加载数据源页面
 */
var serviceType = "";
var code = "";
var dbType = "";
var platform = "";
var checkedMark = false;
var Step = window.Step={
    dataSource:""
}

$(function(){
    function checkLicense() {
        $.ajax({
            url: "license/checkLicense",
            type: 'get',
            async: false,
            success: function(response) {
                var result = JSON.parse(response);
                if(!result.status && response.code == "403"){
                    alert(result.message);
                    window.close();
                }
            }
        })
    }
    // 检查license
    checkLicense();
    $("#save").click(function () {
        save();
    });
    initParams();
});

var esDataSource = {
    ip: '',
    port: '',
    username:'',
    password:''
}

var minioConfig = {
   endpoint:'',
    accessKey: '',
    secretKey:'',
    bucketName:''
}

var serviceMgrUrl = '';

function initParams() {
    var strDbType = 17;
    var platform = $("#platform").val();
    console.log('平台类型是：' + platform);

    console.log("parent:   " + document.referrer);
    var parentUrl = document.referrer;
    console.log(parentUrl.split("/")[3]);
    var serverName = parentUrl.split("/")[3];
    if(null == serverName || '' === serverName){
        serverName = 'ServiceMgr';
    }
    serverName = 'ServiceMgr';
    var baseHref = window.document.location.href;
    console.log(baseHref);
    var pathName = window.document.location.pathname;
    console.log(pathName);
    var pos = baseHref.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht = baseHref.substring(0, pos);

    serviceMgrUrl = localhostPaht + '/' +  serverName;
    console.log(serviceMgrUrl);
    $.ajax({
        type: "get",
        dataType: "text",
        async: false,
        contentType: "application/x-www-form-urlencoded",
        //url: "http://192.168.101.59:9010/ServiceMgr/geoapps/_load_dbcp?dbtype=17&typeName=extend_es",
        url: serviceMgrUrl + "/geoapps/_load_dbcp?dbtype=17&typeName=extend_es",
        success: function (data) {
            if (data) {
                Step.dataSource = [];
                data = eval("(" + data + ")");
                for (var i = 0; i < data.result.length; i++) {
                    var dataSource = {};
                    dataSource.dataSourceName = data.result[i].key;
                    dataSource.dataSourceId = data.result[i].value;
                    Step.dataSource.push(dataSource);
                }
            }else{
                //无es数据源
            }
        },
        error: function (data){
            console.log(data);
        }
    });
    $("#dataSourceSelect").empty();
    var html = template("dataSourceSelectTemplate",{data : Step.dataSource});
    $("#dataSourceSelect").html(html);
}

/**
 * 从GeoGlobe获取es连接信息
 */
function getEsInfoFromGeoGlobe(){
    var connPoolId = $('#dataSourceSelect option:selected').val();
    $.ajax({
        type: "get",
        dataType: "text",
        async: false,
        contentType: "application/x-www-form-urlencoded",
        url: serviceMgrUrl + "/geoapps/editCP?" + "connPoolId=" + connPoolId + "&responseType=json",
        success: function (data) {
            if (data) {
                Step.dataSource = [];
                esDataSource.ip = JSON.parse(JSON.parse(data).customStr).ip;
                esDataSource.port = JSON.parse(JSON.parse(data).customStr).port;
                esDataSource.username = JSON.parse(JSON.parse(data).customStr).username;
                esDataSource.password = JSON.parse(JSON.parse(data).customStr).password;
            }else{
                //无es数据源
            }
        },
        error: function (data){
            console.log(data);
        }
    });
}

/**
 *  从geoglobe接口返回es索引列表
 */
function checkMinio(){
    minioConfig.endpoint = $("#endpoint").val();
    minioConfig.accessKey = $("#accessKey").val();
    minioConfig.secretKey = $("#secretKey").val();
    minioConfig.bucketName = $("#bucketName").val();
    /*var dialog = showDialog({message:"ES连接中......"});*/
    var esUrl = esDataSource.ip + ":" + esDataSource.port;
    var params = {"endpoint":  minioConfig.endpoint, "accessKey":  minioConfig.accessKey, "secretKey": minioConfig.secretKey,"bucketName":minioConfig.bucketName};
    var resultDiv =  $("#resultDiv");
    $.ajax({
        url: "serviceDataSet/checkMinio",
        type: "post",
        data: params,
        dataType: "json",
        async: false,
        success: function (json) {
            var data = json.data;
            if (json.result){
                resultDiv.text(data);
                resultDiv.css("color","green")
            }else{
                resultDiv.text(data);
                resultDiv.css("color","red")
            }
        },
        error: function (data) {
            resultDiv.text(data);
            resultDiv.css("color","red")
            console.log(data);
        }
    });
}

/**
 *  从geoglobe接口返回es索引列表
 */
function getIndicesFromGeoGlobe(){
    getEsInfoFromGeoGlobe();

    getIndexDataForDLST();

    var esUrl = 'http://' + esDataSource.ip + ':' + esDataSource.port
    $.ajax({
        type: "get",
        dataType: "text",
        async: false,
        contentType: "application/x-www-form-urlencoded",
        url: serviceMgrUrl + "/custom/DSS/ProxyHandlerES.jsp?" + "user=" + esDataSource.username
            + "&pwd=" + esDataSource.password + "&ES_SERVER_URL=" + esUrl + "/_cat/indices?h=index" + "&format=json",
        success: function (data) {
            if (data) {
                Step.dataSource = [];
                var result = JSON.parse(data);
                for (var i = 0; i < result.length; i++) {
                    if(result[i] && result[i].index.indexOf('rhsj_') > -1) {
                        Step.dataSource.push(result[i]);
                    }
                }
            }else{
                //无es数据源
            }
        },
        error: function (data){
            console.log(data);
        }
    });
}

/**
 * 查询地理实体数据规范
 */
function getIndexDataForDLST(){
    /*var dialog = showDialog({message:"ES连接中......"});*/
    var esUrl = esDataSource.ip + ":" + esDataSource.port;
    var params = {"esUrl": esUrl, "esUser": esDataSource.username, "esPwd": esDataSource.password};
    $.ajax({
        url: "serviceDataSet/queryESforDLST",
        type: "post",
        data: params,
        dataType: "json",
        async: false,
        success: function (json) {
            //dialog.close();
            var data = json.data;
            $("#dlstIndexSelect").html('');
            var html = template('dlstIndexSelectTemplate', {'data': data});
            $("#dlstIndexSelect").html(html);
        },
        error: function (data) {
            //dialog.close();
            console.log(data);
        }
    });
}

// save()
// 用于提取页面的参数; 无参数，有返回值。返回值格式推荐为 json格式 方便GeoGlobe Server存储 以及 后续服务启动需要使用解析该json
function save() {
    var esIp = esDataSource.ip;
    var esPort = esDataSource.port;
    var esUsername = esDataSource.username;
    var esPassword = esDataSource.password;

    checkMinio();
    var minioEndpoint = minioConfig.endpoint;
    var minioAccessKey = minioConfig.accessKey;
    var minioSecretKey = minioConfig.secretKey;
    var minioBucketName = minioConfig.bucketName;

    var dgraphIp = $("#dgraphIp").val();
    var dgraphPort = $("#dgraphPort").val();
    var stdbmsprotableName = $("#dlstIndexSelect option:selected").val();
    var isEncrypt = $("input[name='isEncrypt']:checked").val();

    var GeoEntity = new Object();

    GeoEntity.esIp = esIp;
    GeoEntity.esPort = esPort;
    GeoEntity.esUsername = esUsername;
    GeoEntity.esPassword = esPassword;
    GeoEntity.dgraphIp = dgraphIp;
    GeoEntity.dgraphPort = dgraphPort;
    GeoEntity.stdbmsprotableName = stdbmsprotableName;
    GeoEntity.isEncrypt = isEncrypt;

    GeoEntity.endpoint = minioEndpoint;
    GeoEntity.accessKey = minioAccessKey;
    GeoEntity.secretKey = minioSecretKey;
    GeoEntity.bucketName = minioBucketName;

    var requestVO = JSON.stringify(GeoEntity);

    return requestVO;
}

// edit(data):用于将data 填充到 交互页面。有参数，无返回值。
// 该参数 与 save方法 返回值一致。
function eidt(data){
    $("#fuserDataSetName").val(data);
}

/**
 *  20210425 ht 检测dgraph连通性
 *  @param dgraphIp
 *  @param dgraphPort
 *  @returns
 */
function checkDgraphConnect(dgraphIp, dgraphPort){
    $.ajax({
        type: "get",
        dataType: "json",
        url: "dataSource/checkDgraphConnect?host=" + dgraphIp + "&port=" + dgraphPort,
        async: false,
        success : function(result){
            if(result.result){
                checkedMark = true;
                //var html = template("dbcpTypeSelectTemplate",{data : result.data.result});
                //$("#dbcpTypeSelect").html(html);
            }

        }
    });
};


(function () {
    cola(function (model) {

        model.set("items", [
            {
                checkType: "健康检查",
                monitorUrl: "http://10.192.168.178:5080/ucf/healthMonitor",
                requestParams: "{'textProperty':'parameterOne','valueProperty':'parameterTwo'}",
                checkStatus: 1,
                errorMessage: "",
                newColumns: "NEW"
            }, {
                checkType: "版本检查",
                monitorUrl: "http://10.192.168.178:5080/ucf/versionMonitor",
                requestParams: "{'key':'keyValue','value':'valueValue'}",
                checkStatus: 2,
                errorMessage: "error信息",
                newColumns: "NEW"
            }, {
                checkType: "批处理检查",
                monitorUrl: "http://10.192.168.178:5080/ucf/batchMonitor",
                requestParams: "",
                checkStatus: 0,
                errorMessage: "error信息",
                newColumns: "NEW"
            }
        ]);

        model.action({
            addTableColumn: function () {
                debugger;
                var columnName = model.get("columnName");
                var productTable = cola.widget("productTable");
                model.set("newColumn", {
                    caption: columnName,
                    bind: ".newColumns"
                });
                // disabled
                productTable.set('columns', model.get("newColumn").toJSON());
                productTable.refresh();
            }
        });

        model.widgetConfig({
            productTable: {
                $type: "table",
                bind: "item in items",
                showHeader: true,
                selectedProperty: "__select",
                highlightCurrentItem: true,
                changeCurrentItem: true,
                height: 170,
                columns: [{
                    $type: "select"
                }, {
                    caption: "检查类型",
                    bind: ".checkType",
                    align: "center",
                    width: "30"
                }, {
                    caption: "监控URL",
                    bind: ".monitorUrl",
                    align: "center"
                }, {
                    caption: "请求报文",
                    bind: ".requestParams",
                    align: "center"
                }, {
                    caption: "检查状态",
                    template: "checkStatus",
                    align: "center",
                    width: "20"
                }, {
                    caption: "错误信息",
                    bind: ".errorMessage",
                    align: "center",
                    width: "30"
                }]
            }
        });
    });

}).call(this);

(function () {
    cola(function (model) {

        model.set("items0s", [
            {
                checkType: "健康检查",
                monitorUrl: "http://10.192.168.178:5080/ucf/healthMonitor",
                requestParams: "{'textProperty':'parameterOne','valueProperty':'parameterTwo'}",
                checkStatus: 1,
                errorMessage: ""
            }, {
                checkType: "版本检查",
                monitorUrl: "http://10.192.168.178:5080/ucf/versionMonitor",
                requestParams: "{'key':'keyValue','value':'valueValue'}",
                checkStatus: 2,
                errorMessage: "error信息"
            }, {
                checkType: "批处理检查",
                monitorUrl: "http://10.192.168.178:5080/ucf/batchMonitor",
                requestParams: "",
                checkStatus: 0,
                errorMessage: "error信息"
            }
        ]);
        model.set("items1s", model.get("items0s").toJSON());
        model.set("items2s", model.get("items0s").toJSON());

        model.set("categories", [
            {
                value: "S",
                text: "交易服务器"
            }, {
                value: "SS",
                text: "跑批服务器"
            }, {
                value: "SSS",
                text: "文件上传服务器"
            }
        ]);


        model.action({
            renderTable: function (type) {
                if (type === "S") {
                    var count = model.get("categories").entityCount;
                    for (var i = 0; i < count; i++) {

                        var xRenderColaTable = cola.xRender([
                            new cola.Table({
                                $type: "table",
                                bind: "item in " + "items" + i + "s",
                                showHeader: true,
                                selectedProperty: "__select",
                                "id": "renderColaTable" + i, // disabled
                                "class": "render" + i,
                                height: 170,
                                columns: [{
                                    $type: "select"
                                }, {
                                    caption: "检查类型",
                                    bind: ".checkType",
                                    align: "center"
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
                                    bind: ".checkStatus",
                                    align: "center"
                                }, {
                                    caption: "错误信息",
                                    bind: ".errorMessage",
                                    align: "center"
                                }]
                            })
                        ], model);

                        $("#dynamicTableContainer").append(xRenderColaTable);
                    }
                } else if (type === "SS") {

                    var xRenderColaTable = new cola.Table({
                        $type: "table",
                        bind: "items2 in items2s",
                        showHeader: true,
                        selectedProperty: "__select",
                        "id": "renderColaTable", // disabled
                        "class": "render",
                        height: 170,
                        columns: [{
                            $type: "select"
                        }, {
                            caption: "检查类型",
                            bind: ".checkType",
                            align: "center"
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
                            bind: ".checkStatus",
                            align: "center",
                            width: "40px"
                        }, {
                            caption: "错误信息",
                            bind: ".errorMessage",
                            align: "center"
                        }]
                    });
                    xRenderColaTable.appendTo($("#dynamicTableContainer"));
                } else {
                    for (var i = 0; i < 1; i++) {
                        var xRenderColaTable = cola.xRender('<div id="productTable' + i + '" class="productTable"> <template name="checkStatus"><div><i class="icon circle thin" c-class="red:item.checkStatus==0; green:item.checkStatus==1"></i> </div> </template> </div>', model);
                        $("#dynamicTableContainer").append(xRenderColaTable);
                    }
                }
            },
        });

        model.set("selectValue", "所有");
        model.widgetConfig({
            selectDropDown: {
                $type: "dropdown",
                openMode: "drop",
                valueProperty: "value",
                textProperty: "text",
                placeholder: "所有", // disabled
                items: "{{category in categories}}",
                bind: "selectValue",
                post: function (self, arg) {
                    debugger;
                    model.action.renderTable(self.get("value"));
                }
            },
            productTable0: {
                $type: "table",
                bind: "item in items0s",
                showHeader: true,
                selectedProperty: "__select",
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
                }],
                renderCell: function (self, arg) {
                    debugger;
                }
            }
        });
    });

}).call(this);

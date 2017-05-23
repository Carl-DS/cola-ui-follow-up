(function () {
    cola(function (model) {

        model.set("items", [
            {
                checkType: "健康检查",
                monitorUrl: "http://10.192.168.178:5080/ucf/healthMonitor",
                requestParams: "{'textProperty':'parameterOne','valueProperty':'parameterTwo'}",
                checkStatus: 1,
                errorMessage: "",
                newColumns: "NEW",
                monthDay: 29
            }, {
                checkType: "版本检查",
                monitorUrl: "http://10.192.168.178:5080/ucf/versionMonitor",
                requestParams: "{'key':'keyValue','value':'valueValue'}",
                checkStatus: 2,
                errorMessage: "error信息",
                newColumns: "NEW",
                monthDay: 28
            }, {
                checkType: "批处理检查",
                monitorUrl: "http://10.192.168.178:5080/ucf/batchMonitor",
                requestParams: "",
                checkStatus: 0,
                errorMessage: "error信息",
                newColumns: "NEW",
                monthDay: 31
            }
        ]);

        model.action({
            addTableColumn: function () {
                debugger;
                var container = $(".dynamicTableContainer");
                container.empty();
                var columnType = model.get("columnName");
                if (parseInt(columnType) === 1) {
                    var innerDom1 = cola.xRender('<c-table bind="items" id="productTable" sortMode="local"> <column property="checkType" caption="{{items.checkType}}" sortable="true"  template="input-column"></column> </c-table>');
                    container.append(innerDom1);
                } else if (parseInt(columnType) === 2) {
                    var innerDom2 = cola.xRender('<c-table id="productTable" bind="items"  sortMode="local">' +
                        '<template name="checkStatus"><div><i class="icon circle thin" c-class="red:item.checkStatus==0; green:item.checkStatus==1"></i></div></template>' +
                        '<column property="checkType" caption="{{items.checkType}}" sortable="true"  template="input-column"></column>' +
                        '<column template="checkStatus" caption="检查状态" sortable="true"  template="input-column"></column>' +
                        '</c-table>');
                    container.append(innerDom2);
                } else {
                    var innerDom3 = cola.xRender('<div id="productTable"> <template name="checkStatus"><div><i class="icon circle thin" c-class="red:item.checkStatus==0; green:item.checkStatus==1"></i></div> </template> </div>');
                    container.append(innerDom3);
                }
            }
        });

        model.widgetConfig({
            productTable: {
                $type: "table",
                showHeader: true,
                highlightCurrentItem: true,
                changeCurrentItem: true,

            }
        });
    });

}).call(this);

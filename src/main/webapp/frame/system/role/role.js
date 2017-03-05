/**
 * Created by carl.li on 2017/2/22.
 */
(function () {
    cola(function (model) {
        model.dataType({
            name: "Power",
            properties: {
                products: {
                    provider: {
                        url: "/service/product/?categoryId={{@id}}",
                        pageSize: 5
                    }
                }
            }
        });
        model.dataType({
            name: "Role",
            properties: {
                powers: {
                    dataType: "Power",
                    provider: {
                        url: "./service/category/",
                        pageSize: 20
                    }
                }
            }
        });

        model.describe("roles", {
            dataType: "Role",
            provider: {
                url: "./service/employee/",
                pageSize: 20,
                complete: function () {
                    // 生成序号
                    var serialNo = 1;
                    model.get("roles").each(function(role){
                        role.set("serialNo",serialNo++);
                    });
                }
            }
        });

        model.set("deletedArrayId",[]);
        model.action({
            selectedNodes: function (nodes, arrayNode) {
                $.each(nodes, function () {
                    var node = this;
                    if (node.get("checked")) {
                        arrayNode.push(node.get("data.powerName"));
                    }
                    var children = node.get("children");
                    if (children) {
                        model.action.selectedNodes(children, arrayNode);
                    }
                });
            },
            savePower: function () {
                var nodes = cola.widget("powerTree").getItems();
                var arrayNode = [];
                model.action.selectedNodes(nodes,arrayNode);
                if (arrayNode.length > 0) {
                    cola.alert("选中的节点有："+arrayNode);
                } else {
                    cola.alert("请选择树节点！");
                }
            },
            refreshPower: function () {
                // model.flush("powers");
                cola.alert("刷新成功!");
            },
            savePowerDetail: function () {
                var addArray = [];
                model.get("powerDetails").each(function (powerDetail) {
                    var detailState = powerDetail.state;
                    if (detailState =="new") {
                        addArray.push(powerDetail);
                    }
                });

                var powerDetailTable, powerDetailTableDom;
                powerDetailTable = cola.widget("powerDetailTable");
                powerDetailTableDom = powerDetailTable.getDom();
                $fly(powerDetailTableDom).find(".table.item.default .current-property").removeClass("focus");

                cola.alert("新增数据:"+addArray+"; 删除数据:"+model.get("deletedArrayId").toJSON()+"<br><br>保存成功!");
            },
            addPowerDetail: function () {
                var powerDetailTable, powerDetailTableDom;
                powerDetailTable = cola.widget("powerDetailTable");
                powerDetailTableDom = powerDetailTable.getDom();

                model.get("powerDetails").insert({propName:"",propValue:""});
                powerDetailTable.set("currentItem", model.get("powerDetails").last());

                $fly(powerDetailTableDom).find(".table.item.default.current .current-property").addClass("focus");
            },
            deletePowerDetail: function (powerDetail) {
                debugger;
                model.get("deletedArrayId").insert({id:powerDetail.get("id")});
                powerDetail.remove();
            }
        });

        model.widgetConfig({
            roleTable: {
                $type: "table",
                bind: "role in roles",
                showHeader: true,
                changeCurrentItem: true,
                highlightCurrentItem: true,
                currentPageOnly: true,
                columns: [{
                    caption: "序号",
                    bind: ".serialNo"
                },{
                    caption: "角色编码",
                    bind: ".id"
                },{
                    caption: "角色名称",
                    bind: ".lastName"
                }],
                itemClick: function (self, arg) {
                    // 拿到当前行id,根据id获取后台功能数据
                    //var roleId = self.get("currentItem").get("roleCode");
                }
            },
            powerTree: {
                $type: "tree",
                highlightCurrentItem: true,
                height: "100%",
                bind: {
                    checkedProperty: "checked",
                    expanded: false,
                    valueProperty: "id",
                    textProperty: "categoryName",
                    expression: "power in roles.powers",
                    child: {
                        recursive: true,
                        checkedProperty: "checked",
                        valueProperty: "id",
                        textProperty: "productName",
                        expression: "power in power.products"
                    }
                },
                itemClick: function (self, arg) {
                    // 拿到当前树节点数据
                    var currentData, nodeVal, status;
                    currentData = self.get("currentNode.data");
                    nodeVal = currentData.get("productName");
                    status = currentData.get("categoryId");
                    debugger;
                    if (cola.defaultAction.isEmpty(status)) {
                        model.set("powerDetails","");
                        return;
                    }

                    // 模拟数据的加载
                    //model.set("powerId", currentData.get("id"));
                    // model.flush("powerDetails");
                    model.set("powerDetails",[{serialNo:"1",id:"p001",propName:nodeVal+"机构",propValue:nodeVal+"上海"},
                        {serialNo:"2",id:"p002",propName:nodeVal+"等级",propValue:nodeVal+"S级"}]);
                },
            },
            powerDetailTable: {
                $type: "table",
                bind: "powerDetail in powerDetails",
                showHeader: true,
                highlightCurrentItem: true,
                currentPageOnly: true,
                changCurrentItem: true,
                columns: [{
                    caption: "属性名",
                    /*bind: ".propName"*/
                    template: "propNameColumn"
                },{
                    caption: "属性值",
                    /*bind: ".propValue"*/
                    template: "propValueColumn"
                },{
                    caption: "操作",
                    template: "operation"
                }],
                renderCell: function (self, arg) {
                    var caption;
                    caption = arg.column.get("caption");
                    if (caption === "属性名" || caption === "属性值") {
                        $(arg.dom).addClass("current-property");
                    }

                }
            }
        });
    });
}).call(this);
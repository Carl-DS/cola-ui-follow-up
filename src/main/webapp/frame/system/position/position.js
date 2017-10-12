(function () {
    cola(function (model) {
        model.dataType({
            name: "Position",
            properties: {
                name: {
                    validators: ["required"]
                },
                desc: {
                    $type: 'string'
                }
            }
        });
        model.describe("positions", {
            dataType: "Position",
            provider: {
                url: "./service/frame/position",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    debugger;
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.contain = encodeURI(contain);
                    }
                }
            }
        });
        model.describe("editItem", "Position");

        model.action({
            uuid:function () {
                return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
                    return v.toString(16);
                });
            },
            search: function () {
                debugger;
                model.flush("positions");
            },
            enterSearch: function () {
                var keycode = event.keyCode==null?event.which:event.keyCode;
                if (keycode===13) { // 回车查询
                    model.action.search();
                }
            },
            editPosition: function (position) {
                debugger;
                if (position.dataType) { // 修改
                    model.set("editItem", position.toJSON());
                } else { // 新增
                    model.set("editItem", {
                        id: model.action.uuid(),
                        companyId: "bstek02"
                    });
                    model.get("editItem").setState("new");
                }
                return cola.widget("addPositionSidebar").show();
            },
            deletePosition: function (position) {
                debugger;
                cola.confirm("确认删除吗?",{
                    title: "消息提示",
                    level: "info",
                    onApprove: function () { // 确认时触发的回调
                        position.remove();
                        $.ajax("./service/frame/position/"+position.get("id"), {
                            type: "DELETE",
                            success: function () {
                                debugger;
                            }
                        })
                    }
                });
            },
            cancel: function () {
                model.set("editItem", {});
                return cola.widget("addPositionSidebar").hide();
            },
            savePosition: function () {
                debugger;
                var position, state, data;
                position = model.get("editItem");
                state = position.state;
                if (position.validate()) {
                    data = position.toJSON();
                    return $.ajax("./service/frame/position", {
                        data: JSON.stringify(data),
                        type: state==="new" ? "POST" : "PUT",
                        contentType: "application/json",
                        success: function () {
                            debugger;
                            model.flush("positions");
                            model.get("editItem").setState("none");
                            cola.widget("addPositionSidebar").hide();
                        }
                    });
                }
            }
        });
        model.widgetConfig({
            addPositionSidebar: {
                $type: "sidebar",
                size: "60%",
                direction: "bottom",
                modalOpacity: 0.3,
                dimmerClose: false
            },
            positionTable: {
                $type: "table",
                bind: "position in positions",
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "岗位名称"
                    }, {
                        bind: ".desc",
                        caption: "岗位描述"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            }
        });

    });
}).call(this);

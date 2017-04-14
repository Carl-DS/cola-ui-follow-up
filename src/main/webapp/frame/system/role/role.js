(function () {
    cola(function (model) {
        model.dataType({
            name: "Role",
            properties: {
                name: {
                    validators: ["required"]
                },
                desc: {
                    $type: 'string'
                }
            }
        });
        model.describe("roles", {
            dataType: "Role",
            provider: {
                url: "/service/frame/role/",
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
        model.describe("editItem", "Role");

        model.action({
            uuid:function () {
                return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
                    return v.toString(16);
                });
            },
            search: function () {
                debugger;
                model.flush("roles");
            },
            enterSearch: function () {
                var keycode = event.keyCode==null?event.which:event.keyCode;
                if (keycode===13) { // 回车查询
                    model.action.search();
                }
            },
            editRole: function (role) {
                debugger;
                if (role.dataType) { // 修改
                    model.set("editItem", role.toJSON());
                } else { // 新增
                    model.set("editItem", {
                        id: model.action.uuid(),
                        companyId: "bstek02"
                    });
                    model.get("editItem").setState("new");
                }
                return cola.widget("addGroupSidebar").show();
            },
            deleteRole: function (role) {
                debugger;
                cola.confirm("确认删除吗?",{
                    title: "消息提示",
                    level: "info",
                    onApprove: function () { // 确认时触发的回调
                        role.remove();
                        $.ajax("./service/frame/role/"+role.get("id")+"/", {
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
                return cola.widget("addGroupSidebar").hide();
            },
            saveRole: function () {
                debugger;
                var role, state, data;
                role = model.get("editItem");
                state = role.state;
                if (role.validate()) {
                    data = role.toJSON();
                    return $.ajax("./service/frame/role/", {
                        data: JSON.stringify(data),
                        type: state==="new" ? "POST" : "PUT",
                        contentType: "application/json",
                        success: function () {
                            debugger;
                            model.flush("roles");
                            model.get("editItem").setState("none");
                            cola.widget("addGroupSidebar").hide();
                        }
                    });
                }
            }
        });
        model.widgetConfig({
            addGroupSidebar: {
                $type: "sidebar",
                size: "60%",
                direction: "bottom",
                modalOpacity: 0.3,
                dimmerClose: false
            },
            roleTable: {
                $type: "table",
                bind: "role in roles",
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "角色名"
                    }, {
                        bind: ".desc",
                        caption: "描述"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            }
        });

    });
}).call(this);

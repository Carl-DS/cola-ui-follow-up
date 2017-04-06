(function () {
    cola(function (model) {
        model.dataType({
            name: "MessageTemplate",
            properties: {
                name: {
                    validators: ["required"]
                },
                type: {
                    validators: ["required"]
                },
                content: {
                    validators: ["required"],
                    $type: 'string'
                }
            }
        });
        model.describe("messageTemplates", {
            dataType: "MessageTemplate",
            provider: {
                url: "/service/frame/messageTemplate/",
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
        model.describe("editItem", "MessageTemplate");

        model.action({
            uuid:function () {
                return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
                    return v.toString(16);
                });
            },
            search: function () {
                debugger;
                model.flush("messageTemplates");
            },
            enterSearch: function () {
                var keycode = event.keyCode==null?event.which:event.keyCode;
                if (keycode===13) { // 回车查询
                    model.action.search();
                }
            },
            editMessageTemplate: function (messageTemplate) {
                debugger;
                if (messageTemplate.dataType) { // 修改
                    model.set("editItem", messageTemplate.toJSON());
                } else { // 新增
                    model.set("editItem", {
                        id: model.action.uuid(),
                        companyId: "bstek02"
                    });
                    model.get("editItem").setState("new");
                }
                return cola.widget("addMessageTemplateSidebar").show();
            },
            deleteMessageTemplate: function (messageTemplate) {
                debugger;
                cola.confirm("确认删除吗?",{
                    title: "消息提示",
                    level: "info",
                    onApprove: function () { // 确认时触发的回调
                        messageTemplate.remove();
                        $.ajax("./service/frame/messageTemplate/"+messageTemplate.get("id")+"/", {
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
                return cola.widget("addMessageTemplateSidebar").hide();
            },
            saveMessageTemplate: function () {
                debugger;
                var messageTemplate, state, data;
                messageTemplate = model.get("editItem");
                state = messageTemplate.state;
                if (messageTemplate.validate()) {
                    data = messageTemplate.toJSON();
                    return $.ajax("./service/frame/messageTemplate/", {
                        data: JSON.stringify(data),
                        type: state==="new" ? "POST" : "PUT",
                        contentType: "application/json",
                        success: function () {
                            debugger;
                            model.flush("messageTemplates");
                            model.get("editItem").setState("none");
                            cola.widget("addMessageTemplateSidebar").hide();
                        }
                    });
                }
            }
        });
        model.widgetConfig({
            addMessageTemplateSidebar: {
                $type: "sidebar",
                size: "60%",
                direction: "bottom",
                modalOpacity: 0.3,
                dimmerClose: false
            },
            messageTemplateTable: {
                $type: "table",
                bind: "messageTemplate in messageTemplates",
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "模板名"
                    }, {
                        bind: ".type",
                        caption: "类型"
                    }, {
                        bind: ".content",
                        caption: "内容"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            }
        });

    });
}).call(this);

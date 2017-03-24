(function () {
    cola(function (model) {
        model.dataType({
            name: "User",
            properties: {
                username: {
                    validators: ["required", {
                        $type: "custom",
                        async: true,
                        message: "该用户名已被使用",
                        func: function (value, callback) {
                            debugger;
                            $.ajax({
                                url: "/service/frame/user/check",
                                data: {username:value},
                                success: function(message) {
                                    debugger;
                                    cola.callback(callback, true, message);
                                },
                                error: function(xhr, status, ex) {
                                    cola.callback(callback, false, ex);
                                }
                            })
                        }
                    }],
                },
                cname: {
                    validators: ["required"]
                },
                ename: {
                    validators: ["required"]
                },
                male: {
                    dataType: "boolean",
                    validators: ["required"]
                },
                administrator: {
                    defaultValue: false, // disabled
                    validators: ["required"]
                },
                mobile: {
                    validators: ["required"]
                },
                email: {
                    validators: [{
                        $type: "email",
                        message: "格式错误"
                    }]
                },
                password: {
                    validators: ["required", {
                        $type: "custom",
                        message: "应为8-30位字母数字符号组合",
                        func:function(value) {
                            if(value && value.length>20){
                                return true;
                            }else{
                                var reg = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,20}');
                                var result =reg.test(value);
                                if(result){
                                    return true;
                                }else{
                                    return false;
                                }
                            }
                        }
                    }]
                }
            }
        });

        model.describe("users", {
            dataType: "User",
            provider: {
                url: "/service/frame/user/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    debugger;
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        arg.options.data.contain = contain;
                    }
                }
            }
        });
        model.describe("editItem", "User");

        model.action({
            search: function () {
                debugger;
                model.flush("users");
            },
            enterSearch: function () {
                var keycode = event.keyCode==null?event.which:event.keyCode;
                if (keycode===13) { // 回车查询
                    model.action.search();
                }
            },
            editUser: function (user) {
                debugger;
                if (user.dataType) { // 修改
                    model.set("editItem", user.toJSON());
                } else { // 新增
                    model.set("editItem", {
                        male: true,
                        administrator: false,
                        companyId: "bstek02",
                        salt: 1
                    });
                    model.get("editItem").setState("new");
                }
                return cola.widget("addUserSidebar").show();
            },
            deleteUser: function (user) {
                debugger;
                cola.confirm("确认删除吗?",{
                    title: "消息提示",
                    level: "info",
                    onApprove: function () { // 确认时触发的回调
                        user.remove();
                        $.ajax("./service/frame/user/"+user.get("username")+"/", {
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
                return cola.widget("addUserSidebar").hide();
            },
            userSave: function () {
                debugger;
                var user, state, data;
                user = model.get("editItem");
                state = user.state;
                if (user.validate()) {
                    data = user.toJSON();
                    return $.ajax("./service/frame/user/", {
                        data: JSON.stringify(data),
                        type: state==="new" ? "POST" : "PUT",
                        contentType: "application/json",
                        success: function () {
                            debugger;
                            model.flush("users");
                            model.get("editItem").setState("none");
                            cola.widget("addUserSidebar").hide();
                        }
                    });
                }
            }
        });
        model.widgetConfig({
            addUserSidebar: {
                $type: "sidebar",
                size: "60%",
                direction: "right",
                modalOpacity: 0.3,
                dimmerClose: false
            },
            genderRadioGroup: {
                items: [{label: "男", value: true}, {label: "女", value: false}]
            },
            adminRadioGroup: {
                items: [{label: "是", value: true}, {label: "否", value: false}]
            },
            userTable: {
                $type: "table",
                bind: "user in users",
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".username",
                        caption: "用户名"
                    }, {
                        bind: ".cname",
                        caption: "中文名"
                    }, {
                        bind: ".ename",
                        caption: "英文名"
                    }, {
                        bind: "user.male?'男':'女'",
                        caption: "性别"
                    }, {
                        bind: "user.administrator?'是':'否'",
                        caption: "是否管理员"
                    }, {
                        bind: ".mobile",
                        caption: "手机"
                    }, {
                        bind: ".email",
                        caption: "邮箱"
                    }, {
                        bind: "user.enabled?'是':'否'",
                        caption: "是否可用"
                    }, {
                        bind: ".dept",
                        caption: "岗位"
                    }, {
                        bind: ".component",
                        caption: "部门"
                    }, {
                        bind: "formatDate(user.birthday,'yyyy-MM-dd')",
                        caption: "出生日期"
                    }, {
                        bind: ".address",
                        caption: "地址"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            }
        });

    });
}).call(this);

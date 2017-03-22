(function () {
    cola(function (model) {
        model.dataType({
            name: "User",
            properties: {
                username: {
                    validators: ["required"]
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
                    validators: ["required"]
                }
            }
        });

        model.describe("users", {
            dataType: "User",
            provider: {
                url: "/service/frame/user/",
                pageSize: 2
            }
        });
        model.describe("editItem", "User");

        model.action({
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
                user.remove();
                $.ajax("./service/frame/user/"+user.get("username")+"/", {
                    type: "DELETE",
                    success: function () {
                        debugger;
                    }
                })
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
                modalOpacity: 0.3
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

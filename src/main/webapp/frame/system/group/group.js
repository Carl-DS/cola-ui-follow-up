/**
 * Created by carl.li on 2017/2/22.
 */
(function () {
    cola(function (model) {
        model.dataType({
            name: "Group",
            properties: {
                name: {
                    validators: ["required"]
                },
                desc: {
                    $type: 'string'
                }
            }
        });
        model.describe("groups", {
            dataType: "Group",
            provider: {
                url: "/service/frame/group/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.contain = encodeURI(contain);
                    }
                }
            }
        });
        // 以同步的方式先将groups加载出来,
        // 便于其它数据获取groupId
        model.get("groups", "sync");
        model.describe("editItem", "Group");

        // 待选择的用户
        model.describe("users", {
            provider: {
                url: "/service/frame/user/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.contain = encodeURI(contain);
                    }
                }
            }
        });
        // 声明群组成员用户的EntityList
        model.describe("groupUsers", {
            provider: {
                url: "/service/frame/user/groupusers/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    debugger;
                    var groupId = cola.widget("groupTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(groupId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.groupId = encodeURI(groupId);
                    }
                }
            }
        });
        // 声明已选择的用户的EntityList
        model.set("selectedUsers", []);

        // 待选择的岗位
        model.describe("positions", {
            provider: {
                url: "/service/frame/position/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.contain = encodeURI(contain);
                    }
                }
            }
        });
        // 声明群组成员岗位的EntityList
        model.describe("groupPositions", {
            provider: {
                url: "/service/frame/position/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var contain = model.get("contain");
                    if (cola.defaultAction.isNotEmpty(contain)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.contain = encodeURI(contain);
                    }
                }
            }
        });
        // 声明已选择的岗位的EntityList
        model.set("selectedPositions", []);

        // 待选择的部门
        model.describe("depts", {
            provider: {
                url: "service/frame/dept/depts"
            }
        });
        // 声明群组成员部门的EntityList
        model.describe("groupDepts", {
            provider: {
                url: "service/frame/dept/depts"
            }
        });
        // 声明已选择的岗位的EntityList
        model.set("selectedDepts", []);



        model.action({
            uuid:function () {
                return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
                    return v.toString(16);
                });
            },
            editGroup: function (group) {
                debugger;
                if (group.dataType) { // 修改
                    model.set("editItem", group.toJSON());
                } else { // 新增
                    model.set("editItem", {
                        id: model.action.uuid(),
                        companyId: "bstek02"
                    });
                    model.get("editItem").setState("new");
                }
                return cola.widget("groupSidebar").show();
            },
            deleteGroup: function (group) {
                debugger;
                cola.confirm("确认删除吗?",{
                    title: "消息提示",
                    level: "info",
                    onApprove: function () { // 确认时触发的回调
                        group.remove();
                        $.ajax("./service/frame/group/"+group.get("id")+"/", {
                            type: "DELETE",
                            success: function () {
                                debugger;
                            }
                        })
                    }
                });
            },
            cancel: function (type) {
                debugger;
                model.set("editItem", {});
                var widgetId = type + "Sidebar";
                return cola.widget(widgetId).hide();
            },
            saveGroup: function () {
                debugger;
                var group, state, data;
                group = model.get("editItem");
                state = group.state;
                if (group.validate()) {
                    data = group.toJSON();
                    return $.ajax("./service/frame/group/", {
                        data: JSON.stringify(data),
                        type: state==="new" ? "POST" : "PUT",
                        contentType: "application/json",
                        success: function () {
                            debugger;
                            model.flush("groups");
                            model.get("editItem").setState("none");
                            cola.widget("groupSidebar").hide();
                        }
                    });
                }
            },
            editUser: function () {
                // 刷新待选用户
                model.flush("users");
                // 重置已选择用户
                model.set("selectedUsers", []);
                cola.widget("userSidebar").show();
            },
            editPosition: function () {
                // 刷新待选用户
                model.flush("positions");
                // 重置已选择用户
                model.set("selectedPositions", []);
                cola.widget("positionSidebar").show();
            },
            editDept: function () {
                // 刷新待选用户
                model.flush("depts");
                // 重置已选择用户
                model.set("selectedDepts", []);
                cola.widget("deptSidebar").show();
            },
            deleteUser: function(user) {
                user.remove();
            },
            deletePosition: function(position) {
                position.remove();
            },
            deleteDept: function(dept) {
                dept.remove();
            },
            delete: function (model) {
                debugger;
                var data = model.toJSON();
                data.groupId = cola.widget("groupTable").get("currentItem").get("id");
                $.ajax("./service/frame/groupmember/username/", {
                    type: "GET",
                    data: {"groupId": data.groupId, "username": data.username},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.remove();
                    }
                });
            },
            selectUser: function() {
                debugger;
                var currentUser = cola.widget("userTable").get("currentItem");
                var groupId = cola.widget("groupTable").get("currentItem").get("id");
                var username = currentUser.get("username");
                $.ajax("./service/frame/groupmember/checksame/", {
                    type: "GET",
                    data: {"groupId": groupId, "username": username},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        if (self.length > 0) {
                            cola.alert("当前选择的[" + currentUser.get("cname") + "]已添加, 请重新选择");
                        } else {
                            model.get("selectedUsers").insert(currentUser.toJSON());
                            currentUser.remove();
                        }
                    }
                });
            },
            removeUser: function () {
                var currentSelectedUser = cola.widget("selectedUserTable").get("currentItem");
                model.get("users").insert(currentSelectedUser.toJSON());
                currentSelectedUser.remove();
            },
            saveGroupUser: function () {
                debugger;
                var selectedUsers = model.get("selectedUsers");
                var groupUserIds = [];
                if (selectedUsers.entityCount > 0) {
                    selectedUsers.each(function (selectedUser) {
                        groupUserIds.push(selectedUser.get("username"));
                    });
                }
                var currentGroupId = cola.widget("groupTable").get("currentItem").get("id");
                var data = {"groupId":currentGroupId,"groupUserIds":groupUserIds};
                $.ajax("./service/frame/groupmember/", {
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.flush("groupUsers");
                        cola.widget("userSidebar").hide();
                    }
                })
            }
        });

        model.widgetConfig({
            groupTable: {
                $type: "table",
                bind: "group in groups",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "名称"
                    }, {
                        bind: ".desc",
                        caption: "描述"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ],
                itemClick: function (self, arg) {
                    // 拿到当前行id,根据id获取后台功能数据
                    var groupId = self.get("currentItem").get("id");
                    if (groupId!=model.get("groupId")) {
                        model.set("groupId", groupId);
                        model.flush("groupUsers");
                    }
                }
            },
            groupUserTable: {
                $type: "table",
                bind: "groupUser in groupUsers",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".username",
                        caption: "用户名",
                        visible: true // 是否可见
                    }, {
                        bind: ".cname",
                        caption: "中文名"
                    }, {
                        bind: ".ename",
                        caption: "英文名"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            userTable: {
                $type: "table",
                bind: "user in users",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".username",
                        caption: "用户名",
                        visible: true // 是否可见
                    }, {
                        bind: ".cname",
                        caption: "中文名"
                    }, {
                        bind: ".ename",
                        caption: "英文名"
                    }
                ]
            },
            selectedUserTable: {
                $type: "table",
                bind: "selectedUser in selectedUsers",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".username",
                        caption: "用户名",
                        visible: true // 是否可见
                    }, {
                        bind: ".cname",
                        caption: "中文名"
                    }, {
                        bind: ".ename",
                        caption: "英文名"
                    }
                ]
            },
            groupPositionTable: {
                $type: "table",
                bind: "groupPosition in groupPositions",
                changeCurrentItem: true,
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "岗位名称"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            positionTable: {
                $type: "table",
                bind: "position in positions",
                changeCurrentItem: true,
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "岗位名称"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            selectedPositionTable: {
                $type: "table",
                bind: "selectedPosition in selectedPositions",
                changeCurrentItem: true,
                showHeader: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "岗位名称"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            groupDeptTable: {
                $type: "table",
                bind: "dept in depts",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "部门"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            deptTable: {
                $type: "table",
                bind: "dept in depts",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "部门"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            selectedDeptTable: {
                $type: "table",
                bind: "dept in depts",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "部门"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            }

        });
    });
}).call(this);
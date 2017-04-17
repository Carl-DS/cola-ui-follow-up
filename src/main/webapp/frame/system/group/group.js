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
                url: "/service/frame/position/grouppositions/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var groupId = cola.widget("groupTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(groupId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.groupId = encodeURI(groupId);
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
                url: "service/frame/dept/groupdepts/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var groupId = cola.widget("groupTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(groupId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.groupId = encodeURI(groupId);
                    }
                }
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
                cola.confirm("确认删除吗?",{
                    title: "消息提示",
                    level: "info",
                    onApprove: function () { // 确认时触发的回调
                        $.ajax("./service/frame/group/"+group.get("id")+"/", {
                            type: "DELETE",
                            success: function () {
                                group.remove();
                            }
                        })
                    }
                });
            },
            saveGroup: function () {
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
                            model.flush("groups");
                            model.get("editItem").setState("none");
                            cola.widget("groupSidebar").hide();
                        }
                    });
                }
            },
            cancel: function (type) {
                model.set("editItem", {});
                var widgetId = type + "Sidebar";
                return cola.widget(widgetId).hide();
            },
            editUser: function () {
                // 刷新待选用户
                model.flush("users");
                // 重置已选择用户
                model.set("selectedUsers", []);
                cola.widget("userSidebar").show();
            },
            editPosition: function () {
                // 刷新待选岗位
                model.flush("positions");
                // 重置已选择岗位
                model.set("selectedPositions", []);
                cola.widget("positionSidebar").show();
            },
            editDept: function () {
                cola.widget("deptSidebar").show();
            },
            deleteUser: function(model) {
                var data = model.toJSON();
                data.groupId = cola.widget("groupTable").get("currentItem").get("id");
                $.ajax("./service/frame/groupmember/user/", {
                    type: "GET",
                    data: {"groupId": data.groupId, "username": data.username},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.remove();
                    }
                });
            },
            deletePosition: function(model) {
                var data = model.toJSON();
                data.groupId = cola.widget("groupTable").get("currentItem").get("id");
                $.ajax("./service/frame/groupmember/position/", {
                    type: "GET",
                    data: {"groupId": data.groupId, "positionId": data.id},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.remove();
                    }
                });
            },
            deleteDept: function(model) {
                debugger;
                var data = model.toJSON();
                data.groupId = cola.widget("groupTable").get("currentItem").get("id");
                $.ajax("./service/frame/groupmember/dept/", {
                    type: "GET",
                    data: {"groupId": data.groupId, "deptId": data.id},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.remove();
                    }
                });
            },
            selectUser: function() {
                var currentUser = cola.widget("userTable").get("currentItem");
                var groupId = cola.widget("groupTable").get("currentItem").get("id");
                var username = currentUser.get("username");
                $.ajax("./service/frame/groupmember/checksame/user/", {
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
            selectPosition: function() {
                var groupId = cola.widget("groupTable").get("currentItem").get("id");
                var currentPosition = cola.widget("positionTable").get("currentItem");
                var positionId = currentPosition.get("id");
                $.ajax("./service/frame/groupmember/checksame/position/", {
                    type: "GET",
                    data: {"groupId": groupId, "positionId": positionId},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        if (self.length > 0) {
                            cola.alert("当前选择的[" + currentPosition.get("name") + "]已添加, 请重新选择");
                        } else {
                            model.get("selectedPositions").insert(currentPosition.toJSON());
                            currentPosition.remove();
                        }
                    }
                });
            },
            selectDept: function() {
                var groupId = cola.widget("groupTable").get("currentItem").get("id");
                var currentDept = cola.widget("deptTable").get("currentItem");
                var deptId = currentDept.get("id");
                $.ajax("./service/frame/groupmember/checksame/dept/", {
                    type: "GET",
                    data: {"groupId": groupId, "deptId": deptId},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        if (self.length > 0) {
                            cola.alert("当前选择的[" + currentDept.get("name") + "]已添加, 请重新选择");
                        } else {
                            model.get("selectedPositions").insert(currentDept.toJSON());
                            // currentDept.remove();
                        }
                    }
                });
            },
            removeUser: function () {
                var currentSelectedUser = cola.widget("selectedUserTable").get("currentItem");
                model.get("users").insert(currentSelectedUser.toJSON());
                currentSelectedUser.remove();
            },
            removePosition: function () {
                var currentSelectedPosition = cola.widget("selectedPositionTable").get("currentItem");
                model.get("positions").insert(currentSelectedPosition.toJSON());
                currentSelectedPosition.remove();
            },
            saveGroupUser: function () {
                var selectedUsers = model.get("selectedUsers");
                var groupUserIds = [];
                if (selectedUsers.entityCount > 0) {
                    selectedUsers.each(function (selectedUser) {
                        groupUserIds.push(selectedUser.get("username"));
                    });
                }
                var currentGroupId = cola.widget("groupTable").get("currentItem").get("id");
                var data = {"groupId":currentGroupId,"groupUserIds":groupUserIds};
                $.ajax("./service/frame/groupmember/user/", {
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.flush("groupUsers");
                        cola.widget("userSidebar").hide();
                    }
                })
            },
            saveGroupPosition: function () {
                var selectedPositions = model.get("selectedPositions");
                var groupPositionIds = [];
                if (selectedPositions.entityCount > 0) {
                    selectedPositions.each(function (selectedPosition) {
                        groupPositionIds.push(selectedPosition.get("id"));
                    });
                }
                var currentGroupId = cola.widget("groupTable").get("currentItem").get("id");
                var data = {"groupId":currentGroupId,"groupPositionIds":groupPositionIds};
                $.ajax("./service/frame/groupmember/position/", {
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.flush("groupPositions");
                        cola.widget("positionSidebar").hide();
                    }
                })
            },
            saveGroupDept: function () {
                debugger;
                var selectedDepts = model.get("selectedDepts");
                var groupDeptIds = [];
                if (selectedDepts.entityCount > 0) {
                    selectedDepts.each(function (selectedDept) {
                        groupDeptIds.push(selectedDept.get("id"));
                    });
                }
                var currentGroupId = cola.widget("groupTable").get("currentItem").get("id");
                var data = {"groupId":currentGroupId,"groupDeptIds":groupDeptIds};
                $.ajax("./service/frame/groupmember/dept/", {
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.flush("groupDepts");
                        model.set("selectedDepts", []);
                        cola.widget("deptSidebar").hide();
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
                        model.flush("groupPositions");
                        model.flush("groupDepts");
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
                    }
                ]
            },
            groupDeptTable: {
                $type: "table",
                bind: "groupDept in groupDepts",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "部门名称"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
            deptTree: {
                $type: "tree",
                lazyRenderChildNodes: true,
                autoExpand: false,
                autoCollapse: false,
                highlightCurrentItem: true,
                bind: {
                    expression: "dept in depts",
                    expanded: false,
                    checkedProperty: "checked",
                    valueProperty: "id",
                    textProperty: "name",
                    child: {
                        recursive: true,
                        expression: "dept in sort(dept.depts, 'order')",
                        expanded: false,
                        checkedProperty: "checked",
                        valueProperty: "id",
                        textProperty: "name"
                    }
                },
                itemClick: function (self, arg) {
                    debugger;
                    var isSame = false;
                    var currentData = self.get("currentNode").get("data");
                    if (cola.defaultAction.isNotEmpty(currentData)) {
                        if (currentData.get("checked")) {
                            var groupId = cola.widget("groupTable").get("currentItem").get("id");
                            var deptId = currentData.get("id");

                            var selectedDepts = model.get("selectedDepts");
                            if (selectedDepts.entityCount > 0) {
                                selectedDepts.each(function (selectedDept) {
                                    if (deptId === selectedDept.get("id")) {
                                        return isSame = true;
                                    }
                                });
                            }
                            if (!isSame) {
                                $.ajax("./service/frame/groupmember/checksame/dept/", {
                                    type: "GET",
                                    data: {"groupId": groupId, "deptId": deptId},
                                    contentType: "application/json; charset=utf-8",
                                    success: function (self, arg) {
                                        if (self.length > 0) {
                                            currentData.set("checked", false);
                                            cola.alert("当前选择的[" + currentData.get("name") + "]已添加, 请重新选择");
                                        } else {
                                            model.get("selectedDepts").insert(currentData.toJSON());
                                        }
                                    }
                                });
                            }
                        }

                    }
                }
            }

        });
    });
}).call(this);
/**
 * Created by carl.li on 2017/2/22.
 */
(function () {
    cola(function (model) {
        model.describe("roles", {
            provider: {
                url: "/service/frame/role/",
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
        // 以同步的方式先将roles加载出来,
        // 便于其它数据获取roleId
        model.get("roles", "sync");

        // 声明角色成员用户的EntityList
        model.describe("roleUsers", {
            provider: {
                url: "/service/frame/user/roleusers/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var roleId = cola.widget("roleTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(roleId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.roleId = encodeURI(roleId);
                    }
                }
            }
        });
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
        // 声明已选择的用户的EntityList
        model.set("selectedUsers", []);

        // 声明角色成员岗位的EntityList
        model.describe("rolePositions", {
            provider: {
                url: "/service/frame/position/rolepositions/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var roleId = cola.widget("roleTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(roleId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.roleId = encodeURI(roleId);
                    }
                }
            }
        });
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
        // 声明已选择的岗位的EntityList
        model.set("selectedPositions", []);

        // 声明角色成员部门的EntityList
        model.describe("roleDepts", {
            provider: {
                url: "service/frame/dept/roledepts/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var roleId = cola.widget("roleTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(roleId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.roleId = encodeURI(roleId);
                    }
                }
            }
        });
        // 待选择的部门
        model.describe("depts", {
            provider: {
                url: "service/frame/dept/depts"
            }
        });
        // 声明已选择的岗位的EntityList
        model.set("selectedDepts", []);

        // 声明角色成员群组的EntityList
        model.describe("roleGroups", {
            provider: {
                url: "service/frame/group/rolegroups/",
                pageSize: 2,
                beforeSend: function (self, arg) {
                    var roleId = cola.widget("roleTable").get("currentItem").get("id");
                    if (cola.defaultAction.isNotEmpty(roleId)) {
                        // 使用encodeURI() 为了解决GET下传递中文出现的乱码
                        arg.options.data.roleId = encodeURI(roleId);
                    }
                }
            }
        });
        // 待选择的群组
        model.describe("groups", {
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
        // 声明已选择的群组的EntityList
        model.set("selectedGroups", []);

        model.action({
            refresh: function () {
                cola.confirm("真的要刷新权限缓存信息吗?",{
                    title: "消息提示",
                    level: "info",
                    onApprove: function () { // 确认时触发的回调
                        model.flush("roles");
                        cola.alert("刷新成功!");
                    }
                });
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
                // 重置已选择岗位
                model.set("selectedDepts", []);
                cola.widget("deptSidebar").show();
            },
            editGroup: function () {
                // 刷新待选群组
                model.flush("groups");
                // 重置已选择群组
                model.set("selectedGroups", []);
                cola.widget("groupSidebar").show();
            },
            deleteUser: function(model) {
                var data = model.toJSON();
                data.roleId = cola.widget("roleTable").get("currentItem").get("id");
                $.ajax("./service/frame/rolemember/user/", {
                    type: "GET",
                    data: {"roleId": data.roleId, "username": data.username},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.remove();
                    }
                });
            },
            deletePosition: function(model) {
                var data = model.toJSON();
                data.roleId = cola.widget("roleTable").get("currentItem").get("id");
                $.ajax("./service/frame/rolemember/position/", {
                    type: "GET",
                    data: {"roleId": data.roleId, "positionId": data.id},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.remove();
                    }
                });
            },
            deleteDept: function(model) {
                var data = model.toJSON();
                data.roleId = cola.widget("roleTable").get("currentItem").get("id");
                $.ajax("./service/frame/rolemember/dept/", {
                    type: "GET",
                    data: {"roleId": data.roleId, "deptId": data.id},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.remove();
                    }
                });
            },
            deleteGroup: function(model) {
                var data = model.toJSON();
                data.roleId = cola.widget("roleTable").get("currentItem").get("id");
                $.ajax("./service/frame/rolemember/group/", {
                    type: "GET",
                    data: {"roleId": data.roleId, "groupId": data.id},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.remove();
                    }
                });
            },
            selectUser: function() {
                var roleId = cola.widget("roleTable").get("currentItem").get("id");
                var currentUser = cola.widget("userTable").get("currentItem");
                var username = currentUser.get("username");
                $.ajax("./service/frame/rolemember/checksame/user/", {
                    type: "GET",
                    data: {"roleId": roleId, "username": username},
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
                var roleId = cola.widget("roleTable").get("currentItem").get("id");
                var currentPosition = cola.widget("positionTable").get("currentItem");
                var positionId = currentPosition.get("id");
                $.ajax("./service/frame/rolemember/checksame/position/", {
                    type: "GET",
                    data: {"roleId": roleId, "positionId": positionId},
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
                var roleId = cola.widget("roleTable").get("currentItem").get("id");
                var currentDept = cola.widget("deptTable").get("currentItem");
                var deptId = currentDept.get("id");
                $.ajax("./service/frame/rolemember/checksame/dept/", {
                    type: "GET",
                    data: {"roleId": roleId, "deptId": deptId},
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
            selectGroup: function() {
                var roleId = cola.widget("roleTable").get("currentItem").get("id");
                var currentGroup = cola.widget("groupTable").get("currentItem");
                var groupId = currentGroup.get("id");
                $.ajax("./service/frame/rolemember/checksame/group/", {
                    type: "GET",
                    data: {"roleId": roleId, "groupId": groupId},
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        if (self.length > 0) {
                            cola.alert("当前选择的[" + currentGroup.get("name") + "]已添加, 请重新选择");
                        } else {
                            model.get("selectedGroups").insert(currentGroup.toJSON());
                            currentGroup.remove();
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
            removeGroup: function () {
                var currentSelectedGroup = cola.widget("selectedGroupTable").get("currentItem");
                model.get("groups").insert(currentSelectedGroup.toJSON());
                currentSelectedGroup.remove();
            },
            saveRoleUser: function () {
                debugger;
                var selectedUsers = model.get("selectedUsers");
                var roleUserIds = [];
                if (selectedUsers.entityCount > 0) {
                    selectedUsers.each(function (selectedUser) {
                        roleUserIds.push(selectedUser.get("username"));
                    });
                }
                var currentRoleId = cola.widget("roleTable").get("currentItem").get("id");
                var data = {"roleId":currentRoleId,"roleUserIds":roleUserIds};
                $.ajax("./service/frame/rolemember/user/", {
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.flush("roleUsers");
                        cola.widget("userSidebar").hide();
                    }
                })
            },
            saveRolePosition: function () {
                var selectedPositions = model.get("selectedPositions");
                var rolePositionIds = [];
                if (selectedPositions.entityCount > 0) {
                    selectedPositions.each(function (selectedPosition) {
                        rolePositionIds.push(selectedPosition.get("id"));
                    });
                }
                var currentRoleId = cola.widget("roleTable").get("currentItem").get("id");
                var data = {"roleId":currentRoleId,"rolePositionIds":rolePositionIds};
                $.ajax("./service/frame/rolemember/position/", {
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.flush("rolePositions");
                        cola.widget("positionSidebar").hide();
                    }
                })
            },
            saveRoleDept: function () {
                var selectedDepts = model.get("selectedDepts");
                var roleDeptIds = [];
                if (selectedDepts.entityCount > 0) {
                    selectedDepts.each(function (selectedDept) {
                        roleDeptIds.push(selectedDept.get("id"));
                    });
                }
                var currentRoleId = cola.widget("roleTable").get("currentItem").get("id");
                var data = {"roleId":currentRoleId,"roleDeptIds":roleDeptIds};
                $.ajax("./service/frame/rolemember/dept/", {
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.flush("roleDepts");
                        model.set("selectedDepts", []);
                        cola.widget("deptSidebar").hide();
                    }
                })
            },
            saveRoleGroup: function () {
                var selectedGroups = model.get("selectedGroups");
                var roleGroupIds = [];
                if (selectedGroups.entityCount > 0) {
                    selectedGroups.each(function (selectedGroup) {
                        roleGroupIds.push(selectedGroup.get("id"));
                    });
                }
                var currentRoleId = cola.widget("roleTable").get("currentItem").get("id");
                var data = {"roleId":currentRoleId,"roleGroupIds":roleGroupIds};
                $.ajax("./service/frame/rolemember/group/", {
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (self, arg) {
                        model.flush("roleGroups");
                        cola.widget("groupSidebar").hide();
                    }
                })
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
                    caption: "角色名称",
                    bind: ".name"
                },{
                    caption: "角色描述",
                    bind: ".desc"
                }],
                itemClick: function (self, arg) {
                    // 拿到当前行id,根据id获取后台功能数据
                    var roleId = self.get("currentItem").get("id");
                    if (roleId!=model.get("roleId")) {
                        model.set("roleId", roleId);
                        model.flush("roleUsers");
                        model.flush("rolePositions");
                        model.flush("roleDepts");
                        model.flush("roleGroups");
                    }
                }
            },
            roleUserTable: {
                $type: "table",
                bind: "roleUser in roleUsers",
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
            rolePositionTable: {
                $type: "table",
                bind: "rolePosition in rolePositions",
                showHeader: true,
                changeCurrentItem: true,
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
                showHeader: true,
                changeCurrentItem: true,
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
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "岗位名称"
                    }
                ]
            },
            roleDeptTable: {
                $type: "table",
                bind: "roleDept in roleDepts",
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
                            var roleId = cola.widget("roleTable").get("currentItem").get("id");
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
                                $.ajax("./service/frame/rolemember/checksame/dept/", {
                                    type: "GET",
                                    data: {"roleId": roleId, "deptId": deptId},
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
            },
            roleGroupTable: {
                $type: "table",
                bind: "roleGroup in roleGroups",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "群组名称"
                    }, {
                        caption: "操作",
                        template: "operation"
                    }
                ]
            },
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
                        caption: "群组名称"
                    }
                ]
            },
            selectedGroupTable: {
                $type: "table",
                bind: "selectedGroup in selectedGroups",
                showHeader: true,
                changeCurrentItem: true,
                currentPageOnly: true,
                highlightCurrentItem: true,
                columns: [
                    {
                        bind: ".name",
                        caption: "群组名称"
                    }
                ]
            }

        });
    });
}).call(this);
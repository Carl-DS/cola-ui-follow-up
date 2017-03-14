/**
 * Created by carl.li on 2017/2/16.
 */
(function () {
    cola(function (model) {
    model.set("category", {key:"tabOne",value:"饮料",closeable:"true",icon:"bitbucket"});

    model.action({
        addTab: function () {
            var tab, viewTab;
            tab = new cola.TabButton({
                content: {
                    // 内容也可以是一个iFrame等等
                    /*$type: "iFrame",
                    path: "http://localhost:7070/tab/tab.html"*/
                    tagName: "li",
                    content: {
                        tagName: "h1",
                        content: "饮料展示区",
                        style: "text-align:center"
                    }
                },
                icon: model.get("category.icon"),
                name: model.get("category.key"),
                closeable: model.get("category.closeable"),
                caption: model.get("category.value")
            });
            viewTab = cola.widget("viewTab");
            viewTab.addTab(tab);
            return viewTab.setCurrentTab(tab)
        },
        closeTab: function () {
            var keycode = event.keyCode==null?event.which:event.keyCode;
            var viewTab = cola.widget("viewTab");
            if (keycode==87) { // 快捷键w,关闭当前标签页
                viewTab.removeTab(viewTab.get("currentTab"));
            }
        }
    });

    });
}).call(this);
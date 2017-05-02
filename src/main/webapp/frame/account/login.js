(function () {
    cola(function (model) {
        var mainPath, showMessage, submit;
        model.describe({
            username: {
                validators: {
                    $type: "required",
                    message: ""
                }
            },
            password: {
                validators: {
                    $type: "required",
                    message: ""
                }
            }
        });
        mainPath = "" + (App.prop("mainView"));
        model.set("username", $.cookie("_username"), {
            path: "/"
        });
        showMessage = function (content) {
            return cola.widget("formSignIn").setMessages([
                {
                    type: "error",
                    text: content
                }
            ]);
        };
        submit = function () {
            debugger;
            var username = model.get("username");
            var password = model.get("password");
            model.set("principal", {
                "username": model.get("username"),
                "password": model.get("password")
            });
            cola.widget("containerSignIn").addClass("loading");
            return $.ajax({
                type: "POST",
                url: App.prop("service.login"),
                data: JSON.stringify(model.get("principal").toJSON()),
                contentType: "application/json; charset=utf-8"
            }).done(function (result) {
                debugger;
                cola.widget("containerSignIn").removeClass("loading");
                if (!result.type) {
                    showMessage(result.message);
                    return;
                }
                if (model.get("cacheInfo")) {
                    $.cookie("_username", model.get("username"), {
                        path: "/",
                        expires: 365
                    });
                }
                return window.location.href = mainPath;
            }).fail(function () {
                cola.widget("containerSignIn").removeClass("loading");
            });
        };
        return model.action({
            signIn: function () {
                var data;
                cola.widget("formSignIn").setMessages(null);
                data = model.get();
                if (data.validate()) {
                    return submit();
                } else {
                    return showMessage("用户名或密码不能为空！");
                }
            },
            autoLogin:function(){
                var keycode=event.keyCode==null?event.which:event.keyCode;
                if(keycode==13) {
                    model.action.signIn();
                }
            }
        });
    });

}).call(this);

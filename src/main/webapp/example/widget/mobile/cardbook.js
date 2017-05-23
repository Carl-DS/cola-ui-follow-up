/**
 * Created by carl.li on 2017/2/16.
 */
(function () {
    cola(function (model) {
        model.action({
            changeCard: function(self, arg) {
                var index = parseInt(self.get("userData"));
                cola.widget("card-book-demo").setCurrentIndex(index);
            }
        })
    });
}).call(this);
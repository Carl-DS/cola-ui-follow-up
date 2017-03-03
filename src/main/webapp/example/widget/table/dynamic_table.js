(function() {
  cola(function(model) {
    model.describe("products", {
      dataType: {
        name: "Product",
        properties: {
          name: {
            validators: ["required"]
          },
          price: {
            dataType: "number"
          }
        }
      },
      provider: {
        url: "/service/product/?categoryId=1",
        pageSize: 10
      }
    });
    model.get("products");

    model.action({
      blur: function(self, arg) {
        return self.get$Dom().closest(".product-price").removeClass("focus");
      },
      renderTable: function () {
        var productTable;
        productTable = new cola.Table({
          $type: "table",
          showHeader: true,
          bind: "item in products",
          highlightCurrentItem: true,
          currentPageOnly: true,
          height: "100%",
          columns: model.action.getColumns()
        });
        return productTable.appendTo($("#dynamicTable"));
      },
      getColumns: function () {
        if (cola.widget("productTable"))
          cola.widget("productTable").remove();
          return [
            {
              bind: ".id",
              caption: "产品编号"
            }, {
              caption: "产品名称",
              bind: ".productName"
            }, {
              bind: ".reorderLevel",
              caption: "订货量",
            }, {
              bind: ".quantityPerUnit",
              caption: "经营商"
            }
          ];
        }
    });
    return model.widgetConfig({
      productTable: {
        $type: "table",
        showHeader: true,
        bind: "item in products",
        highlightCurrentItem: true,
        currentPageOnly: true,
        height: "100%",
        columns: [
          {
            bind: ".id",
            caption: "产品编号"
          }, {
            caption: "产品名称",
            bind: ".productName"
          }, {
            bind: ".quantityPerUnit",
            caption: "经营商"
          }
        ]
      }
    });
  });

}).call(this);

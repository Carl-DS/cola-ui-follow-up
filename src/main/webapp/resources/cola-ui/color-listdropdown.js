(function() {
	var __hasProp = {}.hasOwnProperty,
		__extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

	cola.ColorGrid = (function(_super) {
		__extends(ColorGrid, _super);

		function ColorGrid() {
			return ColorGrid.__super__.constructor.apply(this, arguments);
		}

		ColorGrid.CLASS_NAME = "color-grid";

		ColorGrid.attributes = {
			bind: {
				refreshItems: true,
				setter: function(bindStr) {
					return this._bindSetter(bindStr);
				}
			},
			columns: {
				refreshItems: true,
				defaultValue: 4
			},
			highlightCurrentItem: {
				type: "boolean",
				defaultValue: true
			}
		};

		ColorGrid.TEMPLATES = {
			"default": {
				tagName: "li"
			},
			"color": {
				tagName: "div",
				"c-code": "$default.code"
			},
			label: {
				tagName: "div",
				"c-bind": "$default.label"
			}
		};

		ColorGrid.prototype._initDom = function(dom) {
			return ColorGrid.__super__._initDom.call(this, dom);
		};

		ColorGrid.prototype._doRefreshDom = function() {
			ColorGrid.__super__._doRefreshDom.call(this);
			if (this._doms) {
				return $(this._doms.itemsWrapper).addClass("small-block-grid-" + this._columns);
			}
		};

		ColorGrid.prototype._createNewItem = function(itemType, item) {
			var container, contentDom, itemDom, name, template, _i, _len, _ref;
			template = this._getTemplate(itemType);
			itemDom = this._cloneTemplate(template);
			$fly(itemDom).addClass("item " + itemType);
			itemDom._itemType = itemType;
			_ref = ["color", "label"];
			for (_i = 0, _len = _ref.length; _i < _len; _i++) {
				name = _ref[_i];
				template = this._getTemplate(name);
				contentDom = this._cloneTemplate(template, true);
				container = $.xCreate({
					tagName: "div",
					"class": name
				});
				if (name === "color") {
					contentDom.style.backgroundColor = item.get("code");
				}
				container.appendChild(contentDom);
				itemDom.appendChild(container);
			}
			if (!this._currentItem) {
				this._setCurrentNode(item);
			}
			return itemDom;
		};

		return ColorGrid;

	})(cola.AbstractList);

	cola.ColorEditor = (function(_super) {
		__extends(ColorEditor, _super);

		function ColorEditor() {
			return ColorEditor.__super__.constructor.apply(this, arguments);
		}

		ColorEditor.tagName = "c-coloreditor";

		ColorEditor.CLASS_NAME = "app-color input drop";

		ColorEditor.TEMPLATES = {
			"default": {
				tagName: "li",
				"c-bind": "$default"
			},
			"list": {
				tagName: "div",
				contextKey: "flexContent",
				content: {
					tagName: "div",
					contextKey: "list",
					"c-widget": "listView; columns:3",
					style: "height:100%;overflow:auto"
				}
			}
		};

		ColorEditor.prototype._doRefreshDom = function() {
			var value;
			ColorEditor.__super__._doRefreshDom.call(this);
			value = this._value;
			if (this._doms) {
				this._doms.iconDom.style.backgroundColor = value;
				this._doms.input.readOnly = true;
				return this._doms.input.style.borderColor = value;
			}
		};

		ColorEditor.prototype._initValueContent = function(valueContent, context) {
			var template;
			ColorEditor.__super__._initValueContent.call(this, valueContent, context);
			if (!valueContent.firstChild) {
				template = this._getTemplate("default");
				if (template) {
					valueContent.appendChild(this._cloneTemplate(template));
				}
			}
		};

		ColorEditor.prototype.open = function() {
			var list;
			ColorEditor.__super__.open.call(this);
			list = this._list;
			if (list && this._currentItem !== list.get("currentItem")) {
				list.set("currentItem", this._currentItem);
			}
		};

		ColorEditor.prototype._getDropdownContent = function() {
			var attrBinding, list, template, templateName;
			if (!this._dropdownContent) {
				templateName = "list";
				template = this._getTemplate(templateName);
				this._dropdownContent = template = cola.xRender(template, this._scope);
				this._list = list = cola.widget(this._doms.list);
				list._regTemplate("default", {
					tagName: "li",
					content: [
						{
							tagName: "div",
							"c-code": "$default.code",
							"class": "color"
						}, {
							tagName: "div",
							"c-bind": "$default.label",
							"class": "label"
						}
					]
				});
				list.on("itemClick", (function(_this) {
					return function() {
						return _this.close(list.get("currentItem"));
					};
				})(this));
				list.on("renderItem", function(self, arg) {
					return $(arg.dom).find(".color").css("background-color", arg.item.get("code"));
				});
			}
			attrBinding = this._elementAttrBindings["items"];
			list = this._list;
			if (attrBinding) {
				list.set("bind", attrBinding.expression.raw);
			} else {
				list.set("items", this._items);
			}
			list.refresh();
			list.get$Dom().addClass("color-list");
			return template;
		};

		return ColorEditor;

	})(cola.AbstractDropdown);

	cola.registerWidget(cola.ColorEditor);

	cola.registerWidget(cola.ColorGrid);

}).call(this);

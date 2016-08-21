define([ 'http', 'codetable-data' ], function(http, data) {

	var ret = {
		// 重新绑定和整理数据
		__rebind : function() {
			// 构建客户端的数据结构
			var obj = {};
			for (_$i in data) {
				var cate = data[_$i];
				obj[cate.category] = cate;
			}
			this.__data = obj;
		},
		__original : data,
		// 当前的字典表数据
		__data : undefined,
		/**
		 * 获得当前可用的字典表分类列表
		 */
		list : function() {
			return this.__original;
		},
		getCategory : function(categoryName) {
			return this.__data[categoryName];
		},
		getCode : function(categoryName, codeName) {
			var cate = this.getCategory(categoryName);
			if (cate != undefined && cate.codes != undefined) {
				for (__$i in cate.codes) {
					var c = cate.codes[i];
					if (c != undefined && c.value == codeName) {
						return c;
					}
				}
			}
			return undefined;
		},
		updateCode : function(code, opt) {

			if (code != undefined) {
				http.load({
					url : "/api/codetable/code/update",
					data : {
						jsonStr : JSON.stringify(code)
					},
					context : this,
					success : function(ret) {
						var forCategory = code.category.category;
						var toCategory = undefined;

						for (_$i in this.context.__original) {
							var cate = this.context.__original[_$i];
							if (cate.category == forCategory) {
								toCategory = cate;
							}
						}
						if (toCategory == undefined) {
							// 没有找到类别
							return;
						}

						if (ret.isSuccess) {
							// 更新原始数据
							if (code.id != undefined) {
								// 编辑
								for (i in toCategory.codes) {
									var c = toCategory.codes[i];
									if (c.id == ret.object.id) {
										c.value = ret.object.value;
										c.desc = ret.object.desc;
									}
								}
							} else {
								// 新增
								if (toCategory.codes == undefined) {
									toCategory.codes = new Array();
								}
								toCategory.codes.push(ret.object);
							}
						}

						this.context.__rebind();
						
						if (opt && opt.success) {
							opt.success(ret);
						}
					}
				});
			}
		},
		removeCode : function(data, opt) {
			http.load({
				url : "/api/codetable/code/remove",
				data : data,
				context : this,
				success : function(ret) {
					if (ret.isSuccess) {
						// 删除成功
						for (_$i in this.context.__original) {
							var cate = this.context.__original[_$i];
							if (cate.id == data.categoryId) {
								// 找到对应的类别
								for (_$ci in cate.codes) {
									var c = cate.codes[_$ci];
									if (c.id == data.codeId) {
										cate.codes.splice(_$ci, 1);
									}
								}
							}
						}

						this.context.__rebind();

						// 调用回调函数
						if (opt && opt.success) {
							opt.success(ret);
						}

					} else {

					}
				}
			});
		}
	}

	ret.__rebind();

	return ret;
});